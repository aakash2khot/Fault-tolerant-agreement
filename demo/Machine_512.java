package demo;

import java.util.*;
import java.util.ArrayList;
import common.Location;
import common.Machine;

public class Machine_512 extends Machine {
	private int decision_0;
	private ArrayList<Machine> machines_list = new ArrayList<Machine>();
	private boolean State;
	private boolean if_leader = false;
	private int left = 0;
	private int right = 0;
	private int final_decision = 2;
	private boolean phase_finished = false;
	private boolean east = true, west = false, south = false, north = false;
	private boolean round_1_completed = false;
	private boolean round_2_completed = false;
	private int phase_number = 0;

	public Machine_512() {
		id = nextId++;

	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines_list.addAll(machines);
	}

	@Override
	public void setStepSize(int stepSize) {
		step = stepSize;
	}

	@Override
	public void setState(boolean isCorrect) {
		State = isCorrect;
	}

	@Override
	public void setLeader() {
		if_leader = true;
		Random rand = new Random();
		int decision_of_leader = rand.nextInt(2);
		if (State == true) {
			for (int i = 0; i < machines_list.size(); i++) {
				machines_list.get(i).sendMessage(id, phase_number, 0, decision_of_leader);
			}
		} else {
			int t = machines_list.size() / 3;
			int avoiding = rand.nextInt(t + 1);
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < machines_list.size(); i++) {
				list.add(i);
			}
			Collections.shuffle(list);
			for (int i = avoiding; i < machines_list.size(); i++) {
				machines_list.get(list.get(i)).sendMessage(id, phase_number, 0, decision_of_leader);
			}
		}

	}

	@Override
	public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) {
		if (roundNum == 0) {
			decision_0 = decision;
			round_2_completed = false;
			round_1_completed = false;
			if (State == true) {
				for (int i = 0; i < machines_list.size(); i++) {
					machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, decision_0);
				}
			} else {
				Random rand = new Random();
				int decision_of_faulty = rand.nextInt(3);
				if (decision_of_faulty == 0) {
					for (int i = 0; i < machines_list.size(); i++) {
						machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, decision_of_faulty);
					}
				} else if (decision_of_faulty == 1) {
					for (int i = 0; i < machines_list.size(); i++) {
						machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, decision_of_faulty);
					}
				}

			}

		} else if (roundNum == 1) {
			// for(int i=0;i<machines_list.size();i++){
			// machines_list.get(i).sendMessage(id, phaseNum, roundNum, decision_0);
			// }
			if (round_1_completed == false) {
				if (decision == 0) {
					left++;
				} else {
					right++;
				}
				if (left > (int) (machines_list.size() / 3 )|| right > (int) (machines_list.size() / 3)) {
					if (State == true) {
						if (left > right) {

							round_1_completed = true;
							left = 0;
							right = 0;
							for (int i = 0; i < machines_list.size(); i++) {
								machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, 0);
							}
						} else if (right > left) {

							round_1_completed = true;
							left = 0;
							right = 0;
							for (int i = 0; i < machines_list.size(); i++) {
								machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, 1);
							}
						}
					} else {
						round_1_completed = true;
						Random rand = new Random();
						int decision_of_faulty = rand.nextInt(3);
						if (decision_of_faulty == 0) {
							for (int i = 0; i < machines_list.size(); i++) {
								machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, decision_of_faulty);
							}
						} else if (decision_of_faulty == 1) {
							for (int i = 0; i < machines_list.size(); i++) {
								machines_list.get(i).sendMessage(id, phaseNum, roundNum + 1, decision_of_faulty);
							}
						}
					}

				}

			}

		} else if (roundNum == 2) {
			// for (int i = 0; i < machines_list.size(); i++) {
			// if (left > right) {
			// machines_list.get(i).sendMessage(id, phaseNum, roundNum, 0);
			// } else {
			// machines_list.get(i).sendMessage(id, phaseNum, roundNum, 1);
			// }
			// }
			if (round_2_completed == false) {
				if (State == true) {
					if (decision == 0) {
						left++;
					} else {
						right++;
					}
					if (left > right && left > 2 * (int)( machines_list.size() / 3)) {
						phase_number++;
	                    left = 0;
						right = 0;
						final_decision = 0;
						round_2_completed = true;
						phase_finished = true;
					} else if (right > left && right > 2 *(int)( machines_list.size() / 3) ){
						phase_number++;
						left = 0;
						right = 0;
						final_decision = 1;
						round_2_completed = true;
						phase_finished = true;
					}
				} else {
					Random rand = new Random();
					final_decision = rand.nextInt(3);
					phase_number++;
					round_2_completed = true;
				}

			}
		}

	}

	@Override
	public void move() {
		if (final_decision != 2) {
			
			if (east == true) {
				if (final_decision == 0) {
					dir.setLoc(0, 1);
					east = false;
					north = true;
					west=false;
					south = false;
				} else {
					dir.setLoc(0, -1);
					east = false;
					south = true;
					west = false;
					north = false;
				}
			} else if (west == true) {
				if (final_decision == 0) {
					dir.setLoc(0, -1);
					west = false;
					south = true;
					north = false;
					east = false;
				} else {
					dir.setLoc(0, 1);
					west = false;
					south = false;
					east= false;
					north = true;
				}
			} else if (north == true) {
				if (final_decision == 0) {
					dir.setLoc(-1, 0);
					north = false;
					east= false;
					south = false;
					west = true;
				} else {
					dir.setLoc(1, 0);
					north = false;
					south= false;
					west = false;
					east = true;
				}
			} else if (south == true) {
				if (final_decision == 0) {
					dir.setLoc(1, 0);
					south = false;
					north = false;
					west = false;
					east = true;
				} else {
					dir.setLoc(-1, 0);
					south = false;
					east = false;
					north = false;
					west = true;
				}
			}
			final_decision = 2;
			pos.setLoc(pos.getX() + dir.getX() * step, pos.getY() + dir.getY() * step);
		} else {
			pos.setLoc(pos.getX() + dir.getX() * step, pos.getY() + dir.getY() * step);
		}
	}

	@Override
	public String name() {
		return "demo_" + id;
	}

	@Override
	public Location getPosition() {

		return new Location(pos.getX(), pos.getY());
	}

	private int step;
	private Location pos = new Location(0, 0);
	private Location dir = new Location(0, 1); // using Location as a 2d vector. Bad!
	private static int nextId = 0;
	private int id;

}
