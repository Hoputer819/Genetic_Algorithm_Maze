package maze;

import java.util.Random;
import java.util.Stack;
import java.util.ArrayDeque;

public class MazeGenerator {
    //cell 클래스 객체 생성
    Cell cell = new Cell();

    //Random 객체 생성
    Random random = new Random();

    //미로 크기 변수
    public final int width;
    public final int height;

    //미로 좌표 저장 스택
    Stack<Integer> x_coordinate = new Stack<>();
    Stack<Integer> y_coordinate = new Stack<>();

    //미로 좌표 저장 큐
    ArrayDeque<Integer> x_queue = new ArrayDeque<>();
    ArrayDeque<Integer> y_queue = new ArrayDeque<>();

    //미로 좌표 변수
    private int x;
    private int y;

    //미로 끝좌표 변수
    public int last_x;
    public int last_y;

    //미로 배열
    public Cell[][] map;
    private int[][] last_map;

    //---------------------------프로그램--------------------------------

    //생성자(미로 크기 받기,리셋 메서드 호출)
    public MazeGenerator(int width,int height){
        this.width = width;
        this.height = height;

        reset();
    }

    //리셋 메서드(좌표 시작 위치로 초기화,미로 배열 초기화)
    private void reset(){
        map = new Cell[height][width];
        last_map = new int[height][width];
        x = 0;
        y = height-1;

        //미로 초기화
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                map[i][j] = new Cell();
                if(i == 0) {
                    map[i][j].direction[2] = true;
                }
                if(i == height-1) {
                    map[i][j].direction[3] = true;
                }
                if(j == 0){
                    map[i][j].direction[1] = true;
                }
                if(j == width-1) {
                    map[i][j].direction[0] = true;
                }
            }
        }
        map[y][x].visited = true;

        //끝지점 지정 미로 초기화
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                last_map[i][j] = -1;
            }
        }
        x_queue.add(0);
        y_queue.add(height-1);
        last_map[height-1][0] = 0;
    }

    //모든 벽 벽인지 확인 메서드
    private boolean allWall(){
        int count = 0;

        for(int i = 0; i < map[y][x].direction.length; i++){
            if(map[y][x].direction[i])
                count++;
        }

        return count == map[y][x].direction.length;
    }

    //맵 생성 메서드
    public void generate() {
        int moveDirection;
        int p_x;
        int p_y;

        //미로 만들기 메서드(Stack,DFS)
        while(true) {
            //모두 벽일때 백트래킹 실행
            if(allWall()) {
                if(x_coordinate.isEmpty() || y_coordinate.isEmpty())
                    break;

                x = x_coordinate.pop();
                y = y_coordinate.pop();
                continue;
            }

            p_x = x;
            p_y = y;

            //갈 방향 랜덤으로 정하기
            do{
                moveDirection = random.nextInt(map[y][x].direction.length);
            } while (map[y][x].direction[moveDirection]);

            map[y][x].direction[moveDirection] = true;

            //움직이기
            switch(moveDirection){
                case 0:
                    x += 1;
                    map[y][x].direction[moveDirection+1] = true;
                    break;
                case 1:
                    x -= 1;
                    map[y][x].direction[moveDirection-1] = true;
                    break;
                case 2:
                    y -= 1;
                    map[y][x].direction[moveDirection+1] = true;
                    break;
                case 3:
                    y += 1;
                    map[y][x].direction[moveDirection-1] = true;
                    break;
            }

            //갔던 곳이면 원래대로 돌아가기
            if (map[y][x].visited) {
                x = p_x;
                y = p_y;
                continue;
            }

            map[y][x].visited = true;

            x_coordinate.push(p_x);
            y_coordinate.push(p_y);

            map[p_y][p_x].wall[moveDirection] = true;
            switch(moveDirection){
                case 0, 2:
                    map[y][x].wall[moveDirection+1] = true;
                    break;
                case 1, 3:
                    map[y][x].wall[moveDirection-1] = true;
                    break;
            }
        }

        //미로 끝지점 정하기 메서드(Queue,BFS)
        while(!x_queue.isEmpty() && !y_queue.isEmpty()){
            last_x = x_queue.poll();
            last_y = y_queue.poll();
            for(int i = 0; i < 4; i++){
                if(map[last_y][last_x].wall[i]){
                    switch(i){
                        case 0:
                            if(last_map[last_y][last_x+1] == -1) {
                                last_map[last_y][last_x+1] = last_map[last_y][last_x] + 1;
                                x_queue.add(last_x + 1);
                                y_queue.add(last_y);
                            }
                            break;
                        case 1:
                            if(last_map[last_y][last_x-1] == -1) {
                                last_map[last_y][last_x-1] = last_map[last_y][last_x] + 1;
                                x_queue.add(last_x - 1);
                                y_queue.add(last_y);
                            }
                            break;
                        case 2:
                            if(last_map[last_y-1][last_x] == -1) {
                                last_map[last_y-1][last_x] = last_map[last_y][last_x] + 1;
                                x_queue.add(last_x);
                                y_queue.add(last_y - 1);
                            }
                            break;
                        case 3:
                            if(last_map[last_y+1][last_x] == -1) {
                                last_map[last_y+1][last_x] = last_map[last_y][last_x] + 1;
                                x_queue.add(last_x);
                                y_queue.add(last_y + 1);
                            }
                            break;
                    }
                }

            }
        }
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(last_map[i][j] > last_map[last_y][last_x]){
                    last_x = j;
                    last_y = i;
                }
            }
        }

    }

}

