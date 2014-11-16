//package MComSim.AcknowledgementMolecule;

import java.util.*;

public class AcknowledgementMolecule extends Molecule{

	private NanoMachine source;
	private ArrayList<NanoMachine> destinations;
	private int msgId;

	public void move() {
		setPosition(getMovementController().getNextPosition(this, getSimulation()));
		/*if(reachedDestination()){
			destination.receiveMolecule(this);
		}*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private boolean reachedDestination() {
		for(NanoMachine dest : destinations){
			if(haveOverlap(dest)){
				return true;
			}
		}
		return false;
	}

	public AcknowledgementMolecule(MovementController mc, Position psn, double r, MolComSim sim, NanoMachine src, int msgNum, MoleculeMovementType molMvType) {
		super(mc, psn, r, sim, molMvType);
		source = src;
		msgId = msgNum; 
		destinations = sim.getTransmitters();
	}

	private boolean haveOverlap(NanoMachine dest) {
		return getPosition().getDistance(dest.getPosition()) < getRadius() + dest.getRadius();
	}

}
