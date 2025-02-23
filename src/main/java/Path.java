import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class references the object representing the map where the drone will be flying, the class includes 
 * methods which follow the specified algorithm to find a path the destination of the drone
 */
public class Path {
	
	//INSTANCE VARIABLE
	/**
	 * The object representing the map which the drone will have to find a path to the customer through
	 */
	DroneMap cityMap;

	//CONSTRUCTOR
	/**
	 * The constructor of the Path object calls the constructor of DroneMap and passes it the file name that contains the data for the map of the city, 
	 * all exceptions thrown are from the constructor of the DroneMap object and are dealt with in the main
	 * @param filename The file which contains the data for constructing a map of the city for the drone, is passed to the DroneMap constructor
	 * @throws IOException Thrown when an invalid input is passed to the constructor
	 * @throws FileNotFoundException Thrown when there is no file with a matching name in the directory
	 * @throws InvalidMapException Thrown when the data in the given file is not consistent with the construction of the DroneMap object
	 */
	public Path(String filename) throws InvalidMapException, FileNotFoundException, IOException {

		this.cityMap = new DroneMap(filename);
	}

	//PUBLIC METHODS
	
	/**
	 * This method will find a path from the initial hex cell (UWO Store) to the end hex cell (Customer), or it will return no path if there is no possible path
	 * @param pathObject The path object that the drone is operating from
	 * @return String in the format "Path found of length x cells", where x is the number of hex cells in the path, or "No path found" if there is no possible path
	 */
	public String findPath(Path pathObject) {
		
		DroneStack<MapCell> flightPath = new DroneStack<MapCell>();

		//Inputs the starting cell into the DroneStack
		flightPath.push(cityMap.getStart());
		flightPath.peek().markInStack();
		

		try {
			while (!flightPath.peek().isCustomer() && !flightPath.isEmpty()) { //The drone continue searching until it reaches the customer or runs out of options and is forced back to the first hex cell

				if (nearTower(flightPath.peek())) { //Checks that the drone is not near a tower
					flightPath.pop().markOutStack();
				}

				if (nextCell(flightPath.peek()) == null) { //If there are no feasible options for the drone from this cell, move back a step
					flightPath.pop().markOutStack();
				} else { //If there is one move to that cell
					flightPath.push(nextCell(flightPath.peek()));
					flightPath.peek().markInStack();
				}
			}
		} catch (OverflowException | EmptyStackException e) { //Catches any errors with the array
			return "No path found";
		}

		if (flightPath.isEmpty()) { //returns end result
			return "No path found";
		} else {
			return "Path found of length " + flightPath.size() + " cells";
		}
	}

	/**
	 * Checks that there are no towers neighbouring the given cell
	 * @param cell The cell that is being checked
	 * @return boolean value of true if there is a tower neighbouring the given cell, false otherwise
	 */
	public boolean nearTower(MapCell cell) {
		for (int i = 0; i <= 5; i++) {
			try {
				if (cell.getNeighbour(i).isTower()) {
					return true;
				}
			} catch (NullPointerException e) {} //catch NullPointerException when one of the neighbouring cells is null
		}
		return false;
	}

	/**
	 * Determines the best next cell according to the criteria given in the assignment
	 * @param cell The current cell of the drone
	 * @return MapCell object of the best next cell
	 */
	public MapCell nextCell(MapCell cell) {

		MapCell highestPriority = cell.getNeighbour(0); //Set the best option to the first
		
		for (int i = 1; i <= 5; i++) { //For every surrounding cell:
			try {
				if ((higherPriority(highestPriority, cell.getNeighbour(i)) && !cell.getNeighbour(i).isMarked()) //If the cell of index i has higher priority based off its initially assigned type and it has not been marked 
						|| highestPriority.isMarked()) { //or if the current highest is marked: switch values
					highestPriority = cell.getNeighbour(i);
				}
			} catch (NullPointerException e) {} //catch NullPointerExpception when one of the neighbours is null

		}

		if (highestPriority == null) {
			return null;
		} else if (highestPriority.isMarked() || highestPriority.isNoFlying()) {
			return null;
		} else {
			return highestPriority;
		}
		
	}

	//HELPER or PRIVATE METHOD
	/**
	 * Hierarchy of cell type, returns true when higher is of greater base (ignoring marked status) value compared to other, false otherwise
	 * @param higher The presumed higher MapCell
	 * @param other The presumed lower MapCell
	 * @return boolean value true when higher is of greater base (ignoring marked status) value compared to other, false otherwise
	 */
	private static boolean higherPriority(MapCell higher, MapCell other) {
		
		if (higher == null) {
			return true;
		}

		try {

			switch (higher.getType()) {
			case FREE:
				return false;
			case CUSTOMER:
				return false;
			case HIGH_ALTITUDE:
				if (other.getType() == MapCell.CellType.FREE || other.getType() == MapCell.CellType.CUSTOMER) {
					return true;
				}
				return false;
			case THIEF:
				if (other.getType() == MapCell.CellType.FREE || other.getType() == MapCell.CellType.CUSTOMER
						|| other.getType() == MapCell.CellType.HIGH_ALTITUDE) {
					return true;
				}
				return false;
			case NO_FLYING:
				if (other.getType() == MapCell.CellType.FREE || other.getType() == MapCell.CellType.CUSTOMER
						|| other.getType() == MapCell.CellType.HIGH_ALTITUDE
						|| other.getType() == MapCell.CellType.THIEF) {
					return true;
				}
				return false;
			default:
				return false;
			}

		} catch (NullPointerException e) {
			return false;
		}
	}
	
	/**
	 * The main method of the file, creates a path object and executes the methods, catches and prints any exceptions
	 * @param args args[0] is the name of the file that is passed to the constructor
	 */
	public static void main(String args[]) {
		try {
			Path path = new Path(args[0]);
			System.out.print(path.findPath(path));
		} catch (InvalidMapException | IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
