package entity.map;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.*;

import controler.Game;
import controler.GameGUI;
import entity.land.*;
import entity.player.*;


public class Map {
	
	private int length;
	private int width;
	private ArrayList<Cell> cells = new ArrayList<Cell>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	public Map(int l,int w) {
		this.length = l;
		this.width = w;
	}

	public ArrayList<Cell> getCells() {
		return cells;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Cell getCell(int x, int y){
		return cells.stream().filter(item->((item.getX()==x) && (item.getY()==y))).findFirst().orElse(null);
	}

	public Cell createCell(int x, int y, LandType type, GameGUI game) {
		Cell newCell = new Cell(this,x,y,type,game);
		cells.add(newCell);
		return newCell;
	}
	public int getLength() {
		return length;
	}
	public int getWidth() {
		return width;
	}
	
	public void addPlayer(Player player){
		players.add(player);
		this.getCell(0, 0).addPlayer(player);
	}
	/*to meet the requirement of lambda*/
	private Direction d = Direction.right;
	private Cell curCell;
	public void connect(Cell startCell){
		assert(startCell!=null);
		curCell = startCell;
		
		while(curCell.getNextCell()==null){
			Cell next = null;
			List<Cell> neighbors = cells.stream().filter(e->(Math.abs(e.getX()-curCell.getX())+Math.abs(e.getY()-curCell.getY())==1)).collect(Collectors.toList());
			switch(d){
			case up:	
				next = neighbors.stream().filter(e->(e.getY()==(curCell.getY()-1))).findFirst().orElse(
							neighbors.stream().filter(e->(e.getY()==curCell.getY())).findFirst().orElse(null)
						); 
				break;
			case down:
				next = neighbors.stream().filter(e->(e.getY()==(curCell.getY()+1))).findFirst().orElse(
					neighbors.stream().filter(e->(e.getY()==curCell.getY())).findFirst().orElse(null)
				); 
				break;
			case left:	
				next = neighbors.stream().filter(e->(e.getX()==(curCell.getX()-1))).findFirst().orElse(
					neighbors.stream().filter(e->(e.getX()==curCell.getX())).findFirst().orElse(null)
				); break;
			case right:	
				next = neighbors.stream().filter(e->(e.getX()==(curCell.getX()+1))).findFirst().orElse(
						neighbors.stream().filter(e->(e.getX()==curCell.getX())).findFirst().orElse(null)
					); break;
			}
			if(next.getY()==curCell.getY())
				d = next.getX()<curCell.getX()?Direction.left:Direction.right;
			if(next.getX()==curCell.getX())
				d = next.getY()<curCell.getY()?Direction.up:Direction.down;
			assert(next!=null);
			curCell.setNextCell(next);
			next.setPrevCell(curCell);
			curCell = next;
		}
	}
	
	private enum Direction {
		up,
		down,
		left,
		right;
	}
	
	/*
	 * @param false-find the poorest;true-find the richest
	 */
	public List<Player> getMost(boolean poorest_richest) {
		BinaryOperator<Integer> acc = poorest_richest?((a,b)->(a>b?a:b)):((a,b)->(a<b?a:b));
		int most = poorest_richest?0:Integer.MAX_VALUE;
		return players.stream().filter(e->(
				e.getMyHouse().size()==(players.stream().map(e1->(e1.getMyHouse().size())).reduce(most,acc))
				)).collect(Collectors.toList());
	}

	public List<Player> getRichest() {
		return players.stream().filter(e->(
				e.totalProperty()==(players.stream().map(e1->(e1.totalProperty())).reduce(0.0,(a,b)->(a<b?b:a)))
				)).collect(Collectors.toList());
	}

	public Cell getHospital() {
		return this.cells.stream().filter(e->(e.getLand().getType()==LandType.hospital)).findAny().get();
	}

	
}
