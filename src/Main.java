import javafx.application.Application;
import maze.MazeGenerator;
import visualization.WindowGenerator;
import genetic.GeneticMover;

public class Main {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        MazeGenerator maze = new MazeGenerator(5);
        maze.generate();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        GeneticMover.maze = maze;
        WindowGenerator.maze = maze;
        Application.launch(WindowGenerator.class);

        System.out.println("실행 시간: " + duration + " ms (" + (duration / 1000.0) + " 초)");

    }
}