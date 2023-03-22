package fifteenpuzzle;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Solver {

	// Initialize privtate variables
	private int boardSize = 0;

	// Function that creates and formats the board that will be solved
	private Solver(File intake) {

	}

	// Function that solves the puzzle
	//private boolean

	// Fucntion that checks that the board is correct
	private boolean boardCorrect() {
		// For loop that runs through depending on the size of the puzzle to see if it is correct
		for(int i = 0; i < (boardSize-1); i++) {
			//if( != (i+1)) {
			//	return false;
			// }
		}
		// If no problems are detected during the check
		return true;
	}

	// Function that takes the export file and fills it with the answer
	private void exporter(File export) {

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
		// solve...
		//Solver brandnew = new Solver(input);


		// Check if the solution is correct


		File output = new File(args[1]);
		//brandnew.exporter(output);
	}
}
