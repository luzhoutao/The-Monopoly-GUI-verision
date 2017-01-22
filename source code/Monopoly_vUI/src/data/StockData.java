package data;

import java.util.ArrayList;

import entity.player.Player;
import entity.stock.Stock;
import javafx.beans.property.SimpleStringProperty;

public class StockData {

	private SimpleStringProperty name;
	private SimpleStringProperty price;
	private SimpleStringProperty scope;
	private SimpleStringProperty player0;
	private SimpleStringProperty player1;
	private SimpleStringProperty player2;
	private SimpleStringProperty player3;
	
	public StockData(Stock s,ArrayList<Player> players,int index){
		this.name = new SimpleStringProperty(s.getName());
		this.price = new SimpleStringProperty(String.valueOf(s.getPrice()));
		this.scope = new SimpleStringProperty(String.valueOf(s.getScope()));
		
		for(int i = 0;i<players.size();i++){
			switch(i){
				case 0:	
					player0 = new SimpleStringProperty(String.valueOf(players.get(i).getNumberOfStock(index)));
					break;
				case 1:
					player1 = new SimpleStringProperty(String.valueOf(players.get(i).getNumberOfStock(index)));
					break;
				case 2:
					player2 = new SimpleStringProperty(String.valueOf(players.get(i).getNumberOfStock(index)));
					break;
				case 3:
					player3 = new SimpleStringProperty(String.valueOf(players.get(i).getNumberOfStock(index)));
					break;
			}
		}
	}

	public String getName() {
		return name.get();
	}

	public String getPrice() {
		return price.get();
	}

	public String getScope() {
		return scope.get();
	}

	public String getPlayer0() {
		return player0.get();
	}

	public String getPlayer1() {
		return player1.get();
	}

	public String getPlayer2() {
		return player2.get();
	}

	public String getPlayer3() {
		return player3.get();
	}
}
