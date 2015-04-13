/**
 * Noise Molecules exist in the medium
 * They do not move around, but may interfere
 * with the other molecules
 */
public class NoiseMolecule extends Molecule{

	public NoiseMolecule(MovementController mc, Position psn, MolComSim sim, MoleculeMovementType molMvType) {
		super(mc, psn, sim, molMvType);
		msgId = null;
	}
	
	public NoiseMolecule(Position psn, MolComSim sim, MoleculeMovementType molMvType) {
		super(psn, sim, molMvType);
		msgId = null;
	}

	public void move() {
		setPosition(getMovementController().getNextPosition(this, getSimulation()));
	}

} 
