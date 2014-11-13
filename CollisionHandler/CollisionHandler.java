package MComSim.CollisionHandler;

import java.io.*;
import java.util.*;

public abstract class CollisionHandler {

	public abstract Position handlePotentialCollisions(Molecule mol, Position nextPosition, MolComSim simulation);

}
