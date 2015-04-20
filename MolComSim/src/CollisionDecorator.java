/**
 * CollisionDecorator uses the Decorator design pattern
 * to allow CollisionHandlers to use any combination of parameters:
 * Null, Standard, OnTubule, or Decomposing
 * 
 */
public abstract class CollisionDecorator implements CollisionHandler {

	protected CollisionHandler collH;
	
	public CollisionDecorator(CollisionHandler cH){
		collH = cH;
	}
	
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation){
		return collH.handlePotentialCollisions(mol, nextPosition, simulation);
	}

}
