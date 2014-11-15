package MComSim.MovementController;

import java.io.*;
import java.util.*;

public abstract class MovementController {

	private CollisionHandler collisionHandler;
	private MolComSim simulation;
	private Molecule molecule;

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
