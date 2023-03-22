package fifteenpuzzle;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;


public class Solver {

	//TODO Initialize privtate variables
	private int boardSize = 0;
	private int trackedMoves = 0;



	//TODO Function that checks if the puzzle is solvable
	private boolean canSolvePuzzle() {

		return true;
	}

	//TODO Function that creates and formats the board that will be solved
	private Solver(File intake) {

	}

	//TODO Function that solves the puzzle
	//private boolean

	//TODO Function that checks that the board is correct
	private boolean boardCorrect() {
		return true;
	}

	//TODO Function that takes the export file and fills it with the answer
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
