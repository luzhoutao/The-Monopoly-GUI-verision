package entity.prop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controler.GameGUI;
import controler.MessageDialog;
import entity.player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DiceControler extends Prop {

	private int expected;

	public DiceControler() {
		super(null,PropType.diceControler);
		price = 25;
	}
	public DiceControler(Player owner) {
		super(owner,PropType.diceControler);
		price = 25;
	}

	public int getExpected() {
		return expected;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "卡片名：遥控骰子\n"
				+ "卡片适用对象：自己\n"
				+ "卡片使用效果：\n"
				+ "使用时可以任意控制骰子的结果，结果只能是1-6";
	}

	@Override
	public String getName() {
		return "遥控骰子";
	}

	public int getPrice() {
		return price;
	}
	@Override
	public boolean canWork() {
		return true;
	}
	@Override
	public void _work(Stage primaryStage,GameGUI game) {
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(primaryStage);
		stage.setTitle("Dice Controller");
		
		Group root = new Group();
		Scene scene = new Scene(root,400,170);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		Label disLabel = new Label("The number you want (1-6):");
		TextField disField = new TextField();
		Button okBt = new Button("OK");
		Button exitBt = new Button("Exit");
		
		gp.setPadding(new Insets(50,0,0,10));
		gp.setVgap(10);
		gp.setHgap(10);
		gp.add(disLabel, 1, 1);
		GridPane.setColumnSpan(disField, 2);
		gp.add(disField, 2, 1);
		gp.add(okBt, 3, 2);
		gp.add(exitBt, 4, 2);
		
		okBt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try{
					expected = Integer.parseInt(disField.getText());
					if(expected>6 || expected<1) 
						throw new NumberFormatException();
					addToWorkingProp();
					removeFromOwner();
					new MessageDialog(primaryStage,"Done!",140,70);
					stage.hide();
				}catch(NumberFormatException e){
					new MessageDialog(primaryStage,"Incorrect distance input! Try again!",200,100);
					disField.setText("");
				}
			}
		});
		
		exitBt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
		});
		root.getChildren().add(gp);
		stage.show();
	}
}
