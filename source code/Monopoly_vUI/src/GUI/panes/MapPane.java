package GUI.panes;

import java.util.stream.Stream;

import controler.Game;
import controler.GameGUI;
import entity.land.House;
import entity.land.LandType;
import entity.map.Cell;
import entity.map.Map;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MapPane extends GridPane {

	private Map map;
	public MapPane(GameGUI game){
		this.map = Game.getGame().getMap();
		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(10,10,10,10));
		this.addCells();
		
		Pane dice = new DiceView(game);
		GridPane.setColumnSpan(dice, 2);
		GridPane.setRowSpan(dice, 2);
		this.add(dice, 8,5);
	}

	private void addCells() {
		int width = map.getWidth();
		int length = map.getLength();
		System.out.println(length+" "+width);
		for(int y = 0;y<width;y++){
			for(int x = 0;x<length;x++){
				Cell curCell;
				if((curCell = map.getCell(x, y))!=null){
					if(curCell.getLand().getType()==LandType.house){
						StackPane sp = ((House)(curCell.getLand())).getTag();
						sp.setMaxSize(Cell.w, Cell.w);
						if(x==0)
							this.add(sp, 0,y+1);
						else if(y==0)
							this.add(sp,x+1,0);
						else if(x==length-1)
							this.add(sp, length+1, y+1);
						else if(y==width-1)
							this.add(sp, x+1,width+1);
						else if(y<width/2)
							this.add(sp, x+1,y);
						else
							this.add(sp, x+1, y+2);
					}
					this.add(curCell, x+1, y+1);
				}
			}
		}
	}
}
