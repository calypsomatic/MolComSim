
public enum MoleculeType {

	INFO,
	ACK,
	NOISE;
	
	public static MoleculeType getMoleculeType(String stringRep) {
		if(stringRep.equals("INFO")) {
			return INFO;
		} else if(stringRep.equals("ACK")) {
			return ACK;
		} else if(stringRep.equals("NOISE")) {
			return NOISE;
		} else {
			throw new IllegalArgumentException("Invalid argument: " + stringRep + 
					" to MoleculeType.getMoleculeType"); 
		}	
	}
}
