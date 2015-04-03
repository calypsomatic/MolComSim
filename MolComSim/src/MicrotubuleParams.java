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
	
<<<<<<< HEAD
	public MicrotubuleParams(Position plus, Position minus, int radius){
		this.startPoint = plus;
		this.endPoint = minus;
	}
	
=======
>>>>>>> d0a004491afb8546b2a4567a755d87bb68e87a13
	public MicrotubuleParams(Position start, Position end, double radius){
		startPoint = start;
		endPoint = end;
		this.radius = radius;
	}
	
	public MicrotubuleParams(Scanner readParams) {
		startPoint = new Position(readParams);
		endPoint = new Position(readParams);
<<<<<<< HEAD
		radius = readParams.nextInt();
=======
		radius = readParams.nextDouble(); 
>>>>>>> d0a004491afb8546b2a4567a755d87bb68e87a13
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
