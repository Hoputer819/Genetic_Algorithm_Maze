package visualization;

import maze.MazeGenerator;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WindowGenerator extends Application{
    public static MazeGenerator maze;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene mazeScene = new Scene(root,600,600);
        Canvas canvas = new Canvas(600,600);
        GraphicsContext line = canvas.getGraphicsContext2D();
        int pen_x = 50;
        int pen_y = 50;
        int cell_x = 500/maze.width;
        int cell_y = 500/maze.height;

        //창 이름
        stage.setTitle("Genetic_Algorithm");

        //색깔
        root.setStyle("-fx-background-color: black;");
        line.setStroke(Color.WHITE);

        //기타 설정
        stage.setScene(mazeScene);
        root.getChildren().add(canvas);


        for(int i = 0; i < maze.height; i++){
            for(int j = 0; j < maze.width; j++){
                if(i == maze.height-1 && j == 0) {
                    line.setFill(Color.RED);
                    line.fillRect(pen_x+1, pen_y+1, cell_x-1, cell_y-1);
                }
                if(i == maze.last_y && j == maze.last_x) {
                    line.setFill(Color.BLUE);
                    line.fillRect(pen_x+1, pen_y+1, cell_x-1, cell_y-1);
                }
                for(int k = 0; k < 4; k++){
                    if(!maze.map[i][j].wall[k]){
                        switch(k){
                            case 0:
                                line.strokeLine(pen_x+cell_x,pen_y,pen_x+cell_x,pen_y+cell_y);
                                break;
                            case 1:
                                if(j == 0)
                                    line.strokeLine(pen_x,pen_y,pen_x,pen_y+cell_y);
                                break;
                            case 2:
                                if(i == 0)
                                    line.strokeLine(pen_x,pen_y,pen_x+cell_x,pen_y);
                                break;
                            case 3:
                                line.strokeLine(pen_x,pen_y+cell_y,pen_x+cell_x,pen_y+cell_y);
                                break;
                        }
                    }
                }
                pen_x += cell_x;
            }
            pen_x = 50;
            pen_y += cell_y;
        }
        stage.show();
    }
}
