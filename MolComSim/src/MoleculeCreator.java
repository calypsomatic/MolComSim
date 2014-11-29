/**
 * Creates molecules according to molParams
 * and places them at a starting point
 */

import java.io.*;
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
	
	//TODO: Are these movementcontrollers good without having their molecule
	//explicitly assigned to them?  How to determine if there are nearby molecules?
	//TODO: This entire method is crap.  Make helper methods or use some kind of design pattern
	//Currently assigns same movementcontrollers and collisionhandlers to multiple molecules
	//That needs to be fixed for sure
	public void createMolecules() {
		ArrayList<Molecule> newMols = new ArrayList<Molecule>();
		for (MoleculeParams mp : molParams){
			MoleculeType molType = mp.getMoleculeType();
			double rad = mp.getRadius();
			MoleculeMovementType molMoveType = mp.getMoleculeMovementType();
			MovementController mover;
			CollisionHandler collH;
			if (molMoveType.equals(MoleculeMovementType.ACTIVE)){
				boolean nearbyMT = false;
				Microtubule microtubule = null;
				for (Microtubule mt : simulation.getMicrotubules()){
					if (position.getDistance(mt.getMinusEndCenter()) + mt.getRadius() < rad){
						nearbyMT = true;
						microtubule = mt;
						break;
					}
				}
				if (nearbyMT){
					collH = simulation.isUsingCollisions() ? new OnTubuleCollisionHandler() : new NullCollisionHandler();
					mover = new OnMicrotubuleMovementController(collH, simulation, microtubule);
				}
				else{
					collH = simulation.isUsingCollisions() ? new StandardCollisionHandler() : new NullCollisionHandler();
					mover = new DiffusiveRandomMovementController(collH, simulation);
				}
			}
			else if (molMoveType.equals(MoleculeMovementType.PASSIVE)){
				collH = simulation.isUsingCollisions() ? new OnTubuleCollisionHandler() : new NullCollisionHandler();
				mover = new DiffusiveRandomMovementController(collH, simulation);
			}
			else if (molMoveType.equals(MoleculeMovementType.NONE)){
				collH = new NullCollisionHandler();
				mover = new NullMovementController(collH, simulation);
			} else {
				//TODO: error management
				mover = null;
			}
			//TODO: make adding movement type work
			//also current message id
			for (int i = 0; i < mp.getNumMolecules(); i++){
				Molecule tempMol;
				if (molType.equals(MoleculeType.ACK)){
					tempMol = new AcknowledgementMolecule(mover,
							position, rad, simulation, source, source.getCurrMsgId(),molMoveType);
				}
				else if (molType.equals(MoleculeType.INFO)){
					tempMol = new InformationMolecule(mover,
							position, rad, simulation, source, source.getCurrMsgId(), molMoveType);
				}
				else if (molType.equals(MoleculeType.NOISE)){
					tempMol = new NoiseMolecule(mover,
							position, rad, simulation, molMoveType);
				}
				else {
					//TODO: Error management?
					tempMol = null;
				}
				newMols.add(tempMol);
			}
			}
			simulation.addMolecules(newMols);
	}
}