package MComSim.NoiseMolecule;

import java.io.*;
import java.util.*;

public class NoiseMolecule extends Molecule{

	public void move() {
		setPosition(getMovementController().getNextPosition(this, getSimulation());
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public NoiseMolecule(MovementController mc, Position psn, double r, MolComSim sim, MoleculeMovementType molMvType) {
		super(mc, psn, r, sim, molMvType);
	}

}
