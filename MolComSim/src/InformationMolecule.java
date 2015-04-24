/**
 * Information Molecule is the type of molecule
 * that is sent out by a transmitter to 
 * communicate a message
 */
import java.util.*;

public class InformationMolecule extends Molecule{

	//Indicates where molecule is intended to go
	private ArrayList<NanoMachine> destinations;
	//Where molecule started from
	private NanoMachine source;

	public InformationMolecule(MovementController mc, Position psn, MolComSim sim, NanoMachine src, int msgNum, MoleculeMovementType molMvType) {
		super(mc, psn, sim, molMvType);
		this.source = src;
		this.msgId = msgNum; 
		this.destinations = sim.getReceivers();
	}
	
	public InformationMolecule(Position psn, MolComSim sim, NanoMachine src, int msgNum, MoleculeMovementType molMvType) {
		super(psn, sim, molMvType);
		this.source = src; 
		this.msgId = msgNum; 
		this.destinations = sim.getReceivers();
	}
	
	public void move() {
		setPosition(getMovementController().getNextPosition(this, getSimulation()));
		NanoMachine rx = simulation.getMedium().getRxNanoMachineAtPos(getPosition());
		if(rx != null)
		{
			rx.receiveMolecule(this);
		}
	}

}
