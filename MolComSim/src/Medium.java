package MComSim.Medium;

import java.io.*;
import java.util.*;

public class Medium {

	private double length;
	private double height;
	private double width;
	private NoiseMoleculeCreator mCreator;
	private MolComSim simulation;

	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public Medium(double l, double h, double w, ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void createMolecules() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public MolComSim getSimulation() {
		return simulation;
	}

}
