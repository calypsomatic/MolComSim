
public abstract class CollisionDecorator implements CollisionHandler {

	protected CollisionHandler collH;
	
	public CollisionDecorator(CollisionHandler cH){
		collH = cH;
	}
	
	public Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation){
		return collH.handlePotentialCollisions(mol, nextPosition, simulation);
	}
	
	//public abstract Position handlePotentialCollections(Molecule mol, Position nextPosition, MolComSim simulation);

}
