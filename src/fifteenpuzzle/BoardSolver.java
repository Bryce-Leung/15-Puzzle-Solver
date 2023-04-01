package fifteenpuzzle;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BoardSolver {
    public static void main(String[] args) throws IOException {
        int[][] board = readBoard("board22.txt");
        List<String> instructions = Files.readAllLines(Paths.get("sol1.txt"));
        for (String instruction : instructions) {
            board = move(board, instruction);
        }
        writeBoard(board, "wow.txt");
    }

    private static int[][] readBoard(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int n = lines.size();
        int[][] board = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] numbers = lines.get(i).trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(numbers[j]);
            }
        }
        return board;
    }

    private static int[][] move(int[][] board, String instruction) {
        String[] parts = instruction.split(" ");
        int number = Integer.parseInt(parts[0]);
        char direction = parts[1].charAt(0);

        int n = board.length;
        int row = -1, col = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == number) {
                    row = i;
                    col = j;
                    break;
                }
            }
            if (row != -1) break;
        }

        int newRow = row, newCol = col;
        switch (direction) {
            case 'U':
                newRow = row - 1;
                break;
            case 'D':
                newRow = row + 1;
                break;
            case 'L':
                newCol = col - 1;
                break;
            case 'R':
                newCol = col + 1;
                break;
        }

        if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && board[newRow][newCol] == 0) {
            board[row][col] = 0;
            board[newRow][newCol] = number;
        }
        return board;
    }

    private static void writeBoard(int[][] board, String filename) throws IOException {
        int n = board.length;
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]).append(' ');
            }
            lines.add(sb.toString().trim());
        }
        Files.write(Paths.get(filename), lines);
    }
}
