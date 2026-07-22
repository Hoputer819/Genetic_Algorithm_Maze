package genetic;


public class Individual {
    public int[] gene;
    public int fitness = 0;
    public int cell_move = 0;
    public int current_x;
    public int current_y;

    public Individual(int size){
        gene = new int[size];
    }

}
