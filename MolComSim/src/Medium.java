/**
 *  The medium in which the simulation takes place
 *  Has dimensions and is responsible for
 *  populating itself with noise molecules
 */

import java.util.*;

public class Medium {

	private double length;
	private double height;
	private double width;
	private NoiseMoleculeCreator mCreator;
	private MolComSim simulation;

	public Medium(double l, double h, double w, ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
		this.length = l;
		this.height = h;
		this.width = w;
		this.simulation = sim;
		this.mCreator = new NoiseMoleculeCreator(noiseMoleculeParams, this.simulation);
	}

	/** Populate itself with noise molecules*/
	public void createMolecules() {
		mCreator.createMolecules();
	}
	
	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public MolComSim getSimulation() {
		return simulation;
	}

}
