package genetic;


public class Individual {
    public int[] gene;
    public int fitness = 0;

    public Individual(int size){
        gene = new int[(size * size) * 2];
    }

}
