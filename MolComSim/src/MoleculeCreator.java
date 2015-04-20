/**
 * Creates molecules according to molParams
 * and places them at a starting point
 */

import java.util.*;

public class MoleculeCreator {

	protected ArrayList<MoleculeParams> molParams;
	protected MolComSim simulation;
	private NanoMachine source;
	private Position position;

	public MoleculeCreator(ArrayList<MoleculeParams> mParams, MolComSim sim) {
		this.molParams = mParams;
		this.simulation = sim;
	}

	public MoleculeCreator(ArrayList<MoleculeParams> mParams, MolComSim sim, NanoMachine src, Position molReleasePsn) {
		this.molParams = mParams;
		this.simulation = sim;
		this.source = src;
		this.position = molReleasePsn;
	}
	

	//TODO: Should this method be simplified with factories or helper methods?
	public void createMolecules() { 
		ArrayList<Molecule> newMols = new ArrayList<Molecule>();
		for (MoleculeParams mp : molParams){
			MoleculeType molType = mp.getMoleculeType();
			MoleculeMovementType molMoveType = mp.getMoleculeMovementType();
			for (int i = 0; i < mp.getNumMolecules(); i++){
				Molecule tempMol;
				if (molType.equals(MoleculeType.ACK)){
					tempMol = new AcknowledgementMolecule(position, simulation, source, source.getReceiverMessageId(),molMoveType);
				}
				else if (molType.equals(MoleculeType.INFO)){
					tempMol = new InformationMolecule(position, simulation, source, source.getTransmitterMessageId(), molMoveType);
				}
				else if (molType.equals(MoleculeType.NOISE)){
					tempMol = new NoiseMolecule(position, simulation, molMoveType);
				}
				else {
					//TODO: Error management?
					tempMol = null;
				}
				CollisionHandler collH;
				//Look for nearby microtubules if the molecules are ACTIVE
				if (molMoveType.equals(MoleculeMovementType.ACTIVE)){
					Microtubule microtubule = simulation.getMedium().hasMicrotubule(tempMol.getPosition());
					if (microtubule != null){
						collH = simulation.isUsingCollisions() ? 
								(simulation.decomposing() ? new OnTubuleCollisionHandler(new DecomposingCollisionHandler(new SimpleCollisionHandler())) :
									new OnTubuleCollisionHandler(new SimpleCollisionHandler())) : new SimpleCollisionHandler();
						new OnMicrotubuleMovementController(collH, simulation, tempMol, microtubule);
					}
					else{
						collH = simulation.isUsingCollisions() ? 
								(simulation.decomposing() ? new DecomposingCollisionHandler(new SimpleCollisionHandler()) :
									new StandardCollisionHandler(new SimpleCollisionHandler())) : new SimpleCollisionHandler();
						new DiffusiveRandomMovementController(collH, simulation, tempMol);
					}
				}
				else if (molMoveType.equals(MoleculeMovementType.PASSIVE)){
					collH = simulation.isUsingCollisions() ? 
							(simulation.decomposing() ? new DecomposingCollisionHandler(new SimpleCollisionHandler()) :
								new StandardCollisionHandler(new SimpleCollisionHandler())) : new SimpleCollisionHandler();
					new DiffusiveRandomMovementController(collH, simulation, tempMol);
				}
				else if (molMoveType.equals(MoleculeMovementType.NONE)){
					collH = new SimpleCollisionHandler();
					new NullMovementController(collH, simulation, tempMol);
				} else {
					//TODO: error management
				}
				newMols.add(tempMol);
			}
		}
		simulation.addMolecules(newMols);
	}
}
