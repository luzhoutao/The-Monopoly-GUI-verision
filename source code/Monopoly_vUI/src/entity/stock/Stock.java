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
		allStocks.add(new Stock("����ʯ��",38.58));
		allStocks.add(new Stock("�Ϻ�����",142.19));
		allStocks.add(new Stock("��ʫС��",26.20));
		allStocks.add(new Stock("��������",17.71));
		allStocks.add(new Stock("���ݺ��",101.21));
		allStocks.add(new Stock("ũ��ɽȪ",56.98));
		allStocks.add(new Stock("�����׶�",87.09));
		allStocks.add(new Stock("հ������",34.25));
		allStocks.add(new Stock("Ħ����ͨ",28.16));
		allStocks.add(new Stock("����ʯ��",38.58));
		allStocks.add(new Stock("���ϴ�˹",112.77));
		return allStocks;
	}
	
	public static void printStocks(List<Stock> list,Collection<Player> players){
		System.out.print("���\t����\t\t����\t�ǵ���\t");
		players.stream().forEach(e->{
			System.out.print(e.getName()+"������\t");
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
