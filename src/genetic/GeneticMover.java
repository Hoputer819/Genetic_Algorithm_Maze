package genetic;

import java.util.Random;
import java.util.ArrayList;
import maze.MazeGenerator;

public class GeneticMover {

    public static MazeGenerator maze;
    Random random = new Random();

    int individual_num;
    int gene_num;
    int elite_count;
    int gene_count;
    double individual_mutation_rate;
    double gene_mutation_rate;

    //객체 리스트
    ArrayList<Individual> population = new ArrayList<>();

    //생성자
    public GeneticMover(){
        individual_num =  (maze.size * maze.size) / 2;
        gene_num = (maze.size * maze.size) * 2;
        individual_mutation_rate = 1.0 / (2 * maze.size);
        gene_mutation_rate = 1.0 * (maze.size) / gene_num;
        elite_count = maze.size >= 10  ? (int)(individual_num * 0.1) : 2;
        for(int i = 0; i < individual_num; i++){
            population.add(new Individual(gene_num, maze.size));
        }
        gene_reset();
    }

    //유전자 초기화 메서드
    private void gene_reset(){
        for(int i =  0; i < individual_num; i++){
            for(int j = 0; j < gene_num; j++)
                population.get(i).gene[j] = random.nextInt(4);
        }
    }// 0 : 오른쪽, 1 : 왼쪽, 2 : 위, 3 : 아래

    //선택 메서드
    private void selection(){
        int visit_again;
        int big_individual;
        int max_distance = maze.back_map[maze.size-1][0];
        Individual temp;

        //적합도 평가
        for(int i =  0; i < individual_num; i++){
            visit_again = 0;

            //갔던길을 한번 더 가면 -
            for(int j = 0; j < maze.size; j++){
                for(int k = 0; k < maze.size; k++){
                    if(population.get(i).visited[j][k] > 1)
                        visit_again += (population.get(i).visited[j][k] - 1);
                }
            }
            population.get(i).fitness -= visit_again * 10;

            //목적지와의 거리(BFS 활용) +
            population.get(i).fitness += (max_distance - maze.back_map[population.get(i).current_y][population.get(i).current_x]) * 20;

            //움직인 칸 수 +
            population.get(i).fitness += population.get(i).cell_move * 10;

            //목적지 도착 +
            if(population.get(i).current_x == maze.last_x && population.get(i).current_y == maze.last_y) {
                //이동한 유전자 길이 -
                population.get(i).fitness -= (population.get(i).cell_move) * 20;
                population.get(i).fitness += 100000;
            }
        }

        //내림차순 정렬(선택 정렬)
        for(int i = 0; i < individual_num-1; i++){
            big_individual = i;
            for(int j = i+1; j < individual_num; j++){
                if(population.get(j).fitness > population.get(big_individual).fitness)
                    big_individual = j;
            }

            temp = population.get(i);
            population.set(i,population.get(big_individual));
            population.set(big_individual,temp);
        }

    }

    //교차 메서드
    private void crossover(){
        int mom;
        int dad;

        for(int i = elite_count; i < individual_num; i++){
            mom = random.nextInt(elite_count);
            do {
                dad = random.nextInt(elite_count);
            }while(mom == dad);

            for(int j = 0; j < gene_num; j++){
                if(j < gene_num/2)
                    population.get(i).gene[j] = population.get(mom).gene[j];
                else
                    population.get(i).gene[j] = population.get(dad).gene[j];
            }
        }
    }

    //변이 메서드
    private void mutation(){
        for(int i = elite_count; i < individual_num; i++){
            if(Math.random() < individual_mutation_rate){
                for(int j = 0; j < gene_num; j++){
                    if(Math.random() < gene_mutation_rate)
                        population.get(i).gene[j] = random.nextInt(4);
                }
            }
        }
    }

    //대치 메서드
    public void replace(){

        selection();
        crossover();
        mutation();

        for(int k = 0; k < individual_num; k++){
            population.get(k).fitness = 0;
            population.get(k).cell_move = 0;
            population.get(k).current_x = 0;
            population.get(k).current_y = maze.size - 1;
            population.get(k).die = false;
            for(int l = 0; l < maze.size; l++){
                for(int m = 0; m < maze.size; m++)
                    population.get(k).visited[l][m] = 0;
            }
            population.get(k).visited[maze.size-1][0] = 1;
        }
    }

    //개체 움직이기 메서드
    public void run(){

        for (int i = 0; i < individual_num; i++) {

            if(population.get(i).die)
                continue;

            switch (population.get(i).gene[gene_count]) {
                case 0:
                    if (maze.map[population.get(i).current_y][population.get(i).current_x].direction[population.get(i).gene[gene_count]]) {
                        population.get(i).current_x += 1;
                        population.get(i).visited[population.get(i).current_y][population.get(i).current_x] += 1;
                        population.get(i).cell_move += 1;
                        if(population.get(i).current_x == maze.last_x && population.get(i).current_y == maze.last_y) {
                            population.get(i).die = true;
                            break;
                        }
                    }
                    else {
                        population.get(i).die = true;
                        break;
                    }
                    break;
                case 1:
                    if (maze.map[population.get(i).current_y][population.get(i).current_x].direction[population.get(i).gene[gene_count]]) {
                        population.get(i).current_x -= 1;
                        population.get(i).visited[population.get(i).current_y][population.get(i).current_x] += 1;
                        population.get(i).cell_move += 1;
                        if(population.get(i).current_x == maze.last_x && population.get(i).current_y == maze.last_y) {
                            population.get(i).die = true;
                            break;
                        }
                    }
                    else {
                        population.get(i).die = true;
                        break;
                    }
                    break;
                case 2:
                    if (maze.map[population.get(i).current_y][population.get(i).current_x].direction[population.get(i).gene[gene_count]]) {
                        population.get(i).current_y -= 1;
                        population.get(i).visited[population.get(i).current_y][population.get(i).current_x] += 1;
                        population.get(i).cell_move += 1;
                        if(population.get(i).current_x == maze.last_x && population.get(i).current_y == maze.last_y) {
                            population.get(i).die = true;
                            break;
                        }
                    }
                    else {
                        population.get(i).die = true;
                        break;
                    }
                    break;
                case 3:
                    if (maze.map[population.get(i).current_y][population.get(i).current_x].direction[population.get(i).gene[gene_count]]) {
                        population.get(i).current_y += 1;
                        population.get(i).visited[population.get(i).current_y][population.get(i).current_x] += 1;
                        population.get(i).cell_move += 1;
                        if(population.get(i).current_x == maze.last_x && population.get(i).current_y == maze.last_y) {
                            population.get(i).die = true;
                            break;
                        }
                    }
                    else {
                        population.get(i).die = true;
                        break;
                    }
                    break;
            }
        }


    }



}
