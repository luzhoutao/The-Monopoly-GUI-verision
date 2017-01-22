package entity.player;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import entity.land.*;
import entity.map.Cell;
import entity.map.Map;
import entity.prop.*;
import entity.stock.Stock;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Player {

	/*
	 * different kinds of attributes
	 */
	//fundamental attribute
	private String name;
	private int id;
	private Image profile;
	private ImageView profile_view;
	//property
	private double cash = 3000;
	private double deposite = 3000;
	private int coupon = 80;
	private Color myLandColor;
	private ArrayList<House> myHouse = new ArrayList<House>();
	private ArrayList<Prop> myProp = new ArrayList<Prop>();
	private int[] numberOfStock = new int[Stock.getAllStock().size()];
	//map related
	private Cell cell;
	private Direction direction = Direction.clockwise;
	//attribute for controlling
	private ArrayList<Prop> workingProp = new ArrayList<Prop>();
	private int waitingRound = 0;
	
	//class variables
	private static ArrayList<Image> player_profile = new ArrayList<Image>();
	private static ArrayList<Color> player_color = new ArrayList<Color>();
	
	public static ArrayList<Image> getPlayer_profile() {
		return player_profile;
	}
	public static ArrayList<Color> getPlayer_color() {
		return player_color;
	}
	static {
		player_profile.addAll(Arrays.asList(
				new Image("file:icons/player1.gif"),
				new Image("file:icons/player2.gif"),
				new Image("file:icons/player3.gif"),
				new Image("file:icons/player4.gif")
		));
		player_color.addAll(Arrays.asList(
			Color.OLIVE,
			Color.BURLYWOOD,
			Color.DARKMAGENTA,
			Color.SILVER
		));
	}
	
	
	public Player(Cell cell,String name,int id){
		this.cell = cell;
		this.name = name;
		this.id = id;
		this.profile = player_profile.remove(0);
		this.profile_view = new ImageView(profile);
		this.myLandColor = player_color.remove(0);
		List<Prop> props = PropType.allVirtualProp();
		for(int i = 0;i<props.size();i++)
			this.myProp.add(PropType.getProp(this, props.get(i).getType()));
	}

	//take a single step
	public Cell step(){
		Cell next = cell.getCell(direction);
		assert(next!=null);
		cell.removePlayer(this);
		cell = next;
		cell.addPlayer(this);
		return cell;
	}
	
	public Cell getCell(){
		return cell;
	}
	public Cell setCell(Cell c){
		assert(c!=null);
		cell.removePlayer(this);
		cell = c;
		cell.addPlayer(this);
		return c;
	}
	
	public ArrayList<Prop> getWorkingProp() {
		return workingProp;
	}

	public ArrayList<Prop> getMyProp() {
		return myProp;
	}

	public double getDeposite() {
		return deposite;
	}
	public void addDeposite(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.deposite=Double.parseDouble(df.format(this.deposite+d));
	}
	
	public void payTax(double rate) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.deposite -= this.deposite*rate;
		this.deposite = Double.parseDouble(df.format(this.deposite));
	}
	
	public String getName() {
		return name;
	}

	public Color getMyLandColor() {
		return this.myLandColor;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Image getImage() {
		return this.profile;
	}
	
	public ImageView getImageView(){
		this.profile_view.setSmooth(true);
		this.profile_view.setFitHeight(Cell.w);
		this.profile_view.setFitWidth(Cell.w);
		return this.profile_view;
	}
	
	public String getStock() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(Stock.getAllStock().stream()
							.map(e->(e.getActualPrice()*this.numberOfStock[Stock.getAllStock().indexOf(e)]))
							.reduce(0.0,(a,b)->(a+b)));
	}
	
	public int getNumberOfStock(int i) {
		return numberOfStock[i];
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void turnover() {
		this.direction = Direction.counter(this.direction);
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public double getCash() {
		return cash;
	}
	
	public void setCash(double meanCash) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.cash = Double.parseDouble(df.format(meanCash));
	}
	public double totalProperty(){
		return cash+deposite+houseProperty();
	}
	public double houseProperty(){
		int valueOfHouse = 0;
	
		for(int i = 0;i<myHouse.size();i++){
			valueOfHouse+=myHouse.get(i).getValue();
		}
		return valueOfHouse;
	}
	/**
	 * @param the number of cash need be added
	 */
	public void addCash(double i) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.cash=Double.parseDouble(df.format(this.cash+i));
	}
	
	
	public void setWaitingRound(int i){
		this.waitingRound = i;
	}
	public boolean checkNeedWaiting(){
		if(this.waitingRound == 0)
			return false;
		else {
			this.waitingRound--;
			System.out.println("距离出院还有"+this.waitingRound+"回合！");
			return true;
		}
	}
	public int getWaitingRounding(){
		return this.waitingRound+1;
	}
	
	
	public void addProp(Prop p){
		this.myProp.add(p);
	}
	
	public void deposite(double number) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.cash=Double.parseDouble(df.format(this.cash-number));
		this.deposite=Double.parseDouble(df.format(this.deposite+number));
	}

	public void withdraw(double number) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.cash=Double.parseDouble(df.format(this.cash+number));
		this.deposite=Double.parseDouble(df.format(this.deposite-number));
	}

	public void addCoupon(int n) {
		this.coupon+=n;
	}

	public int getCoupon() {
		// TODO Auto-generated method stub
		return coupon;
	}

	/**
	 * @return myHouse
	 */
	public ArrayList<House> getMyHouse() {
		return myHouse;
	}
	
	public void addHouse(House house) {
		this.myHouse.add(house);
	}
	
	public boolean payByCash(double n) {
		DecimalFormat df = new DecimalFormat("0.00");
		if(cash>=n){
			this.cash=Double.parseDouble(df.format(this.cash-n));
			return true;
		}else
			return false;
	}
	public boolean payByDeposite(double amount) {
		DecimalFormat df = new DecimalFormat("0.00");
		if(deposite>=amount){
			this.deposite=Double.parseDouble(df.format(this.deposite-amount));
			return true;
		}else
			return false;
	}
	public double mortageHouse() {
		assert(myHouse.size()!=0);
		House h = myHouse.get(0);
		myHouse.stream().reduce(h, (a,b)->(a.getValue()>b.getValue()?b:a));
		h.reclaim();
		this.myHouse.remove(h);
		System.out.println("已卖出"+h.getName()+"。");
		return h.getValue();
	}
	
	
	public boolean payByCoupon(int price) {
		if(coupon>=price){
			this.coupon-=price;
			return true;
		}else
			return false;
	}
	public List<Player> getReachablePlayer(int i) {
		return this.getCell().getMap().getPlayers().stream().filter(e->(!e.getName().equals(this.getName())))
				.filter(e->(this.getCell().getDistance(this.getDirection(),e.getCell())<=i)).collect(Collectors.toList());
	}
	public boolean buyStock(int index, int n) {
		try{
			Stock s = Stock.getAllStock().get(index);
			double money = n*s.getActualPrice();
			if(this.deposite>=money){
				this.payByDeposite(money);
			}else{
				money-=this.deposite;
				if(this.cash>=money){
					this.payByDeposite(this.deposite);
					this.payByCash(money);
				}else{
					return false;
				}
			}
			this.numberOfStock[index]+=n;
			return true;
		}catch(IndexOutOfBoundsException e){
			return false;
		}
	}
	public boolean sellStock(int index, int n) {
		try{
			if(n>this.numberOfStock[index]){
				return false;
			}
			Stock s = Stock.getAllStock().get(index);
			this.addDeposite(s.getActualPrice()*n);
			this.numberOfStock[index]-=n;
			return true;
		}catch(IndexOutOfBoundsException e){
			return false;
		}
	}
}
