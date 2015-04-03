/**
 * Stores all the parameters needed to define
 * a particular simulation instance
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class SimulationParams {
	private String paramsFileName = "input0.dat";
	private double mediumLength;
	private double mediumWidth;
	private double mediumHeight;
	private String outputFileName = null;
	private ArrayList<Position> transmitterPositions = new ArrayList<Position>();
	private double transmitterRadius;
	private ArrayList<Position> receiverPositions = new ArrayList<Position>();
	private double receiverRadius;
	private ArrayList<Position> intermediateNodePositions;
	private double intermediateNodeRadius;
	private ArrayList<MicrotubuleParams> microtubuleParams = new ArrayList<MicrotubuleParams>();
	//private ArrayList<Double> microtubuleRadii;
	private int numMessages;
	private int maxNumSteps;
	private int numRetransmissions;
	private int retransmitWaitTime;
	private boolean useCollisions;
	private boolean useAcknowledgements;
	private ArrayList<MoleculeParams> moleculeParams = new ArrayList<MoleculeParams>();
	private double molRandMoveX;
	private double molRandMoveY;
	private double molRandMoveZ;
	private double velRail;
	private double probDRail;
	private static final int ARQ_CODE_LENGTH = 2;
	// movement defaults to be used if movement type not specified in the params file.
	private static HashMap<MoleculeType, MoleculeMovementType> movementDefaults = 
			new HashMap<MoleculeType, MoleculeMovementType>(); 
	
	// Try this approach later if time.
	// private HashMap<String, Object> allParams = new HashMap<String, Object>();

	public SimulationParams(String[] args) {
		setInitialMovementDefaults();
		parseArgs(args);
		try {
			readParamsFile(paramsFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MoleculeMovementType getMovementDefault(MoleculeType mt) {
		return movementDefaults.get(mt);
	}
	
	private void setInitialMovementDefaults() {
		movementDefaults.put(MoleculeType.ACK, MoleculeMovementType.ACTIVE);
		movementDefaults.put(MoleculeType.INFO, MoleculeMovementType.ACTIVE);
		movementDefaults.put(MoleculeType.NOISE, MoleculeMovementType.NONE);
	}
	
	private void parseArgs(String[] args) {
		/*parses command line arguments, stores them in fields
	args can include default for type of movement for acknowledgement, information, 
	and noise molecules (active = starting default for info and ack, 
	stationary = starting default for noise).    
	Indicated with: -dmt: (INFO|ACK|NOISE) (ACTIVE|PASSIVE|NONE) 
	args can include type of Automatic Repeat Request scheme used 
	(currently none is default, for no acknowledgement molecules, 
	change to sw11 later).  Indicated with: -arq: (sw)(1..n),(1..m) , 
	where sw means stop-and-wait (might implement 
	other ARQ schemes later, the next integer value represents the 
	number of information molecules to send (minimum 1), and the next 
	integer value represents the number of acknowledgement molecules to send.
	args can include input file location/name (default: params.dat).  
	Indicated with: pfile:<string>.  
	paramsFile must be set up in parseArgs, but not opened for reading.*/
		int numInfoMols = 0;
		int numAckMols = 0;
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("-pfile:")) {
				paramsFileName = args[++i];
			} else if(args[i].equals("-arq:")) {
				int commaIndex = args[i+1].indexOf(',');
				if(commaIndex != -1) {
					numInfoMols = Integer.parseInt(args[i+1].substring(ARQ_CODE_LENGTH, commaIndex));
					numAckMols = Integer.parseInt(args[i+1].substring(commaIndex + 1, args[i+1].length()));
					if(numAckMols > 0) {
						useAcknowledgements = true;
					}
					i++;
				}
			} else if(args[i].equals("-dmt:")) {
				MoleculeType mt = MoleculeType.getMoleculeType(args[++i]);
				MoleculeMovementType mmt = MoleculeMovementType.getMovementType(args[++i]);
				movementDefaults.put(mt, mmt);
			} else {
				throw new IllegalArgumentException("Invalid argument: " + args[i] + 
						" as command line argument.");
			}
		}
		
		if(numInfoMols > 0) {
			moleculeParams.add(
					new MoleculeParams(
							MoleculeType.INFO, movementDefaults.get(MoleculeType.INFO), numInfoMols, 1));
		}
		if(numAckMols > 0) {
			moleculeParams.add(
					new MoleculeParams(
							MoleculeType.ACK, movementDefaults.get(MoleculeType.ACK), numAckMols, 1));			
		}
	}
	private void readParamsFile(String fName) throws IOException{
		/*open params file for reading
	Reads params from paramsFile (field), each param type is identified by the first string starting a line, although its values may extend over multiple lines (for example, to make it easier to view arrays of things).  Stores each param’s value(s) in a
 	private field.  Alternatively, we could do this with a hashmap of key, value pairs, where the key is the String representing the name of the parameter and the value is of Object type so it can be anything we want.  Not sure which is the better way
 	to go.  
	close param file for reading*/
		
		String line;
		BufferedReader br = new BufferedReader(new FileReader(fName));
		
		while((line = br.readLine())!=null){
			String param = "";
			if(!line.equals(""))
				param = line.substring(line.indexOf(" ")+1).trim();
			if(line.startsWith("stepLengthX")){
				molRandMoveX = Double.parseDouble(param);
			}
			else if(line.startsWith("stepLengthY")){
				molRandMoveY = Double.parseDouble(param);
			}
			else if(line.startsWith("stepLengthZ")){
				molRandMoveZ = Double.parseDouble(param);
			}
			else if(line.startsWith("mediumDimensionX")){
				mediumLength = Double.parseDouble(param);
			}
			else if(line.startsWith("mediumDimensionY")){
				mediumWidth = Double.parseDouble(param);				
			}
			else if(line.startsWith("mediumDimensionZ")){
				mediumHeight = Double.parseDouble(param);
			}		
			else if (line.startsWith("maxSimulationStep")){
				maxNumSteps = Integer.parseInt(param);
			}
			else if(line.startsWith("receiverRadius")){
				receiverRadius = Double.parseDouble(param);				
			}
			else if(line.startsWith("transmitterRadius")){
				transmitterRadius = Double.parseDouble(param);				
			}
			else if(line.startsWith("intermediateNodeRadius")){
				intermediateNodeRadius = Double.parseDouble(param);				
			}
			else if(line.startsWith("numMessages")){
				numMessages = Integer.parseInt(param);				
			}
			else if(line.startsWith("numRetransmissions")){
				numRetransmissions = Integer.parseInt(param);				
			}
			else if(line.startsWith("retransmitWaitTime")){
				retransmitWaitTime = Integer.parseInt(param);				
			}
			else if(line.startsWith("useCollisions")){
				//how are we coding booleans in the params file?
				useCollisions = (Integer.parseInt(param) == 1) ? true : false;
			}
			else if(line.startsWith("useAcknowledgements")){
				//how are we coding booleans in the params file?
				useAcknowledgements = (Integer.parseInt(param) == 1) ? true : false;
			}
			else if(line.startsWith("velRail")){
				velRail = Double.parseDouble(param);				
			}
			else if(line.startsWith("probDRail")){
				probDRail = Double.parseDouble(param);				
			}
			else if(line.startsWith("transmitterPosition")){
				transmitterPositions.add(
						new Position(
								new Scanner(
										line.substring(line.indexOf(" ")))));
			}
			else if(line.startsWith("receiverPosition")){
				receiverPositions.add(
						new Position(
								new Scanner(
										line.substring(line.indexOf(" ")))));
			} else if (line.startsWith("moleculeParams")) {
				moleculeParams.add(
						new MoleculeParams(
								new Scanner(
										line.substring(line.indexOf(" ")))));

				//placeholder for a real way to read in microtubule params
			} else if(line.startsWith("microtubuleParams")) {
				microtubuleParams.add(
						new MicrotubuleParams(
								new Scanner(
										line.substring(line.indexOf(" ")))));
			} else if(line.startsWith("outputFile") && !(param.equals("Off"))) {
				outputFileName = param;
			}
		}
		br.close();
	}

	public String getOutputFileName() {
		return outputFileName;
		
	}
	
	public double getMediumLength() {
		return mediumLength;
	}

	public double getMediumWidth() {
		return mediumWidth;
	}

	public double getMediumHeight() {
		return mediumHeight;
	}

	public ArrayList<MoleculeParams> getAllMoleculeParams() {
		return moleculeParams;
	}

	//am I understanding these getParams methods correctly?
	public ArrayList<MoleculeParams> getNoiseMoleculeParams() {
		ArrayList<MoleculeParams> noiseMParams = new ArrayList<MoleculeParams>();
		for (MoleculeParams mp : moleculeParams){
			if (mp.getMoleculeType().equals(MoleculeType.NOISE)){
				noiseMParams.add(mp);
			}
		}
		return noiseMParams;
	}

	public ArrayList<MoleculeParams> getInformationMoleculeParams() {
		ArrayList<MoleculeParams> infoMParams = new ArrayList<MoleculeParams>();
		for (MoleculeParams mp : moleculeParams){
			if (mp.getMoleculeType().equals(MoleculeType.INFO)){
				infoMParams.add(mp);
			}
		}
		return infoMParams;
	}

	public ArrayList<MoleculeParams> getAcknowledgmentMoleculeParams() {
		ArrayList<MoleculeParams> ackMParams = new ArrayList<MoleculeParams>();
		for (MoleculeParams mp : moleculeParams){
			if (mp.getMoleculeType().equals(MoleculeType.ACK)){
				ackMParams.add(mp);
			}
		}
		return ackMParams;
	}

	public ArrayList<Position> getTransmitterPositions() {
		return transmitterPositions;
	}

	public ArrayList<Position> getReceiverPositions() {
		return receiverPositions;
	}

	public ArrayList<Position> getIntermediateNodePositions() {
		return intermediateNodePositions;
	}

	public double getTransmitterRadius() {
		return transmitterRadius;
	}

	public double getReceiverRadius() {
		return receiverRadius;
	}

	public double getIntermediateNodeRadius() {
		return intermediateNodeRadius;
	}

	public ArrayList<MicrotubuleParams> getMicrotubuleParams() {
		return microtubuleParams;
	}

	public int getNumRetransmissions() {
		return numRetransmissions;
	}

	public int getRetransmitWaitTime() {
		return retransmitWaitTime;
	}

	public boolean isUsingCollisions() {
		return useCollisions;
	}

	public boolean isUsingAcknowledgements() {
		return useAcknowledgements;
	}

	public double getMolRandMoveX() {
		return molRandMoveX;
	}

	public double getMolRandMoveY() {
		return molRandMoveY;
	}

	public double getMolRandMoveZ() {
		return molRandMoveZ;
	}

	public double getVelRail() {
		return velRail;
	}

	public double getProbDRail() {
		return probDRail;
	}

	public int getMaxNumSteps() {
		return maxNumSteps;
	}

	public int getNumMessages() {
		return numMessages;
	}

}
