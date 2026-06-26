package maze;

import java.util.Random;

public class MazeGenerator {
    Random random = new Random();

    private final int width; //미로 가로 크기
    private final int height; //미로 세로 크기
    private int x; //미로 가로 좌표
    private int y; //미로 세로 좌표
    private int[][] mazeMap; //미로 배열
    private boolean[][] mazeVisited; //미로 방문 여부 배열
    private boolean[] direction; //{오른쪽,왼쪽,위,아래}

    //미로 크기 생성자
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
    public void print(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.printf("%d  ",mazeMap[i][j]);
            }
            System.out.println();
        }
    }

    //미로 방문 여부 메서드
    private void check(int x,int y){
        for(int i = 0; i < 4; i++){
            direction[i] = false;
        }
        for(int i = 0; i < 2; i++){
            if(x > 0 && x < width)
                direction[i] = mazeVisited[y][x-(2*i-1)];
            if(y > 0 && y < height)
                direction[i+2] = mazeVisited[y+(2*i-5)][x];
        }
    }

    //미로 그리기 메서드

    //미로 백트래킹 메서드
}
