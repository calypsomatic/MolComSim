/**
 * INFO stands for Information Molecules, which are transmitted
 * 	from a transmitter nanomachine
 * ACK stands for Acknowledgement Molecules, which are sent from
 *  receiver nanomachines in response to receiving INFO molecules
 * NOISE molecules are stationary molecules in the medium that are
 *  not involved with the communication but may interfere with
 *  the other molecules
 *
 */

public enum MoleculeType {

	INFO,
	ACK,
	NOISE
}
