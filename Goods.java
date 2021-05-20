package com.satyam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="goods")
public class Goods {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name="";						//"stove","Glass chulha","Hose pipe","smart burner"
	@Column(name="type")
	private String type=" ";					//"local","branded","Ujwala/BPL","3-burner","glass_chulha","190/-"or"100/-"
	@Column(name="quantity")
	private int quantity;
	@Column(name="price")
	private int price;							//"1900","2200","990","2800","3600","190"or"100"
	@Column(name="date")
	private String date;
	@Column(name="sold_or_purchase")
	private String soldOrPurchase;				//"buy","sold","new"
	
	public Goods() { }
	
	public Goods(int price, int quantity) {
		this.price = price;
		this.quantity = quantity;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSoldOrPurchase() {
		return soldOrPurchase;
	}
	public void setSoldOrPurchase(String soldOrPurchase) {
		this.soldOrPurchase = soldOrPurchase;
	}
	
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", type=" + type + ", quantity=" + quantity + ", price=" + price
				+ ", date=" + date + ", soldOrPurchase=" + soldOrPurchase + "]";
	}
}
