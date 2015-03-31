import java.util.Scanner;

/**
 * Stores the positions of the endpoints
 * for a microtubule
 *
 */

public class MicrotubuleParams {

	Position startPoint;
	Position endPoint;
	double radius;
	
	public MicrotubuleParams(Position start, Position end, double radius){
		startPoint = start;
		endPoint = end;
		this.radius = radius;
	}
	
	public MicrotubuleParams(Scanner readParams) {
		startPoint = new Position(readParams);
		endPoint = new Position(readParams);
		radius = readParams.nextDouble(); 
	}

	public Position getEndPoint(){
		return endPoint;
	}
	
	public Position getStartPoint(){
		return startPoint;
	}
	
	public double getRadius(){
		return radius;
	}

}
