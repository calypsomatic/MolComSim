package MComSim.MolComSim-context;

import java.io.*;
import java.util.*;

public class MolComSim {

	private int simStep;
	private boolean lastMsgCompleted;
	private ArrayList<Molecule> molecules;
	private int messagesCompleted;
	private SimulationParams simParams;
	private ArrayList<NanoMachine> nanoMachines;
	private ArrayList<NanoMachine> transmitters;
	private ArrayList<NanoMachine> receivers;

	public static void main(String[] args) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void startSim(String[] args) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static MolComSim createInstance() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void run(String[] args) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getSimStep() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean isLastMsgCompleted() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void createMedium() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void createNanoMachines() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void createMicrotubules() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void endSim() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void addMolecules(ArrayList<Molecule> mols) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void completedMessage(int msgNum) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getMessagesCompleted() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public SimulationParams getSimParams() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Molecule> getMolecules() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Microtubule> getMicrotubules() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<NanoMachine> getNanoMachines() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Medium getMedium() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<NanoMachine> getReceivers() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<NanoMachine> getTransmitters() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
