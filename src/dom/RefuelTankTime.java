package dom;

import java.util.Vector;

public class RefuelTankTime {
	
	Vector<Integer> refuel;
	Vector<Double> tank;
	Vector<Double> distances; // voor het gemak in Route heb ik distances hier toegevoegd, maar nog niet in de class name. makkelijker met indexen
	double current_time=0;
	public boolean valid=false;
	
	/**
	 * Copy Constructor
	 * @param copy to be copied from
	 */
	RefuelTankTime(RefuelTankTime copy){
		this.refuel=copy.refuel;
		this.tank=copy.tank;
		this.distances=copy.distances;
		this.current_time=copy.current_time;
		this.valid=copy.valid;
	}
	
	// TODO clone vectors? Check!
	RefuelTankTime(Vector<Integer> refuel, Vector<Double> tank, Vector<Double> distances, double time){
		this.refuel=(Vector<Integer>) refuel.clone();			//this.refuel = (Vector<Integer>) refuel.clone() ofzoiets?
		this.tank=(Vector<Double>) tank.clone();			
		this.distances=(Vector<Double>) distances.clone();
		this.current_time=time;
	}
	
	RefuelTankTime(Vector<Integer> refuel, Vector<Double> tank, double time, boolean valid){
		this.refuel=refuel;		// wordt dit geen reference naar de refuel die je meegeeft? Weet niet of je hier een copy moet maken door nieuw object ipv dit
		this.tank=tank;			// ,,
		this.current_time=time;
		this.valid=valid;
	}
	
	RefuelTankTime(){
		refuel = new Vector<Integer>();
		tank = new Vector<Double>();
	}

}
