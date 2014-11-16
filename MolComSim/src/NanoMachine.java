package MComSim.NanoMachine;

import java.io.*;
import java.util.*;

public class NanoMachine {

	private Position position;
	private double radius;
	private Receiver rx;
	private Transmitter tx;
	private MolComSim simulation;

	private NanoMachine(Position psn, double r) {
		this.position = psn;
		this.radius = r;
	}

	public static NanoMachine createTransmitter(Position p, double r, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		/*NanoMachine retVal = new NanoMachine(position, radius);
	retVal.tx = new Transmitter(retVal, molecule params, simulation);
	retVal.rx = null;
	return retVal;*/
	throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static NanoMachine createReceiver(Position p, double r, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		/*NanoMachine retVal = new NanoMachine(position, radius);
	retVal.rx = new receiver(retVal, molecule params, simulation);
	retVal.tx = null;
	return retVal;*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public static NanoMachine createIntermediateNode(Position p, double r, ArrayList<MoleculeParams> mpl, MolComSim sim) {
		/*NanoMachine retVal = new NanoMachine(position, radius);
	retVal.rx = new receiver(retVal, molecule params, simulation);
	retVal.tx = new Transmitter(retVal, molecule params, simulation);
	return retVal;	*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void createInfoMolecules() {
		/*if(tx != null) 
	{
		tx.createMolecules();
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void nextStep() {
		/*if(tx != null)
	{
		tx.nextStep();
	}
	if(rx != null)
	{
		rx.nextStep();
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public void receiveMolecule(Molecule m) {
		/*if(molecule is information && (rx != null))
	{
		rx.receiveMolecule(molecule);
	} 
	else if(molecule is acknowledgement) && (tx != null))
	{
		tx.receiveMolecule(molecule);
	}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public Position getPosition() {
		return position;
	}

	public double getRadius() {
		return radius;
	}

	public MolComSim getSimulation() {
		return simulation;
	}

}
