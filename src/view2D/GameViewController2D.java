package view2D;

import engine.controllers.ActionEvent;
import engine.controllers.GameHandler;
import engine.controllers.GameInitializer;
import engine.entities.components.InventoryComponent;
import engine.entities.components.interfaces.HealthComponent;
import engine.entities.components.interfaces.WeaponComponent;
import engine.entities.items.weapons.Weapon;
import engine.gamestate.HighScoreData;
import engine.services.save.SaveGameHandler;
import engine.view.DrawableTile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.GameViewController;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
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
    Label levelLabel, scoreLabel, highscoreLbl;

    @FXML
    public
    Button save, gameBtn, settingsBtn, gotomenuBtn;

    @FXML
    private
    Canvas gameCanvas;

    @FXML
    private TabPane TabMenu;

    @FXML
    private Tab tabGame, tabSettings, tabHighscore;

    @FXML
    private
    Pane  gameOver1, toolBar, toolBarUnder, hideBlood;

    @FXML
    private Rectangle redBar, greenBar;

    @FXML
    private Label weaponAmmoLabel, weaponNameLabel;

    @FXML
    private ImageView weapon, bloodsplatt;

    @FXML
    private TextArea highscoreText, highscoreText2;

    @FXML
    private BorderPane borderPane, menuBorderPane;

    @FXML
    private StackPane stackPane;


    @Override
    public void updateDrawableState(DrawableTile[][] drawableMatrix) {
        this.drawableMatrix = drawableMatrix;
    }

    @Override
    public void initializeGameEnv() {
        //scaleWidth();
        //scaleHeight();
        //scaleAllScreens();
        initializeView();
        System.out.println("Game controller active 2");

        menuBorderPane.setMaxWidth(400);
        menuBorderPane.setMaxHeight(400);
        if(stackPane.getHeight() < stackPane.getWidth()){
            gameCanvas.setHeight(stackPane.getHeight());
            gameCanvas.setWidth(stackPane.getHeight());
        }
        else{
            gameCanvas.setHeight(stackPane.getWidth());
            gameCanvas.setWidth(stackPane.getWidth());
        }

        stackPane.heightProperty().addListener((ov, oldHeight, newHeight) -> {
            if(stackPane.getWidth() > newHeight.doubleValue()){
                gameCanvas.setHeight(newHeight.doubleValue());
                gameCanvas.setWidth(newHeight.doubleValue());
            }
        });

        stackPane.widthProperty().addListener((ov, oldHeight, newWidth) -> {
                if(stackPane.getHeight() > newWidth.doubleValue()){
                    gameCanvas.setHeight(newWidth.doubleValue());
                    gameCanvas.setWidth(newWidth.doubleValue());
                }
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
                        gameLoop.pause();
                        TabMenu.setVisible(true);
                        hideBlood.setVisible(false);
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
        highscoreText.setEditable(false);
        TabMenu.setVisible(false);
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        HighScoreData highScoreData = SaveGameHandler.loadHighscore();
        StringBuilder builder = new StringBuilder();
        highScoreData.getHighscoreSet().forEach(highScoreEntry -> builder.append(highScoreEntry +"\n\n"));
        highscoreText.setText(builder.toString());
    }

    @Override
    public void stopGameLoop(){
        gameHandler.shutDown();
        gameLoop.stop();
    }

    public void goToMenu() throws IOException {
        stopGameLoop();
        System.out.println("Back to main menu");
        Stage stage = (Stage)gameCanvas.getScene().getWindow();
        Parent newRoot = FXMLLoader.load(getClass().getResource("gamemenu.fxml"));
        stage.setScene(new Scene(newRoot, 650, 650));
        stage.getScene().setFill(Color.BLACK);
    }

    public void goToDeathScreen() {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        HighScoreData highScoreData = SaveGameHandler.loadHighscore();
        StringBuilder builder = new StringBuilder();
        highScoreData.getHighscoreSet().forEach(highScoreEntry -> builder.append(highScoreEntry +"\n\n"));
        highscoreText.setText(builder.toString());
        stopGameLoop();
        System.out.println("Game ended");
        TabMenu.setVisible(true);
        tabGame.setDisable(true);
        tabHighscore.setDisable(false);
        hideBlood.setVisible(true);
        tabSettings.setDisable(true);
        highscoreLbl.setText("Game Over");
        gotomenuBtn.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                gameOver1.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
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
        gameLoop.playFromStart();
        TabMenu.setVisible(false);
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
            weaponNameLabel.setText("Weapon: " + getSelectedWeapon().getWeaponType().getDisplayName());
            weapon.setImage(renderer.getSpriteTranslator()
                    .getSpriteImage(getSelectedWeapon().getWeaponType().getSprite()));
        }
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setSelectedWeapon(Weapon selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
    }
}
