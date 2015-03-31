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

	public MoleculeCreator(ArrayList<MoleculeParams> mParams, MolComSim sim, NanoMachine src) {
		this.molParams = mParams;
		this.simulation = sim;
		this.source = src;
		this.position = src.getPosition();
	}
	
	//TODO: How to determine if there are nearby microtubules?
	//TODO: This entire method is crap.  Make helper methods or use some kind of design pattern
	public void createMolecules() { 
		ArrayList<Molecule> newMols = new ArrayList<Molecule>();
		for (MoleculeParams mp : molParams){
			MoleculeType molType = mp.getMoleculeType();
			double rad = mp.getRadius();
			MoleculeMovementType molMoveType = mp.getMoleculeMovementType();
			for (int i = 0; i < mp.getNumMolecules(); i++){
				Molecule tempMol;
				if (molType.equals(MoleculeType.ACK)){
					tempMol = new AcknowledgementMolecule(position, rad, simulation, source, source.getReceiverMessageId(),molMoveType);
				}
				else if (molType.equals(MoleculeType.INFO)){
					tempMol = new InformationMolecule(position, rad, simulation, source, source.getTransmitterMessageId(), molMoveType);
				}
				else if (molType.equals(MoleculeType.NOISE)){
					tempMol = new NoiseMolecule(position, rad, simulation, molMoveType);
				}
				else {
					//TODO: Error management?
					tempMol = null;
				}
				CollisionHandler collH;
				//Look for nearby microtubules if the molecules are ACTIVE
				if (molMoveType.equals(MoleculeMovementType.ACTIVE)){
					boolean nearbyMT = false;
					Microtubule microtubule = null;
					for (Microtubule mt : simulation.getMicrotubules()){
						//If a microtubule is found nearby, attach to it
						if (mt.isNearby(tempMol.getPosition(), tempMol.getRadius())){
							nearbyMT = true;
							microtubule = mt;
							break;
						}
					}
					if (nearbyMT){
						collH = simulation.isUsingCollisions() ? new OnTubuleCollisionHandler() : new NullCollisionHandler();
						new OnMicrotubuleMovementController(collH, simulation, tempMol, microtubule);		
					}
					else{
						collH = simulation.isUsingCollisions() ? new StandardCollisionHandler() : new NullCollisionHandler();
						new DiffusiveRandomMovementController(collH, simulation, tempMol);
					}
				}
				else if (molMoveType.equals(MoleculeMovementType.PASSIVE)){
					collH = simulation.isUsingCollisions() ? new OnTubuleCollisionHandler() : new NullCollisionHandler();
					new DiffusiveRandomMovementController(collH, simulation, tempMol);
				}
				else if (molMoveType.equals(MoleculeMovementType.NONE)){
					collH = new NullCollisionHandler();
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