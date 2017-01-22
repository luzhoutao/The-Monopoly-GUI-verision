package entity.stock;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import entity.player.Player;

public class Stock {
	
	private String name;
	private double price;
    private double scope;
    private static ArrayList<Stock> allStocks;

	public Stock(String name,double price) {
		this.name  = name;
		this.price = price;
		this.updateScope();
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
	
	public double getActualPrice() {
		return price*(1+scope/100);
	}

	public void updateScope() {
		this.scope = Double.parseDouble( (new DecimalFormat("0.00")).format( (Math.random()*2000)/100.0-10 ) );
	}
	
	public double getScope(){
		return scope;
	}
	
	public static List<Stock> getAllStock(){
		if(allStocks!=null)
			return allStocks;
		allStocks = new ArrayList<Stock>();
		allStocks.add(new Stock("壳牌石油",38.58));
		allStocks.add(new Stock("上海大众",142.19));
		allStocks.add(new Stock("菲诗小铺",26.20));
		allStocks.add(new Stock("联合利华",17.71));
		allStocks.add(new Stock("广州恒大",101.21));
		allStocks.add(new Stock("农夫山泉",56.98));
		allStocks.add(new Stock("曼秀雷敦",87.09));
		allStocks.add(new Stock("瞻博网络",34.25));
		allStocks.add(new Stock("摩根大通",28.16));
		allStocks.add(new Stock("壳牌石油",38.58));
		allStocks.add(new Stock("阿迪达斯",112.77));
		return allStocks;
	}
	
	public static void printStocks(List<Stock> list,Collection<Player> players){
		System.out.print("序号\t名称\t\t单价\t涨跌幅\t");
		players.stream().forEach(e->{
			System.out.print(e.getName()+"持有数\t");
		});
		System.out.println("");
		for(int i = 0;i<list.size();i++){
			System.out.print(i+"\t"+list.get(i).getName()+"\t"+list.get(i).getPrice()+"\t"+list.get(i).getScope()+"%\t");
			for(Player p:players){
				System.out.print(p.getNumberOfStock(i)+"\t");
			}
			System.out.println("");
		}
	}

	public static void update() {
		allStocks.stream().forEach(e->(e.updateScope()));
	}
}
