import java.io.*;
import java.util.*;

public class SolveMaze {

    private int[][] maze;
    private int rows, cols;
    private int endRow, endCol;
    private int startRow, startCol;

    public SolveMaze(String filename){
            loadMaze(filename);
    }

    private void loadMaze(String filename){
            List<int[]> temp = new ArrayList<>();
            try(BufferedReader file = new BufferedReader(new FileReader(filename))){
                String line;
                int r = 0;

                while ((line = file.readLine()) != null) {

                    String[] values = line.split("\t");
                    int[] row = new int[values.length];

                    for (int c = 0; c < values.length; c++) {

                        switch (values[c]) {
                            case "W":
                                row[c] = -1;
                                break;
                            case "C":
                                row[c] = 0;
                                break;
                            case "S":
                                row[c] = 1;
                                startRow = r;
                                startCol = c;
                                break;
                            case "F":
                                row[c] = -2;
                                endRow = r;
                                endCol = c;
                                break;
                        }
                    }

                    temp.add(row);
                    r++;
                }

            } catch(IOException e) {
                    e.printStackTrace();
            }

            rows = temp.size();
            cols = temp.get(0).length;

            maze = new int[rows][cols];

            for (int i = 0; i < rows; i++)
                maze[i] = temp.get(i);
    }


    private void numberAdjacent(int x, int y, int step) {

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};

        for (int[] d : directions) {

            int nx = x + d[0];
            int ny = y + d[1];

            if (nx>=0 && ny>=0 && nx<rows && ny<cols) {
                if (maze[nx][ny] == 0 || maze[nx][ny] == -2)
                    maze[nx][ny] = step + 1;
            }
        }
    }

    public void makePaths() {
        int step = 1;
        boolean found = false;

        while (!found) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {

                    if (maze[i][j] == step) 
                        numberAdjacent(i, j, step);

                    if (maze[endRow][endCol] > 0)
                        found = true;
                }
            }
            step++;
        }
    }

    public void backtrack() {

        int x = endRow;
        int y = endCol;
        int n = maze[x][y];
        maze[startRow][startCol] = -2;

        while (n > 1) {

            maze[x][y] = -2;

            if (x+1 < rows && maze[x+1][y] == n-1) x++;
            else if (x-1 >= 0 && maze[x-1][y] == n-1) x--;
            else if (y+1 < cols && maze[x][y+1] == n-1) y++;
            else if (y-1 >= 0 && maze[x][y-1] == n-1) y--;

            n--;
        }
    }

    public void printMaze() {

        System.out.println("Labirynt:\n");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                System.out.printf("%4d", maze[i][j]);
            System.out.println();
        }
    }

    public void printPath() {

        System.out.println("\nNajkrótsza ścieżka:\n");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (maze[i][j] == -2)
                    System.out.print("* ");
                else
                    System.out.print("- ");
                    
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
       
        SolveMaze maze = new SolveMaze("maze.txt");

        maze.makePaths();
        maze.printMaze();
        maze.backtrack();
        maze.printPath();

    }
}
