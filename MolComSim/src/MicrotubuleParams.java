/**
 * Stores the positions of the endpoints
 * and the radius of a microtubule
 *
 */

public class MicrotubuleParams {

	//TODO: Do these need better names?
	Position plusEndPoint;
	Position minusEndPoint;
	double radius;
	
	public MicrotubuleParams(Position plus, Position minus, double radius){
		this.plusEndPoint = plus;
		this.minusEndPoint = minus;
		this.radius = radius;
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
