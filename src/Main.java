import javafx.application.Application;
import maze.MazeGenerator;
import visualization.WindowGenerator;

public class Main {
    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(10);
        maze.generate();

        WindowGenerator.maze = maze;
        Application.launch(WindowGenerator.class);

    }
}