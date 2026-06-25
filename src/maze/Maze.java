package maze;

import java.util.Random;
import java.util.Stack;

public class Maze {
    Random random = new Random();//랜덤 생성기

    Stack<Integer> width_coordinate = new Stack<>();
    Stack<Integer> height_coordinate = new Stack<>();
    private int[][] map; //미로 배열
    private boolean[][] visit;
    private boolean[] direction = {false,false,false,false};//{오른쪽,왼쪽,위,아래}
    private final int width; //미로 가로 길이
    private final int height; //미로 세로 길이
    private int count;
    private int map_count = 0;


    public Maze(int width, int height){
        this.width = width;
        this.height = height;
    }

    //미로 좌표 방문 표시
    private void change(int x,int y){
        map[y][x] = 1;
        visit[y][x] = true;
        width_coordinate.push(x);
        height_coordinate.push(y);
        map_count += 1;
    }

    //사방 뚫렸는지 확인
    private void check(int x,int y){
        count = 0;

        //상하좌우 초기화
        for (int i = 0; i < 4; i++)
            direction[i] = false;

        for (int i = 1; count < 2; i *= -1){
            // 좌우 확인
            if (visit[y][x+i] == false){
                switch(count){
                    case 0:
                        direction[0] = true;
                        break;
                    case 1:
                        direction[1] = true;
                        break;
                }
            }

            //상하 확인
            if (visit[y+i][x] == false){
                switch(count){
                    case 0:
                        direction[3] = true;
                        break;
                    case 1:
                        direction[2] = true;
                        break;
                }
            }

            count += 1;
        }
    }

    //미로 생성 메서드
    public void generate() {
        map = new int[height][width];
        visit = new boolean[height][width];
        int x = random.nextInt(width);
        int y = height-1;
        int num = random.nextInt(4);

        //미로 초기화
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = 0;
                visit[i][j] = false;
            }
        }

        do{
            change(x,y);
            check(x,y);
            for (int i; i < 4; i++){

            }
        }while(map_count != width*height);
    }

    //미로 출력 메서드
    public void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[i][j]);
                System.out.print("  ");
            }
            System.out.println();
        }
    }
}
