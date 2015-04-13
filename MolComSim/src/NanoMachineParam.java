import java.util.Scanner;


public class NanoMachineParam {
	private Position center;
	private Position molReleasePt;
	private double radius;
	
	public NanoMachineParam(Position cntr, double r, Position moleculeReleasePoint) {
		center = cntr;
		radius = r;
		molReleasePt = moleculeReleasePoint;
	}
	
	public NanoMachineParam(Scanner readParams) {
		center = new Position(readParams);
		if(readParams.hasNextDouble()) {
			radius = readParams.nextDouble();
		} else {
			radius = 1.0;
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
	
	public double getRadius(){
		return radius;
	}

}
