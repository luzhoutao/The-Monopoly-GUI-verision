package GUI.panes;

import java.text.DecimalFormat;

import controler.Game;
import entity.map.Cell;
import entity.player.Player;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PlayerPane extends StackPane {
	
	private GridPane content = new GridPane();
	
	private Player p;
	
	private ImageView profile;
	private Text index = new Text();
	private Text name = new Text();
	
	private Label cash_l = new Label("Cash:");
	private Text cash = new Text();
	
	private Label deposite_l = new Label("Deposite:");
	private Text deposite = new Text();
	
	private Label stock_l = new Label("Stock:");
	private Text stock = new Text();
	
	private Label coupon_l = new Label("Coupon:");
	private Text coupon = new Text();
	
	private Label property_l = new Label("Property:");
	private Text property = new Text();

	public PlayerPane(){
		this.profile = new ImageView();
		profile.setFitHeight(Cell.w*3);
		profile.setFitWidth(Cell.w*2);
		this.p = Game.getGame().getControlPlayer();
		this.content.setStyle("-fx-font-size:14px");
		this.content.setHgap(10);
		this.content.setVgap(12);
		
		this.content.setPadding(new Insets(20,0,0,10));
		
		GridPane.setColumnSpan(profile, 2);
		GridPane.setRowSpan(profile, 3);
		content.add(profile, 1, 1);
		
		content.add(index, 3, 2);
		content.add(name, 3, 3);
		
		content.add(cash_l, 1, 5);content.add(cash, 3, 5);
		content.add(deposite_l, 1, 6);content.add(deposite, 3, 6);
		content.add(coupon_l, 1, 7);content.add(coupon, 3,7);
		content.add(stock_l, 1, 8);content.add(stock, 3, 8);
		content.add(property_l, 1, 9);content.add(property, 3, 9);
		
		GridPane.setColumnSpan(cash, 3);
		GridPane.setColumnSpan(deposite, 3);
		GridPane.setColumnSpan(coupon, 3);
		GridPane.setColumnSpan(stock, 3);
		GridPane.setColumnSpan(property, 3);
		
		refresh();
		
		this.getChildren().add(getbg());
		this.getChildren().add(content);
	}

	public void refresh() {
		p = Game.getGame().getControlPlayer();
		this.profile.setImage(p.getImage());
		this.index.setText("Player "+(p.getId()+1));
		this.name.setText(p.getName());
		this.cash.setText(new DecimalFormat("0.00").format(p.getCash()));
		this.deposite.setText(new DecimalFormat("0.00").format(p.getDeposite()));
		this.coupon.setText(String.valueOf(p.getCoupon()));
		this.property.setText(new DecimalFormat("0.00").format(p.totalProperty()));
		this.stock.setText(p.getStock());
	}
	
	public Node getbg(){
		Rectangle r = new Rectangle();
		r.setArcHeight(40);
		r.setArcWidth(40);
		r.setWidth(220);
		r.setHeight(350);
		r.setFill(Color.WHITE);
		r.setOpacity(0.3);
		return r;
	}
}
