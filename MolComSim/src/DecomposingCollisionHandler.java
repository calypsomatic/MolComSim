/**
 * Handles collisions in the case that information molecules decompose when 
 * colliding with acknowledgement molecules of matching id
 */
import java.util.ArrayList;

public class DecomposingCollisionHandler extends CollisionDecorator{
	
	public DecomposingCollisionHandler(CollisionHandler cH){
		super(cH);
	}

	/**
	 * Moving molecule stays in place if it collides with another molecule;
	 * Additionally, if an acknowledgement molecule and an information molecule
	 * with the same ID number collide, the information molecule is deleted
	 */
	public Position handlePotentialCollisions(Molecule mol, Position nextPos, MolComSim simulation) {
		Position nextPosition = collH.handlePotentialCollisions(mol, nextPos, simulation);
		if (simulation.getMedium().hasMolecule(nextPosition)){
			ArrayList<Object> alreadyThere = simulation.getMedium().getObjectsAtPos(nextPosition);
			if (mol instanceof InformationMolecule){
				for (Object o : alreadyThere){
					if (o instanceof AcknowledgementMolecule){
						if ( ((AcknowledgementMolecule) o).getMsgId() == mol.getMsgId()){
							//remove info molecule from simulation
							//TODO: a better way to get this spot
							//TODO: change moveObject to return a position so this can be done in one line
							simulation.moveObject(mol, mol.getPosition(), simulation.getMedium().garbageSpot());
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
