package com.erp.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.service.AccService;
import com.erp.service.ProductService;
import com.erp.service.SupplierService;
import com.erp.vo.Accounting;
import com.erp.vo.Product;
import com.erp.vo.Supplier;
import com.erp.vo.Users;

// user (유저 컨트롤러)	
@Controller
@RequestMapping(value = "/user/*")
public class UsersController {
	
	@Inject
	ProductService pro_service;
	
	@Inject
	SupplierService supp_service;
	
	@Inject
	AccService acc_service;
	
	// userMain(유저 메인)	
	@RequestMapping(value ="/userMain", method = RequestMethod.GET)
	public String main(Model model, HttpSession session) {
		
		Users user = (Users) session.getAttribute("users");
		
		model.addAttribute("users", user);
		
		return "user/userMain";
	}

	// --- myPage
	// myPage(마이페이지)
	@RequestMapping(value="/myPage", method = RequestMethod.GET)
	public String myPage(Model model) {
		return "user/myPage";

	}
	
	// --- product
	// product(제품관리)
	@RequestMapping(value="/product", method = RequestMethod.GET) 
	public String product(Model model) throws Exception{
		
		List<Product> product_List = pro_service.getProductList();
		model.addAttribute("product_List",product_List);
		
		return "user/product";
	}
	
	// 제품 이름을 받아와 검색
	@RequestMapping(value="/searchProduct", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> searchProduct(String pro_name) throws Exception{
		
		List<Product> pro_list = pro_service.searchProduct(pro_name);
		
		return pro_list;
	}
	
	// 제품 등록
	@RequestMapping(value="/addProductAction", method = RequestMethod.POST)
	@ResponseBody
	public List <Product> addProductAction(Product product) throws Exception{
		
		pro_service.addProductAction(product);
		
		return pro_service.getProductList();
	}
	
	
	// --- clients
	// clients(고객관리)
	@RequestMapping(value ="/clients", method = RequestMethod.GET)
	public String clients(Model model) {
		return "user/clients";
	}
	
	// --- orders
	// orders(발주관리)
	@RequestMapping(value ="/orders", method = RequestMethod.GET)
	public String orders(Model model) {
		return "user/orders";
	}
	
	// --- salesList
	// salesList(영업관리)
	@RequestMapping(value ="/salesList", method = RequestMethod.GET)
	public String salesList(Model model) {
		return "user/salesList";
	}
	
	// --- supplier
	// supplier (공급처)	
	@RequestMapping(value ="/supplier", method = RequestMethod.GET)
	public String supplier(Model model, HttpSession session, Users users) throws Exception {
		
		Users user = (Users) session.getAttribute("users");
		
		List<Supplier> supp_list = supp_service.getSupplierList();
		model.addAttribute("supp_list", supp_list);
		model.addAttribute("users", user);
		
		return "user/supplier";
	}
	
	// 공급처 검색
	@RequestMapping(value = "/searchSupplier", method = RequestMethod.POST)
	@ResponseBody
	public List<Supplier> searchSupplier(String supp_name) throws Exception {
		
		List<Supplier> supp_list = supp_service.searchSupplier(supp_name);
		
		return supp_list;
	}
	
	
	// 공급처 등록
	@RequestMapping(value = "/addSupplierAction", method = RequestMethod.POST)
	@ResponseBody
	public List<Supplier> addSupplierAction(Supplier supplier) throws Exception {
		
		supp_service.addSupplierAction(supplier);
		
		List<Supplier> supp_list = supp_service.getSupplierList();
		
		return supp_list;
	}
	
	// 공급처 ID 기준으로 리스트 삭제
	@RequestMapping(value="/deleteSupplier", method = RequestMethod.POST)
	@ResponseBody
	public List<Supplier> deleteSupplier(@RequestParam(value="supp_id[]") List<String> supp_id) throws Exception {
		
		supp_service.deleteSupplier(supp_id);
		
		List<Supplier> supp_list = supp_service.getSupplierList();
		
		return supp_list;
	}

	
	// 공급처 수정을 위해 데이터 받아오기(supp_id 기준)	
	@RequestMapping(value="/getSearchSupplier", method = RequestMethod.POST)
	@ResponseBody
	public Supplier getSearchSupplier(Model model, String supp_id) throws Exception {
		
		return supp_service.getSearchSupplier(supp_id);
	}
	
	
	// 공급처 수정 	
	@RequestMapping(value="/updateSupplier", method = RequestMethod.POST)
	@ResponseBody
	public List<Supplier> updateSupplier(Supplier supplier) throws Exception {
		
		supp_service.updateSupplier(supplier);
		
		List<Supplier> supp_list = supp_service.getSupplierList();
	
		return supp_list;
	}
	
	// --- accounting
	// accounting (회계)	
	@RequestMapping(value ="/accounting", method = RequestMethod.GET)
	public String accounting(Model model, HttpSession session) throws Exception {
		
		Users sion = (Users) session.getAttribute("users");
		
		
		List<Accounting> acc_list = acc_service.getAccList();
		model.addAttribute("acc_list", acc_list);
		model.addAttribute("users", sion);
		
		return "user/accounting";
	}
	
	// 회계 데이터 등록	
	@RequestMapping(value ="/addAccAction", method = RequestMethod.POST)
	@ResponseBody
	public List<Accounting> addAccAction(Accounting accounting, HttpSession session, Model model) throws Exception {
		
		Users sion = (Users) session.getAttribute("users");
		
		accounting.setAcc_writer(sion.getUser_name());
		
		acc_service.addAccAction(accounting);
		
		return acc_service.getAccList();
	}
	
	@RequestMapping(value="/searchAccPayMent", method  = RequestMethod.POST)
	@ResponseBody
	public List<Accounting> searchAccPayMent(String acc_payment) throws Exception {
		
		return acc_service.searchAccPayMent(acc_payment);
	}
	
	@RequestMapping(value="/searchAccWriter", method  = RequestMethod.POST)
	@ResponseBody
	public List<Accounting> searchAccWriter(String acc_writer) throws Exception {

		return acc_service.searchAccWriter(acc_writer);
	}
	
	@RequestMapping(value="/searchAccDate", method  = RequestMethod.POST)
	@ResponseBody
	public List<Accounting> searchAccDate(String acc_occdate) throws Exception {
		
		return acc_service.searchAccDate(acc_occdate);
	}
	
}
