/**
 * Parameters for a NanoMachine
 */

import java.util.Scanner;


public class NanoMachineParam {
	private Position center;
	private Position molReleasePt;
	private int radius;
	
	public NanoMachineParam(Position cntr, int r, Position moleculeReleasePoint) {
		center = cntr;
		radius = r;
		molReleasePt = moleculeReleasePoint;
	}
	
	public NanoMachineParam(Scanner readParams) {
		center = new Position(readParams);
		if(readParams.hasNextDouble()) {
			radius = readParams.nextInt();
		} else {
			radius = 1;
		}
		if(readParams.hasNext()) {
			molReleasePt = new Position(readParams);
		} else {
			molReleasePt = new Position(center.getX(), center.getY(), center.getZ());
		}
	}

	public Position getCenter(){
		return center;
	}
	
	public Position getMolReleasePoint(){
		return molReleasePt;
	}
	
	public int getRadius(){
		return radius;
	}

}
