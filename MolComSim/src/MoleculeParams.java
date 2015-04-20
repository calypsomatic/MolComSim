import java.util.Scanner;

/** Stores the parameters needed
 * to create a particular type of molecule
 *
 */

public class MoleculeParams {

	private int numMolecules;
	private MoleculeType moleculeType;
	private MoleculeMovementType moleculeMovementType = SimulationParams.getMovementDefault(moleculeType);
	private int adaptiveChange = 0; // default is no adaptive change.  Amount to adjust num mols based
									// on comm success/failure.

	public MoleculeParams(MoleculeType mType, MoleculeMovementType mMovementType, int numMols, int adaptiveChange) {
		this.numMolecules = numMols;
		this.moleculeMovementType = mMovementType;
		this.moleculeType = mType;
		this.adaptiveChange = adaptiveChange;
	}

	public MoleculeParams(Scanner readParams) {
		numMolecules = readParams.nextInt();
		moleculeType = MoleculeType.getMoleculeType(readParams.next());
		if(readParams.hasNext()) { // could be either a movement type or an amount of adaptive change
			if(!readParams.hasNextInt()) { // it's not an adaptive change, must be a movement type
				moleculeMovementType = MoleculeMovementType.getMovementType(readParams.next());
			} 
			if(readParams.hasNextInt()) {// An adaptive change.  After movement type param if there is a movement type
				adaptiveChange = readParams.nextInt();
			}
		} 
	}

	public int getNumMolecules() {
		return numMolecules;
	}

	public MoleculeType getMoleculeType() {
		return moleculeType;
	}

	public MoleculeMovementType getMoleculeMovementType() {
		return moleculeMovementType;
	}
	
	public int getAdaptiveChange() {
		return adaptiveChange;
	}
	
	// Changes the number of molecules to send out based on prior communication success or failure.
	public void applyAdaptiveChange(int lastTransmissionStatus ) {
		if(lastTransmissionStatus == NanoMachine.LAST_COMMUNICATION_SUCCESS) {
			numMolecules -= adaptiveChange;
			if(numMolecules < 1) {
				numMolecules = 1;
			}
		} else if (lastTransmissionStatus == NanoMachine.LAST_COMMUNICATION_FAILURE){
			numMolecules += adaptiveChange;
		}
	}
}
