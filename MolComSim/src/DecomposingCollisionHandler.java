/**
 * Handles collisions in the case that molecules decompose over time
 */
import java.util.ArrayList;

public class DecomposingCollisionHandler extends CollisionDecorator{
	
	public DecomposingCollisionHandler(CollisionHandler cH){
		super(cH);
	}

	//TODO: Add possibility of microtubule as well
	//TODO: Put in params or whatever of when/how to actually use this collision
	public Position handlePotentialCollisions(Molecule mol, Position nextPos, MolComSim simulation) {
		Position nextPosition = collH.handlePotentialCollisions(mol, nextPos, simulation);
		if (simulation.getMedium().isOccupied(nextPosition)){
			//System.out.println("decomposing collision");
			ArrayList<Object> alreadyThere = simulation.getMedium().getObjectsAtPos(nextPosition);
			if (mol instanceof InformationMolecule){
				for (Object o : alreadyThere){
					if (o instanceof AcknowledgementMolecule){
						if ( ((AcknowledgementMolecule) o).getMsgId() == mol.getMsgId()){
							//remove info molecule from simulation
							//TODO: a better way to get this spot
							//TODO: change moveObject to return a position so this can be done in one line
							simulation.moveObject(mol, mol.getPosition(), simulation.getMedium().garbageSpot());
							System.out.println("remove mol " + mol);
							return simulation.getMedium().garbageSpot();
						}						
					}
				}
			}
			else if (mol instanceof AcknowledgementMolecule){
				for (Object o : alreadyThere){
					if (o instanceof InformationMolecule){
						if ( ((InformationMolecule) o).getMsgId() == mol.getMsgId()){
							//remove info molecule from simulation
							System.out.println("remove mol " + o);
							simulation.getMedium().moveObject(o, nextPosition, simulation.getMedium().garbageSpot());
							break;
						}						
					}
				}
			}
			return mol.getPosition();
		}
		 simulation.moveObject(mol, mol.getPosition(), nextPosition);
		 return nextPosition;
	}
 
}
