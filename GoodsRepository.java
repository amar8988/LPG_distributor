package com.satyam.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.satyam.model.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {
	@Query(value="select * from goods g where g.price=:price and sold_or_purchase=:soldOrPurchase order by id desc limit 1" , nativeQuery=true)
	public Optional<Goods> getAllGoodsByPrice(@Param("price") int price , @Param("soldOrPurchase") String soldOrPurchase);
	
	@Query(value="select date from goods where sold_or_purchase='sold' order by id desc limit 1" , nativeQuery=true)
	public String getLastDate();
	
	@Query(value="select * from goods g where g.date=:date and g.sold_or_purchase='sold'" , nativeQuery=true)
	public Optional<List<Goods>> getsoldGoodsByDate(@Param("date") String date);
}
