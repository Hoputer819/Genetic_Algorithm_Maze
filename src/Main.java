import maze.MazeGenerator;

public class Main {
    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(5,5);
        maze.mapPrint();
    }
}