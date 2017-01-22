package entity.land;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import entity.map.Cell;
import entity.player.Player;
import entity.prop.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class PropShop extends Land {
	/*
	 * GUI version
	 */
	public PropShop(Cell cell,GameGUI game) {
		super(cell,LandType.propShop,game);
		name = "王师傅的道具铺子";
		this.image = new ImageView(new Image("file:icons/propshop.gif"));
	}
	@Override
	public void _response(Player cp) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle("Prop Shop");
		
		Group root = new Group();
		Scene scene = new Scene(root,400,200);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10,0,0,10));
		gp.setHgap(10);
		gp.setVgap(10);
		Text info = new Text("You have "+cp.getCoupon()+" coupons.");
		ArrayList<RadioButton> props_bt = new ArrayList<RadioButton>();
		List<Prop> props = PropType.allVirtualProp();
		props.stream().forEach(e->{
			props_bt.add(new RadioButton(e.getName()+"("+e.getPrice()+")"));
		});
		props_bt.get(0).setSelected(true);
		ToggleGroup tg = new ToggleGroup();
		Button buy_bt = new Button("Buy");
		Button help_bt = new Button("Help");
		Button leave_bt = new Button("Leave");
		
		GridPane.setColumnSpan(info, 3);
		gp.add(info, 1, 1);
		props_bt.stream().forEach(e->{
			gp.add(e, props_bt.indexOf(e)%2+1, props_bt.indexOf(e)/2+2);
			e.setToggleGroup(tg);
			e.setUserData(props_bt.indexOf(e));
		});
		gp.add(buy_bt, 4, props.size()/2+3);
		gp.add(help_bt, 5, props.size()/2+3);
		gp.add(leave_bt, 6, props.size()/2+3);
		
		root.getChildren().add(gp);
		
		buy_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				int index = (int) tg.getSelectedToggle().getUserData();
				if(cp.payByCoupon(props.get(index).getPrice())){
					Prop p = PropType.getProp(cp, props.get(index).getType());
					cp.addProp(p);
					new MessageDialog(stage,p.getName()+"is bought!",180,60);
					info.setText("You have "+cp.getCoupon()+" coupons.");
				}else{
					new MessageDialog(stage,"Some error encountered or no ability to afford!",180,60);
				}
			}
			
		});
		
		help_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				int index = (int) tg.getSelectedToggle().getUserData();
				new MessageDialog(stage,props.get(index).getDescription(),300,120);
			}
			
		});	
		
		leave_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
				Game.getGame().getMenuControler().go_on();
			}
			
		});	
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){

			@Override
			public void handle(WindowEvent arg0) {
				Game.getGame().getMenuControler().go_on();
			}
			
		});
		stage.show();
	}
	
	/*
	 * general part
	 */
	@Override
	public String getDiscription() {
		// TODO Auto-generated method stub
		return "类型：道具店\n"
				+ "名称：王师傅的道具铺子";
	}
}
