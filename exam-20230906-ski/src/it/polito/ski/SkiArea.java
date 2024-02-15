package it.polito.ski;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SkiArea {
	String name;
	Map<String, LiftType> liftTypes = new HashMap<>();
	Map<String, Lift> lifts = new HashMap<>();
	Map<String, Slope> slopes = new HashMap<>();
	Map<String, Parking> parkings = new HashMap<>();
	/**
	 * Creates a new ski area
	 * @param name name of the new ski area
	 */
	public SkiArea(String name) {
		this.name = name;
    }

	/**
	 * Retrieves the name of the ski area
	 * @return name
	 */
	public String getName() { 
		return this.name; 
	}

    /**
     * define a new lift type providing the code, the category (Cable Cabin, Chair, Ski-lift)
     * and the capacity (number of skiers carried) of a single unit
     * 
     * @param code		name of the new type
     * @param category	category of the lift
     * @param capacity	number of skiers per unit
     * @throws InvalidLiftException in case of duplicate code or if the capacity is <= 0
     */
    public void liftType(String code, String category, int capacity) throws InvalidLiftException {
    	if(capacity <= 0)throw new InvalidLiftException();
    	if(liftTypes.containsKey(code))throw new InvalidLiftException();
    	
    	LiftType type = new LiftType(code,category,capacity);
    	liftTypes.put(code, type);
    }
    
    /**
     * retrieves the category of a given lift type code
     * @param typeCode lift type code
     * @return the category of the type
     * @throws InvalidLiftException if the code has not been defined
     */
    public String getCategory(String typeCode) throws InvalidLiftException {
    	if (!liftTypes.keySet().contains(typeCode)) throw new InvalidLiftException();
    	LiftType type = liftTypes.get(typeCode);
		
		return type.getCategory();
    }

    /**
     * retrieves the capacity of a given lift type code
     * @param typeCode lift type code
     * @return the capacity of the type
     * @throws InvalidLiftException if the code has not been defined
     */
    public int getCapacity(String typeCode) throws InvalidLiftException {
    	if (!liftTypes.keySet().contains(typeCode)) throw new InvalidLiftException();
    	LiftType type = liftTypes.get(typeCode);
		
		return type.getCapacity();
    }


    /**
     * retrieves the list of lift types
     * @return the list of codes
     */
	public Collection<String> types() {
		return liftTypes.keySet();
	}
	
	/**
	 * Creates new lift with given name and type
	 * 
	 * @param name		name of the new lift
	 * @param typeCode	type of the lift
	 * @throws InvalidLiftException in case the lift type is not defined
	 */
    public void createLift(String name, String typeCode) throws InvalidLiftException{
    	if (!liftTypes.keySet().contains(typeCode)) throw new InvalidLiftException();
    	
    	Lift lift = new Lift(name, typeCode);
    	lifts.put(name, lift);
    }
    
	/**
	 * Retrieves the type of the given lift
	 * @param lift 	name of the lift
	 * @return type of the lift
	 */
	public String getType(String lift) {
		return lifts.get(lift).getTypeCode();
	}

	/**
	 * retrieves the list of lifts defined in the ski area
	 * @return the list of names sorted alphabetically
	 */
	public List<String> getLifts(){
		return lifts.keySet().stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

	/**
	 * create a new slope with a given name, difficulty and a starting lift
	 * @param name			name of the slope
	 * @param difficulty	difficulty
	 * @param lift			the starting lift for the slope
	 * @throws InvalidLiftException in case the lift has not been defined
	 */
    public void createSlope(String name, String difficulty, String lift) throws InvalidLiftException {
    	Lift startLift = lifts.get(lift);
    	if(startLift == null) throw new InvalidLiftException();
    	
    	Slope slope = new Slope(name, difficulty, lift);
    	slopes.put(name, slope);
    	
    	
    }
    
    /**
     * retrieves the name of the slope
     * @param slopeName name of the slope
     * @return difficulty
     */
	public String getDifficulty(String slopeName) {
		return slopes.get(slopeName).getDifficulty();
	}

	/**
	 * retrieves the start lift
	 * @param slopeName name of the slope
	 * @return starting lift
	 */
	public String getStartLift(String slopeName) {
		return slopes.get(slopeName).getLift();
	}

	/**
	 * retrieves the list of defined slopes
	 * 
	 * @return list of slopes
	 */
    public Collection<String> getSlopes(){
		return slopes.keySet();
    }

    /**
     * Retrieves the list of slopes starting from a given lift
     * 
     * @param lift the starting lift
     * @return the list of slopes
     */
    public Collection<String> getSlopesFrom(String lift){
		return slopes.values().stream().filter(s -> s.getLift().equals(lift)).map(s -> s.getName()).collect(Collectors.toList());
    }

    /**
     * Create a new parking with a given number of slots
     * @param name 	new parking name
     * @param slots	slots available in the parking
     */
    public void createParking(String name, int slots){
    	Parking parking = new Parking(name,slots);
    	parkings.put(name, parking);
    	
    }

    /**
     * Retrieves the number of parking slots available in a given parking
     * @param parking	parking name
     * @return number of slots
     */
	public int getParkingSlots(String parking) {
		return parkings.get(parking).getSlots();
	}

	/**
	 * Define a lift as served by a given parking
	 * @param lift		lift name
	 * @param parking	parking name
	 */
	public void liftServedByParking(String lift, String parking) {
		Parking p = parkings.get(parking);
		Lift l = lifts.get(lift);
		p.addLiftServed(l);
	}

	
	/**
	 * Retrieves the list of lifts served by a parking.
	 * @param parking	parking name
	 * @return the list of lifts
	 */
	public Collection<String> servedLifts(String parking) {
		Parking p = parkings.get(parking);
		return p.getLiftsServed().stream().map(l -> l.getName()).collect(Collectors.toList());
	}

	/**
	 * Checks whether the parking is proportional to the capacity of the lift it is serving.
	 * A parking is considered proportionate if its size divided by the sum of the capacity of the lifts 
	 * served by the parking is less than 30.
	 * 
	 * @param parkingName name of the parking to check
	 * @return true if the parking is proportionate
	 */
	public boolean isParkingProportionate(String parkingName) {
		Parking parking = parkings.get(parkingName);
		int size = parking.getSlots();
		int sum = parking.getLiftsServed().stream().map(l -> liftTypes.get(l.getTypeCode()).getCapacity()).reduce(0, (a,b)-> a+b);
		
		int num = size / sum;
		if (num<30) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * reads the description of lift types and lift descriptions from a text file. 
	 * The contains a description per line. 
	 * Each line starts with a letter indicating the kind of information: "T" stands for Lift Type, 
	 * while "L" stands for Lift.
	 * A lift type is described by code, category and seat number. 
	 * A lift is described by the name and the lift type.
	 * Different data on a line are separated by ";" and possible spaces surrounding the separator are ignored.
	 * If a line contains the wrong number of information it should be skipped and
	 * the method should continue reading the following lines. 
	 * 
	 * @param path 	the path of the file
	 * @throws IOException	in case IO error
	 * @throws InvalidLiftException in case of duplicate type or non-existent lift type
	 */
    public void readLifts(String path) throws IOException, InvalidLiftException {
    	Path pathOf = Path.of(path);
    
    	try {
    		List<String> rLifts = Files.readAllLines(pathOf);
    
        	for(String parted : rLifts) {
        		String[] part = parted.split(";");
        		if (part[0].strip().equals("L")) {
        			
        			if(part.length == 3) {
        				String name = part[1].strip();
                		String typecode = part[2].strip();
                		if (!liftTypes.containsKey(typecode)) throw new InvalidLiftException();
                		Lift lift = new Lift(name, typecode);
                		lifts.put(name, lift);
        			}
        			
        			
        		} 
        		
        		if (part[0].strip().equals("T")) {
        			
        			if(part.length == 4) {
        				String code = part[1].strip();
                		String category = part[2].strip();
                		Integer seatCount = Integer.parseInt(part[3].strip());
                		if(liftTypes.containsKey(code)) throw new InvalidLiftException();
                		LiftType liftType = new LiftType(code, category, seatCount);
                		liftTypes.put(code, liftType);
        			}
        			
        		}
        	}
    	} 
    	catch (IOException ioe) {
			 ioe.printStackTrace();
		}		
    	
    }

}
