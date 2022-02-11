package common;
import java.util.ArrayList;

public abstract class Game {

	// set up the game by passing in a list of Machines, and "t", the number of faulty machines
	public abstract void addMachines(ArrayList<Machine> machines, int numFaulty);

	// called when a new phase is to be started
	// selects a new leader, identifies the faulty machines, and initiates agreement
	public abstract void startPhase();
	
	
}
