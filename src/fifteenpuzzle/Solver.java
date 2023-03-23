package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Scanner;

//TODO check if we are supposed to write to an output file or if we are supposed to create the output file

// References:
//https://www.baeldung.com/java-write-to-file
//https://www.youtube.com/watch?v=g0phuZDM6Mg
//https://en.wikipedia.org/wiki/A*_search_algorithm
//

public class Solver {
	// Initialize private global variables
	File solutionFile;
	Scanner cleanup;



	// Constructor that calls for the board to be made
	public Solver(File in) {
		// Passes the file to the Tile constructor to create the board that will be manipulated
		try {
			Tile puzzle = new Tile(in);
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
	private int ManhattanHeuristic() {
		return 0;
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
		String lineIn = this.cleanup.nextLine();

		// Outputs an error the file could not be found
		if (solutionFile == null){
			throw new FileNotFoundException("File Not Found Exception: Output file could not be found");
		}

		//TODO add writing logic here once the list of movements are available

		// Close the output file
		cleanup.close();

		// Output message after completely filling out file
		System.out.println("Puzzle has been solved solution has been placed in " + out);
	}


	public static void main(String[] args) throws IOException {

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

		// Setup the File type variables from the arguments provided by the user
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
