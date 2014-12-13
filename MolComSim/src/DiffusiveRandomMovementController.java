/**
 * Moves molecules according to passive transport,
 * using a random walk
 *
 */

import java.util.*;

public class DiffusiveRandomMovementController extends MovementController{
	private static Random random = new Random();
	
	public DiffusiveRandomMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		super(collHandle, sim, mol);
	}
	
	//TODO: Do we need a molecule at all?
	/*public DiffusiveRandomMovementController(CollisionHandler collHandle, MolComSim sim) {
		super(collHandle, sim);
	}*/
	
	/** Randomly selects where to move molecule based on simulation step length parameters
	 *  @param molecule The molecule to move
	 *  @return the position to move to
	 * 
	 */
	protected Position decideNextPosition(Molecule molecule) {
		//Randomly decide the next position based on current position + some delta.
		Position currentPosition = molecule.getPosition();
		double currentX = currentPosition.getX();
		double currentY = currentPosition.getY();
		double currentZ = currentPosition.getZ();
		// The idea behind the getMolRandMove~ methods are that the molecule moves a random amount in one step
		// , and that will, for each dimension, be a random amount between -getMolRandMove~ and +getMolRandMove~
		double maxXDelta = getSimulation().getSimParams().getMolRandMoveX();
		double maxYDelta = getSimulation().getSimParams().getMolRandMoveY();;
		double maxZDelta = getSimulation().getSimParams().getMolRandMoveZ();;
		double nextX = currentX + (2 * random.nextDouble() * maxXDelta) - maxXDelta;
		double nextY = currentY + (2 * random.nextDouble() * maxYDelta) - maxYDelta;
		double nextZ = currentZ + (2 * random.nextDouble() * maxZDelta) - maxZDelta;
		Position nextPosition = new Position(nextX, nextY, nextZ);
		if (molecule.getMoleculeMovementType() == MoleculeMovementType.ACTIVE){
			for (Microtubule mt : getSimulation().getMicrotubules()){
				/*if(molecule touches the microtubule at new position){ //  use current code to determine
					MovementController tubuleMovement = new OnMicroTubuleMovementController(new OnTubuleCollisionHandler(), getSimulation(), getMolecule(), currentMicrotubule);
					molecule.setMoleculeMovementController(tubuleMovement);
					break;*/
				//LEGACY CODE
				double r = mt.getRadius();
				Position plusend = mt.getPlusEndCenter();
				Position minusend = mt.getMinusEndCenter();
				double x1 = plusend.getX();
				double y1 = plusend.getY();
				double z1 = plusend.getZ();
				double x2 = minusend.getX();
				double y2 = minusend.getY();
				double z2 = minusend.getZ();
				double mtXLength = x2-x1; //l
				double mtYLength = y2-y1; //m
				double mtZLength = z2-z1; //n
				//TODO: rename these variables, use a separate method, explain what
				//is happening here
				double a = Math.pow(mtZLength*(nextY-currentY) - mtYLength*(nextZ-currentZ), 2) +
					Math.pow(mtXLength*(nextZ-currentZ) - mtZLength*(nextX-currentX), 2) +
					Math.pow(mtYLength*(nextX-currentX) - mtXLength*(nextY-currentY), 2);
				double b = (mtZLength*(nextY-currentY) - mtYLength*(nextZ-currentZ))*(mtZLength*(currentY-y1)
						- mtYLength*(currentZ-z1)) + (mtXLength*(nextZ-currentZ) - 
						mtZLength*(nextX-currentX))*(mtXLength*(currentZ-z1) - mtZLength*(currentX-x1)) + 
						(mtYLength*(nextX-currentX) - mtXLength*(nextY-currentY))*(mtYLength*(currentX-x1) - 
								mtXLength*(currentY-y1));
				double c = Math.pow(mtZLength*(currentY-y1) - mtYLength*(currentZ-z1), 2) +
						Math.pow(mtXLength*(currentZ-z1) - mtZLength*(currentX-x1), 2) +
						Math.pow(mtYLength*(currentX-x1) - mtXLength*(currentY-y1), 2) -
						r*r*(mtXLength*mtXLength + mtYLength*mtYLength + mtZLength*mtZLength);
				double det = -1;
				//TODO: definitely change this whole mess
				if((det = b*b - a*c)>= 0){
					double t1 = (Math.sqrt(det) - b)/a ;
					Position pos1 = new Position(currentX + t1*(nextX-currentX),currentY+t1*(nextY-currentY),currentZ+t1*(nextZ-currentZ));
					double t2 = -(Math.sqrt(det) + b)/a ;
					Position pos2 = new Position(currentX + t2*(nextX-currentX),currentY+t2*(nextY-currentY),currentZ+t2*(nextZ-currentZ));
					double dist1 = Double.MAX_VALUE;
					double dist2 = Double.MAX_VALUE;
					if(t1>0 && t1<=1 && 
							Math.sqrt(Math.pow(pos1.getDistance(plusend),2) - r*r)<= plusend.getDistance(minusend) &&
							Math.sqrt(Math.pow(pos1.getDistance(minusend),2) - r*r) <= plusend.getDistance(minusend)){
						dist1 = pos1.getDistance(currentPosition); 
					}
					if(t2>0 && t2<=1 && 
							Math.sqrt(Math.pow(pos2.getDistance(plusend),2) - r*r) <= plusend.getDistance(minusend) &&
							Math.sqrt(Math.pow(pos2.getDistance(minusend),2) - r*r) <= plusend.getDistance(minusend)){
						dist2 = pos2.getDistance(currentPosition); 
					}
					if(dist1<=dist2 && dist1!=Double.MAX_VALUE){
						MovementController tubuleMovement = new OnMicrotubuleMovementController(new OnTubuleCollisionHandler(), getSimulation(), getMolecule(), mt);
						molecule.setMovementController(tubuleMovement);
					}
					if(dist2<dist1){
						MovementController tubuleMovement = new OnMicrotubuleMovementController(new OnTubuleCollisionHandler(), getSimulation(), getMolecule(), mt);
						molecule.setMovementController(tubuleMovement);					}
					}
				}
			}
			return nextPosition;
	}

}
