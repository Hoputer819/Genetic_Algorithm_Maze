package maze;

public class Maze {
    private final int width;
    private final int height;

    public Maze(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void generate(){
        int[][] map = new int[height][width];

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                map[i][j] = 0;
            }
        }

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                System.out.print(map[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

    }
}
