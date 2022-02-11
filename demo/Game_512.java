package demo;
import java.util.*;
import java.util.ArrayList;
import common.Game;
import common.Machine;

public class Game_512 extends Game {
	private ArrayList<Machine> machines = new ArrayList<Machine>();
    private int total_machines;
    private int faulty_machines;
	@Override
	public void addMachines(ArrayList<Machine> machines, int numFaulty) {
		// TODO Auto-generated method stub
		this.machines.addAll( machines );
        total_machines = machines.size();
        faulty_machines = numFaulty;
	}

	@Override
	public void startPhase() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<machines.size();i++){
			list.add(i);
		}
		Collections.shuffle(list);
		for(int i=0;i<faulty_machines;i++){
			machines.get(list.get(i)).setState(false);
		}
		for(int i=faulty_machines;i<machines.size();i++){
			machines.get(list.get(i)).setState(true);
		}
        Random rand = new Random();
		int leader = rand.nextInt(total_machines);
		machines.get(leader).setLeader();

		// TODO Auto-generated method stub
		
	}

}
