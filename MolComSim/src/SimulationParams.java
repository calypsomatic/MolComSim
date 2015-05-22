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
	private int mediumLength = 100;
	private int mediumWidth = 100;
	private int mediumHeight = 100;
	private String outputFileName = null;
	private ArrayList<NanoMachineParam> transmitterParams = new ArrayList<>();
	private ArrayList<NanoMachineParam> receiverParams = new ArrayList<>();
	private ArrayList<IntermediateNodeParam> intermediateNodeParams = new ArrayList<>();
	private ArrayList<MicrotubuleParams> microtubuleParams = new ArrayList<MicrotubuleParams>();
	private int numMessages = 1;
	private int maxNumSteps = 100000;
	private int numRetransmissions = 0;
	private int retransmitWaitTime = 100;
	private boolean useCollisions = true;
	private boolean decomposing = false;
	private boolean useAcknowledgements = true;
	private ArrayList<MoleculeParams> moleculeParams = new ArrayList<MoleculeParams>();
	private int molRandMoveX = 1;
	private int molRandMoveY = 1;
	private int molRandMoveZ = 1;
	private int velRail = 1; 
	private double probDRail = 0.0;
	private boolean batchRun; // store single result (last simulation step used) in batch file, append to file if already there.
	
	private static final int ARQ_CODE_LENGTH = 2;
	// movement defaults to be used if movement type not specified in the params file.
	private static HashMap<MoleculeType, MoleculeMovementType> movementDefaults = 
			new HashMap<MoleculeType, MoleculeMovementType>(); 
	
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
			} else if(args[i].equals("-batchRun")) {
				setBatchRun(true);
			} else {
				throw new IllegalArgumentException("Invalid argument: " + args[i] + 
						" as command line argument.");
			}
		}
		
		if(numInfoMols > 0) {
			moleculeParams.add(
					new MoleculeParams(
							MoleculeType.INFO, movementDefaults.get(MoleculeType.INFO), numInfoMols, 0));
		}
		if(numAckMols > 0) {
			moleculeParams.add(
					new MoleculeParams(
							MoleculeType.ACK, movementDefaults.get(MoleculeType.ACK), numAckMols, 0));			
		}
	}
	
	/* Open params file for reading
	   Reads params from paramsFile (field)
	   Each param type is identified by the first string starting a line,
	   Although its values may extend over multiple lines
	   Stores each param’s value(s) in a private field. */
	private void readParamsFile(String fName) throws IOException{
		
		String line;
		BufferedReader br = new BufferedReader(new FileReader(fName));
		
		while((line = br.readLine())!=null){
			String param = "";
			if(!line.equals(""))
				param = line.substring(line.indexOf(" ")+1).trim();
			if(line.startsWith("stepLengthX")){
				molRandMoveX = Integer.parseInt(param);
			}
			else if(line.startsWith("stepLengthY")){
				molRandMoveY = Integer.parseInt(param);
			}
			else if(line.startsWith("stepLengthZ")){
				molRandMoveZ = Integer.parseInt(param);
			}
			else if(line.startsWith("mediumDimensionX")){
				mediumLength = Integer.parseInt(param);
			}
			else if(line.startsWith("mediumDimensionY")){
				mediumWidth = Integer.parseInt(param);				
			}
			else if(line.startsWith("mediumDimensionZ")){
				mediumHeight = Integer.parseInt(param);
			}		
			else if (line.startsWith("maxSimulationStep")){
				maxNumSteps = Integer.parseInt(param);
			}
			else if(line.startsWith("transmitter")){
				transmitterParams.add(
						new NanoMachineParam(
								new Scanner(
									line.substring(line.indexOf(" ")))));				
			}
			else if(line.startsWith("receiver")){
				receiverParams.add(
						new NanoMachineParam(
								new Scanner(
									line.substring(line.indexOf(" ")))));				
			}
			else if(line.startsWith("intermediateNode")){
				intermediateNodeParams.add(
						new IntermediateNodeParam(
								new Scanner(
									line.substring(line.indexOf(" ")))));				
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
				useCollisions = (Integer.parseInt(param) == 1) ? true : false;
			}
			else if(line.startsWith("decomposing")){
				decomposing = (Integer.parseInt(param) == 1) ? true : false;
			}
			else if(line.startsWith("useAcknowledgements")){
				useAcknowledgements = (Integer.parseInt(param) == 1) ? true : false;
			}
			else if(line.startsWith("velRail")){
				velRail = Integer.parseInt(param);				
			}
			else if(line.startsWith("probDRail")){
				probDRail = Double.parseDouble(param);				
			} else if (line.startsWith("moleculeParams")) {
				moleculeParams.add(
						new MoleculeParams(
								new Scanner(
										line.substring(line.indexOf(" ")))));

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
		setMolDefaultsIfNeeded();
	}

	private void setMolDefaultsIfNeeded() {
		if(getInformationMoleculeParams().isEmpty()) {
			moleculeParams.add(
					new MoleculeParams(
							MoleculeType.INFO, movementDefaults.get(MoleculeType.INFO), 1, 0));
		}
		if(useAcknowledgements && getAcknowledgmentMoleculeParams().isEmpty()) {
			moleculeParams.add(
					new MoleculeParams(
							MoleculeType.ACK, movementDefaults.get(MoleculeType.ACK), 1, 0));
		}	
	}

	public String getOutputFileName() {
		return outputFileName;
		
	}
	
	public int getMediumLength() {
		return mediumLength;
	}

	public int getMediumWidth() {
		return mediumWidth;
	}

	public int getMediumHeight() {
		return mediumHeight;
	}

	public ArrayList<MoleculeParams> getAllMoleculeParams() {
		return moleculeParams;
	}

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

	public ArrayList<NanoMachineParam> getTransmitterParams() {
		return transmitterParams;
	}

	public ArrayList<NanoMachineParam> getReceiverParams() {
		return receiverParams;
	}

	public ArrayList<IntermediateNodeParam> getIntermediateNodeParams() {
		return intermediateNodeParams;
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
	
	//Decomposing can only be used if acknowledgements are also being used
	public boolean isDecomposing() {
		return decomposing && useAcknowledgements;
	}

	public boolean isUsingAcknowledgements() {
		return useAcknowledgements;
	}

	public int getMolRandMoveX() {
		return molRandMoveX;
	}

	public int getMolRandMoveY() {
		return molRandMoveY;
	}

	public int getMolRandMoveZ() {
		return molRandMoveZ;
	}

	public int getVelRail() {
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

	public boolean isBatchRun() {
		return batchRun;
	}

	public void setBatchRun(boolean batchRun) {
		this.batchRun = batchRun;
	}

}
