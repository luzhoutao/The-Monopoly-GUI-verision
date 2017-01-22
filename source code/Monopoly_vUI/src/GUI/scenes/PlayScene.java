package GUI.scenes;

import controler.Game;
import controler.GameGUI;
import entity.map.Cell;
import GUI.panes.MapPane;
import GUI.panes.MenuPane;
import GUI.panes.MessagePane;
import GUI.panes.PlayerPane;
import GUI.panes.TimePane;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class PlayScene extends Scene{

	private static Image bg = new Image("file:icons/play_bg.jpg");
	private GameGUI game;
	private MenuPane menuPane;
	private MapPane mapPane;
	private MessagePane messagePane;
	private TimePane timePane;
	private PlayerPane playerPane;
	
	public PlayScene(HBox root, GameGUI game) {
		super(root,bg.getWidth(),bg.getHeight());
		this.game = game;
		
		root.setBackground(new Background(new BackgroundImage(bg,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
		
		VBox left_box = new VBox();
		VBox right_box = new VBox();
		
		left_box.getChildren().add(menuPane = new MenuPane(game));
		left_box.getChildren().add(mapPane = new MapPane(game));
		left_box.getChildren().add(messagePane = new MessagePane(game));
		
		right_box.getChildren().add(timePane = new TimePane(game));
		right_box.getChildren().add(playerPane = new PlayerPane());
		
		root.getChildren().add(left_box);
		root.getChildren().add(right_box);
		right_box.setPadding(new Insets(Cell.w*2,0,0,0));
		right_box.setSpacing(Cell.w);
		root.setSpacing(Cell.w*2);
	}

	public MenuPane getMenuPane() {
		return menuPane;
	}

	public MapPane getMapPane() {
		return mapPane;
	}

	public MessagePane getMessagePane() {
		return messagePane;
	}

	public TimePane getTimePane() {
		return timePane;
	}

	public PlayerPane getPlayerPane() {
		return playerPane;
	}
}
