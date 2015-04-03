/**
 * NoiseMoleculeCreator is a MoleculeCreator that creates
 * sedentary noise molecules randomly throughout the medium
 */

import java.util.*;

public class NoiseMoleculeCreator extends MoleculeCreator{

	public NoiseMoleculeCreator(ArrayList<MoleculeParams> noiseMoleculeParams, MolComSim sim) {
		super(noiseMoleculeParams, sim);
	}

	/**
	 * Create molecules and populate them through the medium
	 */
	public void createMolecules() {
		ArrayList<Molecule> noiseMolecules = new ArrayList<Molecule>();
		//TODO: check these values to make sure they're not occupied
		for (MoleculeParams nmp : molParams){
			for (int i = 0; i < nmp.getNumMolecules(); i++){
				double x = Math.random()*simulation.getSimParams().getMediumHeight();
				double y = Math.random()*simulation.getSimParams().getMediumLength();
				double z = Math.random()*simulation.getSimParams().getMediumWidth();
				Position randomPos = new Position(x, y, z);
				NoiseMolecule tempmol = new NoiseMolecule(randomPos, nmp.getRadius(), simulation, nmp.getMoleculeMovementType());
				new NullMovementController(new NullCollisionHandler(), simulation, tempmol);
				noiseMolecules.add(tempmol);
			}
		//create molecules using noise molecule params with positions randomly distributed throughout the medium (simulation.getMedium().getlength()…)
			simulation.addMolecules(noiseMolecules);
		}
	}

}
