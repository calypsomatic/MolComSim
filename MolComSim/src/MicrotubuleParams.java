import java.util.Scanner;

/**
 * Stores the positions of the endpoints
 * for a microtubule
 *
 */

public class MicrotubuleParams {

	Position startPoint;
	Position endPoint;
	
	public MicrotubuleParams(Position start, Position end){
		startPoint = start;
		endPoint = end;
	}
	
	public MicrotubuleParams(Scanner readParams) {
		startPoint = new Position(readParams);
		endPoint = new Position(readParams);
	}

	public Position getEndPoint(){
		return endPoint;
	}
	
	public Position getStartPoint(){
		return startPoint;
	}
	
}
