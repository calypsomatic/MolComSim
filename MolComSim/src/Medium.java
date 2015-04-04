/**
 *  The medium in which the simulation takes place
 *  Has dimensions and is responsible for
 *  populating itself with noise molecules
 */

import java.util.*;

public class Medium {

	private double length;
	private double height;
	private double width;
	private NoiseMoleculeCreator mCreator;
	private MolComSim simulation;
	private HashMap<Position, ArrayList<Object>> grid;
	private final Position garbageSpot;

	public Medium(double l, double h, double w, ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
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
	
	public double getLength() {
		return length;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public MolComSim getSimulation() {
		return simulation;
	}
	
	// Checks to see if toCheck is within bounds of medium.
	// if so, returns toCheck,  otherwise returns whatever closest position 
	// to toCheck is within the medium.
	//TODO: Incorporate molecule's radius
	Position getClosestPosition(Position toCheck) {
		double x, y, z;
		if(toCheck.getX() > (length / 2.0)) {
			x = length / 2.0; 
		} else if (toCheck.getX() < (length / -2.0)) {
			x = (length / -2.0);
		} else {
			x = toCheck.getX();
		}
		
		if(toCheck.getY() > (width / 2.0)) {
			y = width / 2.0; 
		} else if (toCheck.getY() < (width / -2.0)) {
			y = (width / -2.0);
		} else {
			y = toCheck.getY();
		}
		
		if(toCheck.getZ() > (height / 2.0)) {
			z = height / 2.0; 
		} else if (toCheck.getZ() < (height / -2.0)) {
			z = (height / -2.0);
		} else {
			z = toCheck.getZ();
		}
		
		return new Position(x, y, z);
		
	}
	
	public void addObject(Object obj, Position pos){
		if (!grid.containsKey(pos)){
			grid.put(pos, new ArrayList<Object>());
		}
		grid.get(pos).add(obj);
	}
	
	public void moveObject(Object obj, Position oldPos, Position newPos){
		if (grid.containsKey(oldPos)){
			grid.get(oldPos).remove(obj);
			if (grid.get(oldPos).isEmpty())
				grid.remove(oldPos);
			}
		addObject(obj, newPos);
	}
	
	public boolean isOccupied(Position pos){
		if (!grid.containsKey(pos) || grid.get(pos).isEmpty())
			return false;
		return true;
	}
	
	public ArrayList<Object> getObjectsAtPos(Position pos){
		if (isOccupied(pos) || pos.equals(garbageSpot))
			return grid.get(pos);
		else 
			return null;
	}
	
	public Position garbageSpot(){
		return garbageSpot;
	}
	
	public void collectGarbage(){
		grid.put(garbageSpot, new ArrayList<Object>());
	}

}
