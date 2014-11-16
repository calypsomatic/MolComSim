package MComSim.SimulationParams-context;

import java.io.*;
import java.util.*;

public class SimulationParams {

	private String paramsFileName;
	private double mediumLength;
	private double mediumWidth;
	private double mediumHeight;
	private ArrayList<Position> transmitterPositions;
	private double transmitterRadius;
	private ArrayList<Position> receiverPositions;
	private double receiverRadius;
	private ArrayList<Position> intermediateNodePositions;
	private double intermediateNodeRadius;
	private ArrayList<Position> microtubulePlusEndPoints;
	private ArrayList<Position> microtubuleMinusEndPoints;
	private ArrayList<double> microtubuleRadii;
	private int numMessages;
	private int maxNumSteps;
	private int numRetransmissions;
	private int retransmitWaitTime;
	private boolean useCollisions;
	private double molRandMoveX;
	private double molRandMoveY;
	private double molRandMoveZ;
	private double velRail;
	private double probDRail;

	public int getMaxNumSteps() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getNumMessages() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public SimulationParams(String[] args) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void parseArgs(String[] args) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	private void readParamsFile(String fName) {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getMediumLength() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getMediumWidth() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getMediumHeight() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<MoleculeParams> getAllMoleculeParams() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<MoleculeParams> getNoiseMoleculeParams() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<MoleculeParams> getInformationMoleculeParams() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<MoleculeParams> getAcknowledgmentMoleculeParams() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Position> getTransmitterPositions() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Position> getReceiverPositions() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Position> getIntermediateNodePositions() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Position> getMicrotubulePlusEndPoints() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<Position> getMicrotubuleMinusEndPoints() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getTransmitterRadius() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getReceiverRadius() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getIntermediateNodeRadius() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public ArrayList<double> getMicrotubuleRadii() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getNumRetransmissions() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public int getRetransmitWaitTime() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean isUsingCollisions() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public boolean isUsingAcknowledgements() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getMolRandMoveX() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getMolRandMoveY() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getMolRandMoveZ() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getVelRail() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

	public double getProbDRail() {
		throw new UnsupportedOperationException("The method is not implemented yet.");
	}

}
