package fifteenpuzzle;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;


public class Solver {
	//call board to be made
	//

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
		// solve..



		//brandnew.exporter(output);
	}
}
