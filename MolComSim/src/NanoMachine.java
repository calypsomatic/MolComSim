/**
 * The NanoMachine class represents a molecular machine
 * that can transmit information molecules and receive
 * acknowledgement molecules, transmit acknowledgement
 * molecules and receive information molecules, or both.
 * 
 */

import java.util.*;

public class NanoMachine {
	
	private Position position;
	private double radius;
	private MolComSim simulation;
	private Receiver rx;
	private Transmitter tx;

	private NanoMachine(Position psn, double r) {
		this.position = psn;
		this.radius = r;
	}

	/** Make this NanoMachine a Transmitter only
	 * 
	 * @param position Where the NanoMachine is located
	 * @param radius The radius of the NanoMachine
	 * @param mpl The parameters for the molecules the Nanomachine will transmit
	 * @param sim The simulation in which this is taking place
	 * @return The resulting transmitter-only nanomachine
	 */
	public static NanoMachine createTransmitter(Position position, double radius, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		NanoMachine retVal = new NanoMachine(position, radius);
		retVal.tx = new Transmitter(retVal, mpl, sim);
		retVal.rx = null;
		return retVal;
	}

	/** Make this NanoMachine a Receiver only
	 * 
	 * @param position Where the NanoMachine is located
	 * @param radius The radius of the NanoMachine
	 * @param mpl The parameters for the molecules the Nanomachine will receive
	 * @param sim The simulation in which this is taking place
	 * @return The resulting receiver-only nanomachine
	 */
	public static NanoMachine createReceiver(Position position, double radius, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		NanoMachine retVal = new NanoMachine(position, radius);
		retVal.rx = new Receiver(retVal, mpl, sim);
		retVal.tx = null;
		return retVal;
	}

	/** Make this NanoMachine a Transmitter and Receiver
	 * 
	 * @param position Where the NanoMachine is located
	 * @param radius The radius of the NanoMachine
	 * @param mpl The parameters for the molecules the Nanomachine will transmit and receive
	 * @param sim The simulation in which this is taking place
	 * @return The resulting transmitter-receiver nanomachine
	 */
	public static NanoMachine createIntermediateNode(Position position, double radius, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		NanoMachine retVal = new NanoMachine(position, radius);
		retVal.rx = new Receiver(retVal, mpl, sim);
		retVal.tx = new Transmitter(retVal, mpl, sim);
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
		if(m instanceof InformationMolecule && rx != null) {
			rx.receiveMolecule(m);
		} 
		else if(m instanceof AcknowledgementMolecule && tx != null) {
			tx.receiveMolecule(m);
		}
	}
	
	/**
	 * @return the latest message ID sent from the transmitter
	 *  -1 if this nanomachine is not a transmitter
	 */
	public int getTransmitterMessageId(){
		if (tx != null)
			return tx.getCurrMsgId();
		return -1;
	}
	
	/**
	 * @return the latest message ID received at the receiver
	 *  -1 if this nanomachine is not a receiver
	 */
	public int getReceiverMessageId(){
		if (rx != null)
			return rx.getCurrMsgId();
		return -1;
	}

	public Position getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public MolComSim getSimulation() {
		return simulation;
	}
	

	

	/**
	 * Inner class that enables NanoMachine to transmit
	 *  information molecules
	 *
	 */
	public static class Transmitter {

		private MolComSim simulation;
		// The message ID of the last message sent out
		private int currMsgId;
		private int retransmissionsLeft;
		private MoleculeCreator moleculeCreator;
		private NanoMachine nanoMachine;
		private int countdown;

		public Transmitter(NanoMachine nm, ArrayList<MoleculeParams> mpl, MolComSim sim) {
			this.nanoMachine = nm;
			this.simulation = sim;
			this.moleculeCreator = new MoleculeCreator(mpl, this.simulation, this.nanoMachine);
			this.currMsgId = 0;
			this.retransmissionsLeft =  this.simulation.getNumRetransmissions();
		}

		/**
		 *  Creates molecules for this transmitter
		 */
		public void createMolecules() {
			moleculeCreator.createMolecules();
			countdown = simulation.getRetransmitWaitTime();
		}

		/**
		 * Creates molecules if time has run out and there are
		 * retransmissions left
		 */
		public void nextStep() {
			if((countdown-- <= 0) && (retransmissionsLeft > 0)) {
				System.out.println("Resending molecules\n");
				createMolecules();
				retransmissionsLeft--;
			} 
		}

		/**
		 * Receive molecule and tell simulation this message has been received,
		 * create more molecules if needed
		 * 
		 * @param m Molecule being received
		 */
		public void receiveMolecule(Molecule m) {
			//If an acknowledgement for the last message is received,
			//that message is completed.  Increment message ID
			if(m.getMsgId() == currMsgId){
				System.out.println("Message " + currMsgId + " received\n");
				simulation.completedMessage(currMsgId++);
			
				//If there are more messages left to send, create
				//molecules with the next message ID and reset retransmissions
				if(currMsgId < simulation.getNumMessages()) {
					createMolecules();
					retransmissionsLeft =  simulation.getNumRetransmissions();
				}
			}
			//If message is not complete and we haven't run out of retransmissions,
			//send out more molecules with same message ID and decrement retransmissions
			else if (retransmissionsLeft > 0) {
				createMolecules();
				retransmissionsLeft--;
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

	public static class Receiver {

		private MolComSim simulation;
		//The latest message ID received
		private int currMsgId;
		private int retransmissionsLeft;
		private MoleculeCreator moleculeCreator;
		private NanoMachine nanoMachine;
		private int countdown;

		public Receiver(NanoMachine nm, ArrayList<MoleculeParams> mpl, MolComSim sim) {
			this.nanoMachine = nm;
			this.simulation = sim;
			currMsgId = 0;
			
			//Receivers can only create molecules if the simluation is using acknowledgements
			if(this.simulation.isUsingAcknowledgements())
			{
				this.moleculeCreator = new MoleculeCreator(mpl, simulation, nanoMachine);
				retransmissionsLeft = this.simulation.getNumRetransmissions();
			}
		}

		/**
		 *  Creates molecules for this receiver
		 */
		public void createMolecules() {
			moleculeCreator.createMolecules();
			countdown = simulation.getRetransmitWaitTime();
		}
		
		/**
		 * Creates acknowledgment molecules if needed by
		 * this simulation and time has run out
		 */
		public void nextStep() {
			if(simulation.isUsingAcknowledgements() && 
			((countdown-- <= 0) && (retransmissionsLeft > 0))){
				createMolecules();
				retransmissionsLeft--;
			} 
		}

		/**
		 * Receive molecule and tell simulation this message has been received,
		 * create more molecules if needed
		 * 
		 * @param m Molecule being received
		 */
		public void receiveMolecule(Molecule m) {
			//If this message is the next one, increment latest received id
			if(m.getMsgId() == currMsgId + 1){
				currMsgId++;
				System.out.println("Message " + currMsgId + " received\n");
				
				//Send out acknowledgement molecules if simulation calls for it
				if(simulation.isUsingAcknowledgements()) {
					System.out.println("Sending acknowledgement\n");
					createMolecules();
					retransmissionsLeft =  simulation.getNumRetransmissions();
				} 
				//Otherwise mark message as completed
				else {
					simulation.completedMessage(currMsgId);
				}
			}
			//If this is not the message we're waiting for, resend last acknowledgement
			else if (simulation.isUsingAcknowledgements() && (retransmissionsLeft > 0)) {
				createMolecules();
				retransmissionsLeft--;
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
