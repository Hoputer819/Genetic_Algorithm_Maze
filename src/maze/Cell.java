package maze;

public class Cell {
    //방문 확인 배열
    public boolean visited = false;
    //벽 배열
    public boolean[] wall = {false,false,false,false}; //{오른쪽,왼쪽,위,아래}
}
