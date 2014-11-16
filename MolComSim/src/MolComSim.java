//package MComSim.MolComSim;

import java.io.*;
import java.util.*;

public class MolComSim {

	private int simStep;
	private boolean lastMsgCompleted;
	private ArrayList<Molecule> molecules;
	private int messagesCompleted;
	private SimulationParams simParams;
	private ArrayList<Microtubule> microtubules;
	private ArrayList<NanoMachine> nanoMachines;
	private Medium medium;
	private ArrayList<NanoMachine> transmitters;
	private ArrayList<NanoMachine> receivers;

	public static void main(String[] args) {
		/*molComSim = createInstance();
		molComSim.run(args);*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void startSim(String[] args) {
		/*simStep = 0;
		lastMsgCompleted = false;
		simParams = new SimulationParams(args);
		createMedium();
		createNanoMachines();
		createMicroTubules();*/
		// Note: it is the job of the medium and NanoMachines to create molecules
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static MolComSim createInstance() {
		/*if(molComSim == null)
		{
			molComSim = new MolComSim();
		}
		return molComSim;*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void run(String[] args) {
		/*startSim(args);
		for(; (simStep < simParams.getMaxNumSteps()) && (!lastMsgCompleted); simStep++) 
		{
			for(each NanoMachine nm)
			{
				nm.nextStep();
			}
			for(each Molecule m)
			{	
				m.move();
			}
		}
		endSim();*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getSimStep() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean isLastMsgCompleted() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void createMedium() {
		/*get Medium params, NoiseMolecule params from simParams
		medium = new Medium(medum params, noise molecule params, this);
		medium.createMolecules();*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void createNanoMachines() {
		/*for(each NanoMachine specified by simParams)
	{
		get NanoMachine params, Info/Ack molecule params to be released by this NanoMachine from simParams
		construct a temporary nano machine, by calling NanoMachine method createTransmitter, createReceiver, or createIntermediateNode, and passing it the nano machine params, the molecule params, and this simulation object. 
		If we are creating a transmitter, add it to the list of transmitters. 
		If we are creating a receiver add it to the list of receivers.  
		If we are creating an intermediate note add it to both the list of transmitters and the list of receivers.
		tempNanoMachine.createInfoMolecules();
		nanoMachineList.add(tempNanoMachine);
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void createMicrotubules() {
		/*for(each MicroTubule specified by simParams)
	{
		get microtubule params from simParams
		construct a temporary microtubule, passing it the microtubule params and this simulation object
		microTubuleList.add(tempMicroTubule);
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	//any cleanup tasks, including printing simulation results to monitor or file.
	private void endSim() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	//add molecules to molecules list field
	public void addMolecules(ArrayList<Molecule> mols) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void completedMessage(int msgNum) {
		/*possibly print results to file and/or monitor
	if(msgId >= numMsgs – 1)
	{
		lastMsgCompleted = true;
	}*/
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
