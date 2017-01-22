package GUI.panes;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import controler.Game;
import controler.GameGUI;
import controler.MenuControler;
import controler.MessageDialog;
import data.PlayerData;
import data.StockData;
import entity.map.Cell;
import entity.prop.Prop;
import entity.prop.PropType;
import entity.stock.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuPane extends HBox {
	
	GameGUI game;
	private MenuControler mc;

	Button view_bt = new Button("View");
	Button tool_bt = new Button("Tool");
	Button stock_bt = new Button("Stock");
	Button giveUp_bt = new Button("Give Up");

	public MenuPane(GameGUI game){
		this.game = game;
		this.mc = Game.getGame().getMenuControler();
		
		this.setSpacing(10);
		
		this.setPadding(new Insets(5,0,5,10));
		
		this.getChildren().addAll(view_bt,tool_bt,stock_bt,giveUp_bt);
		
		view_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				showViewStage();
			}
		});
		
		tool_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				showToolStage();
			}
		});
		
		stock_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				showStockStage();
			}
		});
		
		giveUp_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				showGiveUpStage();
			}
		});
	}
	
	public void showViewStage(){
		final Stage stage = new Stage();
		//Initialize the Stage with type of modal
		stage.initModality(Modality.APPLICATION_MODAL);
		//Set the owner of the Stage 
		stage.initOwner(game.getStage());
		stage.setTitle("View");
		
		Group select_root = new Group();
		VBox view_player_root = new VBox();
		Group view_land_root = new Group();
		Scene scene = new Scene(select_root,200,100);
		stage.setScene(scene);
		
		/*
		 * setting for select_root
		 */
		GridPane gpForSelect = new GridPane();
		ToggleGroup tg = new ToggleGroup();
		RadioButton player_bt = new RadioButton("View players");
		RadioButton land_bt = new RadioButton("View land");
		Button okForSelect = new Button(" OK ");
		
		player_bt.setToggleGroup(tg);
		player_bt.setUserData(0);
		land_bt.setToggleGroup(tg);
		land_bt.setUserData(1);
		player_bt.setSelected(true);
		
		
		gpForSelect.add(player_bt, 1, 1);
		gpForSelect.add(land_bt, 1, 2);
		gpForSelect.add(okForSelect, 1, 3);
		gpForSelect.setLayoutX(50);
		gpForSelect.setLayoutY(10);
		gpForSelect.setVgap(10);
		select_root.getChildren().add(gpForSelect);
		
		okForSelect.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				if((int)tg.getSelectedToggle().getUserData()==0){
					stage.setWidth(600);
					stage.setHeight(Game.getGame().getMap().getPlayers().size()*50+100);
					scene.setRoot(view_player_root);
				}else{
					stage.setWidth(400);
					stage.setHeight(170);
					scene.setRoot(view_land_root);
				}
			}
		});
		
		/*
		 * setting for view_player_root
		 */
		
		final Label label = new Label("All Players");
        label.setFont(new Font("Arial", 20));  
        
        TableView<PlayerData> table = new TableView<PlayerData>();
        table.setEditable(false);  
   
        TableColumn<PlayerData,String> nameCol = new TableColumn<PlayerData,String>("Name"); 
        nameCol.setMinWidth(80);  
        nameCol.setCellValueFactory(  
                new PropertyValueFactory<>("name"));  
        TableColumn<PlayerData,String> cashCol = new TableColumn<PlayerData,String>("Cash"); 
        cashCol.setMinWidth(80);  
        cashCol.setCellValueFactory(  
                new PropertyValueFactory<>("cash")); 
        TableColumn<PlayerData,String> depositeCol = new TableColumn<PlayerData,String>("Deposite");
        depositeCol.setMinWidth(80);  
        depositeCol.setCellValueFactory(  
                new PropertyValueFactory<>("deposite")); 
        TableColumn<PlayerData,String> couponCol = new TableColumn<PlayerData,String>("Coupon");
        couponCol.setMinWidth(80);  
        couponCol.setCellValueFactory(  
                new PropertyValueFactory<>("Coupon")); 
        TableColumn<PlayerData,String> houseCol = new TableColumn<PlayerData,String>("House");
        houseCol.setMinWidth(80);  
        houseCol.setCellValueFactory(  
                new PropertyValueFactory<>("house")); 
        TableColumn<PlayerData,String> stockCol = new TableColumn<PlayerData,String>("Stock");
        stockCol.setMinWidth(80);  
        stockCol.setCellValueFactory(  
                new PropertyValueFactory<>("stock")); 
        TableColumn<PlayerData,String> totalCol = new TableColumn<PlayerData,String>("Total");
        totalCol.setMinWidth(80);  
        totalCol.setCellValueFactory(  
                new PropertyValueFactory<>("total")); 
         
        table.getColumns().addAll(nameCol, cashCol, depositeCol,couponCol,houseCol,stockCol,totalCol);  
   
        
        ObservableList<PlayerData> list =  FXCollections.observableArrayList();
        Game.getGame().getMap().getPlayers().forEach(e->(list.add(new PlayerData(e))));
        table.setItems(list);
        
        Button okForPlayers = new Button("OK");
        
        okForPlayers.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
        });
        
        view_player_root.setSpacing(5);  
        view_player_root.setPadding(new Insets(10, 0, 0, 10));  
        view_player_root.getChildren().addAll(label, table);  
		view_player_root.getChildren().add(okForPlayers);
		
		
		/*
		 * setting for view_land_root
		 */
		GridPane gpForLand = new GridPane();
		ToggleGroup tgForLand = new ToggleGroup();
		RadioButton forwardRb = new RadioButton("Forward");
		forwardRb.setSelected(true);
		forwardRb.setToggleGroup(tgForLand);
		forwardRb.setUserData(0);
		RadioButton backwardRb = new RadioButton("Backward");
		backwardRb.setToggleGroup(tgForLand);
		backwardRb.setUserData(1);
		Label disLabel = new Label("Distance:");
		TextField disField = new TextField();
		Button checkBt = new Button("Check");
		Button exitBt = new Button("Exit");
		
		gpForLand.setPadding(new Insets(20,0,0,20));
		gpForLand.setVgap(10);
		gpForLand.setHgap(10);
		gpForLand.add(forwardRb, 1, 1);
		gpForLand.add(backwardRb, 2, 1);
		gpForLand.add(disLabel, 1, 2);
		GridPane.setColumnSpan(disField, 2);
		gpForLand.add(disField, 2, 2);
		gpForLand.add(checkBt, 3, 3);
		gpForLand.add(exitBt, 4, 3);
		
		checkBt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				try{
					int direction = (int) tgForLand.getSelectedToggle().getUserData();
					int dis = Integer.parseInt(disField.getText());
					if(dis<0) throw new NumberFormatException();
					disField.setText("");
					new MessageDialog(game.getStage(),
							Game.getGame().getControlPlayer().getCell().getAnotherCell(direction==0, dis).getLand().getDiscription(),
							300,150);
				}catch(NumberFormatException e){
					new MessageDialog(game.getStage(),"Incorrect distance input! Try again!",200,100);
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
		view_land_root.getChildren().add(gpForLand);
		stage.show();
		
	}
	
	public void showToolStage(){
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(this.game.getStage());
		stage.setTitle("Tool");
		
		Group root = new Group();
		Scene scene = new Scene(root,400,200);
		stage.setScene(scene);
		
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10,0,0,10));
		gp.setHgap(10);
		gp.setVgap(10);
		Text info = new Text("All Tools:");
		ArrayList<RadioButton> props_bt = new ArrayList<RadioButton>();
		List<Prop> props = Game.getGame().getControlPlayer().getMyProp();
		props.stream().forEach(e->{
			RadioButton rb = new RadioButton(e.getName());
			rb.setUserData(e);
			props_bt.add(rb);
		});
		ToggleGroup tg = new ToggleGroup();
		Button use_bt = new Button("Use it");
		Button help_bt = new Button("Help");
		Button leave_bt = new Button("Leave");
		
		GridPane.setColumnSpan(info, 3);
		gp.add(info, 1, 1);
		props_bt.stream().forEach(e->{
			gp.add(e, props_bt.indexOf(e)%2+1, props_bt.indexOf(e)/2+2);
			e.setToggleGroup(tg);
		});
		gp.add(use_bt, 4, props.size()/2+3);
		gp.add(help_bt, 5, props.size()/2+3);
		gp.add(leave_bt, 6, props.size()/2+3);
		
		use_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Toggle toggle = tg.getSelectedToggle();
				if(toggle==null)
					new MessageDialog(stage,"Select one prop first",150,100);
				else{
					Prop p = (Prop)toggle.getUserData();
					if(!p.canWork()){
						new MessageDialog(game.getStage(),"Not able to work! Please check again!",200,100);
					}else{
						((RadioButton)toggle).setSelected(false);
						((RadioButton)toggle).setDisable(true);
						p._work(game.getStage(),game);
						stage.hide();
					}
				}
			}
		});
		
		help_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Toggle toggle = tg.getSelectedToggle();
				if(toggle==null)
					new MessageDialog(stage,"Select one prop first",150,100);
				else
					new MessageDialog(stage,((Prop)toggle.getUserData()).getDescription(),300,120);
			}
		});	
		leave_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
		});
		root.getChildren().add(gp);
		
		stage.show();
	}
	public void showStockStage(){
		
		if(Game.getGame().getDate().getDayOfWeek()==DayOfWeek.SATURDAY||Game.getGame().getDate().getDayOfWeek()==DayOfWeek.SUNDAY){
			new MessageDialog(game.getStage(),"This is weekend. Stock market is not available!",300,80);
			return;
		}
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(game.getStage());
		stage.setTitle("Stock");
		
		VBox root = new VBox();
		Scene scene = new Scene(root,450,500);
		stage.setScene(scene);
	
		final Label label = new Label("All Stocks");
        label.setFont(new Font("Arial", 20));  
        
        StockTable table = new StockTable(game,stage);
        
        Button okForPlayers = new Button("OK");
        
        okForPlayers.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
        });
        
        root.setSpacing(5);  
        root.setPadding(new Insets(10, 0, 0, 10));  
        root.getChildren().addAll(label, table);  
		root.getChildren().add(okForPlayers);
		
		stage.show();
	}
	
	public void showGiveUpStage(){
		final Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(game.getStage());
		stage.setTitle("Give up");
		
		GridPane root = new GridPane();
		Scene scene = new Scene(root,300,100);
		stage.setScene(scene);
	
		root.setHgap(20);
		root.setVgap(10);
		root.setPadding(new Insets(20,0,0,20));
		Text tip = new Text("Do you really wanna give up?");
		Button okButton = new Button("Confirm");
		Button cancelButton = new Button("Cancel");
		
		GridPane.setColumnSpan(tip, 2);
		root.add(tip, 1, 1);
		root.add(okButton, 2, 2);
		root.add(cancelButton, 3, 2);
		
		okButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				Game.getGame().getMenuControler().giveIn();
				stage.hide();
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				stage.hide();
			}
		});
		stage.show();
	}
}
