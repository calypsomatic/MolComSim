package MComSim.Molecule;

import java.io.*;
import java.util.*;

public abstract class Molecule {

	private Position position;
	private double radius;
	private MovementController movementController;
	private MolComSim simulation;
	private MoleculeMovementType moleculeMovementType;

	public abstract void move();

	public Position getPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getRadius() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void setMovementController(MovementController mc) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	protected Molecule(MovementController mc, Position psn, double r, MolComSim sim, MoleculeMovementType molMvType) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public MolComSim getSimulation() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public MovementController getMovementController() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	protected void setPosition(Position p) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public MoleculeMovementType getMoleculeMovementType() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
