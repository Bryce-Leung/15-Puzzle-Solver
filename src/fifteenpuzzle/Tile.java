package fifteenpuzzle;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tile {
    private class positional {
        public int x;
        public int y;

        public positional(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Initialize private global variable
    private int size = 0;
    private int [][] board;
    private positional empty;
    File BoardFile;
    Scanner setup;

    // Constructor creates the Tile board from the file
    public Tile(String input) throws IOException {
        // Initialize variable
        int x = 0;

        // Uses scanner to read the board file
        this.BoardFile = new File(input);
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
                System.out.println(x);
                System.out.println(y);
                System.out.println(board[x][y]);
            }
            x++;
        }
        setup.close();

    }

    // Constructor used to create a generic solution board based on the size of the problem board
    public Tile(int sized) {
        size = sized;
        // Initialize variables
        int counter = 1;
        board = new int [size][size];

        // Insert the empty space into the board
        empty = new positional((size-1), (size-1));
        board[empty.x][empty.y] = 0;

        // Loop through each row fills the board
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = counter;
                counter++;
            }
        }
    }

    // Create the solution board
    private Tile soluton = new Tile(size);

    // Returns the position of the empty tile
    private positional getEmpty() {
        return empty;
    }

    // Returns the value at desired position
    private int getValue(positional location) {
        return board[location.x][location.y];
    }

    /*
    // Valid move check
    private boolean isValid(positional candidate) {
        // Check if the x or y location is within the valid range
        if((candidate.x < 0||candidate.x >= size) || (candidate.y < 0||candidate.y >= size)) {
            return false;
        }

        // Check if the candidate movement is within the range of the empty tile
        if() {
            return false;
        }

        return true;
    }

    // Move the empty tile to a specified location swapping it with the non empty tile in it's path
    public void tileMove(positional location) {
        // Checks if the proposed location to move is valid
        if(isValid(location) == false) {

        }

    }

     */

}
