package data;

import java.text.DecimalFormat;

import entity.player.Player;
import javafx.beans.property.SimpleStringProperty;

public class PlayerData {
	private SimpleStringProperty name;
	private SimpleStringProperty cash;
	private SimpleStringProperty deposite;
	private SimpleStringProperty coupon;
	private SimpleStringProperty stock;
	private SimpleStringProperty house;
	private SimpleStringProperty total;
	
	public PlayerData(Player p){
		this.name = new SimpleStringProperty(p.getName());
		this.cash = new SimpleStringProperty(new DecimalFormat("#.00").format(p.getCash()));
		this.deposite = new SimpleStringProperty(new DecimalFormat("#.00").format(p.getDeposite()));
		this.coupon = new SimpleStringProperty(String.valueOf(p.getCoupon()));
		this.stock = new SimpleStringProperty(p.getStock());
		this.house = new SimpleStringProperty(new DecimalFormat("#.00").format(p.houseProperty()));
		this.total = new SimpleStringProperty(new DecimalFormat("#.00").format(p.totalProperty()));
	}

	public String getName() {
		return name.get();
	}

	public String getCash() {
		return cash.get();
	}

	public String getDeposite() {
		return deposite.get();
	}

	public String getCoupon() {
		return coupon.get();
	}

	public String getStock() {
		return stock.get();
	}

	public String getHouse() {
		return house.get();
	}

	public String getTotal() {
		return total.get();
	}
}
