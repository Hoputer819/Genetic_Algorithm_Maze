import maze.Maze;

public class Main {
    public static void main(String[] args) {
        Maze court = new Maze(5,5);

        court.generate();
        court.print();
    }
}