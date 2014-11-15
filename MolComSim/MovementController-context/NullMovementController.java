package MComSim.MovementController-context;

import java.io.*;
import java.util.*;

public class NullMovementController extends MovementController {

	protected Position decideNextPosition() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public NullMovementController(CollisionHandler collHandle, MolComSim sim, Molecule mol) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
