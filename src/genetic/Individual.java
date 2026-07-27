package genetic;


public class Individual {
    public int[] gene;
    public int fitness = 0;
    public int cell_move = 0;
    public int current_x;
    public int current_y;
    public boolean die = false;
    int[][] visited;

    public Individual(int size,int maze_size){
        gene = new int[size];
        visited = new int[maze_size][maze_size];
        for(int i = 0; i < maze_size; i++){
            for(int j = 0; j < maze_size; j++){
                visited[j][i] = 0;
            }
        }
        visited[maze_size-1][0] = 1;
    }

}
