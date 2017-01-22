package controler;
import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import GUI.scenes.PlayScene;
import entity.land.*;
import entity.map.Map;
import entity.player.*;
import entity.stock.Stock;


public class Game {

	private LocalDate date = LocalDate.of(2016, 1, 1);
	private Player controlPlayer;
	private LocalDate expiredDate = LocalDate.of(2016, 12, 31);
	private MenuControler menuControler ;
	private Map map;
	private static Game game;
	
	//changeable
	private List<String> maps = Arrays.asList(
			//level1
			"11 8\n"
			+ "kttttcooooo\n"
			+ "toooodooooo\n"
			+ "tooooxttttk\n"
			+ "tooooooooot\n"
			+ "tooooooooot\n"
			+ "yttttnoooot\n"
			+ "ooooohoooot\n"
			+ "ooooocttttq\n"
	);
	
	private Game(){};

	/*
	 * GUI version
	 */	
	public void buildMap(int level,GameGUI game) throws IOException{
		
		BufferedReader br = new BufferedReader(new StringReader(maps.get(level)));
		String[] dimension = br.readLine().split(" ");
		Map map = new Map(Integer.parseInt(dimension[0]),Integer.parseInt(dimension[1]));
		for(int y = 0;y < map.getWidth();y++){
			char[] curLine = br.readLine().toCharArray();
			for(int x = 0;x < map.getLength();x++){
				LandType curType = LandType.getLandType(curLine[x]);
				if(curType==null){
					continue;
				}
				if(map.getCell(x, y)==null){
					map.createCell(x, y, curType,game);
				}
			}
		}
		map.connect(map.getCell(0,0));
		this.map = map;
	}	
	
	public void initiate(GameGUI game) throws IOException  {
		buildMap(0,game);
		menuControler = new MenuControler(game);       
	}

	public void nextPlayer(GameGUI gameGUI){
		if(game.getMap().getPlayers().size()==1 || game.isExpired()){
			end(gameGUI);
			return;
		}
		int size = game.getMap().getPlayers().size();
		int curIndex = game.getMap().getPlayers().indexOf(game.getControlPlayer());
		
		if(curIndex == size-1){
			game.setController(game.getMap().getPlayers().get(0));
			gameGUI.getPlayScene().getTimePane().nextDay();
			if(game.getDate().plusDays(1).getDayOfMonth()==1)
				Bank.monthInterest(game.getMap().getPlayers(),gameGUI.getStage());
			Stock.update();
		}else
			game.setController(game.getMap().getPlayers().get(curIndex+1));
		
		gameGUI.freshMessage();
		
			
		game.getMenuControler().reset();
		gameGUI.freshPlayerInfo();
	}
	
	public void end(GameGUI gameGUI) {
		String message = "--Game Over--\n";
		List<Player> winner = new ArrayList<Player>();
		if(game.getMap().getPlayers().size()==1)
			winner.add((Player) game.getMap().getPlayers().toArray()[0]);
		else{
			winner = game.getMap().getRichest();
		}
		message += ("Winner: "+winner.stream().map(e->(e.getName())).reduce("",(a,b)->(a+b+" "))+"\nCongratulation!");
		MessageDialog md = new MessageDialog(gameGUI.getStage(),message,400,200);
		md.getBt().setText("Exit");
		md.getBt().addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				gameGUI.getStage().hide();
			}
		});
	}

	public void setPlayer(String e,GameGUI game) {
		Map map = this.getMap();
		map.addPlayer(new Player(map.getCell(0, 0),e,map.getPlayers().size()));
	}
	/*
	 * general part
	 */
	public static Game getGame(){
		if(game != null)
			return game;
		return (game = new Game());
	}

	public Player getControlPlayer() {
		return controlPlayer;
	}

	public void setController(Player p){
		this.controlPlayer = p;
	}
	
	public boolean isExpired() {
		return date.isAfter(expiredDate);
	}

	public Map getMap() {
		return map;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void plusDate(){
		this.date = this.date.plusDays(1);
	}

	public MenuControler getMenuControler() {
		return this.menuControler;
	}
}
