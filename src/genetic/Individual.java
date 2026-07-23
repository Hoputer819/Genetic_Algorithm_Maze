package genetic;


public class Individual {
    public int[] gene;
    public int fitness = 0;
    public int cell_move = 0;
    public int die_x;
    public int die_y;
    int[][] visited;

    public Individual(int size){
        gene = new int[size];
        visited = new int[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                visited[j][i] = 0;
            }
        }
        visited[size-1][0] = 1;
    }

}
