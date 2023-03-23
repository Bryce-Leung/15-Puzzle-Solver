package fifteenpuzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.Math;

// Author: Peiman Zhiani Asgharzadeh
// Computing ID: pza42
// Student #: 301438914

// Author: Bryce Leung
// Computing ID: bla135
// Student #: 301421630

// References:
// https://codereview.stackexchange.com/questions/86597/optimizing-manhattan-distance-method-for-n-by-n-puzzles
//

public class Tile {
    // Initialize private global variable
    private int size;
    private int [][] board;
    private positional empty;
    private Tile solution;
    File BoardFile;
    Scanner setup;

    // Constructor creates the Tile board from the file
    public Tile(File input) throws IOException {
        // Initialize variable
        int x = 0;

        // Uses scanner to read the board file
        this.BoardFile = input;
        this.setup = new Scanner(this.BoardFile);
        String lineIn = this.setup.nextLine();

        // Update the size of the board
        size = Integer.parseInt(lineIn);
        board = new int [size][size];

        // Loops through each line obtaining the values and storing them in their respective positions
        while (this.setup.hasNext())
        {
            // Obtain the next line of the board file
            lineIn = this.setup.nextLine();

            for(int y = 0; y < size; y++) {
                try {
                    this.board[x][y] = Integer.parseInt(lineIn.substring(3 * y, 2 + 3 * y).trim());
                }

                catch (NumberFormatException e) {
                    empty = new positional(x,y);
                    this.board[x][y] = 0;
                }
            }
            x++;
        }

        // Outputs an error the file could not be found
        if (BoardFile == null){
            throw new FileNotFoundException("File Not Found Exception: input file could not be found");
        }

        // Close the file
        setup.close();

        // Create the solution board
        solution = new Tile(size);
    }


    // Constructor used to create a generic solution board based on the size of the problem board
    public Tile(int sized) {
        // Initialize variables
        size = sized;
        int counter = 1;
        board = new int [size][size];

        // Insert the empty space into the board
        empty = new positional((size-1), (size-1));
        board[empty.getX()][empty.getY()] = 0;

        // Loop through each row fills the board
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = counter;
                counter++;
            }
        }
    }

    public Tile(Tile currentBoard, positional candidate) {
        // Initialize variables
        size = currentBoard.size;
        board = new int [size][size];

        // Copy the current board to the new board
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                board[x][y] = currentBoard.board[x][y];
            }
        }

        // Clone empty position
        empty = currentBoard.getEmpty();

        // Perform the movement on the board
        if(isValid(candidate)) {
            tileMove(candidate);
        }

        // Create solution board
        solution = new Tile(size);
    }


    // Returns the size of the
    public Tile getSolution() {
        return this.solution;
    }


    // Returns the position of the empty tile
    private positional getEmpty() {
        return empty;
    }


    // Returns the value at desired position
    public int getValue(positional location) {
        return board[location.getX()][location.getY()];
    }


    // Valid move check
    private boolean isValid(positional candidate) {
        // Check if the x or y location is within the valid range
        if((candidate.getX() < 0||candidate.getX() >= size) || (candidate.getY() < 0||candidate.getY() >= size)) {
            return false;
        }

        // Check if the candidate movement is within the range of the empty tile
        if((Math.abs(empty.getX() - candidate.getX()) + Math.abs(empty.getY() - candidate.getY())) != 1) {
            return false;
        }

        // If nothing is wrong with its movement then it returns true
        return true;
    }


    // Move the empty tile to a specified location swapping it with the non-empty tile in its path
    public void tileMove(positional location) throws IllegalArgumentException{
        // Checks if the proposed location to move is valid
        if(!isValid(location)) {
            throw new IllegalArgumentException("Illegal Argument Exception: The desired movement is not possible");
        }

        // Place the value into the empty tile of the board
        board[empty.getX()][empty.getY()] = board[location.getX()][location.getY()];

        // Move the empty tile to the position the non-empty tile used to be and set that position as the new empty space
        empty = location;
        board[location.getX()][location.getY()] = 0;
    }


    //TODO Find all possible movements for the board for the algorithm to use
    public ArrayList<positional> movementOptions() {
        // Initialize variables and an arraylist to store the possible movements
        ArrayList<positional> options = new ArrayList<>();
        positional candidate;
        int x;
        int y;

        // Check all possible directions the empty space can be moved and add all that are considered valid
        for(int shiftx = -1; shiftx <= 1 ; shiftx++) {
            for(int shifty = -1; shifty <= 1; shifty++) {
                x = empty.getX() + shiftx;
                y = empty.getY() + shifty;
                candidate = new positional(x,y);
                // Checks if the movement would be valid before adding it to the list
                if(isValid(candidate)) {
                    options.add(candidate);
                }
            }
        }

        return options;
    }


    //TODO rewrite this

    public List<positional> allTilePos() {
        ArrayList<positional> out = new ArrayList<positional>();
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                out.add(new positional(i,j));
            }
        }
        return out;
    }


    // Find the tile position
    public positional findPosition(int item) {
        // Initialize variable
        positional output = null;

        // Loops through all tiles in the board to find the position of the item present
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                // When the item has been found to position is stored and outputted
                if(board[x][y] == item) {
                    output = new positional(x,y);
                    return output;
                }
            }
        }

        // If the item cannot be found on the board
        return null;
    }


    // Checks if the board has been solved
    public boolean isSolved() {
        // Initialize variable
        int incorrect = 0;

        // Runs through the board and compares it with the solution board to ensure that they match
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                if((board[x][y] >0) && (board[x][y] != solution.board[x][y])) {
                    incorrect++;
                }
            }
        }

        // Checks if the counter for incorrect values has incremented
        if(incorrect > 0) {
            return false;
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if(o instanceof Tile) {
            for(positional p: allTilePos()) {
                if( this.getValue(p) != ((Tile) o).getValue(p)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        int out=0;
        for(positional p: allTilePos()) {
            out= (out*size*size) + this.getValue(p);
        }
        return out;
    }
}
