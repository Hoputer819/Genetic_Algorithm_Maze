package maze;

import java.util.Random;

public class MazeGenerator {
    //cell 클래스 객체 생성
    Cell cell = new Cell();

    //미로 크기 변수
    private final int width;
    private final int height;

    //미로 좌표 변수
    private int x;
    private int y;

    //미로 배열
    public Cell[][] map;


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
            for(int j = 0; j < width; j++)
                map[i][j] = new Cell();
        }
    }

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
}
