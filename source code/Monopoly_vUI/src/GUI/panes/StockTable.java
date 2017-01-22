package GUI.panes;

import java.util.ArrayList;

import controler.Game;
import controler.GameGUI;
import controler.MessageDialog;
import data.StockData;
import entity.player.Player;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StockTable extends TableView<StockData>{
	private Stage stage;
	private GameGUI gameGUI;

	public StockTable(GameGUI gameGUI,Stage stage){
		this.stage = stage;
		this.gameGUI = gameGUI;
		
        this.setEditable(false);  
        
        TableColumn<StockData,String> nameCol = this.getColumn("Name", "name", 80); 
        TableColumn<StockData,String> priceCol = this.getColumn("Price", "price", 80); 
        TableColumn<StockData,String> scopeCol = this.getColumn("Scope", "scope", 80); 
        ArrayList<TableColumn<StockData,String>> numberCols = new ArrayList<TableColumn<StockData,String>>();
        Game.getGame().getMap().getPlayers().stream().forEach(e->{
        	numberCols.add(getColumn(e.getName()+" owns","player"+Game.getGame().getMap().getPlayers().indexOf(e),80));
        }); 

        this.getColumns().addAll(nameCol, priceCol, scopeCol); 
        this.getColumns().addAll(numberCols);
   
        
        ObservableList<StockData> list =  FXCollections.observableArrayList();
        Stock.getAllStock().stream().forEach(e->(list.add(new StockData(e,Game.getGame().getMap().getPlayers(),Stock.getAllStock().indexOf(e)))));
        this.setItems(list);
	}
	
	private TableColumn<StockData, String> getColumn(String columnName, String propertyName, int width) {
	    TableColumn<StockData, String> tableColumn = new TableColumn<>(columnName);
	    tableColumn.setCellFactory(new Callback<TableColumn<StockData, String>, TableCell<StockData, String>>(){
	    	@Override
		    public TableCell<StockData, String> call(TableColumn<StockData, String> param) {
		        TextFieldTableCell<StockData, String> cell = new TextFieldTableCell<>();
		        cell.setOnMouseClicked((MouseEvent t) -> {
		            if (t.getClickCount() == 2) {
		                transaction(Game.getGame().getControlPlayer(),cell.getIndex());
		            }
		        });
		        //cell.setContextMenu(taskContextMenu);
		        return cell;
		    }
	    });
	    tableColumn.setCellValueFactory(new PropertyValueFactory<>(propertyName));
	    tableColumn.setMinWidth(width);
	    tableColumn.setPrefWidth(width);
	    return tableColumn;
	}
	
	private void transaction(Player p, int stockIndex){
		Stock stock = Stock.getAllStock().get(stockIndex);
		final Stage lstage = new Stage();
		lstage.initModality(Modality.APPLICATION_MODAL);
		lstage.initOwner((Stage)this.stage.getOwner());
		lstage.setTitle(stock.getName());
		
		Group root = new Group();
		Scene scene = new Scene(root,400,200);
		lstage.setScene(scene);
		
		GridPane gp = new GridPane();
		Text info = new Text("You have "+stock.getName()+" "+p.getNumberOfStock(stockIndex)+" shares.");
		ToggleGroup tg = new ToggleGroup();
		RadioButton buy_rb = new RadioButton("Buy");
		buy_rb.setUserData(0);
		buy_rb.setSelected(true);
		buy_rb.setToggleGroup(tg);
		RadioButton sell_rb = new RadioButton("Sell");
		sell_rb.setUserData(1);
		sell_rb.setToggleGroup(tg);
		Label amount_l = new Label("Amount:");
		TextField amount_tf = new TextField();
		Button ok_bt = new Button("OK");
		Button leave_bt = new Button("Leave");
		
		gp.setPadding(new Insets(20,0,0,20));
		gp.setVgap(10);
		gp.setHgap(10);
		GridPane.setColumnSpan(info, 4);
		gp.add(info, 1, 1);
		gp.add(buy_rb, 1,2);
		gp.add(sell_rb, 2, 2);
		gp.add(amount_l, 1, 3);
		GridPane.setColumnSpan(amount_tf, 3);
		gp.add(amount_tf, 2, 3);
		gp.add(ok_bt, 3, 4);
		gp.add(leave_bt, 4, 4);
		
		ok_bt.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				try{
					int typeIndex = (int) (tg.getSelectedToggle().getUserData());
					int amount = Integer.parseInt(amount_tf.getText());
					if((typeIndex==0 && p.buyStock(stockIndex,amount)) || (typeIndex==1 &&p.sellStock(stockIndex,amount))){
						MessageDialog md = new MessageDialog(lstage,"Done!",100,100);
						gameGUI.freshPlayerInfo();
						md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								lstage.hide();
								stage.hide();
							}
						});
					}else {
						new MessageDialog(lstage,"Not able to buy or sell!",250,120);
						amount_tf.setText("");
					}
				}catch(NumberFormatException e){
					new MessageDialog(lstage,"Incorrect number input! Try again!",250,150);
					amount_tf.setText("");
				}
			}	
			
		});
		
		leave_bt.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				lstage.hide();
			}
		});
		root.getChildren().add(gp);
		lstage.show();
	}
}
