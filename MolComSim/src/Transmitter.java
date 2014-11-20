//package MComSim.Transmitter;

import java.io.*;
import java.util.*;

public class Transmitter {

	private NanoMachine nanoMachine;
	private MolComSim simulation;
	private int currMsgId = 0;
	private int retransmissionsLeft;
	private MoleculeCreator moleculeCreator;

	public Transmitter(NanoMachine nm, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		this.nanoMachine = nm;
		this.simulation = sim;
		this.moleculeCreator = new MoleculeCreator(mpl, this.simulation, this.nanoMachine);
		this.currMsgId = 0;
		this.retransmissionsLeft =  this.simulation.getMaxRetransmissions();
	}

	public void createMolecules() {
		/*moleculeCreator.createMoreMolecules(currMsgId);
		countdown = sim.getRetransmissionWaitTime();*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void nextStep() {
		/*if((countdown-- > 0) && (retransmissionsLeft > 0))
	{
		createMolecules();
		retransmissionsLeft--;
	} */
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void receiveMolecule(Molecule m) {
		/*if(molecule.getMsgId() == currMsgId)
	{
		sim.completedMessage(currMsgId++);
		if(currMsgId < sim.getNumMessages()) 
		{
			createMolecules();
			retransmissionsLeft =  sim.getMaxRetransmissions();
		}
	}
	else if (retransmissionLeft > 0)
	{
		createMolecules();
		retransmissionsLeft--;
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public NanoMachine getNanoMachine() {
		return nanoMachine;
	}

	public MolComSim getSimulation() {
		return simulation;
	}

	public int getCurrMsgId() {
		return currMsgId;
	}

}
