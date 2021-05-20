package com.satyam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satyam.dao.CylinderRepository;
import com.satyam.model.Cylinder;

@Service
public class CylinderService {
	@Autowired
	CylinderRepository repo;
	
	public List<Cylinder> getAllCylinders() {
	  	List<Cylinder> cylinderPropList1 = getAllFilledCylinderByTypeCapacity("filled");
		List<Cylinder> cylinderPropList2 = getAllFilledCylinderByTypeCapacity("reFilled");
		List<Cylinder> cylinderList = new ArrayList<>();
		int i=0;
		
		for(Cylinder cylinder : cylinderPropList1)
			cylinderList.add(cylinder);
		for(Cylinder cylinder : cylinderPropList2){
			cylinderList.get(i).setQuantity(cylinder.getQuantity()+cylinderList.get(i).getQuantity());
			++i;
		}
		
		for(Cylinder cylinder : cylinderList)
			cylinder.setName(cylinder.getCapacity() + "Kg " + cylinder.getType() + " cylinder");
		
		return cylinderList;
	}

	public List<Cylinder> getAllFilledCylinderByTypeCapacity(String status) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		List<Cylinder> cylinderList = new ArrayList<>();
		Optional<Cylinder> optionalCylinder;
		
		optionalCylinder =  repo.findCylinderByStatusTypeCapacity(status,"subsidy", "5");
		if(optionalCylinder.isPresent())
			cylinderList.add(optionalCylinder.get());
		else
			cylinderList.add(new Cylinder("subsidy", "5", 0, sdf.format(date)));
		
		optionalCylinder =  repo.findCylinderByStatusTypeCapacity(status,"subsidy", "14.2");
		if(optionalCylinder.isPresent())
			cylinderList.add(optionalCylinder.get());
		else
			cylinderList.add(new Cylinder("subsidy", "14.2", 0, sdf.format(date)));
		
		optionalCylinder = repo.findCylinderByStatusTypeCapacity(status,"commercial", "5");
		if(optionalCylinder.isPresent())
			cylinderList.add(optionalCylinder.get());
		else
			cylinderList.add(new Cylinder("commercial", "5", 0, sdf.format(date)));
		
		optionalCylinder = repo.findCylinderByStatusTypeCapacity(status,"commercial", "19");
		if(optionalCylinder.isPresent())
			cylinderList.add(optionalCylinder.get());
		else
			cylinderList.add(new Cylinder("commercial", "19", 0, sdf.format(date)));
		
		return cylinderList;
	}

	public void saveSoldCylinder(Cylinder cylinder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();

		cylinder.setDate(sdf.format(date));
		cylinder.setName("cylinder");
		cylinder.setSoldOrPurchase("sold");
		cylinder.setStatus("");
		repo.save(cylinder);
		
		Optional<Cylinder> optionalCylinder = repo.findCylinderByStatusTypeCapacity("filled", cylinder.getType(), cylinder.getCapacity());
		if(optionalCylinder.isPresent()) {
			Cylinder cq = optionalCylinder.get();
			if(cq.getQuantity() > 0) {
				updateStatusQuantityByTypeCapacity("filled", cylinder.getType(), cylinder.getCapacity(), cq.getQuantity()-1);
				updateStatusDateByTypeCapacity("filled", cylinder.getType(), cylinder.getCapacity());
			}
			else {
				optionalCylinder = repo.findCylinderByStatusTypeCapacity("reFilled", cylinder.getType(), cylinder.getCapacity());
				if(optionalCylinder.isPresent()) {
					cq=optionalCylinder.get();
					updateStatusQuantityByTypeCapacity("reFilled", cylinder.getType(), cylinder.getCapacity(), cq.getQuantity()-1);
					updateStatusDateByTypeCapacity("reFilled", cylinder.getType(), cylinder.getCapacity());
				}
				
			}
			int ecq = repo.findCylinderByStatusTypeCapacity("empty", cylinder.getType(), cylinder.getCapacity()).get().getQuantity();
			if(cylinder.getCustomerType().equals("old"))
				updateStatusQuantityByTypeCapacity("empty", cylinder.getType(), cylinder.getCapacity(), ecq+1);
		
			updateStatusDateByTypeCapacity("empty", cylinder.getType(), cylinder.getCapacity());
		}
	}

	public List<Cylinder> getReFilledCylinderList() {
		List<Cylinder> reFilledCylinderList = new ArrayList<>();
		reFilledCylinderList.add(repo.findCylinderByStatusTypeCapacity("reFilled","subsidy", "5").get());
		reFilledCylinderList.add(repo.findCylinderByStatusTypeCapacity("reFilled","subsidy", "14.2").get());
		reFilledCylinderList.add(repo.findCylinderByStatusTypeCapacity("reFilled","commercial", "5").get());
		reFilledCylinderList.add(repo.findCylinderByStatusTypeCapacity("reFilled","commercial", "19").get());
		
		return reFilledCylinderList;
	}

	public List<Cylinder> getSoldCylinder(){
		int quantity=0, maxEmptyId;
		String lastDate;
		List<Cylinder> soldCylinderList = new ArrayList<>();
		List<Cylinder> soldCylinderList_5Subsidy, soldCylinderList_14Subsidy, soldCylinderList_5Commercial, soldCylinderList_19Commercial = new ArrayList<>();
		
		maxEmptyId=repo.getMaxIdByTypeCapacity("subsidy", "5");
		lastDate=repo.getLastDateById(maxEmptyId);
		soldCylinderList_5Subsidy = repo.findSoldCylinderListByTypeCapacityDate("subsidy", "5", lastDate);
		for(Cylinder cylinder : soldCylinderList_5Subsidy)
			quantity += cylinder.getQuantity();
		soldCylinderList.add(new Cylinder("subsidy", "5", quantity, lastDate));
		quantity=0;
		
		maxEmptyId=repo.getMaxIdByTypeCapacity("subsidy", "14.2");
		lastDate=repo.getLastDateById(maxEmptyId);
		soldCylinderList_14Subsidy = repo.findSoldCylinderListByTypeCapacityDate("subsidy", "14.2", lastDate);
		for(Cylinder cylinder : soldCylinderList_14Subsidy)
			quantity += cylinder.getQuantity();
		soldCylinderList.add(new Cylinder("subsidy", "14.2", quantity, lastDate));
		quantity=0;
		
		maxEmptyId=repo.getMaxIdByTypeCapacity("commercial", "5");
		lastDate=repo.getLastDateById(maxEmptyId);
		soldCylinderList_5Commercial = repo.findSoldCylinderListByTypeCapacityDate("commercial", "5", lastDate);
		for(Cylinder cylinder : soldCylinderList_5Commercial)
			quantity += cylinder.getQuantity();
		soldCylinderList.add(new Cylinder("commercial", "5", quantity, lastDate));
		quantity=0;
		
		maxEmptyId=repo.getMaxIdByTypeCapacity("commercial", "19");
		lastDate=repo.getLastDateById(maxEmptyId);
		soldCylinderList_19Commercial = repo.findSoldCylinderListByTypeCapacityDate("commercial", "19", lastDate);
		for(Cylinder cylinder : soldCylinderList_19Commercial)
			quantity += cylinder.getQuantity();
		soldCylinderList.add(new Cylinder("commercial", "19", quantity, lastDate));
		
		for(Cylinder cylinder : soldCylinderList)
			cylinder.setName(cylinder.getCapacity()+"Kg cylinder");

		return soldCylinderList;
	}
	
	public List<Cylinder> getBuyCylinder(){
		List<Cylinder> buyCylinderList = getReFilledCylinderList();
		for(Cylinder cylinder : buyCylinderList)
			cylinder.setName(cylinder.getCapacity()+"Kg cylinder");
		
		return buyCylinderList;
	}
	
	public void saveDeliveredCylinder(Cylinder cylinderDelivered) {
		if(cylinderDelivered.getQuantity()>0){
			int q1,q2;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
			Date date = new Date();
			cylinderDelivered.setDate(sdf.format(date));
			cylinderDelivered.setName("cylinder");
			cylinderDelivered.setStatus("reFilled");
			cylinderDelivered.setSoldOrPurchase("buy");
			
			Cylinder cq1 = repo.findCylinderByStatusTypeCapacity("filled", cylinderDelivered.getType(), cylinderDelivered.getCapacity()).get();
			q1 = cq1.getQuantity();
			Cylinder cq2 = repo.findCylinderByStatusTypeCapacity("reFilled", cylinderDelivered.getType(), cylinderDelivered.getCapacity()).get();
			q2 = cq2.getQuantity();
			repo.delete(cq2);
			repo.save(cylinderDelivered);
			updateStatusQuantityByTypeCapacity("filled", cylinderDelivered.getType(), cylinderDelivered.getCapacity(), q1+q2);
		}
	}

	public void saveCylinderEntry(Cylinder cylinder){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		
		cylinder.setDate(sdf.format(date));
		cylinder.setName("cylinder");
		cylinder.setCustomerType("");
		cylinder.setConsumerNumber("inStock");
		if((cylinder.getStatus().equals("filled")) || (cylinder.getStatus().equals("reFilled")))
			cylinder.setSoldOrPurchase("buy");
		else if(cylinder.getStatus().equals("empty"))
			cylinder.setSoldOrPurchase("");
				
		repo.save(cylinder);
	}
	
	private void updateStatusQuantityByTypeCapacity(String status, String type, String capacity, int quantity) {
		Cylinder cylinder2 = repo.findCylinderByStatusTypeCapacity(status, type, capacity).get();
		Cylinder cylinder = new Cylinder(cylinder2);
		repo.delete(cylinder2);
		cylinder.setQuantity(quantity);
		repo.save(cylinder);
	}
	
	private void updateStatusDateByTypeCapacity(String status, String type, String capacity) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
		Date date = new Date();
		
		Cylinder cylinder = repo.findCylinderByStatusTypeCapacity(status, type, capacity).get();
		cylinder.setDate(sdf.format(date));
		
		repo.save(cylinder);
	}
}
