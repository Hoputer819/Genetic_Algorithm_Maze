package maze;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    Random random = new Random();
    //변수
    private final int width; //미로 가로 크기
    private final int height; //미로 세로 크기
    private int x; //미로 가로 좌표
    private int y; //미로 세로 좌표
    private boolean end = false; //미로 그리기 완료 변수
    Stack<Integer> x_coordinate = new Stack<>();
    Stack<Integer> y_coordinate = new Stack<>();

    // 배열
    private int[][] mazeMap; //미로 배열
    private boolean[][] mazeVisited; //미로 방문 여부 배열(비어있으면 false)
    private boolean[] direction; //미로 상하좌우 확인 변수{오른쪽,왼쪽,아래,위}(비어있으면 true)


    //미로 크기 생성자(완성)
    public MazeGenerator(int width,int height){
        this.width = width;
        this.height = height;
        reset();
    }

    //미로 초기화 메서드(완성)
    private void reset(){
        mazeMap = new int[height][width];
        mazeVisited = new boolean[height][width];
        direction = new boolean[4];
        x = random.nextInt(width);
        y = height-1;

    }

    //미로 출력 메서드(완성)
    public void mapPrint(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.printf("%d  ",mazeMap[i][j]);
            }
            System.out.println();
        }
    }

    //미로 방문 여부 출력 메서드
    public void visitedPrint(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.printf("%b  ",mazeVisited[i][j]);
            }
            System.out.println();
        }
    }

    //미로 사방 확인 메서드(완성)
    private void check(){
        for (int i = 0; i < 4; i++) {
            direction[i] = false;
        }
        for (int i = 0; i < 2; i++) {
            if (x - (2 * i - 1) >= 0 && x - (2 * i - 1) < width)
                direction[i] =  !mazeVisited[y][x - (2 * i - 1)];
            if (y + (1-2*i) >= 0 && y + (1-2*i) < height)
                direction[i + 2] = !mazeVisited[y + (1-2*i)][x];
        }
    }

    //미로 벽 생성 조건 메서드
    private boolean wallCheck(){
        int count = 0;

        for (int i = 0; i < 2; i++) {
            if (x - (2 * i - 1) >= 0 && x - (2 * i - 1) < width) {
                if (mazeMap[y][x - (2 * i - 1)] == 1) {
                    count += 1;
                }
            }
            if (y + (1-2*i) >= 0 && y + (1-2*i) < height) {
                if (mazeMap[y + (1 - 2 * i)][x] == 1) {
                    count += 1;
                }
            }
            if( count >= 2)
                return true;
        }
        return false;
    }

    //미로 길 표시 메서드(완성)
    private void mark(){
        mazeMap[y][x] = 1;
        mazeVisited[y][x] = true;
        x_coordinate.push(x);
        y_coordinate.push(y);
    }

    //미로 모두 방문 여부(완성)
    private boolean allVisited(){
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (mazeVisited[i][j])
                    return true;
            }
        }
        return false;
    }

    //사방이 벽인지 확인 메서드(완성)
    private boolean allWall(){
        for(int i = 0; i < 4; i++) {
            if(direction[i])
                return false;
        }
        return true;
    }

    //미로 만들기 메서드
    public void generate() {
        while(!end) {
            int move_direction;//움직일 방향 변수

            mark();
            check();
            for(int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if(!mazeVisited[i][j]){
                        if(wallCheck()){
                            mazeVisited[i][j] = true;
                        }
                    }
                }
            }
            if(allWall()){
                backTracking();
                break;
            }
            while (true) {
                move_direction = random.nextInt(4);
                if (!direction[move_direction]) {
                    continue;
                }
                break;
            }
            switch (move_direction) {
                case 0:
                    x += 1;
                    break;
                case 1:
                    x -= 1;
                    break;
                case 2:
                    y += 1;
                    break;
                case 3:
                    y -= 1;
                    break;
            }
            mapPrint();
            System.out.println();
            visitedPrint();
            System.out.println();
        }
    }

    //미로 백트래킹 메서드(완성)
    private void backTracking() {
        while (allVisited()) {
            int move_direction;//움직일 방향 변수

            if(!x_coordinate.isEmpty() && !y_coordinate.isEmpty()) {
                x = x_coordinate.pop();
                y = y_coordinate.pop();
            }
            else
                end = true;
            while (true) {
                move_direction = random.nextInt(4);
                if (!direction[move_direction]) {
                    continue;
                }
                break;
            }

            generate();
        }
    }
}
