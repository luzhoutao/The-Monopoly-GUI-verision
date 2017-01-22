package entity.prop;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.player.Direction;
import entity.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Turnover extends Prop {

	public Turnover() {
		super(null,PropType.turnover);
		price = 30;
	}
	public Turnover(Player owner) {
		super(owner,PropType.turnover);
		price = 30;
	}

	@Override
	public String getDescription() {
		return "卡片名：转向卡\n"
				+ "卡片适用对象：自己或距离自己五步以内的对手\n"
				+ "卡片使用效果：\n"
				+ "使目标调转方向";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "转向卡";
	}
	
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}
	@Override
	public boolean canWork() {
		return true;
	}
	@Override
	public void _work(Stage primaryStage, GameGUI game) {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(primaryStage);
		stage.setTitle("Ask whom to turn over?");
		
		Group root = new Group();
		Scene scene = new Scene(root,200,250);
		stage.setScene(scene);
		
		VBox box = new VBox();
		box.setSpacing(10);
		box.setPadding(new Insets(20,0,0,20));
		Label title = new Label("All reachable players:");
		ArrayList<RadioButton> player_rbs = new ArrayList<RadioButton>();
		List<Player> reachablePlayers = Game.getGame().getControlPlayer().getReachablePlayer(5);
		reachablePlayers.add(this.owner);
		reachablePlayers.forEach(e->{
			player_rbs.add(new RadioButton(e.getName()));
		});
		ToggleGroup tg = new ToggleGroup();
		player_rbs.get(0).setSelected(true);
		Button ok_bt = new Button("That's it!");
		
		box.getChildren().add(title);
		player_rbs.stream().forEach(e->{
			e.setToggleGroup(tg);
			box.getChildren().add(e);
			e.setUserData(reachablePlayers.get(player_rbs.indexOf(e)));
		});
		box.getChildren().add(ok_bt);
		
		ok_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Player target = (Player) tg.getSelectedToggle().getUserData();		
				target.turnover();
				removeFromOwner();
				stage.hide();
				game.freshMessage();
				new MessageDialog(stage,"Player "+target.getName()+" change to "+Direction.getDescription(target.getDirection()),300,100);
			}
		});
		
		root.getChildren().add(box);
		stage.show();
	}

}
