package genetic;

import genetic.Individual;
import java.util.ArrayList;

public class GeneticMover {
    //객체 배열
    ArrayList<Individual> current_population = new ArrayList<>();
    ArrayList<Individual> next_population = new ArrayList<>();

    //생성자
    public GeneticMover(int size){
        for(int i = 0; i < (size * size)/2; i++){
            current_population.add(new Individual(size));
            next_population.add(new Individual(size));
        }
    }

    /*
    초기화 메서드

        1.랜덤 방향 유전자로 초기 개체 생성
     */

    /*
    선택 메서드

        1.적합도 평가
        * 갔던길을 한번 더 가면 -
        * 이동한 유전자 길이 -
        * 움직인 칸 수 +
        * 목적지 도착 +
        * 목적지와의 거리(BFS 활용) +

        2.내림차순 정렬

     */

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
