package GUI.panes;

import controler.Game;
import controler.GameGUI;
import entity.map.Cell;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TimePane extends StackPane {
	
	GameGUI game;
	private GridPane contentPane = new GridPane();
	private Text day = new Text();
	private Text date = new Text();

	public TimePane(GameGUI game){
		this.game = game;
		
		this.contentPane.setAlignment(Pos.CENTER);
		StackPane.setMargin(this, new Insets(Cell.w*3,0,Cell.w,0));
		
		this.contentPane.add(day, 1, 1);
		this.contentPane.add(date,1,2);
		this.contentPane.setHgap(12);
		
		this.day.setText(Game.getGame().getDate().toString());
		this.date.setText(Game.getGame().getDate().getDayOfWeek().toString());
		
		this.day.setFont(new Font(22));
		this.date.setFont(new Font(22));
		
		this.getChildren().add(this.getbg());
		this.getChildren().add(contentPane);
	}

	public void nextDay() {
		Game.getGame().plusDate();
		this.day.setText(Game.getGame().getDate().toString());
		this.date.setText(Game.getGame().getDate().getDayOfWeek().toString());
	}
	
	public Node getbg(){
		Rectangle r = new Rectangle();
		r.setArcHeight(40);
		r.setArcWidth(40);
		r.setWidth(220);
		r.setHeight(100);
		r.setFill(Color.WHITE);
		r.setOpacity(0.3);
		return r;
	}
}
