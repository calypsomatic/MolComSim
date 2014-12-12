import java.util.Scanner;

/**
 * Stores the positions of the endpoints
 * for a microtubule
 *
 */

public class MicrotubuleParams {

	Position plusEndPoint;
	Position minusEndPoint;
	double radius;
	
	public MicrotubuleParams(Position plus, Position minus, double radius){
		this.plusEndPoint = plus;
		this.minusEndPoint = minus;
		this.radius = radius;
	}
	
	public MicrotubuleParams(Scanner readParams) {
		plusEndPoint = new Position(readParams);
		minusEndPoint = new Position(readParams);
		radius = readParams.nextDouble();
	}

	public Position getPlusEndPoint(){
		return this.plusEndPoint;
	}
	
	public Position getMinusEndPoint(){
		return this.minusEndPoint;
	}
	
	public double getRadius(){
		return this.radius;
	}

}
