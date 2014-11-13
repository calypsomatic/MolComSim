package MComSim.MoleculeMovement-context;

import java.io.*;
import java.util.*;

public abstract class MovementController {

	private MolComSim simulation;

	protected MovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Position getNextPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	protected abstract Position decideNextPosition();

	protected MolComSim getSimulation() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	protected Molecule getMolecule() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
