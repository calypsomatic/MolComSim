import java.util.Scanner;

/** Stores the parameters needed
 * to create a particular type of molecule
 *
 */

public class MoleculeParams {

	private int numMolecules;
	private MoleculeType moleculeType;
	private MoleculeMovementType moleculeMovementType;

	public MoleculeParams(MoleculeType mType, MoleculeMovementType mMovementType, int numMols) {
		this.numMolecules = numMols;
		this.moleculeMovementType = mMovementType;
		this.moleculeType = mType;
	}

	public MoleculeParams(Scanner readParams) {
		numMolecules = readParams.nextInt();
		moleculeType = MoleculeType.getMoleculeType(readParams.next());
		if(readParams.hasNext()) {
			moleculeMovementType = MoleculeMovementType.getMovementType(readParams.next());
		} else { // movement type not specified, use default
			moleculeMovementType = SimulationParams.getMovementDefault(moleculeType);
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
	

}
