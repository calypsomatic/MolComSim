/**
 * Parameters for a NanoMachine that has both a Transmitter and a Receiver
 */

import java.util.Scanner;


public class IntermediateNodeParam extends NanoMachineParam {
	private Position infoMolReleasePoint;
	private Position ackMolReleasePoint;
	
	public IntermediateNodeParam(Position cntr, int r, Position infoPoint, Position ackPoint) {
		super(cntr, r, cntr);
		infoMolReleasePoint = infoPoint;
		ackMolReleasePoint = ackPoint;
	}
	
	public IntermediateNodeParam(Scanner readParams) {
		super(readParams);
		infoMolReleasePoint = super.getMolReleasePoint();
		if(readParams.hasNext()) {
			ackMolReleasePoint = new Position(readParams);
		} else {
			ackMolReleasePoint = new Position(super.getCenter().getX(), super.getCenter().getY(), super.getCenter().getZ());
		}
	}

	public Position getInfoMolReleasePoint() {
		return infoMolReleasePoint;
	}
	
	public Position getAckMolReleasePoint() {
		return ackMolReleasePoint;
	}
	
	
}
