package heuristic;

import java.util.Vector;

public class RefuelTankTime {
	
	Vector<Integer> refuel;
	Vector<Double> tank;
	double current_time=0;
	boolean valid=false;
	
	RefuelTankTime(Vector<Integer> refuel, Vector<Double> tank, double time, boolean valid){
		this.refuel=refuel;
		this.tank=tank;
		this.current_time=time;
		this.valid=valid;
	}
	
	RefuelTankTime(){
		valid=false;
	}

}
