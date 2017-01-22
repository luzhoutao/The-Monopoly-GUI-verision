package GUI.panes;

import controler.Game;
import controler.GameGUI;
import entity.map.Cell;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MessagePane extends GridPane{
	
	private static Image icon_img = new Image("file:icons/message.gif");
	private static String mesg = "Nothing in the way!";
	private GameGUI game;
	private ImageView icon = new ImageView(icon_img);
	private Text message = new Text(mesg);

	public MessagePane(GameGUI game){
		this.game = game;
		this.setPadding(new Insets(10,0,0,0));
		this.setHgap(3);
		
		icon.setFitHeight(Cell.w);
		icon.setFitWidth(Cell.w);
		this.add(icon, 1, 1);
		
		GridPane.setColumnSpan(message, 10);
		message.setFill(Color.WHITE);
		this.add(message, 2,1);
		
		Rectangle r = new Rectangle();
		GridPane.setColumnSpan(r, 10);
		r.setWidth((Cell.w+5)*(Game.getGame().getMap().getLength()+2));
		r.setHeight(25);
		r.setFill(Color.WHITE);
		r.setOpacity(0.2);
		this.add(r, 2,1 );
	}
	
	public void setIcon(Image image){
		this.icon.setImage(image);
	}
	
	public void setMessage(String text){
		message.setText(text);
	}
	
	public void update(){
		this.setMessage(Game.getGame().getMenuControler().getAlarmMessage());
	}
}
