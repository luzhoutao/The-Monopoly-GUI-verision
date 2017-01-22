package GUI.scenes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import controler.Game;
import controler.GameGUI;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.transform.*;

public class StartScene extends Scene{
	private static Image bg_image = new Image("file:icons/main_bg.jpg");
	private Text startTxt;
	private GridPane gp; 
	private ToggleGroup tg;
	private List<RadioButton> rbs;
	private GameGUI game;
	
	public StartScene(Group root,GameGUI game) {
		super(root, bg_image.getWidth(),bg_image.getHeight());
		this.setFill(new ImagePattern(bg_image));
		this.addTitle(root);
		this.addStartTxt(root);
		this.game = game;
	}
	private void addStartTxt(Group root) {
		//effect
		InnerShadow is = new InnerShadow();
		is.setOffsetX(1.0f);
		is.setOffsetY(1.0f);
		//location
		double x = bg_image.getWidth()*0.3;
		double y = bg_image.getHeight()*0.7;
		startTxt = getDesignedText(x,y, "start",Color.WHITE,Font.font("Showcard Gothic",FontWeight.BOLD,40),new Rotate(5,x,y),null);
		startTxt.setOnMouseEntered(new EventHandler<Event>(){
			@Override
			public void handle(Event arg0) {
				startTxt.setEffect(is);
			}
		});
		startTxt.setOnMouseExited(new EventHandler<Event>(){
			@Override
			public void handle(Event arg0) {
				startTxt.setEffect(null);
			}
		});
		startTxt.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event arg0) {
				try {
					Game.getGame().initiate(game);
					setPlayer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		root.getChildren().add(startTxt);
	}

	private void setPlayer() {
		Group newRoot = new Group();
		this.setRoot(newRoot);
		
		double width = this.getWidth();
		double height = this.getHeight();
		Rectangle bg = new Rectangle(width*0.1,height*0.2,width*0.4,height*0.7);
		bg.setArcWidth(20);
		bg.setArcHeight(20);
		bg.setFill(Color.GRAY);
		bg.setOpacity(0.5);
		
		gp = new GridPane();
		Font font = Font.font("Consolas",FontWeight.BOLD,20);
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setLayoutX(width*0.14);
		gp.setLayoutY(height*0.3);
		Label label = new Label("players:");label.setFont(font);
		tg = new ToggleGroup();
		rbs = Arrays.asList(new RadioButton("2"),new RadioButton("3"),new RadioButton("4"));
		rbs.stream().forEach(e->{
			e.setToggleGroup(tg);
			e.setUserData(rbs.indexOf(e)+2);
		});
		rbs.get(0).setSelected(true);
		gp.add(label, 1, 1);
		gp.add(rbs.get(0), 2, 1);
		gp.add(rbs.get(1), 3, 1);
		gp.add(rbs.get(2), 4, 1);
		
		List<Label> la = Arrays.asList(new Label("P1:"),new Label("P2:"),new Label("P3"),new Label("P4"));
		List<TextField> ta = Arrays.asList(new TextField(),new TextField(),new TextField(),new TextField());
		List<Label> mla = Arrays.asList(new Label(),new Label(),new Label(),new Label());
		Label done = new Label(">>");
		done.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
		done.setUnderline(true);
		done.setTooltip(new Tooltip("go"));
		gp.add(done, 5, 7);
		la.stream().forEach(e->{
			e.setFont(font);
			int index = la.indexOf(e);
			gp.add(e, 1, index+3);
			e.setAlignment(Pos.CENTER_RIGHT);
			if(index>1)
				e.setVisible(false);
		});
		ta.stream().forEach(e->{
			e.setFont(font);
			int index = ta.indexOf(e);
			GridPane.setColumnSpan(e, 3);
			gp.add(e,2, index+3);
			if(index>1)
				e.setVisible(false);
		});
		mla.stream().forEach(e->{
			gp.add(e, 5, mla.indexOf(e)+3);
		});
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0,
					Toggle arg1, Toggle arg2) {
				la.stream().forEach(e->(e.setVisible(true)));
				ta.stream().forEach(e->(e.setVisible(true)));
				la.stream().filter(e->(la.indexOf(e)>=(Integer)(tg.getSelectedToggle().getUserData()))).forEach(e->(e.setVisible(false)));
				ta.stream().filter(e->(ta.indexOf(e)>=(Integer)(tg.getSelectedToggle().getUserData()))).forEach(e->(e.setVisible(false)));
			}
			
		});
		done.setOnMouseClicked(new EventHandler<Event>(){
			@Override
			public void handle(Event arg0) {
				int number = (int) tg.getSelectedToggle().getUserData();
				List<String> names = ta.stream().filter(e->(ta.indexOf(e)<number)).map(e->(e.getText())).collect(Collectors.toList());
				if(names.stream().filter(e->(e.length()==0)).count()==0){
					names.stream().forEach(e->{
						Game.getGame().setPlayer(e,game);
					});
					game.setPlayScene();
				}else
					done.requestFocus();
				
			}
		});
		newRoot.getChildren().add(bg);
		newRoot.getChildren().add(gp);
	}
	private void addTitle(Group root){
		double x = bg_image.getWidth()*0.08;
		double y = -bg_image.getHeight()*0.5;
		Text t = new Text(x,y,"THE MONOPOLY");
		t.setCache(true);
		t.setFill(Color.BLACK);
		t.setFont(Font.font("Jokerman", FontWeight.BOLD, 50));
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		t.setEffect(r);
		t.setTranslateY(400);
		root.getChildren().add(t);
	}
	private Text getDesignedText(double x, double y,String content,Color c, Font f, Rotate r,Effect e){
		Text txt = new Text(x,y,content);
		txt.setFill(c);
		txt.setFont(f);
		txt.getTransforms().add(r);
		txt.setEffect(e);
		return txt;
	}
}
