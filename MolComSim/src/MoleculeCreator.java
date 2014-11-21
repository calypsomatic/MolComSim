/**
 * Creates molecules according to molParams
 * and places them at a starting point
 */

import java.io.*;
import java.util.*;

public class MoleculeCreator {

	private ArrayList<MoleculeParams> molParams;
	private MolComSim simulation;
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
	
	public void createMolecules() {
		/*ArrayList<Molecule> newMols = new ArrayList<Molecule>();
		for (MoleculeParams mp : molParams){
			MoleculeType molType = mp.getMoleculeType();
			double rad = mp.getRadius();
			MoleculeMovementType molMoveType = mp.getMoleculeMovementType();
			MovementController mover;
			CollisionHandler collH;
			if (simulation.isUsingCollisions()){
				if (onmicrotubule){
					collH = new OnTubuleCollisionHandler();
				}
				
			}
			else {
				collH = new NullCollisionHandler();
			}
			if (molMoveType.equals(MoleculeMovementType.ACTIVE)){
				if (nearby microtubules){
					mover = new OnMicroTubuleMovementController();
				}
				else{
					mover = new DiffusiveRandomMovementController();
				}
			}
			else if (molMoveType.equals(MoleculeMovementType.PASSIVE)){
				mover = new DiffusiveRandomMovementController();
			}
			else if (molMoveType.equals(MoleculeMovementType.NONE)){
				new NullMovementController();
			}
			//TODO: make adding movement type work
			//also current message id
			for (int i = 0; i < mp.getNumMolecules(); i++){
				if (molType.equals(MoleculeType.ACK)){
					Molecule tempMol = new AcknowledgementMolecule(new MovementController(molMoveType),
							position, rad, simulation, source, source.getCurrentMsgID(),molMoveType);
				}
				else if (molType.equals(MoleculeType.INFO)){
					Molecule tempMol = new InformationMolecule(new MovementController(molMoveType),
							position, rad, simulation, source, source.getCurrentMsgID(), molMoveType);
				}
				else if (molType.equals(MoleculeType.NOISE)){
					Molecule tempMol = new NoiseMolecule(new MovementController(molMoveType),
							position, rad, simulation, molMoveType);
				}
				else {
					//TODO: Error management?					
				}
				newMols.add(tempMol);
			}
			simulation.addMolecules(newMols);*/
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}
}