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
	
	public InformationMolecule(Position psn, double r, MolComSim sim, NanoMachine src, int msgNum, MoleculeMovementType molMvType) {
		super(psn, r, sim, molMvType);
		this.source = src;
		this.msgId = msgNum; 
		this.destinations = sim.getReceivers();
	}
	
	public void move() {
		setPosition(getMovementController().getNextPosition(this, getSimulation()));
		System.out.println("Molecule with id " + msgId + " moving to " + this.position);
		if(reachedDestination() != null)
		{
			reachedDestination().receiveMolecule(this);
		}
	}

	//TODO: Should this be renamed whichDestinationReached?
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
	
	//TODO: Should there be two methods like so or one method taking in Object?
	/** @param dest A Nanomachine this molecule may have reached
	 *  @return true if the molecule is close enough to this nanomachine,
	 *  	else false
	 */
	private boolean haveOverlap(NanoMachine dest) {
		return getPosition().getDistance(dest.getPosition()) < getRadius() + dest.getRadius();
	}
	
	/** @param other another molecule that may be nearby
	 *  @return true if the molecule is bumping into the other molecule,
	 *  	else false
	 */
	private boolean haveOverlap(Molecule other) {
		return getPosition().getDistance(other.getPosition()) < getRadius() + other.getRadius();
	}

}
