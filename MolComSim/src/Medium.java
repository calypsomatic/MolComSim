/**
 *  The medium in which the simulation takes place
 *  Has dimensions and is responsible for
 *  populating itself with noise molecules
 */

import java.util.*;

public class Medium {

	private int length;
	private int height;
	private int width;
	private NoiseMoleculeCreator mCreator;
	private MolComSim simulation;
	//Keep track of the location of all objects in the medium
	private HashMap<Position, ArrayList<Object>> grid;
	//garbageSpot is located outside of the bounds of the medium, where molecules go to die
	private final Position garbageSpot;

	public Medium(int l, int h, int w, ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
		this.length = l;
		this.height = h;
		this.width = w;
		this.simulation = sim;
		this.mCreator = new NoiseMoleculeCreator(noiseMoleculeParams, this.simulation);
		this.grid = new HashMap<Position, ArrayList<Object>>();
		garbageSpot = new Position(length*2, height*2, width*2);
		grid.put(garbageSpot, new ArrayList<Object>());
	}

	/** Populate itself with noise molecules*/
	public void createMolecules() {
		mCreator.createMolecules(); 
	}
	
	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public MolComSim getSimulation() {
		return simulation;
	}
	
	// Checks to see if toCheck is within bounds of medium.
	// if so, returns toCheck,  otherwise returns whatever closest position 
	// to toCheck is within the medium.
	Position getClosestPosition(Position toCheck) {
		int x, y, z;
		if(toCheck.getX() > (length / 2)) {
			x = length / 2; 
		} else if (toCheck.getX() < (length / -2)) {
			x = (length / -2);
		} else {
			x = toCheck.getX();
		}
		
		if(toCheck.getY() > (width / 2)) {
			y = width / 2; 
		} else if (toCheck.getY() < (width / -2)) {
			y = (width / -2);
		} else {
			y = toCheck.getY();
		}
		
		if(toCheck.getZ() > (height / 2)) {
			z = height / 2; 
		} else if (toCheck.getZ() < (height / -2)) {
			z = (height / -2);
		} else {
			z = toCheck.getZ();
		}
		
		return new Position(x, y, z);
		
	}
	
	//Add an object to the grid of objects
	public void addObject(Object obj, Position pos){
		if (!grid.containsKey(pos)){
			grid.put(pos, new ArrayList<Object>());
		}
		grid.get(pos).add(obj);
	}
	
	//Move an object from its old location in the grid to the new position
	public void moveObject(Object obj, Position oldPos, Position newPos){
		if (grid.containsKey(oldPos)){
			grid.get(oldPos).remove(obj);
			if (grid.get(oldPos).isEmpty())
				grid.remove(oldPos);
			}
		addObject(obj, newPos);
	}
	
	//Checks to see if a particular position already has anything in it
	public boolean isOccupied(Position pos){
		if (!grid.containsKey(pos) || grid.get(pos).isEmpty())
			return false;
		return true;
	}
	
	public Microtubule hasMicrotubule(Position pos){
		if (!grid.containsKey(pos) || grid.get(pos).isEmpty())
			return null;
		else {
			for (Object o : grid.get(pos)){
				if (o instanceof Microtubule)
					return (Microtubule) o;
			}
		}
		return null;
	}
	
	//Checks to see if a particular position has a molecule in it
	public boolean hasMolecule(Position pos){
		if (!grid.containsKey(pos) || grid.get(pos).isEmpty())
			return false;
		else {
			for (Object o : grid.get(pos)){
				if (o instanceof Molecule)
					return true;
			}
		}
		return false;
	}
	
	//Returns a list of everything located in a particular position
	public ArrayList<Object> getObjectsAtPos(Position pos){
		if (isOccupied(pos) || pos.equals(garbageSpot))
			return grid.get(pos);
		else 
			return null;
	}
	
	public Position garbageSpot(){
		return garbageSpot;
	}
	
	//Erase all objects in the garbageSpot
	public void collectGarbage(){
		grid.put(garbageSpot, new ArrayList<Object>());
	}

	// gets first nanomachine with a receiver at this position.
	// returns it if found, else returns null.
	public NanoMachine getRxNanoMachineAtPos(Position pos) {
		if (!grid.containsKey(pos) || grid.get(pos).isEmpty())
			return null;
		else {
			for (Object o : grid.get(pos)){
				if (o instanceof NanoMachine ) {
					NanoMachine nm = (NanoMachine)o;
					if(nm.hasReceiver())
						return nm;
				}
			}
		}
		return null;
	}

	// gets first nanomachine with a transmitter at this position.
	// returns it if found, else returns null.
	public NanoMachine getTxNanoMachineAtPos(Position pos) {
		if ( pos.equals(garbageSpot) || !grid.containsKey(pos) || grid.get(pos).isEmpty())
			return null;
		else {
			for (Object o : grid.get(pos)){
				if (o instanceof NanoMachine ) {
					NanoMachine nm = (NanoMachine)o;
					if(nm.hasTransmitter())
						return nm;
				}
			}
		}
		return null;
	}
	
	//This is for testing purposes only, remove from program
	/*public HashMap<Position, ArrayList<Object>> getGrid(){
		return grid;
	}*/

}
