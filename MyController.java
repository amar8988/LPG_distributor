package com.satyam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.satyam.model.Cylinder;
import com.satyam.model.Goods;
import com.satyam.service.CylinderService;
import com.satyam.service.GoodsService;

@Controller
public class MyController {
	@Autowired
	CylinderService cylinderService;
	@Autowired
	GoodsService goodsService;
	
	@GetMapping("/")
	public String home(Model model){
		List<Cylinder> cylinderList = cylinderService.getAllCylinders();
		List<Goods> goodsList = goodsService.getAllGoods();
		
		model.addAttribute("cylinderList", cylinderList);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("soldCylinder", new Cylinder());
		model.addAttribute("soldGoods", new Goods());
			
		return "sale";
	}
	
	@PostMapping("/saleCylinder")
	public String saleCylinder(@ModelAttribute("soldCylinder") Cylinder cylinder , Model model){
		cylinderService.saveSoldCylinder(cylinder);
		
		List<Cylinder> cylinderList = cylinderService.getAllCylinders();
		List<Goods> goodsList = goodsService.getAllGoods();
		
		model.addAttribute("cylinderList", cylinderList);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("soldCylinder", new Cylinder());
		model.addAttribute("soldGoods", new Goods());
		
		return "sale";
	}
	
	@PostMapping("/saleGoods")
	public String saleGoods(@ModelAttribute("soldGoods") Goods goods , Model model){
		goodsService.saveSoldGoods(goods);
		
		List<Cylinder> cylinderList = cylinderService.getAllCylinders();
		List<Goods> goodsList = goodsService.getAllGoods();

		model.addAttribute("cylinderList", cylinderList);
		model.addAttribute("goodsList", goodsList);
		model.addAttribute("soldCylinder", new Cylinder());
		model.addAttribute("soldGoods", new Goods());
		
		return "sale";
	}
	
	@GetMapping("/cylinderRecord")
	public String displayCylinderRecord(Model model){
		List<Cylinder> filledCylinderList = cylinderService.getAllFilledCylinderByTypeCapacity("filled");
		List<Cylinder> emptyCylinderList = cylinderService.getAllFilledCylinderByTypeCapacity("empty");
		List<Cylinder> reFilledCylinderList = cylinderService.getAllFilledCylinderByTypeCapacity("reFilled");
		int quantityInStock = 0;
		
		for(Cylinder cylinder : filledCylinderList){
			quantityInStock += cylinder.getQuantity();
		}
		
		for(Cylinder cylinder : emptyCylinderList){
			quantityInStock += cylinder.getQuantity();
		}
		
		for(Cylinder cylinder : reFilledCylinderList){
			quantityInStock += cylinder.getQuantity();
		}
		
		model.addAttribute("quantityInStock", quantityInStock);
		model.addAttribute("filledCylinderList", filledCylinderList);
		model.addAttribute("emptyCylinderList", emptyCylinderList);
		model.addAttribute("reFilledCylinderList", reFilledCylinderList);
		
		return "cylinder_record";
	}
		
	@GetMapping("/productRecord")
	public String displayProductRecord(Model model){
		List<Cylinder> cylinderList = cylinderService.getAllCylinders();
		List<Goods> goodsList = goodsService.getAllGoods();
		
		model.addAttribute("cylinderList", cylinderList);
		model.addAttribute("goodsList", goodsList);
				
		return "product_record";
	}
	
	@GetMapping("/delivery")
	public String cylinderDelivery(Model model){
		model.addAttribute("cylinderDelivery", new Cylinder());
		model.addAttribute("goodsDelivery", new Goods());
		
		return "refillDeliver";
	}
	
	@PostMapping("/deliveredCylinder")
	public String cylinderDelivered(@ModelAttribute("cylinderDelivery") Cylinder cylinderDelivery, Model model){
		cylinderService.saveDeliveredCylinder(cylinderDelivery);
		
		model.addAttribute("cylinderDelivery", new Cylinder());
		model.addAttribute("goodsDelivery", new Goods());
		
		return "refillDeliver";
	}
	
	@PostMapping("/deliveredGoods")
	public String goodsDelivered(@ModelAttribute("goodsDelivery") Goods goodsDelivery, Model model) {
		goodsService.saveDeliveredGoods(goodsDelivery);
		
		model.addAttribute("cylinderDelivery", new Cylinder());
		model.addAttribute("goodsDelivery", new Goods());
		
		return "refillDeliver";
	}
	
	@GetMapping("/soldBuy")
	public String soldOrBuy(Model model){
		List<Cylinder> soldCylinderList = cylinderService.getSoldCylinder();
		List<Cylinder> buyCylinderList = cylinderService.getBuyCylinder();
		List<Goods> soldGoodsList = goodsService.getSoldGoods();
		List<Goods> buyGoodsList = goodsService.getBuyGoods();
		
		model.addAttribute("soldCylindersList", soldCylinderList);
		model.addAttribute("soldGoodsList", soldGoodsList);
		model.addAttribute("buyCylindersList", buyCylinderList);
		model.addAttribute("buyGoodsList", buyGoodsList);
		
		return "so_pu";
	}
	
	@GetMapping("/entry")
	public String doEntry(Model model){
		model.addAttribute("cylinder", new Cylinder());
		model.addAttribute("goods", new Goods());
		
		return "entry";
	}
	
	@PostMapping("/doCylinderEntry")
	public String saveCylinderEntry(@ModelAttribute Cylinder cylinder, Model model){
		cylinderService.saveCylinderEntry(cylinder);
		
		model.addAttribute("cylinder", new Cylinder());
		model.addAttribute("goods", new Goods());
		
		return "entry";
	}
	
	@PostMapping("/doGoodsEntry")
	public String saveGoodsEntry(@ModelAttribute Goods goods, Model model){
		goodsService.saveGoodsEntry(goods);
		
		model.addAttribute("cylinder", new Cylinder());
		model.addAttribute("goods", new Goods());
		
		
		return "entry";
	}
}
