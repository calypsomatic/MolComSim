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
		NanoMachine dest = reachedDestination();
		if(dest != null)
		{
			dest.receiveMolecule(this);
		}
	}

	/**
	 * @return The destination nanomachine this molecule has arrived at,
	 * 	or null if it has not reached any destination on its list
	 *
	 */
	private NanoMachine reachedDestination() {
		for(NanoMachine dest : destinations){
			if(haveOverlap(dest)){
				return dest;
			}
		}
		return null;
	}
	
	/** @param dest A Nanomachine this molecule may have reached
	 *  @return true if the molecule is close enough to this nanomachine,
	 *  	else false
	 */
	private boolean haveOverlap(NanoMachine dest) {
		return getPosition().getDistance(dest.getPosition()) < 1 + (int)dest.getRadius();
	}

}
