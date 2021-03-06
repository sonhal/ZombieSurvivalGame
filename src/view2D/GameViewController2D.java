package view2D;

import engine.controllers.ActionEvent;
import engine.controllers.GameHandler;
import engine.controllers.GameInitializer;
import engine.entities.components.interfaces.HealthComponent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.entities.items.weapons.Weapon;
import engine.gamestate.HighScoreData;
import engine.services.audio.AudioPlayer;
import engine.services.save.SaveGameHandler;
import engine.view.DrawableTile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.GameViewController;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the application window during game play
 */
public class GameViewController2D implements GameViewController, Initializable, Serializable{

    private GameHandler gameHandler;
    private DrawableTile[][] drawableMatrix;
    private Renderer renderer;
    private Timeline gameLoop;
    private Weapon selectedWeapon;

    @FXML
    private
    Label levelLabel, scoreLabel;

    @FXML
    public
    Button save, gotomenuBtn;

    @FXML
    private
    Canvas gameCanvas;

    @FXML
    private
    Pane  toolBarUnder, hideBlood;

    @FXML
    private Rectangle redBar, greenBar;

    @FXML
    private Label weaponAmmoLabel, weaponNameLabel, gameOverHeader;

    @FXML
    private ImageView weapon, bloodsplatt;

    @FXML
    private BorderPane borderPane, menuBorderPane, deathOverlay, pauseOverlay;

    @FXML
    private StackPane stackPane;

    @FXML
    private HBox mainParent;

    @FXML
    private GridPane topBarGridPane;

    @FXML
    private TableView highScoreTable;

    @FXML
    private TableColumn highScoreDate, highScoreScore;

    @FXML
    private Pane shaderOverlay;


    @Override
    public void updateDrawableState(DrawableTile[][] drawableMatrix) {
        this.drawableMatrix = drawableMatrix;
    }

    /**
     * initializeGameEnv. methods that are to be initialized, controls listeners
     */
    @Override
    public void initializeGameEnv() {
        //scaleWidth();
        //scaleHeight();
        //scaleAllScreens();
        initializeView();
        System.out.println("Game controller active 2");

        gameCanvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            topBarGridPane.setMaxWidth(newValue.doubleValue());
            toolBarUnder.setMaxWidth(newValue.doubleValue());
        });

        renderer = new Renderer(gameCanvas);
        gameCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0,0,gameCanvas.getWidth(),gameCanvas.getHeight());
        System.out.println("Game controller active 3");
        //gameHandler.setObjectInWorld();
    }

    public void startGameloop(){
        runViewTick();
    }

    /**
     * initialize. initializes game view
     */
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

    /**
     * runViewTick. sets gameticks to gameloop
     */
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

    /**
     * setEventHandlers. Controls player inputs, send messages to gameHandler class
     */
    private void setEventHandlers(){
        Platform.runLater(new Runnable() {
            public void run() {
                gameCanvas.getScene().setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.E) {
                        System.out.println("Up key was pressed");
                        gameHandler.sendEvent(ActionEvent.CHANGE_WEAPON);
                    }
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
                    if (e.getCode() == KeyCode.C) {
                        System.out.println("C key was pressed");
                        gameHandler.sendEvent(ActionEvent.CHANGE_WEAPON);
                    }
                    if (e.getCode() == KeyCode.ESCAPE) {
                        System.out.println("Escape key was pressed");
                        goToPauseScreen();
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

    public void initializeView(){
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
    }

    @Override
    public void stopGameLoop(){
        gameHandler.shutDown();
        gameLoop.stop();
    }

    /**
     * gotToMenu. onAction for "back to menu" button, sends user back to game menu
     */
    public void goToMenu() throws IOException {
        stopGameLoop();
        System.out.println("Back to main menu");
        Stage stage = (Stage)gameCanvas.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("gamemenu.fxml"));
        stage.setScene(new Scene(newRoot, 650, 650));
        stage.getScene().setFill(Color.BLACK);
    }


    /**
     * goToDeathScreen. initializes highscore data, toggles death screen overlay, stops gameloop
     */
    public void goToDeathScreen() {
        toggleShaderOverlay();
        Optional<HighScoreData> highScoreData = SaveGameHandler.loadHighScore();
        if(highScoreData.isPresent()){
            highScoreDate.setCellValueFactory(
                    new PropertyValueFactory<HighScoreData.HighScoreEntry,String>("timePoint")
            );
            highScoreScore.setCellValueFactory(
                    new PropertyValueFactory<HighScoreData.HighScoreEntry,Integer>("score")
            );

            StringBuilder builder = new StringBuilder();
            ObservableList<HighScoreData.HighScoreEntry> obsList = FXCollections.observableArrayList();
            obsList.addAll( highScoreData.get().getHighscoreSet());
            highScoreTable.setItems(obsList);
        }
        stopGameLoop();
        System.out.println("Game ended");
        deathOverlay.setVisible(true);
    }

    public void goToPauseScreen(){
        gameLoop.pause();
        toggleShaderOverlay();
        pauseOverlay.setVisible(true);
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
                gameHandler = GameInitializer.loadGameHandler(gameViewController);
                startGameloop();
            }
        });
    }

    public void Continue(){
        toggleShaderOverlay();
        gameLoop.playFromStart();
        pauseOverlay.setVisible(false);
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

    /**
     * updateUI. handels data from gameHandler, connected to java fxml components
     */
    private void updateUI(){

        gameHandler.getPlayer().getComponentByType(HealthComponent.class)
                .ifPresent(scriptableComponent ->
                        greenBar.setWidth(((HealthComponent)scriptableComponent).getHealthAmount()) );

        levelLabel.setText("Level "
                + String.valueOf(gameHandler.getGameStateKeeper().getGameLevelHandler().getCurrentLevel()));

        scoreLabel.setText("Score "
                + String.valueOf(gameHandler.getGameStateKeeper().getPlayerGameScore().getScore()));


        //The parameters activeWeapon sprite, WeaponName, and Ammo should be displayed on screen.
        //Weapon object can be reached by using the get selected activeWeapon bellow.
        gameHandler.getPlayer().getComponentByType(WeaponComponent.class)
                .ifPresent(scriptableComponent ->
                        setSelectedWeapon(((WeaponComponent)scriptableComponent).getActiveWeapon() ));

        if (getSelectedWeapon() != null){
            if (getSelectedWeapon().getAmmo() >= 0){
                weaponAmmoLabel.setText(String.valueOf(getSelectedWeapon().getAmmo()));
            }else{
                weaponAmmoLabel.setText(Character.toString('\u221E'));
            }
            weaponNameLabel.setText(getSelectedWeapon().getWeaponType().getDisplayName());
            weapon.setImage(renderer.getSpriteTranslator()
                    .getSpriteImage(getSelectedWeapon().getWeaponType().getSprite()));
        }
    }

    public void toggleShaderOverlay(){
        if(shaderOverlay.isVisible()){
            shaderOverlay.setVisible(false);
        }
        else {
            shaderOverlay.setVisible(true);
        }
    }

    public void toggleMute(){
        AudioPlayer.getInstance().toggleSound();
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }

}
