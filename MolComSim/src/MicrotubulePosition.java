
//TODO: Is this class ever used?  Erase if not
public class MicrotubulePosition {

	Position plusEndPoint;
	Position minusEndPoint;
	
	public MicrotubulePosition(Position plus, Position minus){
		this.plusEndPoint = plus;
		this.minusEndPoint = minus;
	}
	
	public Position getPlusEndPoint(){
		return this.plusEndPoint;
	}
	
	public Position getMinusEndPoint(){
		return this.minusEndPoint;
	}

}
