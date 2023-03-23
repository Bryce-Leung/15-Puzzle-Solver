package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Scanner;


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


	// A* algorithm that runs and performs the solving of the puzzle and outputs true if completed and false if it cannot be solved
	public void AStarAlgorithm() {
		//TODO maybe output to a global list that track all moves performed so we can use it
	}


	// Prints out the solution to the output file
	public void writeSolution(File out) throws IOException {
		// Uses scanner to read the board file
		this.solutionFile = out;
		this.cleanup = new Scanner(this.solutionFile);
		String lineIn = this.cleanup.nextLine();

		if (solutionFile == null){
			throw new FileNotFoundException("File Not Found Exception: Output file could not be found");
		}
	}


	public static void main(String[] args) {

		// Prints out the argument size provided
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


		//TODO
		File input = new File(args[0]);
		File output = new File(args[1]);

		// Utilize constructor to add the
		Solver compute = new Solver(input);


		compute.AStarAlgorithm();
		compute.writeSolution(output);
		System.out.println("Puzzle has been solved solution has been placed in " + output);
	}
}
