package genetic;

import java.util.Random;
import java.util.ArrayList;
import maze.MazeGenerator;

public class GeneticMover {

    public static MazeGenerator maze;
    Random random = new Random();

    int individual_num;
    int gene_num;

    //객체 리스트
    ArrayList<Individual> current_population = new ArrayList<>();
    ArrayList<Individual> next_population = new ArrayList<>();

    //생성자
    public GeneticMover(){
        individual_num =  (maze.size * maze.size)/2;
        gene_num = (maze.size * maze.size) * 2;
        for(int i = 0; i < individual_num; i++){
            current_population.add(new Individual(gene_num));
            next_population.add(new Individual(gene_num));
        }
        gene_reset();
    }

    //유전자 초기화 메서드
    private void gene_reset(){
        for(int i =  0; i < individual_num; i++){
            for(int j = 0; j < gene_num; j++)
                current_population.get(i).gene[j] = random.nextInt(4);
        }
    }// 0 : 오른쪽, 1 : 왼쪽, 2 : 위, 3 : 아래

    //선택 메서드
    private void selection(){
        int visit_again = 0;
        int big_individual;
        int max_distance = maze.last_map[maze.size-1][0];
        Individual temp;

        //적합도 평가
        for(int i =  0; i < individual_num; i++){

            //갔던길을 한번 더 가면 -
            for(int j = 0; j < maze.size; j++){
                for(int k = 0; k < maze.size; k++){
                    if(current_population.get(i).visited[j][k] > 1)
                        visit_again += (current_population.get(i).visited[j][k] - 1);
                }
            }
            current_population.get(i).fitness -= visit_again * 10;

            //목적지와의 거리(BFS 활용) +
            current_population.get(i).fitness += (max_distance - maze.back_map[current_population.get(i).die_y][current_population.get(i).die_x]) * 20;

            //움직인 칸 수 +
            current_population.get(i).fitness += current_population.get(i).cell_move * 10;

            //목적지 도착 +
            if(current_population.get(i).die_x == maze.last_x && current_population.get(i).die_y == maze.last_y) {
                //이동한 유전자 길이 -
                current_population.get(i).fitness -= (current_population.get(i).cell_move) * 20;
                current_population.get(i).fitness += 100000;
            }
        }

        //내림차순 정렬(선택 정렬)
        for(int i = 0; i < individual_num-1; i++){
            big_individual = i;
            for(int j = i+1; j < individual_num; j++){
                if(current_population.get(j).fitness > current_population.get(big_individual).fitness)
                    big_individual = j;
            }

            temp = current_population.get(i);
            current_population.set(i,current_population.get(big_individual));
            current_population.set(big_individual,temp);
        }

    }

    /*
    교차 메서드

        1.상위 개체 유전자 추출

        2.상위 개체 유전자로 교차

     */

    /*
    대치 메서드

        1.범위만큼 next_population에 저장

        2.다시 current_population으로 저장

     */

    /*
    변이 메서드

        1.객체 선택

        2.그 객체에 유전자 선택

        3.변이
     */

    /*
    객체 움직이기 메서드

        1.벽 만날때까지 유전자대로 진행

        2.상위 10% 대치

        3.상위 10%의 유전자로 1점 교차 및 대치

        4.상위 10%를 제외한 나머지 객체들 중 20%의 객체의 유전자에 5%의 변이 적용
     */


}
