//package MComSim.MolComSim;

import java.io.*;
import java.util.*;

public class MolComSim {

	//Parameters for this simulation instance and a reader for it
	private FileReader paramsFile;
	private SimulationParams simParams;
	
	//Collections of all the actors in this simulation
	private ArrayList<Microtubule> microtubules;
	private ArrayList<NanoMachine> nanoMachines;
	private ArrayList<NanoMachine> transmitters;
	private ArrayList<NanoMachine> receivers;
	private ArrayList<Molecule> molecules;

	//The medium in which the simulation takes place
	private Medium medium;

	//Max number of steps to allow in the simulation
	private int simStep;

	//Keeping track of messages sent and received
	//to identify when simulation completed 
	private int messagesCompleted;
	private boolean lastMsgCompleted;

	//This instance of the Molecular Communication Simulation
	static MolComSim molComSim;

	
	/** Creates a singleton instance of MolComSim
	 *  and runs the simulation according to input 
	 *  parameters
	 *  
	 *  @param args Should be a parameter file
	 * 
	 */
	public static void main(String[] args) {
		MolComSim molComSim = createInstance();
		molComSim.run(args);
	}

	/** Begins simulation with the parameter arguments
	 *  and sets flags simStep and lasMsgCompleted
	 * 
	 * @param args Should be a parameter file
	 */
	private void startSim(String[] args) {
		simStep = 0;
		lastMsgCompleted = false;
		simParams = new SimulationParams(args);
		createMedium();
		createNanoMachines();
		createMicrotubules();		
		// Note: it is the job of the medium and NanoMachines to create molecules
	}

	/** Makes sure there is only one instance of MolComSim
	 * 
	 * @return the only instance of MolComSim
	 */
	public static MolComSim createInstance() {
		if(molComSim == null){
			molComSim = new MolComSim();
		}
		return molComSim;
	}
	
	/** Runs the simulation according to given parameters
	 *  Moves each molecule and nanomachine for each time
	 *  step in the simulation 
	 * 
	 * @param args Should be a parameter file
	 */
	private void run(String[] args) {
		startSim(args);
		//As long as we have not run for too long and have not
		//yet finished sending our messages, move the simulation forward
		for(; (simStep < simParams.getMaxNumSteps()) && (!lastMsgCompleted); simStep++) 
		{
			for(NanoMachine nm : nanoMachines){
				nm.nextStep();
			}
			for(Molecule m : molecules){	
				m.move();
			}
		}
		endSim();
	}

	public int getSimStep() {
		return simStep;
	}

	public boolean isLastMsgCompleted() {
		return lastMsgCompleted;
	}
	
	public int getNumMessages(){
		return simParams.getNumMessages();
	}

	/** Creates the medium in which the simulation takes place
	 *  and places noise molecules inside it
	 * 
	 */
	private void createMedium() {
		//get Medium params, NoiseMolecule params from simParams
		double medLength = simParams.getMediumLength();
		double medHeight = simParams.getMediumHeight();
		double medWidth = simParams.getMediumWidth();
		ArrayList<MoleculeParams> nMParams = simParams.getNoiseMoleculeParams();
		medium = new Medium(medLength, medHeight, medWidth, nMParams, this);
		medium.createMolecules();
	}
	

	/** Creates all nanomachines needed for the simulation
	 *  Each nanomachine creates its own information or
	 *  acknowledgment molecules
	 * 
	 */
	private void createNanoMachines() {
		//TODO: Implement intermediate nodes that are both transmitters
		// and receivers for multi-hop communication 
		double transRadius = simParams.getTransmitterRadius();
		double recRadius = simParams.getReceiverRadius();
		ArrayList<MoleculeParams> ackParams = simParams.getAcknowledgmentMoleculeParams();
		ArrayList<MoleculeParams> infoParams = simParams.getInformationMoleculeParams();
		for (Position p : simParams.getTransmitterPositions()){
			NanoMachine nm = NanoMachine.createTransmitter(p, transRadius, infoParams, this);
			nm.createInfoMolecules();
			transmitters.add(nm);
		}
		for (Position p : simParams.getReceiverPositions()){
			NanoMachine nm = NanoMachine.createReceiver(p, recRadius, ackParams, this);
			transmitters.add(nm);
			
		}
	}

	private void createMicrotubules() {
		//Should probably maybe have an ArrayList of pairs of plus/minus ends
		/*for(each MicroTubule specified by simParams)
	{
		get microtubule params from simParams
		construct a temporary microtubule, passing it the microtubule params and this simulation object
		microTubuleList.add(tempMicroTubule);
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	//any cleanup tasks, including printing simulation results to monitor or file.
	private void endSim() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	/** Add molecules to molecules list field
	 * 
	 * @param mols List of molecules to add to simulation list
	 */
	public void addMolecules(ArrayList<Molecule> mols) {
		this.molecules.addAll(mols);
	}

	public void completedMessage(int msgNum) {
		/*possibly print results to file and/or monitor
	if(msgId >= numMsgs – 1)
	{
		lastMsgCompleted = true;
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getMessagesCompleted() {
		return messagesCompleted;
	}

	public SimulationParams getSimParams() {
		return simParams;
	}

	public ArrayList<Molecule> getMolecules() {
		return molecules;
	}

	public ArrayList<Microtubule> getMicrotubules() {
		return microtubules;
	}

	public ArrayList<NanoMachine> getNanoMachines() {
		return nanoMachines;
	}

	public Medium getMedium() {
		return medium;
	}

	public ArrayList<NanoMachine> getReceivers() {
		return receivers;
	}

	public ArrayList<NanoMachine> getTransmitters() {
		return transmitters;
	}

	public boolean isUsingAcknowledgements() {
		return simParams.isUsingAcknowledgements();
	}

	public int getMaxRetransmissions() {
		// Is this the correct number?
		return simParams.getNumRetransmissions();
	}
	
	public boolean isUsingCollisions() {
		return simParams.isUsingCollisions();
	}

}
