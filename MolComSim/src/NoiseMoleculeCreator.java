/**
 * NoiseMoleculeCreator is a MoleculeCreator that creates
 * stationary noise molecules randomly throughout the medium
 */

import java.util.*;

public class NoiseMoleculeCreator extends MoleculeCreator{

	public NoiseMoleculeCreator(ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
		super(noiseMoleculeParams, sim);
	}

	/**
	 * Create noise molecules and populate them through the medium
	 */
	public void createMolecules() {
		ArrayList<Molecule> noiseMolecules = new ArrayList<Molecule>();
		//Create a set of noise molecules for each set of parameters
		for (MoleculeParams nmp : molParams){
			for (int i = 0; i < nmp.getNumMolecules(); i++){
				//NoiseMolecules are placed randomly anywhere in the medium
				double x = Math.random()*simulation.getSimParams().getMediumHeight();
				double y = Math.random()*simulation.getSimParams().getMediumLength();
				double z = Math.random()*simulation.getSimParams().getMediumWidth();
				Position randomPos = new Position(x, y, z);
				NoiseMolecule tempmol = new NoiseMolecule(randomPos, nmp.getRadius(), simulation, nmp.getMoleculeMovementType());
				new NullMovementController(new NullCollisionHandler(), simulation, tempmol);
				noiseMolecules.add(tempmol);
			}
		}
		//Add all new molecules to the simulation
		simulation.addMolecules(noiseMolecules);
	}

}
