package MComSim.NanoMachine-context;

import java.io.*;
import java.util.*;

public class NanoMachine {

	public static class Transmitter {

		private MolComSim simulation;
		private int currMsgId = 0;
		private int retransmissionsLeft;
		private MoleculeCreator moleculeCreator;

		public Transmitter(NanoMachine nm, ArrayList<MoleculeParams> mpl, MolComSim sim) {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public void createMolecules() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public void nextStep() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public void receiveMolecule(Molecule m) {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public NanoMachine getNanoMachine() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public MolComSim getSimulation() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public int getCurrMsgId() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

	}

	public static class Receiver {

		private MolComSim simulation;
		private int currMsgId;
		private int retransmissionsLeft;
		private MoleculeCreator moleculeCreator;

		public Receiver(NanoMachine nm, ArrayList<MoleculeParams> mpl, MolComSim sim) {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public void createMolecules() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public void nextStep() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public void receiveMolecule(Molecule m) {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public NanoMachine getNanoMachine() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public MolComSim getSimulation() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

		public int getCurrMsgId() {
			throw new UnsupportedOperationException("The method is not implemented yet.");
		}

	}

	private Position position;
	private double radius;
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
