/**
 * Noise Molecules exist in the medium
 * They do not move around, but may interfere
 * with the other molecules
 */
public class NoiseMolecule extends Molecule{

	public NoiseMolecule(MovementController mc, Position psn, double r, MolComSim sim, MoleculeMovementType molMvType) {
		super(mc, psn, r, sim, molMvType);
	}

	public void move() {
		setPosition(getMovementController().getNextPosition(this, getSimulation()));
	}

}
