import javafx.application.Application;
import maze.MazeGenerator;
import visualization.WindowGenerator;
import genetic.GeneticMover;

public class Main {
    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(10);
        maze.generate();

        GeneticMover.maze = maze;
        WindowGenerator.maze = maze;
        Application.launch(WindowGenerator.class);

    }
}