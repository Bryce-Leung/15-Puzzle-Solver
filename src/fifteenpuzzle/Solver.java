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


//TODO check get intput output corrected, comment

// References:
//https://www.baeldung.com/java-write-to-file
//https://www.youtube.com/watch?v=g0phuZDM6Mg
//https://en.wikipedia.org/wiki/A*_search_algorithm
//https://www.geeksforgeeks.org/sum-manhattan-distances-pairs-points/
//

public class Solver {
	// Initialize private and global variables.
	File solutionFile;
	Scanner cleanup;
	Tile candidate;
	LinkedList<LinkedList<Tile>> solutionCollection = new LinkedList<>();
	LinkedList<Tile> answer = new LinkedList<>();

	double startTime;
	double endTime;

	// this inside Node class, used for a hashMap and sets either the 2 or 3 properties in the hashMap.
	class PuzzleNode {
		Tile predecessor;
		int depth;
		int score;
		// this constructor is used for the "greedy search" as it ignores the depth, or the distance already covered.
		public PuzzleNode(Tile predecessor, int score) {
			this.predecessor = predecessor;
			this.score = score;
		}
		public PuzzleNode(Tile predecessor, int depth, int score) {
			this.predecessor = predecessor;
			this.depth = depth;
			this.score = score;
		}
	}

	// Constructor of the solver that calls for the board to be made.
	public Solver(File in) {
		Tile puzzle;
		Tile solved;
		LinkedList<Tile> temp;

		// Passes the file to the Tile constructor to create the board that will be manipulated.
		//This also assures the usage of different Heuristics on different board sizes.
		try {
			puzzle = new Tile(in);
			solved = puzzle.getSolution();
			// the difference is a threshold so that anything above the size of 5x5 board will be solved with other Heuristics.
			int difference = puzzle.getSize() - 5;
			startTime = System.currentTimeMillis();
			Tile new_puzzle = puzzle;
			Tile new_solution = solved;


			if(difference > 0 && difference <= 2) { // if size is 6 and 7 use the Max Coordinate Heuristics + Manhattan Heuristics for more variant state.
				temp = AStarAlgorithm(HeuristicType.MAX_COORDINATE_DISTANCE_AND_MANHATTAN, new_puzzle, new_solution);
			}
			else if(difference > 2) { // if any puzzle size is bigger than 7 which in this case 8 and 9 it uses Euclidean Distance Heuristic.
				temp = AStarAlgorithm(HeuristicType.EUCLIDEAN_DISTANCE, new_puzzle, new_solution);
			}
			else {// if the size is 5 or less it uses Manhattan Distance Heuristic for a more optimal solution
				temp = AStarAlgorithm(HeuristicType.MANHATTAN_DISTANCE, new_puzzle, new_solution);
			}
			// finally the solution will be passed to this Linked list to keep track.
			solutionCollection.add(temp);
		}

		// If the board file given could not be found
		catch (Exception e) {
			System.out.println(in + "could not be read" + e);
			e.printStackTrace();
		}

	}


	// This heuristic function calculates the Hamming distance between the candidate puzzle and the solution.
	// The Hamming distance is a measure of how many tiles are misplaced in the candidate puzzle compared to the solution.
	private int HammingHeuristicDistance(Tile candidatePuzzle, Tile solution) {
		// Initialize variables
		int total = 0;

		// Iterate through all tile positions in the candidate puzzle
		for (positional currentPosition : candidatePuzzle.allTilePos()){
			//get the val of the tile at current position
			int currentValue = candidatePuzzle.getValue(currentPosition);

			// If the currentValue is greater than 0 (not an empty tile), check if the tile is misplaced
			if (currentValue > 0) {
				positional targetPosition = solution.findPosition(currentValue);// Get the target position of the current tile in the solved state

				// If the current position of the tile is different from its target position, increment the total misplaced tiles count
				if(targetPosition != currentPosition) {
					total++;
				}
			}
		}
		return total;
	}


/*	The maxCoordinateDistance heuristic function calculates the maximum coordinate distance for each tile and adds the Manhattan distance to it,
	which should give a more accurate estimation of the solution's cost.*/

	private int maxCoordinateDistance(Tile candidatePuzzle, Tile solved) {
		int total = 0;// Initialize the total distance to 0

		// Iterate through all tiles in the candidate puzzle
		for (positional currentPosition : candidatePuzzle.allTilePos()) {
			// Get the value of the tile at the current position
			int currentValue = candidatePuzzle.getValue(currentPosition);

			// If the currentValue is greater than 0 (not an empty tile), calculate the maximum coordinate distance
			if (currentValue > 0) {
				// Find the target position of the current tile in the solved puzzle
				positional targetPosition = solved.findPosition(currentValue);

				// Calculate the horizontal and vertical distances between the current and target positions
				int horizontalDistance = Math.abs(targetPosition.x - currentPosition.x);
				int verticalDistance = Math.abs(targetPosition.y - currentPosition.y);
				// Update the total distance with the maximum of the vertical and horizontal distances
				total += Math.max(verticalDistance, horizontalDistance);
			}
		}
		// Add the Manhattan heuristic distance to the total distance
		total = total+ ManhattanHeuristicDistance(candidatePuzzle, solved);
		return total;
	}

	//This heuristic function calculates the Euclidean distance between each tile's current position and its target position.
	//The Euclidean distance is the straight-line distance between two points in a 2D space ( it can be diagonal)
	private int EuclideanHeuristicDistance(Tile candidatePuzzle, Tile solution) {
		int total = 0;

		// Iterate through all tile positions in the candidate puzzle
		for (positional currentPosition : candidatePuzzle.allTilePos()) {
			// Get the value of the tile at the current position
			int currentValue = candidatePuzzle.getValue(currentPosition);

			// If the currentValue is greater than 0 (not an empty tile), calculate the Euclidean distance
			if (currentValue > 0) {
				// Get the target position of the current tile in the solution
				positional targetPosition = solution.findPosition(currentValue);

				// Calculate the horizontal and vertical distances between the current and target positions
				int horizontalDistance = targetPosition.x - currentPosition.x;
				int verticalDistance = targetPosition.y - currentPosition.y;

				// Calculate the Euclidean distance for the current tile using the Pythagorean theorem
				// and add it to the total distance
				total +=(int)Math.sqrt(Math.pow(horizontalDistance,2)+Math.pow(verticalDistance,2));
			}
		}
		return total;
	}

	//This heuristic function calculates the Manhattan distance between each tile's current position  and its target position.
	//The Manhattan distance is the sum of the horizontal and vertical distances between two points in a 2D grid
	private int ManhattanHeuristicDistance(Tile candidatePuzzle, Tile solution) {
		int total = 0;
		// Iterate through all tile positions in the candidate puzzle
		for (positional currentPosition : candidatePuzzle.allTilePos()){
			// Get the value of the tile at the current position
			int currentValue = candidatePuzzle.getValue(currentPosition);

			// If the currentValue is greater than 0 (not an empty tile), calculate the Manhattan distance
			if (currentValue > 0) {
				// Get the target position of the current tile in the solved state
				positional targetPosition = solution.findPosition(currentValue);

				// Calculate the horizontal and vertical distances between the current and target positions
				int horizontalDistance = Math.abs(targetPosition.x - currentPosition.x);
				int verticalDistance = Math.abs(targetPosition.y - currentPosition.y);

				// Update the total Manhattan distance by adding the horizontal and vertical distances for the current tile
				total = total + verticalDistance + horizontalDistance;
			}
		}
		//Return the total sum of Manhattan distances for all non-blank tiles
		return total;
	}



	// this method given a board state, returns all the possible places for the empty to move and generate other instances of boards
	public List<Tile> AdjacentNodes(Tile currentBoard){
		ArrayList<Tile> adjacentNodes = new ArrayList<>();
		ArrayList<positional> options = currentBoard.movementOptions();

		// foreach loop that loops at the valid movements and place them into the linked list
		options.forEach(option-> {
			Tile newPuzzle = new Tile(currentBoard,option);
			adjacentNodes.add(newPuzzle);
		});
		//returns the list of possible move boards
		return adjacentNodes;
	}


	// enum creating the different types for Heuristics in case of wanting to switch between different heuristics it makes the switching faster.
	public enum HeuristicType {
		MANHATTAN_DISTANCE,
		HAMMING_DISTANCE,
		MAX_COORDINATE_DISTANCE_AND_MANHATTAN,
		EUCLIDEAN_DISTANCE,
	}


	// This method can be looked at as a helper method of choosing a heuristic.
	// Base on the enum it can be called to use different heuristic.
	public int heuristicCost(HeuristicType heuristicChosen, Tile candidatePuzzle , Tile solution, int pos) {
		switch (heuristicChosen) {
			case EUCLIDEAN_DISTANCE:
				return EuclideanHeuristicDistance(candidatePuzzle, solution);
			case MANHATTAN_DISTANCE:
				return ManhattanHeuristicDistance(candidatePuzzle, solution);
			case HAMMING_DISTANCE:
				return HammingHeuristicDistance(candidatePuzzle, solution);
			case MAX_COORDINATE_DISTANCE_AND_MANHATTAN:
				return maxCoordinateDistance(candidatePuzzle, solution);
			default:
				throw new IllegalArgumentException("Invalid heuristic type");
		}
	}


	//The provided method implements the A* algorithm for solving a puzzle. (this is a Greedy tweak)
	//It uses a priority queue to store nodes to be explored, prioritizing those with the lowest heuristic score.
	//The algorithm continues searching until either a solution is found or the priority queue is empty
	public LinkedList<Tile> AStarAlgorithm(HeuristicType heuristicType, Tile board, Tile solution) {

		// Initialize a Hashmap to store the boards as its key and their (depth, score, predecessor)/(score, predecessor) as its value
		Map<Tile, PuzzleNode> nodes = new HashMap<>();
		// this is the custom comparator to use in priority queue, in order to dequeue, the lowest score first.
		Comparator<Tile> scoreCompare = (a,b) -> nodes.get(a).score - nodes.get(b).score;
		// Initialize a priority queue to store nodes to check, using the custom comparator
		PriorityQueue<Tile> nodesToCheck = new PriorityQueue<>(10000, scoreCompare);


		// Add the initial puzzle state to the map and priority queue
		nodes.put(board,new PuzzleNode(null,heuristicCost(heuristicType,board,solution, 0)));
		nodesToCheck.add(board);
		// counter for total nodes visited
		int totalVisitedNodes = 0;

		// Continue searching until the priority queue is empty
		while (!nodesToCheck.isEmpty()){

			candidate = nodesToCheck.remove();
			totalVisitedNodes++;

			// Print progress information
			if (totalVisitedNodes % 10000 == 0) {
				System.out.printf("Considered %,d total Tables. the current Heap Size = %,d\n", totalVisitedNodes, nodesToCheck.size());
			}

			// If the candidate puzzle is solved, backtrack to reconstruct the solution path
			if (candidate.isSolved()){
				LinkedList<Tile> solutionPath = new LinkedList<>();
				System.out.printf("the solution was found after visiting %d nodes\n ",totalVisitedNodes);
				Tile backTrace = candidate;
				while (backTrace != null){
					solutionPath.add(backTrace);
					backTrace = nodes.get(backTrace).predecessor;
				}

				return solutionPath;
			}

			// If the candidate puzzle is not solved, generate its adjacent nodes/puzzle states
			List<Tile> adjacentNodes = AdjacentNodes(candidate);

			// Iterate through each adjacent node
			adjacentNodes.forEach(node ->{
				// If the node has not been visited yet (not inside the HashMap), calculate its heuristic and depth
				if (!nodes.containsKey(node)){

					// f(Score) = f(heuristic) + f(currentdepth) but since we are doing the greedy search we drop the currentDepth
					int newScore = heuristicCost(heuristicType,node,solution, 0);

					// Update the predecessor, depth, and heuristic score for the adjacent node
					nodes.put(node,new PuzzleNode(candidate, newScore));

					// Add the node to the priority queue with its heuristic score
					nodesToCheck.add(node);
				}
			});

		}
		// If no solution is found, return null
		return null;
	}



	//This method converts a sequence of tile configurations in a solution path into a list of instructions.
	//The instructions represented by strings, describe the moves required to solve the tile puzzle.
	private ArrayList<String> ansFormat() {
		// Initialize list and variables
		ArrayList<String> outputs = new ArrayList<>();
		Tile current;
		Tile next;
		positional newEmptyPosition;
		positional valueNewPosition;
		char direction = 'A';
		int movedValue;
		int curSize;
		int nextSize;



		// Format answer list
		LinkedList<Tile> temp = new LinkedList<>();
		while(!solutionCollection.isEmpty()) {
			temp = solutionCollection.removeLast();
			while(!temp.isEmpty()) {
				answer.add(temp.removeFirst());
			}
		}

		// these information below is used to keep track of total moves to solve the puzzle. and total nodes visisted.
		// also it keeps track of the run aproximate run time.
		int totalMoves = answer.size() -1;
		current = answer.removeLast();
		curSize = current.getSize();

		endTime = System.currentTimeMillis();
		double time = (endTime - startTime) / 1000.00;

		System.out.println("Time taken " + time + " seconds.");
		System.out.println("Solved in " + totalMoves + " moves.");


		// Process the answer list to generate human-readable instructions
		while(!answer.isEmpty()) {
			next = answer.removeLast();
			nextSize = next.getSize();

			if(curSize != nextSize) {
				current = next;
				curSize = nextSize;
			}
			else {
				// Find the new empty position in the next tile configuration
				newEmptyPosition = next.findPosition(0);
				// Get the value of the tile that was moved
				movedValue = current.getValue(newEmptyPosition);

				// Determine the direction of the move
				valueNewPosition = next.findPosition(movedValue);
				// Move right
				if((valueNewPosition.getX() > newEmptyPosition.getX())) {
					direction = 'R';
				}
				// Move left
				else if((valueNewPosition.getX() < newEmptyPosition.getX())) {
					direction = 'L';
				}
				// Move up
				else if((valueNewPosition.getY() < newEmptyPosition.getY())) {
					direction = 'U';
				}
				// Move down
				else {
					direction = 'D';
				}

				// Add the instruction to the output list
				String in = movedValue + " " + direction;
				outputs.add(in);

				current = next;
				curSize = nextSize;
			}

		}

		return outputs;
	}



	// Prints out the solution to the output file
	public void writeSolution(List<String> outputSolution, String fileName) throws IOException {

		// Find the path of the given file
		Path path = Paths.get(fileName);


		try {
			// If the file does not exist, create a new file it it exists overwrite the file
			if (!Files.exists(path)) {
				Files.createFile(path);
			}

			// Write the outputSolution to the file, overwriting the content
			Files.write(path, outputSolution);

			// Print a message indicating the solution has been written to the file
			System.out.println("Solution written to file: " + fileName);
		} catch (IOException e) {
			// If an exception occurs while writing to the file, print an error message
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}



	// main method checks the input, and initializes the reading and writing process.
	public static void main(String [] args) throws IOException {

		// Checks if an argument has been passed in by the user
		if (args.length < 2) {
			System.out.println("File names are not specified");
				System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
				return;
		}

		// Set up the File type variables from the arguments provided by the user
		// reads the first input which is the board
		File input = new File(args[0]);
		Solver compute = new Solver(input);


		String fileName = args[1];
		// Write the solution to the specified output file
		List<String> output = compute.ansFormat();
		compute.writeSolution(output,fileName);
	}

}
