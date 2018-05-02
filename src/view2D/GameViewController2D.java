package view2D;

import engine.controllers.ActionEvent;
import engine.controllers.GameHandler;
import engine.controllers.GameInitializer;
import engine.view.DrawableTile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.GameViewController;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;


public class GameViewController2D implements GameViewController, Initializable, Serializable{

    GameHandler gameHandler;
    DrawableTile[][] drawableMatrix;
    Renderer renderer;
    Scene scene;
    Timeline gameLoop;

    @FXML
    private
    Label gameOverLbl, levelLabel, scoreLabel;

    @FXML
    public
    Button save, gameBtn, settingsBtn;

    @FXML
    private
    Canvas gameCanvas;

    @FXML
    private
    Pane settingsSet, gameSet , colorBG, anchor, gameOver;

    private boolean loadGameFlag;


    @Override
    public void updateDrawableState(DrawableTile[][] drawableMatrix) {
        this.drawableMatrix = drawableMatrix;
    }

    @Override
    public void initializeGameEnv() {
        System.out.println("Game controller active");
        gameCanvas.setHeight(gameCanvas.getHeight());
        gameCanvas.setWidth(gameCanvas.getWidth());
        colorBG.setManaged(false);
        colorBG.setVisible(false);
        settingsSet.setVisible(false);
        settingsSet.setManaged(false);
        gameSet.setVisible(false);
        gameSet.setManaged(false);
        gameBtn.setVisible(false);
        settingsBtn.setVisible(false);
        gameOver.setVisible(false);
        gameOver.setManaged(false);



        System.out.println("Game controller active 2");

        renderer = new Renderer(gameCanvas);
        gameCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        System.out.println("Game controller active 3");
        //gameHandler.setObjectInWorld();
    }

    public void startGameloop(){
        runViewTick();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeGameEnv();
        setEventHandlers();

        save.setOnAction((event) -> {
            // play game button pressed
            try {
                saveGame();
            }catch (Exception err){
                //show error to player
                err.printStackTrace();
            }
        });
    }

    public void runViewTick(){
        gameLoop = new Timeline();
        gameLoop.setCycleCount( Timeline.INDEFINITE );

        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),                // 60 FPS
                ae -> update());

        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    private void setEventHandlers(){
        Platform.runLater(new Runnable() {
            public void run() {
                gameCanvas.getScene().setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.UP) {
                        System.out.println("Up key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_UP);
                    }
                    if (e.getCode() == KeyCode.DOWN) {
                        System.out.println("Down key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_DOWN);
                    }
                    if (e.getCode() == KeyCode.LEFT) {
                        System.out.println("Left key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_LEFT);
                    }
                    if (e.getCode() == KeyCode.RIGHT) {
                        System.out.println("Right key was pressed");
                        gameHandler.sendEvent(ActionEvent.MOVE_RIGHT);
                    }
                    if (e.getCode() == KeyCode.SPACE) {
                        System.out.println("Space key was pressed");
                        gameHandler.sendEvent(ActionEvent.ATTACK_UP);
                    }
                    if (e.getCode() == KeyCode.ESCAPE) {
                        System.out.println("Escape key was pressed");
                        gameLoop.pause();
                        gameSet.setManaged(true);
                        gameSet.setVisible(true);
                        colorBG.setVisible(true);
                        colorBG.setManaged(true);
                        gameBtn.setVisible(true);
                        settingsBtn.setVisible(true);
                        gameOverLbl.setVisible(false);
                    }
                });
            }
        });
    }

    private void update(){
        drawableMatrix = gameHandler.getDrawableWorld();
        renderer.render(drawableMatrix);
        updateUI();
    }

    @Override
    public void stopGameLoop(){
        gameLoop.stop();
    }

    public void goToMenu() throws IOException {
        stopGameLoop();
        System.out.println("Back to main menu");
        Stage stage = (Stage)gameCanvas.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("gamemenuV2.fxml"));
        stage.setScene(new Scene(newRoot, 715, 711));
    }

    public void goToDeathScreen() throws IOException {
        stopGameLoop();
        System.out.println("Game ended");
        gameOver.setVisible(true);
        gameOver.setManaged(true);
        colorBG.setVisible(true);
        colorBG.setManaged(true);
        settingsBtn.setVisible(true);
        gameBtn.setVisible(true);
    }

    public void startNewGame(GameViewController gameViewController){
        Platform.runLater(new Runnable() {
            public void run() {
                gameHandler = GameInitializer.newGame(gameViewController);
                startGameloop();
            }
        });
    }
    public void startLoadGame(GameViewController gameViewController){
        Platform.runLater(new Runnable() {
            public void run() {
                gameHandler = GameInitializer.loadGame(gameViewController);
                startGameloop();
            }
        });
    }
    public void openMenu(){
        gameLoop.pause();
        colorBG.setVisible(true);
        settingsSet.setVisible(true);
        colorBG.setManaged(true);
        settingsSet.setManaged(true);
    }

    public void switchGame() {
        gameOver.setVisible(false);
        gameOver.setManaged(false);
        gameSet.setVisible(true);
        gameSet.setManaged(true);
        settingsSet.setVisible(false);
        settingsSet.setManaged(false);
        gameOverLbl.setVisible(false);
    }

    public void switchSettings() {
        gameOver.setVisible(false);
        gameOver.setManaged(false);
        gameSet.setVisible(false);
        gameSet.setManaged(false);
        settingsSet.setVisible(true);
        settingsSet.setManaged(true);
        gameOverLbl.setVisible(false);
    }

    public void switchHighscore(){
        gameSet.setManaged(false);
        gameSet.setVisible(false);
        settingsSet.setVisible(false);
        settingsSet.setManaged(false);
        gameOver.setManaged(true);
        gameOver.setVisible(true);
        gameOverLbl.setVisible(false);
    }

    public void Continue(){
        gameLoop.playFromStart();
        settingsSet.setManaged(false);
        settingsSet.setVisible(false);
        gameSet.setManaged(false);
        gameSet.setVisible(false);
        colorBG.setVisible(false);
        colorBG.setManaged(false);
        gameBtn.setVisible(false);
        settingsBtn.setVisible(false);
    }

    public void saveGame(){
        gameLoop.stop();
        gameHandler.saveGame();
        try {
            goToMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUI(){
        levelLabel.setText("Level " + String.valueOf(gameHandler.getGameStateKeeper().getGameLevelHandler().getCurrentLevel().getLevel()));
        scoreLabel.setText("Score " + String.valueOf(gameHandler.getGameStateKeeper().getPlayerGameScore().getScore()));
    }
}
