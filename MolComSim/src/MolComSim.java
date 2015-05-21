
import java.io.*;
import java.util.*;

public class MolComSim {

	//Parameters for this simulation instance and a reader for it
	private FileReader paramsFile;
	private SimulationParams simParams;
	private FileWriter outputFile = null;
	private static final boolean APPEND_TO_FILE = true; // used to set the append field for FileWriter to write out to the
							// same file as other simulations during a batch run.
	
	//Collections of all the actors in this simulation
	private ArrayList<Microtubule> microtubules;
	private ArrayList<NanoMachine> nanoMachines;
	private ArrayList<NanoMachine> transmitters;
	private ArrayList<NanoMachine> receivers;
	private ArrayList<Molecule> movingMolecules; 

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
		if((simParams.getOutputFileName() != null) && (!simParams.isBatchRun())) {
			outputFile = new FileWriter(simParams.getOutputFileName());
		}
		microtubules = new ArrayList<Microtubule>();
		nanoMachines = new ArrayList<NanoMachine>();
		transmitters = new ArrayList<NanoMachine>();
		receivers = new ArrayList<NanoMachine>();
		movingMolecules = new ArrayList<Molecule>();
		createMedium();
		createMicrotubules(); 
		createNanoMachines();	
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
			for(Molecule m : movingMolecules){
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
		int medLength = simParams.getMediumLength();
		int medHeight = simParams.getMediumHeight();
		int medWidth = simParams.getMediumWidth();
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
		ArrayList<MoleculeParams> ackParams = simParams.getAcknowledgmentMoleculeParams();
		ArrayList<MoleculeParams> infoParams = simParams.getInformationMoleculeParams();
		for (NanoMachineParam nmp : simParams.getTransmitterParams()){
			NanoMachine nm = NanoMachine.createTransmitter(nmp.getCenter(), nmp.getRadius(), nmp.getMolReleasePoint(), infoParams, this);
			growNanoMachine(nm); // adds NanoMachine to medium's grid
			nm.createInfoMolecules();
			transmitters.add(nm);
			nanoMachines.add(nm);
		}
		for (NanoMachineParam nmp : simParams.getReceiverParams()) {
			NanoMachine nm = NanoMachine.createReceiver(nmp.getCenter(), nmp.getRadius(), nmp.getMolReleasePoint(), ackParams, this);
			growNanoMachine(nm); // adds NanoMachine to medium's grid
			receivers.add(nm);
			nanoMachines.add(nm);			
		}
		for (IntermediateNodeParam inp : simParams.getIntermediateNodeParams()) {
			NanoMachine nm = NanoMachine.createIntermediateNode(inp.getCenter(), inp.getRadius(), 
					inp.getInfoMolReleasePoint(), inp.getAckMolReleasePoint(), infoParams, ackParams, this);
			growNanoMachine(nm); // adds NanoMachine to medium's grid
			transmitters.add(nm);
			receivers.add(nm);
			nanoMachines.add(nm);			
		}
	}

	// Adds nanoMachine to medium's grid throughout its entire volume. 
	private void growNanoMachine(NanoMachine nm) {
		Position center = nm.getPosition();
		int radius = nm.getRadius();
		// doubly nested loop to go over positions for all three dimensions.
		// note that the center position is included, so we subtract one 
		// from the radius, and go from -(radius - 1) to +(radius - 1) units 
		// around the center point in all directions.
		int startX = center.getX() - (radius - 1);
		int endX = center.getX() + (radius - 1);
		int startY = center.getY() - (radius - 1);
		int endY = center.getY() + (radius - 1);
		int startZ = center.getZ() - (radius - 1);
		int endZ = center.getZ() + (radius - 1);
		for(int x = startX; x <= endX; x++) {
			for(int y = startY; y <= endY; y++) {
				for(int z = startZ; z <= endZ; z++) {
					addObject(nm, new Position(x, y, z));
				}
			}
		}
	}
	
	private void createMicrotubules() {
		//		get microtubule params from simParams
		for(MicrotubuleParams mtps : simParams.getMicrotubuleParams()) {
			Position start = mtps.getStartPoint();
			Position end = mtps.getEndPoint();			
			Microtubule tempMT = new Microtubule(start, end, this);
			growMicrotubule(tempMT);
			microtubules.add(tempMT);
		}
	}

	//Adds microtubule to medium's grid all along its length
	private void growMicrotubule(Microtubule tempMT){  
		//Collect all positions the microtubule occupies
		HashSet<Position> mtPos = new HashSet<Position>();
		Position start = tempMT.getStartPoint();
		mtPos.add(start);
		Position end = tempMT.getEndPoint();
		//Determine the direction the microtubule is pointed in, using doubles
		DoublePosition direction = tempMT.getUnitVector();
		DoublePosition currentPos = direction.toDouble(start);
		//Add positions to position set, until we reach the end of the microtubule
		while (!mtPos.contains(end)){		
			mtPos.addAll(direction.add(currentPos));
			currentPos = currentPos.addDouble(direction);
		}
		//Add microtubule and its positions to the grid
		addObjects(tempMT, mtPos);
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
		
		if(!simParams.isBatchRun()) {
			System.out.print(endMessage);
		}
		if((outputFile != null) && (!simParams.isBatchRun())) {
			try {
				outputFile.write(endMessage);
			} catch (IOException e) {
				System.out.println("Error: unable to write to file: " + simParams.getOutputFileName());
				e.printStackTrace();
			}
		}

		if((outputFile != null) && (!simParams.isBatchRun())) {
			outputFile.close();
		} else if(simParams.isBatchRun()) {		// Append batch file result to batch file:		
			FileWriter batchWriter = new FileWriter("batch_" + simParams.getOutputFileName(), APPEND_TO_FILE);
			if(batchWriter != null) {
				batchWriter.append(simStep + "\n");
				System.out.println(simStep);
				batchWriter.close();
			}
		}
	}

	/** Add molecules to molecules list field
	 * 
	 * @param mols List of molecules to add to simulation list
	 */
	public void addMolecules(ArrayList<Molecule> mols) {
		for (Molecule mol : mols){
			// Only add the molecules to the movingMolecules list if they do, in fact, move.
			if(!(mol.getMovementController() instanceof NullMovementController)) {
				movingMolecules.add(mol);
			}
			addObject(mol, mol.getPosition());
		}
	}

	//Reports to the console that a message has been completed
	public void completedMessage(int msgNum) {
		messagesCompleted = msgNum;
		String completedMessage = "Completed message: " + msgNum + ", at step: " + simStep + "\n";
		if(msgNum >= simParams.getNumMessages()){
			lastMsgCompleted = true;
			completedMessage += "Last message completed.\n";
		}
		if(!simParams.isBatchRun()) { 
			System.out.print(completedMessage);
		}
		if((outputFile != null)  && (!simParams.isBatchRun())) {
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

	public ArrayList<Molecule> getMovingMolecules() {
		return movingMolecules;
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
		return simParams.getNumRetransmissions();
	}
	
	public boolean isUsingCollisions() {
		return simParams.isUsingCollisions();
	}
	
	public boolean decomposing(){
		return simParams.isDecomposing();
	}
	
	public int getRetransmitWaitTime(){
		return simParams.getRetransmitWaitTime();
	}
	
	//Add an object to the medium's position grid
	public void addObject(Object obj, Position pos){
		medium.addObject(obj, pos);
	}
	
	//Add an object to multiple positions in the medium's grid
	public void addObjects(Object obj, HashSet<Position> pos){
		for (Position p : pos){
			medium.addObject(obj, p);
		}
	}
	
	public void moveObject(Object obj, Position oldPos, Position newPos){
		medium.moveObject(obj, oldPos, newPos);
	}
	
	public boolean isOccupied(Position pos){
		return medium.isOccupied(pos);
	}
	
	//Removes all molecules located at the garbageSpot, waiting to be deleted
	public void collectGarbage(){
		ArrayList<Object> garbage = medium.getObjectsAtPos(medium.garbageSpot());
		medium.collectGarbage();
		for (Object o : garbage){
			movingMolecules.remove(o);
		}
	}
	
	public FileWriter getOutputFile() {
		return outputFile;
	}


}
