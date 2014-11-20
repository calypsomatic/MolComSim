//package MComSim.Receiver;

import java.io.*;
import java.util.*;

public class Receiver {

	private NanoMachine nanoMachine;
	private MolComSim simulation;
	private int currMsgId;
	private int retransmissionsLeft;
	private MoleculeCreator moleculeCreator;

	public Receiver(NanoMachine nm, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		this.nanoMachine = nm;
		this.simulation = sim;
		if(this.simulation.isUsingAcknowledgements())
		{
			this.moleculeCreator = new MoleculeCreator(mpl, simulation, nanoMachine);
			currMsgId = 0;
			retransmissionsLeft =  this.simulation.getMaxRetransmissions();
		}
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void createMolecules() {
		/*moleculeCreator.createMoreMolecules(currMsgId);
	countdown = sim.getRetransmissionWaitTime();*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void nextStep() {
		/*if(sim.isUsingAcknowledgements() && 
		((countdown-- > 0) && (retransmissionsLeft > 0)))
	{
		createMolecules();
		retransmissionsLeft--;
	} */
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void receiveMolecule(Molecule m) {
		/*if(molecule.getMsgId() == currMsgId + 1)
	{
		currMsgId++;		
		if(sim.isUsingAcknowledgements())
		{
			createMolecules();
			retransmissionsLeft =  sim.getMaxRetransmissions();
		} 
		else
		{
			sim.completedMessage(currMsgId);
		}
	}
	else if (sim.isUsingAcknowledgements() && (retransmissionsLeft > 0))
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
