/**
 * The NanoMachine class represents a molecular machine
 * that can transmit information molecules and receive
 * acknowledgement molecules, transmit acknowledgement
 * molecules and receive information molecules, or both.
 * 
 */

import java.io.IOException;
import java.util.*;

public class NanoMachine {
	
	private Position position;
	private int radius;
	private MolComSim simulation;
	private Receiver rx;
	private Transmitter tx;
	// These are to track communication status for adaptive change
	public static final int LAST_COMMUNICATION_SUCCESS = 1;
	public static final int LAST_COMMUNICATION_FAILURE = -1;
	public static final int NO_PREVIOUS_COMMUNICATION = 0;

	private NanoMachine(Position psn, int r) {
		this.position = psn;
		this.radius = r;
	}

	/** Make this NanoMachine a Transmitter only
	 * 
	 * @param position Where the NanoMachine is located
	 * @param radius The radius of the NanoMachine
	 * @param molReleasePsn Position inside the transmitter at which point new molecules are released (created) 
	 * @param mpl The parameters for the molecules the Nanomachine will transmit
	 * @param sim The simulation in which this is taking place
	 * @return The resulting transmitter-only nanomachine
	 */
	public static NanoMachine createTransmitter(Position position, int radius, Position molReleasePsn, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		NanoMachine retVal = new NanoMachine(position, radius);
		retVal.tx = new Transmitter(retVal, molReleasePsn, mpl, sim);
		retVal.rx = null;
		return retVal;
	}

	/** Make this NanoMachine a Receiver only
	 * 
	 * @param position Where the NanoMachine is located
	 * @param radius The radius of the NanoMachine
	 * @param molReleasePsn Position inside the transmitter at which point new molecules are released (created) 
	 * @param mpl The parameters for the molecules the Nanomachine will receive
	 * @param sim The simulation in which this is taking place
	 * @return The resulting receiver-only nanomachine
	 */
	public static NanoMachine createReceiver(Position position, int radius, Position molReleasePsn, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		NanoMachine retVal = new NanoMachine(position, radius);
		retVal.rx = new Receiver(retVal, molReleasePsn, mpl, sim);
		retVal.tx = null;
		return retVal;
	}

	/** Make this NanoMachine a Transmitter and Receiver
	 * 
	 * @param position Where the NanoMachine is located
	 * @param radius The radius of the NanoMachine
	 * @param infoMolReleasePsn Position inside the transmitter at which point new info molecules are released (created) 
	 * @param ackMolReleasePsn Position inside the transmitter at which point new acko molecules are released (created) 
	 * @param mpl The parameters for the molecules the Nanomachine will transmit and receive
	 * @param ackParams 
	 * @param sim The simulation in which this is taking place
	 * @return The resulting transmitter-receiver nanomachine
	 */
	public static NanoMachine createIntermediateNode(Position position, int radius, 
			Position infoMolReleasePsn, Position ackMolReleasePsn, ArrayList<MoleculeParams> mpl, 
			ArrayList<MoleculeParams> ackParams, MolComSim sim) {
		NanoMachine retVal = new NanoMachine(position, radius);
		retVal.rx = new Receiver(retVal, ackMolReleasePsn, mpl, sim);
		retVal.tx = new Transmitter(retVal, infoMolReleasePsn, mpl, sim);
		retVal.tx.setNumRetransmissions(0);
		retVal.rx.setNumRetransmissions(0);
		return retVal;	
	}

	/**
	 * Creates information molecules originating at this
	 * NanoMachine's transmitter, if it has one
	 */
	public void createInfoMolecules() {
		if(tx != null) {
			tx.createMolecules();
		}
	}

	/**
	 * Calls next step for transmitter
	 * and/or receiver 
	 */
	public void nextStep() {
		if(tx != null) {
			tx.nextStep();
		}
		if(rx != null) {
			rx.nextStep();
		}
	}

	/** Receives molecule by either transmitter or receiver,
	 *  depending on type of molecule
	 * 
	 * @param m Molecule being received
	 */
	public void receiveMolecule(Molecule m) {
		if(rx != null && tx != null) { // intermediate node, can receive any molecule, but uses special routines to do so.
			if(m instanceof InformationMolecule) {
				tx.retransmit(m);
			} else if(m instanceof AcknowledgementMolecule) {
				rx.retransmit(m);
			}
		} else if(m instanceof InformationMolecule && rx != null) {
			rx.receiveMolecule(m);
		} 
		else if(m instanceof AcknowledgementMolecule && tx != null) {
			tx.receiveMolecule(m);
		}
	}

	public Position getPosition() {
		return position;
	}

	public int getRadius() {
		return radius;
	}

	public MolComSim getSimulation() {
		return simulation;
	}
	
	public int getTransmitterMessageId(){
		if (tx != null)
			return tx.getCurrMsgId();
		return -1;
	}
	
	public int getReceiverMessageId(){
		if (rx != null)
			return rx.getCurrMsgId();
		return -1;
	}
	

	public boolean hasReceiver() {
		return rx != null;
	}

	public boolean hasTransmitter() {
		return tx != null;
	}

	/**
	 * Inner class that enables NanoMachine to transmit
	 *  information molecules
	 *
	 */
	public static class Transmitter {

		private MolComSim simulation;
		private int currMsgId = 1;
		private int retransmissionsLeft;
		private MoleculeCreator moleculeCreator;
		private NanoMachine nanoMachine;
		private int countdown;
		private boolean createMoleculesDelayed = false;
		private Position molReleasePsn;
		
		// for intermediate nodes, track the messages we have retransmitted so we 
		// do not retransmit the same message multiple times.
		private ArrayList<Integer> msgsRetransmitted = new ArrayList<>();
		
		// To track communication status for adaptive change
		private int lastCommunicationStatus = NO_PREVIOUS_COMMUNICATION; // TODO: should really be an enumerated type

		public Transmitter(NanoMachine nm, Position molReleasePsn, ArrayList<MoleculeParams> mpl, MolComSim sim) {
			this.molReleasePsn = molReleasePsn;
			this.nanoMachine = nm;
			this.simulation = sim;
			this.moleculeCreator = new MoleculeCreator(mpl, this.simulation, this.nanoMachine, this.molReleasePsn);
			this.retransmissionsLeft =  this.simulation.getNumRetransmissions();
		}

		// in order to have intermediate nodes no retransmit multiple times for each molecule.
		public void setNumRetransmissions(int retransmission) {
			retransmissionsLeft = retransmission;
		}

		/**
		 *  Creates molecules for this transmitter
		 */
		public void createMolecules() {
			moleculeCreator.createMolecules(lastCommunicationStatus);
			countdown = simulation.getRetransmitWaitTime();
		}

		/**
		 * Creates molecules if time hasn't run out
		 */
		public void nextStep() {
			if(createMoleculesDelayed) {
				createMolecules();
				createMoleculesDelayed = false;
			} else if(countdown-- <= 0) {
				if(simulation.isUsingAcknowledgements()) {
					lastCommunicationStatus = LAST_COMMUNICATION_FAILURE;
					if(retransmissionsLeft-- > 0) {
						createMolecules();
					} 
				} else { // time to send out new molecules, not using acknowledgements,
					    // so start new message.
					lastCommunicationStatus = LAST_COMMUNICATION_SUCCESS;
					if (currMsgId < simulation.getNumMessages()) {
						++currMsgId;
						if(!getSimulation().getSimParams().isBatchRun()) {
							String newMessageMessage = "Starting new message: " + currMsgId + " at step: " + 
									getSimulation().getSimStep() + "\n";
							System.out.print(newMessageMessage);
							try {
								getSimulation().getOutputFile().write(newMessageMessage);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					createMolecules();
				}
			} 
		}

		// Receives and retransmits a molecule for multi-hop/signal boosted communications.
		// Only called for intermediate nodes.
		public void retransmit(Molecule m) {
			// don't change communication status because we always want to send out the same number
			// of molecules from an intermediate node.
			if(!simulation.isLastMsgCompleted()) {
				currMsgId = m.getMsgId();
				if(msgsRetransmitted.contains(currMsgId))
					return; // don't retransmit the same message again.
				msgsRetransmitted.add(currMsgId);
				createMoleculesDelayed = true;  
				// Need to remove received molecules from the simulation.
				simulation.moveObject(m, m.getPosition(), simulation.getMedium().garbageSpot());			
			}
		}
		
		/**
		 * Receive molecule and tell simulation this message has been received,
		 * create more molecules if needed
		 * 
		 * @param m Molecule being received
		 */
		public void receiveMolecule(Molecule m) {
			if(m.getMsgId() == currMsgId) {
				lastCommunicationStatus = LAST_COMMUNICATION_SUCCESS;
				simulation.completedMessage(currMsgId++);
				if(!simulation.isLastMsgCompleted()) {
					createMoleculesDelayed = true;  
					retransmissionsLeft = simulation.getNumRetransmissions();
				}
			} else if(retransmissionsLeft-- > 0){
				lastCommunicationStatus = LAST_COMMUNICATION_FAILURE;
				createMoleculesDelayed = true;
			}			
			// Need to remove received molecules from the simulation.
			simulation.moveObject(m, m.getPosition(), simulation.getMedium().garbageSpot());
		}

		public NanoMachine getNanoMachine() {
			return nanoMachine;
		}

		public MolComSim getSimulation() {
			return simulation;
		}

		public int getCurrMsgId() {
			return currMsgId;
		}

	}

	public static class Receiver {

		private MolComSim simulation;
		private int currMsgId;
		private int retransmissionsLeft;
		private MoleculeCreator moleculeCreator;
		private NanoMachine nanoMachine;
		private int countdown;
		private boolean createMoleculesDelayed = false;
		private Position molReleasePsn;
		private boolean neverReceivedAnyInfoMols = true; // Prevent receiver from timing out and sending acknowledgements
														// before receiving anything.

		// for intermediate nodes, track the messages we have retransmitted so we 
		// do not retransmit the same message multiple times.
		private ArrayList<Integer> msgsRetransmitted = new ArrayList<>();

		// To track communication status for adaptive change
		private int lastCommunicationStatus = NO_PREVIOUS_COMMUNICATION;

		public Receiver(NanoMachine nm, Position molReleasePsn, ArrayList<MoleculeParams> mpl, MolComSim sim) {
			this.molReleasePsn = molReleasePsn;
			this.nanoMachine = nm;
			this.simulation = sim;
			if(this.simulation.isUsingAcknowledgements())
			{
				this.moleculeCreator = new MoleculeCreator(mpl, simulation, nanoMachine, molReleasePsn);
				currMsgId = 0;
				retransmissionsLeft =  this.simulation.getNumRetransmissions();
			}
		}

		// in order to have intermediate nodes no retransmit multiple times for each molecule.
		public void setNumRetransmissions(int retransmission) {
			retransmissionsLeft = retransmission;
		}

		/**
		 *  Creates molecules for this receiver
		 */
		public void createMolecules() {
			moleculeCreator.createMolecules(lastCommunicationStatus);
			countdown = simulation.getRetransmitWaitTime();
		}
		
		/**
		 * Creates acknowledgment molecules if needed by
		 * this simulation and time hasn't run out
		 */
		public void nextStep() {
			if(createMoleculesDelayed) {
				createMolecules();
				createMoleculesDelayed = false;
			} else if(simulation.isUsingAcknowledgements() && !neverReceivedAnyInfoMols && 
			((countdown-- <= 0) && (retransmissionsLeft-- > 0))){
				lastCommunicationStatus = LAST_COMMUNICATION_FAILURE;
				createMolecules();
			} 
		}

		/**
		 * Receive molecule and tell simulation this message has been received,
		 * create more molecules if needed
		 * 
		 * @param m Molecule being received
		 */
		public void receiveMolecule(Molecule m) {
			neverReceivedAnyInfoMols = false; // we have received at least one information molecule
			if(m.getMsgId() == currMsgId + 1){
				currMsgId++;		
				lastCommunicationStatus = LAST_COMMUNICATION_SUCCESS;
				if(simulation.isUsingAcknowledgements()) {
					createMoleculesDelayed = true;
					retransmissionsLeft =  simulation.getNumRetransmissions();
				} 
				else {
					simulation.completedMessage(currMsgId);
				}
			}
			else if (simulation.isUsingAcknowledgements() && (retransmissionsLeft-- > 0)) {
				lastCommunicationStatus = LAST_COMMUNICATION_FAILURE;
				createMoleculesDelayed = true;
			}
			// Need to remove received molecules from the simulation.
			simulation.moveObject(m, m.getPosition(), simulation.getMedium().garbageSpot());
		}

		// Receives and retransmits a molecule for multi-hop/signal boosted communications.
		// Only called for intermediate nodes.
		public void retransmit(Molecule m) {
			// don't change communication status because we always want to send out the same number
			// of molecules from an intermediate node.
			if(!simulation.isLastMsgCompleted()) {
				currMsgId = m.getMsgId();
				if(msgsRetransmitted.contains(currMsgId))
					return; // don't retransmit the same message again.
				msgsRetransmitted.add(currMsgId);
				createMoleculesDelayed = true;  
				// Need to remove received molecules from the simulation.
				simulation.moveObject(m, m.getPosition(), simulation.getMedium().garbageSpot());			
			}
		}
		
		public NanoMachine getNanoMachine() {
			return nanoMachine;
		}

		public MolComSim getSimulation() {
			return simulation;
		}

		public int getCurrMsgId() {
			return currMsgId;
		}

	}

}
