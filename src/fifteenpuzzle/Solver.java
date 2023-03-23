package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Scanner;

// Author: Peiman Zhiani Asgharzadeh
// Computing ID: pza42
// Student #: 301438914

// Author: Bryce Leung
// Computing ID:
// Student #: 301421630


//TODO check if we are supposed to write to an output file or if we are supposed to create the output file

// References:
//https://www.baeldung.com/java-write-to-file
//https://www.youtube.com/watch?v=g0phuZDM6Mg
//https://en.wikipedia.org/wiki/A*_search_algorithm
//https://www.geeksforgeeks.org/sum-manhattan-distances-pairs-points/
//

public class Solver {
	// Initialize private global variables
	File solutionFile;
	Scanner cleanup;
	Tile puzzle;
	Tile solved;

	// Constructor that calls for the board to be made
	public Solver(File in) {
		// Passes the file to the Tile constructor to create the board that will be manipulated
		try {
			this.puzzle = new Tile(in);
			this.solved = puzzle.getSolution();
		}
		// If the board file given could not be found
		catch (Exception e) {
			System.out.println(in + "could not be read" + e);
			e.printStackTrace();
		}

	}

	//Add more function here as needed

	// Manhattan heuristic calculation
	//TODO add what must bee needed it is set as private as it will be called within the AStarAlgorithm
	private int ManhattanHeuristicDistance(Tile candidatePuzzle) {
		int total = 0;
		// for each tile position in all tile positions
		for (positional currentPosition : candidatePuzzle.movementOptions()){
			//get the val of the tile at current position
			int currentValue = candidatePuzzle.getValue(currentPosition);

			if (currentValue > 0) {
				positional targetPosition = solved.findPosition(currentValue);// Get the target position of the current tile in the solved state
				//Calculate the Vertical and Horizontal distance between the current position and the target position
				int horizontalDistance = Math.abs(targetPosition.getY() - currentPosition.getX());
				int verticalDistance = Math.abs(targetPosition.getX() - currentPosition.getY());
				total = total + verticalDistance + horizontalDistance;
			}
		}
		//Return the total sum of Manhattan distances for all non-blank tiles
		return total;
	}
	// enum creating the type for Heuristics in case of wants to switch between different heuristics
	public enum HeuristicType {
		NUMBER_MISPLACED_TILES,
		MANHATTAN_DISTANCE
		// more add here..
	}

	// chooses a Heuristic and can make different methods for different Heuristic incase want to make it dynamic
	public int heuristicCost(HeuristicType heuristicChosen, Tile candidatePuzzle ) {
		switch (heuristicChosen) {
			case MANHATTAN_DISTANCE:
				return ManhattanHeuristicDistance(candidatePuzzle);
//			case NUMBER_MISPLACED_TILES:
//				return numberMisplacedTiles(candidatePuzzle);
			default:
				throw new IllegalArgumentException("Invalid heuristic type");
		}
	}

	// A* algorithm that runs and performs the solving of the puzzle and outputs true if completed and false if it cannot be solved
	public void AStarAlgorithm() {
		//TODO maybe output to a global string list or something that track all moves performed so we can use it

	}


	// Prints out the solution to the output file
	public void writeSolution(File out) throws IOException {
		// Uses scanner to read the board file
		this.solutionFile = out;
		this.cleanup = new Scanner(this.solutionFile);

		// Outputs an error the file could not be found
		if (solutionFile == null){
			throw new FileNotFoundException("File Not Found Exception: Output file could not be found");
		}

		//TODO add writing logic here once the list of movements when available
		//while() {
		// }

		// Close the output file
		cleanup.close();

		// Output message after completely filling out file
		System.out.println("Puzzle has been solved solution has been placed in " + out);
	}


	public static void main(String [] args) throws IOException {
//		String [] args = {'board1.txt','board1out.txt'};

		// Prints out the number of arguments provided
		System.out.println("number of arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		// Checks if an argument has been passed in by the user
		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}

		// Set up the File type variables from the arguments provided by the user
		File input = new File(args[0]);
		File output = new File(args[1]);

		// Initialize the solver object with the input board file
		Solver compute = new Solver(input);

		// Perform the A* algorithm to find the solution
		compute.AStarAlgorithm();

		// Write the solution to the specified output file
		compute.writeSolution(output);
	}

}
