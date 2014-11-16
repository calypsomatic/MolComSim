package MComSim.MoleculeParams;

import java.io.*;
import java.util.*;

public class MoleculeParams {

	private int numMolecules;
	private double radius;
	private MoleculeType moleculeType;
	private MoleculeMovementType moleculeMovementType;

	public double getRadius() {
		return radius;
	}

	public MoleculeParams(MoleculeType mType, MoleculeMovementType mMovementType, int numMols) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
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
