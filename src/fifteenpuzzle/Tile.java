/*
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
// https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/amp/

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
        int y = 0;

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

            for(int x = 0; x < size; x++) {
                try {
                    this.board[y][x] = Integer.parseInt(lineIn.substring(3 * x, 2 + 3 * x).trim());
                }

                catch (NumberFormatException e) {
                    empty = new positional(y,x);
                    this.board[y][x] = 0;
                }
            }
            y++;
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


        // Loop through each row fills the board
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                board[y][x] = counter;
                counter++;
            }
        }
        // Insert the empty space into the board
        empty = new positional((size-1), (size-1));
        board[empty.getY()][empty.getX()] = 0;
    }

    public Tile(Tile currentBoard, positional candidate) {
        // Initialize variables
        size = currentBoard.size;
        board = new int [size][size];

        // Copy the current board to the new board
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                board[y][x] = currentBoard.board[y][x];
            }
        }

        // Clone empty position
        empty = currentBoard.getEmpty();

        // Perform the movement on the board
        if(isValid(candidate)) {
            tileMove(candidate);
        }

        // Create solution board
        solution = currentBoard.getSolution();
    }


    public Tile(Tile currentBoard, int rowsToShift) {
        // Initialize variables
        size = currentBoard.size - 1;
        board = new int [size][size];

        // Copy the current board to the new board
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                board[y][x] = currentBoard.board[y + rowsToShift][x + rowsToShift];

                if(currentBoard.board[y + rowsToShift][x + rowsToShift] == 0) {
                    // Clone empty position
                    empty = new positional(y,x);
                }
            }
        }


    }

    // Returns the size of the
    public Tile getSolution() {
        return this.solution;
    }

    public void setSolution(Tile board) {
        this.solution = board;
    }

    // Returns the position of the empty tile
    private positional getEmpty() {
        return empty;
    }


    // Returns the value at desired position
    public int getValue(positional location) {
        return board[location.getY()][location.getX()];
    }


    // Valid move check
    private boolean isValid(positional candidate) {
        // Check if the x or y location is within the valid range
        if((candidate.getY() < 0||candidate.getY() >= size) || (candidate.getX() < 0||candidate.getX() >= size)) {
            return false;
        }

        // Check if the candidate movement is within the range of the empty tile
        if((Math.abs(empty.getY() - candidate.getY()) + Math.abs(empty.getX() - candidate.getX())) != 1) {
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
        board[empty.getY()][empty.getX()] = board[location.getY()][location.getX()];

        // Move the empty tile to the position the non-empty tile used to be and set that position as the new empty space
        empty = location;
        board[location.getY()][location.getX()] = 0;
    }


    //Find all possible movements for the board for the algorithm to use
    public ArrayList<positional> movementOptions() {
        // Initialize variables and an arraylist to store the possible movements
        ArrayList<positional> options = new ArrayList<>();
        positional candidate;
        int x;
        int y;

        // Check all possible directions the empty space can be moved and add all that are considered valid
        for(int shifty = -1; shifty <= 1 ; shifty++) {
            for(int shiftx = -1; shiftx <= 1; shiftx++) {
                x = empty.getX() + shiftx;
                y = empty.getY() + shifty;
                candidate = new positional(y,x);
                // Checks if the movement would be valid before adding it to the list
                if(isValid(candidate)) {
                    options.add(candidate);
                }
            }
        }

        // Returns the list storing all options
        return options;
    }


    //TODO rewrite this
    // Returns a lost of all possible tile positions on the board
    public List<positional> allTilePos() {
        ArrayList<positional> out = new ArrayList<positional>();
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                out.add(new positional(y,x));
            }
        }
        return out;
    }


    // Returns the size of the board
    public int getSize() {
        return size;
    }


    // Find the tile position
    public positional findPosition(int item) {
        // Initialize variable
        positional output = null;

        // Loops through all tiles in the board to find the position of the item present
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                // When the item has been found to position is stored and outputted
                if(board[y][x] == item) {
                    output = new positional(y,x);
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
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                // If the board does not match and adds to the counter
                if((board[y][x] > 0) && (board[y][x] != solution.board[y][x])) {
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


    public boolean isRowColumnSolved(int position) {
        // Initialize variable
        int incorrect = 0;

        // Runs through the board and compares it with the solution board to ensure that they match
        for(int x = 0; x < size; x++) {
            // If the board does not match and adds to the counter
            if((board[position][x] != solution.board[position][x])) {
                incorrect++;
            }
        }

        // Runs through the board and compares it with the solution board to ensure that they match
        for(int y = 1; y < size; y++) {
            // If the board does not match and adds to the counter
            if((board[y][position] != solution.board[y][position])) {
                incorrect++;
            }
        }

        // Checks if the counter for incorrect values has incremented
        if(incorrect > 0) {
            return false;
        }
        return true;
    }


    public boolean isTileSetSolved(int choice) {
        // Choice is used to allow inclusions
        //for(int i = 0; i < choice; i++) {
        //    if((board[0][i] != solution.board[0][i])) {
        //        return false;
       //     }
        //}

        for(int i = 0; i < getSize(); i++) {
           if((board[0][i] != solution.board[0][i])) {
                return false;
            }
        }

        return true;
    }

    //TODO comment
    // Override equals to
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


    //TODO comment
    // Override hashCode to
    @Override
    public int hashCode() {
        int out=0;
        for(positional p: allTilePos()) {
            out= (out*size*size) + this.getValue(p);
        }
        return out;
    }
}


 */




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
// https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/amp/

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
        int y = 0;

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

            for(int x = 0; x < size; x++) {
                try {
                    this.board[y][x] = Integer.parseInt(lineIn.substring(3 * x, 2 + 3 * x).trim());
                }

                catch (NumberFormatException e) {
                    empty = new positional(y,x);
                    this.board[y][x] = 0;
                }
            }
            y++;
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
        board[empty.getY()][empty.getX()] = 0;

        // Loop through each row fills the board
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                board[y][x] = counter;
                counter++;
            }
        }
    }

    public Tile(Tile currentBoard, positional candidate) {
        // Initialize variables
        size = currentBoard.size;
        board = new int [size][size];

        // Copy the current board to the new board
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                board[y][x] = currentBoard.board[y][x];
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
        return board[location.getY()][location.getX()];
    }


    // Valid move check
    private boolean isValid(positional candidate) {
        // Check if the x or y location is within the valid range
        if((candidate.getY() < 0||candidate.getY() >= size) || (candidate.getX() < 0||candidate.getX() >= size)) {
            return false;
        }

        // Check if the candidate movement is within the range of the empty tile
        if((Math.abs(empty.getY() - candidate.getY()) + Math.abs(empty.getX() - candidate.getX())) != 1) {
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
        board[empty.getY()][empty.getX()] = board[location.getY()][location.getX()];

        // Move the empty tile to the position the non-empty tile used to be and set that position as the new empty space
        empty = location;
        board[location.getY()][location.getX()] = 0;
    }


    //Find all possible movements for the board for the algorithm to use
    public ArrayList<positional> movementOptions() {
        // Initialize variables and an arraylist to store the possible movements
        ArrayList<positional> options = new ArrayList<>();
        positional candidate;
        int x;
        int y;

        // Check all possible directions the empty space can be moved and add all that are considered valid
        for(int shifty = -1; shifty <= 1 ; shifty++) {
            for(int shiftx = -1; shiftx <= 1; shiftx++) {
                x = empty.getX() + shiftx;
                y = empty.getY() + shifty;
                candidate = new positional(y,x);
                // Checks if the movement would be valid before adding it to the list
                if(isValid(candidate)) {
                    options.add(candidate);
                }
            }
        }

        // Returns the list storing all options
        return options;
    }


    //TODO rewrite this
    // Returns a lost of all possible tile positions on the board
    public List<positional> allTilePos() {
        ArrayList<positional> out = new ArrayList<positional>();
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                out.add(new positional(y,x));
            }
        }
        return out;
    }


    // Returns the size of the board
    public int getSize() {
        return size;
    }


    // Find the tile position
    public positional findPosition(int item) {
        // Initialize variable
        positional output = null;

        // Loops through all tiles in the board to find the position of the item present
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                // When the item has been found to position is stored and outputted
                if(board[y][x] == item) {
                    output = new positional(y,x);
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
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                // If the board does not match and adds to the counter
                if((board[y][x] > 0) && (board[y][x] != solution.board[y][x])) {
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
    public int isSolved(int val) {
        // Initialize variable
        int incorrect = 0;

        // Runs through the board and compares it with the solution board to ensure that they match
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                // If the board does not match and adds to the counter
                if((board[y][x] > 0) && (board[y][x] != solution.board[y][x])) {
                    incorrect++;
                }
            }
        }

        // Checks if the counter for incorrect values has incremented
        return incorrect;
    }


    //TODO comment
    // Override equals to
//    @Override
//    public boolean equals(Object o) {
//        if(o instanceof Tile) {
//            for(positional p: allTilePos()) {
//                if( this.getValue(p) != ((Tile) o).getValue(p)) {
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Tile other = (Tile) o;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (this.board[y][x] != other.board[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }


    //TODO comment
    // Override hashCode to
//    @Override
//    public int hashCode() {
//        int out=0;
//        for(positional p: allTilePos()) {
//            out= (out*size*size) + this.getValue(p);
//        }
//        return out;
//    }

    @Override
    public int hashCode() {
        int result = 17;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                result = 31 * result + board[y][x];
            }
        }
        return result;
    }
}
