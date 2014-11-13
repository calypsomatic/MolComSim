package MComSim.MolComSim-context;

import java.io.*;
import java.util.*;

public class NanoMachine {

	private Position position;
	private double radius;
	private Receiver rx;
	private Transmitter tx;
	private MolComSim simulation;

	private NanoMachine(Position psn, double r) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static NanoMachine createTransmitter(Position p, double r, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static NanoMachine createReceiver(Position p, double r, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static NanoMachine createIntermediateNode(Position p, double r, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void createInfoMolecules() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void nextStep() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void receiveMolecule(Molecule m) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Position getPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getRadius() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public MolComSim getSimulation() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
