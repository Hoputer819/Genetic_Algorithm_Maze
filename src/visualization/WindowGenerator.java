package visualization;

import javafx.animation.AnimationTimer;
import maze.MazeGenerator;
import genetic.GeneticMover;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WindowGenerator extends Application{
    public static MazeGenerator maze;
    GeneticMover genetic = new GeneticMover();
    AnimationTimer timer;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Scene mazeScene = new Scene(root,600,600);
        Canvas canvas = new Canvas(600,600);
        Canvas genetic_canvas = new Canvas(600,600);
        GraphicsContext line = canvas.getGraphicsContext2D();
        GraphicsContext genetic_line = genetic_canvas.getGraphicsContext2D();
        int pen_x = 50;
        int pen_y = 50;
        int cell_x = 500/maze.size;
        int cell_y = 500/maze.size;
        int oval_x = pen_x + (cell_x/2);
        int oval_y = pen_y + (cell_y/2);

        //창 이름
        stage.setTitle("Genetic_Algorithm");

        //색깔
        root.setStyle("-fx-background-color: black;");
        line.setStroke(Color.WHITE);
        genetic_line.setFill(Color.WHITE);
        genetic_line.setStroke(Color.WHITE);

        //기타 설정
        stage.setScene(mazeScene);
        root.getChildren().addAll(canvas,genetic_canvas);
        genetic_line.setLineWidth(cell_x/2.0);

        //미로 그리기
        for(int i = 0; i < maze.size; i++){
            for(int j = 0; j < maze.size; j++){
                if(i == maze.size-1 && j == 0) {
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


        timer = new AnimationTimer() {

            @Override
            public void handle(long l) {
                boolean all_die = true;
                for (int j = 0; j < 20; j++) {
                    for (int i = 0; i < genetic.individual_num; i++) {
                        if (genetic.population.get(i).die)
                            continue;

                        all_die = false;
                        int bf_x = oval_x + cell_x * (genetic.population.get(i).current_x);
                        int bf_y = oval_y + cell_y * (genetic.population.get(i).current_y);
                        genetic.run(i);
                        int af_x = oval_x + cell_x * (genetic.population.get(i).current_x);
                        int af_y = oval_y + cell_y * (genetic.population.get(i).current_y);
                        genetic_line.strokeLine(bf_x, bf_y, af_x, af_y);

                        if (genetic.population.get(i).current_x == maze.last_x && genetic.population.get(i).current_y == maze.last_y)
                            timer.stop();
                    }
                    genetic.gene_count++;

                    if (all_die || genetic.gene_count == genetic.gene_num) {
                        genetic_line.clearRect(0, 0, 600, 600);
                        genetic.replace();
                    }

                }
            }
        };

        timer.start();

    }
}
