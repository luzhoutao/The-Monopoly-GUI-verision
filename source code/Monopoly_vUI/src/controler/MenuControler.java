package controler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Timer;
import java.util.regex.Pattern;

import entity.land.LandType;
import entity.map.Cell;
import entity.player.Direction;
import entity.player.Player;
import entity.prop.*;
import entity.stock.Stock;

public class MenuControler {
	
	private boolean moved = false;
	private int steps;
	private boolean giveIn = false;
	
	private Game game;
	private GameGUI game_gui;
	
	/*
	 * GUI version
	 */
	public MenuControler(GameGUI game_gui){
		this.game_gui = game_gui;
		this.game = Game.getGame();
	}
	
	/*
	 * @return steps that the player will move for this round, 
	 * x(1-6): maybe some expected or random
	 * 0: due to 'detainer' tool
	 * -1: stands for this is not his movable round, need waiting
	 */
	public int getSteps(){
		DiceControler dc = (DiceControler) game.getControlPlayer().getWorkingProp().stream().filter(e->(e.getType()==PropType.diceControler)).findFirst().orElse(null);
		Detainer detainer = (Detainer) game.getControlPlayer().getWorkingProp().stream().filter(e->(e.getType()==PropType.detainer)).findFirst().orElse(null);
		
		boolean needWaiting = game.getControlPlayer().checkNeedWaiting();
		
		if(dc!=null){
			steps = dc.getExpected();
			game.getControlPlayer().getWorkingProp().remove(dc);
			System.out.println("遥控骰子奏效，您成功掷到了"+steps);
		}
		if(detainer!=null){
			steps = 0;
			game.getControlPlayer().getWorkingProp().remove(detainer);
			System.out.println("滞留卡发动！本回合将停留在原处！");
		}
		if(needWaiting)
			steps = -1;
		if(dc==null && detainer==null && !needWaiting)
			steps = (int) (Math.random()*6)+1;
		return steps;
	}
	
	public void _go(){
		while(steps>0){
			Cell curCell = game.getControlPlayer().step();
			steps--;
			if(curCell.getLand().getType()==LandType.bank){
				return;
			}if(curCell.isBlocked()){
				game_gui.setMessage("Oops! Damn the barrier!");
				curCell.removeBarrier();
				steps = 0;
				break;
			}
			
		}
		this.moved = true;
	}

	public void go_on(){
		if(steps==0)
			game.nextPlayer(game_gui);
		else{
			_go();
			game.getControlPlayer().getCell().getLand()._response(game.getControlPlayer());
		}
	}
	
	public String getAlarmMessage() {
		boolean isClockwise = game.getControlPlayer().getDirection()==Direction.clockwise;
		Cell curCell = game.getControlPlayer().getCell();
		curCell = isClockwise?curCell.getNextCell():curCell.getPrevCell();
		for(int i = 0;i<10;i++){
			if(curCell.isBlocked()){
				return "Attention! A barrier is in "+(i+1)+"steps before!\n";
			}
			curCell = isClockwise?curCell.getNextCell():curCell.getPrevCell();
		}
		return "Nothing in the way!";
	}
	
	public void giveIn() {
		Player player = game.getControlPlayer();
		for(int i = 0;i<player.getMyHouse().size();i++){
			player.getMyHouse().get(i).reclaim();
		}
		game.nextPlayer(game_gui);
		game.getMap().getPlayers().remove(player);
		if(game.getMap().getPlayers().size()==1)
			game.end(game_gui);
		this.giveIn = true;
	}
	/*
	 * general part
	 */
	public void reset(){
		this.moved = false;
		this.giveIn = false;
		this.steps = 0;
	}
	public boolean hasMoved(){
		return moved;
	}
	public boolean isGiveIn() {
		return giveIn;
	}
	
	
}
