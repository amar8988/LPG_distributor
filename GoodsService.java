package com.satyam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satyam.dao.GoodsRepository;
import com.satyam.model.Goods;

@Service
public class GoodsService {
	@Autowired
	GoodsRepository repo;
	
	public List<Goods> getAllGoods(){
		List<Goods> goodsList= getGoodsList();
		String[] names = {"stove","stove","stove","Glass chulha","smart burner","Hose pipe","Hose pipe"};
		String[] types = {"branded","local","Ujwala/BPL","2800/-","3-burner","190/-","100/-"};
		int i=0;
		
		String modifiedName="";		
		for(Goods goods : goodsList){
			modifiedName = types[i] + "_" + names[i];
			goods.setName(modifiedName);
			i++;
		}
		
		return goodsList;
	}
	
	private List<Goods> getGoodsList(){
		int[] quantities = new int[7];
		List<Goods> goodsList = new ArrayList<>();
		List<Integer> prices = Arrays.asList(2200,1900,990,2800,3600,190,100);
		Optional<Goods> optionalGoodsOld, optionalGoodsNew;
		
		for(int i=0; i<7; i++) {
			optionalGoodsOld=repo.getAllGoodsByPrice(prices.get(i),"buy");
			optionalGoodsNew=repo.getAllGoodsByPrice(prices.get(i),"new");
			if(optionalGoodsOld.isPresent()) {
				quantities[i] = optionalGoodsOld.get().getQuantity();
				goodsList.add(optionalGoodsOld.get());
			}
			else {
				quantities[i] = 0;
				goodsList.add(new Goods(prices.get(i), 0));
			}
			if(optionalGoodsNew.isPresent())
				quantities[i] += optionalGoodsNew.get().getQuantity();
			else
				quantities[i] += 0;
		}
		
		for(int i=0;i<7;i++)
			goodsList.get(i).setQuantity(quantities[i]);
		
		return goodsList; 
	}
	
	public void saveSoldGoods(Goods goods){
		Goods goods2 = repo.getAllGoodsByPrice(goods.getPrice(), "buy").get();
		int q = goods2.getQuantity() - goods.getQuantity();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		goods.setDate(sdf.format(date));
		goods.setType(goods2.getType());
		goods.setSoldOrPurchase("sold");
		repo.save(goods);
		
		goods = goods2;
		repo.delete(goods2);
		goods.setQuantity(q);
		repo.save(goods);
	}
	
	public void saveDeliveredGoods(Goods goods) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		
		Goods goods1 = repo.getAllGoodsByPrice(goods.getPrice(), "buy").get();
		int q1 = goods1.getQuantity();
		int q2 = repo.getAllGoodsByPrice(goods.getPrice(), "new").get().getQuantity();
		goods1.setQuantity(q1+q2);
		Goods goods2 = goods1;
		repo.delete(goods1);
		repo.save(goods);
		
		goods.setDate(sdf.format(date));
		goods.setSoldOrPurchase("new");
		goods.setType(goods2.getType());
		repo.save(goods2);
	}
	
	public List<Goods> getSoldGoods(){
		String lastSoldDate=repo.getLastDate();
		List<Goods> soldGoodsList = repo.getsoldGoodsByDate(lastSoldDate).get();
		
		return soldGoodsList;
	}
	
	public List<Goods> getBuyGoods(){
		List<Goods> buyGoodsList = new ArrayList<>();
		List<Integer> priceList = Arrays.asList(2200,1900,990,2800,3600,190,100);
		
		for(int price : priceList)
			buyGoodsList.add(repo.getAllGoodsByPrice(price, "new").get());
		
		return buyGoodsList;
	}
	
	public void saveGoodsEntry(Goods goods){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		
		if(goods.getName().equals("smart burner"))
			goods.setPrice(3600);
		else if(goods.getName().equals("Glass chulha"))
			goods.setPrice(2800);
		else if(goods.getType().equals("local"))
			goods.setPrice(1900);
		else if(goods.getType().equals("branded"))
			goods.setPrice(2200);
		else if(goods.getType().equals("Ujwala/BPL"))
			goods.setPrice(990);
		else if(goods.getType().equals("190/-"))
			goods.setPrice(190);
		else if(goods.getType().equals("100/-"))
			goods.setPrice(100);
		goods.setDate(sdf.format(date));
		
		saveGoods(goods);
	}
	
	public void saveGoods(Goods goods) {
		repo.save(goods);
	}
}
