import engine.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view2D.GameViewController2D;

import static org.junit.jupiter.api.Assertions.*;

class GameViewController2DTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void runGameView2D(){

        StartGameViewStub main = new StartGameViewStub();
        main.go(new String[] {""});


        GameViewController2D gv = main.gameView;
        GameHandler gh = main.gameHandler;
        if(gh == null){
            System.out.println("Faak");
        }

        gv.updateDrawableState(setStubDrawableMatrix(gh));
        gv.startGameloop();


    }

    @Test
    void updateDrawableState() {
    }

    @Test
    void startGame() {
    }

    @Test
    void initialize() {
    }

    @Test
    void runViewTick() {
    }

    @Test
    void drawTile() {
    }


    public DrawableTile[][] setStubDrawableMatrix(GameHandler gh){
        DrawableTile[][] drawableMatrix = gh.getDrawableMatrix(10).matrix;
        try {
            for (DrawableTile[] tiles: drawableMatrix
                    ) {
                for (DrawableTile tile: tiles
                        ) {
                    try{
                        tile.setGameObject(new Item(new Sprite(1), 10));
                    }
                    catch (NullPointerException er){
                        System.out.println("There was a null Tile here");
                    }
                }

            }
            drawableMatrix[1][1].setGameObject(new Item(new Sprite(2), 10));
            drawableMatrix[5][5].setGameObject(new Item(new Sprite(2), 10));
            drawableMatrix[10][10].setGameObject(new Item(new Sprite(2), 10));


        }catch (Exception e){
            e.printStackTrace();
        }
        return drawableMatrix;

    }
}