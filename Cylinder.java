package com.satyam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/*import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cylinder")
public class Cylinder {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="name")
	private String name;					//"cylinder"
	@Column(name="consumer_number")
	private String consumerNumber;			//"inStock",
	@Column(name="type")
	private String type;					//"subsidy"or"commercial"
	@Column(name="capacity")
	private String capacity;				//{"5"or"14.2"}or{"5"or"19"}
	@Column(name="quantity")
	private int quantity;
	@Column(name="invoice")
	private String invoice;					//"",
	@Column(name="date")
	private String date;
	@Column(name="customer_type")
	private String customerType;			//"old"or"new"
	@Column(name="status")
	private String status;					//"empty","filled"or"reFilled"
	@Column(name="sold_or_purchase")
	private String soldOrPurchase;			//"sold"or"buy"
	
	public Cylinder() {	}

	public Cylinder(String type, String capacity, int quantity, String date) {
		this.type = type;
		this.capacity = capacity;
		this.quantity = quantity;
		this.date = date;
	}
	
	public Cylinder(Cylinder cylinder) {
		this.id = cylinder.getId();
		this.name = cylinder.getName();
		this.consumerNumber = cylinder.getConsumerNumber();
		this.type = cylinder.getType();
		this.capacity = cylinder.getCapacity();
		this.quantity = cylinder.getQuantity();
		this.invoice = cylinder.getInvoice();
		this.date = cylinder.getDate();
		this.customerType = cylinder.getCustomerType();
		this.status = cylinder.getStatus();
		this.soldOrPurchase = cylinder.getSoldOrPurchase();
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
	public String getConsumerNumber() {
		return consumerNumber;
	}
	public void setConsumerNumber(String consumerNumber) {
		this.consumerNumber = consumerNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSoldOrPurchase() {
		return soldOrPurchase;
	}
	public void setSoldOrPurchase(String soldOrPurchase) {
		this.soldOrPurchase = soldOrPurchase;
	}
	
	@Override
	public String toString() {
		return "Cylinder [id=" + id + ", name=" + name + ", consumerNumber=" + consumerNumber + ", type=" + type
				+ ", capacity=" + capacity + ", quantity=" + quantity + ", invoice=" + invoice + ", date=" + date
				+ ", customerType=" + customerType + ", status=" + status + ", soldOrPurchase=" + soldOrPurchase + "]";
	}
}
