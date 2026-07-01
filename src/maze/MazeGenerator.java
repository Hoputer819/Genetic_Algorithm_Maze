package maze;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    //cell 클래스 객체 생성
    Cell cell = new Cell();

    //Random 객체 생성
    Random random = new Random();

    //미로 크기 변수
    private final int width;
    private final int height;

    //미로 좌표 저장 스택
    Stack<Integer> x_coordinate = new Stack<>();
    Stack<Integer> y_coordinate = new Stack<>();

    //미로 좌표 변수
    private int x;
    private int y;

    //미로 배열
    public Cell[][] map;

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
        x = 0;
        y = height-1;

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                map[i][j] = new Cell();
                if(i == 0)
                    map[i][j].wall[2] = true;
                if(i == height-1)
                    map[i][j].wall[3] = true;
                if(j == 0)
                    map[i][j].wall[1] = true;
                if(j == width-1)
                    map[i][j].wall[0] = true;
            }
        }
    }

    //Cell 사방 확인 프린트(나중에 수정 필요)
    public void mapPrint(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {
                for(int k = 0; k < 4; k++)
                    System.out.printf("%b ",map[i][j].wall[k]);
                System.out.print("  ");
            }
            System.out.println();
        }
    }

    //미로 길 만들기 메서드
    private void mapMove(int direction){
        map[y][x].visited = true;
        x_coordinate.push(x);
        y_coordinate.push(y);
        map[y][x].wall[direction] = true;
        switch(direction){
            case 0:
                x += 1;
                break;
            case 1:
                x -= 1;
                break;
            case 2:
                y -= 1;
                break;
            case 3:
                y += 1;
                break;
        }
    }

    //갔던 길이면 돌아가기 메서드
    private void mapBack(int direction){
        switch(direction){
            case 0:
                x -= 1;
                break;
            case 1:
                x += 1;
                break;
            case 2:
                y += 1;
                break;
            case 3:
                y -= 1;
                break;
        }
        x_coordinate.pop();
        y_coordinate.pop();
        map[y][x].wall[direction] = false;
    }

    //모든 벽 벽인지 확인 메서드
    private boolean allWall(){
        int count = 0;

        for(int i = 0; i < map[y][x].wall.length; i++){
            if(map[y][x].wall[i])
                count++;
        }

        return count == map[y][x].wall.length;
    }

    //맵 생성 메서드
    public void generate() {
        int direction;
        while(true) {
            //모두 벽일때 백트래킹 실행
            if(allWall()) {
                backTracking();
                break;
            }

            //갈 방향 랜덤으로 정하기
            do{
                direction = random.nextInt(map[y][x].wall.length);
            } while (map[y][x].wall[direction]);

            //움직이기
            mapMove(direction);

            //왔던 곳이면 원래대로 돌아가기
            if (map[y][x].visited) {
                mapBack(direction);
                continue;
            }
            System.out.print("왔다");
        }
    }

    //맵 백트래킹
    private void backTracking(){
        x = x_coordinate.pop();
        y = y_coordinate.pop();
        //스택이 비어있으면
        if(x_coordinate.isEmpty() && y_coordinate.isEmpty())
            return;

        generate();
    }
}

