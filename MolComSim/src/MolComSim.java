
import java.io.*;
import java.util.*;

public class MolComSim {

	//Parameters for this simulation instance and a reader for it
	private FileReader paramsFile;
	private SimulationParams simParams;
	private FileWriter outputFile = null;
	
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
	private int numMessages;
	
	//This instance of the Molecular Communication Simulation
	static MolComSim molComSim;

	
	/** Creates a singleton instance of MolComSim
	 *  and runs the simulation according to input 
	 *  parameters
	 *  
	 *  @param args Should be a parameter file
	 * 
	 */
	public static void main(String[] args) throws IOException {
		MolComSim molComSim = createInstance();
		molComSim.run(args);
	}

	/** Begins simulation with the parameter arguments
	 *  and sets flags simStep and lasMsgCompleted
	 * 
	 * @param args Should be a parameter file
	 */
	private void startSim(String[] args) throws IOException {
		simStep = 0;
		lastMsgCompleted = false;
		simParams = new SimulationParams(args);
		if(simParams.getOutputFileName() != null) {
			outputFile = new FileWriter(simParams.getOutputFileName());
		}
		//TODO: Where is the appropriate place for these to be initialized?
		microtubules = new ArrayList<Microtubule>();
		nanoMachines = new ArrayList<NanoMachine>();
		transmitters = new ArrayList<NanoMachine>();
		receivers = new ArrayList<NanoMachine>();
		molecules = new ArrayList<Molecule>();
		createMicrotubules(); 
		createMedium();
		createNanoMachines();
		//createMicrotubules();		
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
	private void run(String[] args)  throws IOException {
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
			collectGarbage();
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
			nanoMachines.add(nm);
		}
		for (Position p : simParams.getReceiverPositions()){
			NanoMachine nm = NanoMachine.createReceiver(p, recRadius, ackParams, this);
			receivers.add(nm);
			nanoMachines.add(nm);			
		}
	}

	private void createMicrotubules() {
		//		get microtubule params from simParams
		for(MicrotubuleParams mtps : simParams.getMicrotubuleParams()) {
			Position start = mtps.getStartPoint();
			Position end = mtps.getEndPoint();
			double radius = mtps.getRadius();
			
			Microtubule tempMT = new Microtubule(start, end, radius, this);
			microtubules.add(tempMT);
		}
	}

	//any cleanup tasks, including printing simulation results to monitor or file.
	private void endSim() throws IOException {
		String endMessage = "Ending simulation: Last step: " + simStep + "\n";
		if(messagesCompleted < simParams.getNumMessages()){
			endMessage += "Total messages completed: " + messagesCompleted + 
					" out of " + simParams.getNumMessages() + "\n";
		} else {
			endMessage += "All " + simParams.getNumMessages() + " messages completed.\n";
		}
		
		System.out.print(endMessage);
		if(outputFile != null) {
			try {
				outputFile.write(endMessage);
			} catch (IOException e) {
				System.out.println("Error: unable to write to file: " + simParams.getOutputFileName());
				e.printStackTrace();
			}
		}

		if(outputFile != null) {
			outputFile.close();
		}
	}

	/** Add molecules to molecules list field
	 * 
	 * @param mols List of molecules to add to simulation list
	 */
	public void addMolecules(ArrayList<Molecule> mols) {
		this.molecules.addAll(mols);
		for (Molecule mol : mols){
			addObject(mol, mol.getPosition());
		}
	}

	public void completedMessage(int msgNum) {
		messagesCompleted = msgNum;
		String completedMessage = "Completed message: " + msgNum + ", at step: " + simStep + "\n";
		if(msgNum >= simParams.getNumMessages()){
			lastMsgCompleted = true;
			completedMessage += "Last message completed.\n";
		}
		System.out.print(completedMessage);
		if(outputFile != null) {
			try {
				outputFile.write(completedMessage);
			} catch (IOException e) {
				System.out.println("Error: unable to write to file: " + simParams.getOutputFileName());
				e.printStackTrace();
			}
		}
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

	public int getNumRetransmissions() {
		// Is this the correct number?
		return simParams.getNumRetransmissions();
	}
	
	public boolean isUsingCollisions() {
		return simParams.isUsingCollisions();
	}
	
	public int getRetransmitWaitTime(){
		return simParams.getRetransmitWaitTime();
	}
	
	public void addObject(Object obj, Position pos){
		medium.addObject(obj, pos);
	}
	
	public void moveObject(Object obj, Position oldPos, Position newPos){
		medium.moveObject(obj, oldPos, newPos);
	}
	
	public boolean isOccupied(Position pos){
		return medium.isOccupied(pos);
	}
	
	public void collectGarbage(){
		ArrayList<Object> garbage = medium.getObjectsAtPos(medium.garbageSpot());
		medium.collectGarbage();
		for (Object o : garbage){
			molecules.remove(o);
		}
	}


}
