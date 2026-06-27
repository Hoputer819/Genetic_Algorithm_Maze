import maze.MazeGenerator;

public class Main {
    public static void main(String[] args) {
        MazeGenerator map = new MazeGenerator(5,5);
        map.generate();
        //map.mapPrint();
    }
}