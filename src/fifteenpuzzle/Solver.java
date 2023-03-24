package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Scanner;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// Author: Peiman Zhiani Asgharzadeh
// Computing ID: pza42
// Student #: 301438914

// Author: Bryce Leung
// Computing ID: bla135
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
	Tile candidate;
	LinkedList<Tile> solutionPath;

	// experimental class for puzzle node eases the HasMap making
	class PuzzleNode {
		Tile predecessor;
		int depth;
		int score;

		public PuzzleNode(Tile predecessor, int depth, int score) {
			this.predecessor = predecessor;
			this.depth = depth;
			this.score = score;
		}
	}

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
		for (positional currentPosition : candidatePuzzle.allTilePos()){
			//get the val of the tile at current position
			int currentValue = candidatePuzzle.getValue(currentPosition);

			if (currentValue > 0) {
				positional targetPosition = solved.findPosition(currentValue);// Get the target position of the current tile in the solved state
				//Calculate the Vertical and Horizontal distance between the current position and the target position
				int horizontalDistance = Math.abs(targetPosition.x = currentPosition.x);
				int verticalDistance = Math.abs(targetPosition.y = currentPosition.y);
				total = total + verticalDistance + horizontalDistance;
			}
		}

		//Return the total sum of Manhattan distances for all non-blank tiles
		return total;
	}

	public List<Tile> AdjacentNodes(Tile currentBoard){
		ArrayList<Tile> adjacentNodes = new ArrayList<>();
		ArrayList<positional> options = currentBoard.movementOptions();

		options.forEach(option-> {
			Tile newPuzzle = new Tile(currentBoard,option);
			adjacentNodes.add(newPuzzle);
		});

		return adjacentNodes;
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
	public boolean AStarAlgorithm(HeuristicType heuristicType) {
		//TODO maybe output to a global string list or something that track all moves performed so we can use it
		Map<Tile, PuzzleNode> nodes = new HashMap<>();
		// this is the custome comparator to use in priority queue, in order to dequeue, the lowest score first.
		Comparator<Tile> scoreCompare = (a,b) -> nodes.get(a).score - nodes.get(b).score;
		PriorityQueue<Tile> nodesToCheck = new PriorityQueue<>(10000, scoreCompare);

		// initialize our nodes (given the initial puzzle, and null predecessor, 0 deoth and finding initial heurisitcs)
		nodes.put(this.puzzle,new PuzzleNode(null,0,heuristicCost(heuristicType,this.puzzle)));
		nodesToCheck.add(this.puzzle);
		int totalVisitedNodes = 0;
		//do the following until the priority queue is empty.
		while (!nodesToCheck.isEmpty()){
			candidate = nodesToCheck.remove();
			// if solution was found then return base on the predecessor making it a path.
			totalVisitedNodes++;

			if (candidate.isSolved()){
				System.out.printf("the solution was found after visiting %d nodes\n ",totalVisitedNodes);
				solutionPath = new LinkedList<>();
				Tile backTrace = candidate;
				while (backTrace != null){
					solutionPath.add(backTrace);
					backTrace = nodes.get(backTrace).predecessor;
				}
				return true;
			}
			// if not get the candidate's adjacent nodes, and calculate their heurestics, and put them in priority queue
			List<Tile> adjacentNodes = AdjacentNodes(candidate);
			adjacentNodes.forEach(node ->{
				if (!nodes.containsKey(node)){
					int newDepth =nodes.get(candidate).depth + 1;
					// f(Score) = f(heuristic) + f(currentdepth)
					int newScore = newDepth + heuristicCost(heuristicType,node);
					// update the prodecessor, depth and, hscore for this adjacent node.

					nodes.put(node,new PuzzleNode(candidate, newDepth, newScore));
					//add the node to the priority queue with its h(score)
					nodesToCheck.add(node);
				}
			});

		}
		return false;
	}


	// Obtains information for movement base off of list provided
	private ArrayList<String> ansFormat() {
		// Initialize list and variables
		ArrayList<String> outputs = new ArrayList<>();
		Tile current;
		Tile next;
		positional newEmptyPosition;
		positional valueNewPosition;
		char direction = 'A';
		int movedValue;

		current = solutionPath.removeLast();

		while(!solutionPath.isEmpty()) {
			next = solutionPath.removeLast();

			// Finds the value being moved
			newEmptyPosition = next.findPosition(0);
			movedValue = current.getValue(newEmptyPosition);

			// Figure out the direction taken
			valueNewPosition = next.findPosition(movedValue);
			// Move right
			if((valueNewPosition.getY() > newEmptyPosition.getY())) {
				direction = 'R';
			}
			// Move left
			else if((valueNewPosition.getY() < newEmptyPosition.getY())) {
				direction = 'L';
			}
			// Move up
			else if((valueNewPosition.getX() < newEmptyPosition.getX())) {
				direction = 'U';
			}
			// Move down
			else {
				direction = 'D';
			}

			// place the instruction into the string array
			String in = movedValue + " " + direction;
			outputs.add(in);

			current = next;
		}

		return outputs;
	}



	// Prints out the solution to the output file
	public void writeSolution(List<String> outputSolution, String filepath) throws IOException {

		Path path = Paths.get(filepath);

		try {
			// Create a new file if it doesn't exist, otherwise open the existing file for writing
			if (!Files.exists(path)) {
				Files.createFile(path);
			}

			// Write the lines to the file, appending the content if the file exists
			Files.write(path, outputSolution, StandardOpenOption.APPEND);
			System.out.println("Lines written to file: " + filepath);
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}


//
//		//TODO add writing logic here once the list of movements when available
////		outputs = ansFormat();
//
//		// Close the output file
//		cleanup.close();
//
//		// Output message after completely filling out file
//		System.out.println("Puzzle has been solved solution has been placed in " + out);
	}


	public static void main(String [] args) throws IOException {

		// Prints out the number of arguments provided
		System.out.println("number of arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		// Checks if an argument has been passed in by the user
		//if (args.length < 2) {
		//	System.out.println("File names are not specified");
		//		System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
		//		return;
		//	}

		// Set up the File type variables from the arguments provided by the user
		//File input = new File(args[0]);
		//File output = new File(args[1]);

		File input = new File("board2.txt");
//		File output = new File("sol2.txt");

		// Initialize the solver object with the input board file
		Solver compute = new Solver(input);

		// Perform the A* algorithm to find the solution using ManHattan Distance heuristics
		if(compute.AStarAlgorithm(HeuristicType.MANHATTAN_DISTANCE)) {
			String filePath = "sol1.txt";
			// Write the solution to the specified output file
			List<String> output = compute.ansFormat();
			compute.writeSolution(output,filePath);
		}
	}

}
