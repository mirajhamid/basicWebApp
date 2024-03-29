package com.miraj.basicWebApp.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.miraj.basicWebApp.model.Customer;
import com.miraj.basicWebApp.repository.CustomerRepository;
import com.miraj.basicWebApp.response.CustomerResponse;

@Controller
public class HomeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	CustomerRepository cusRepo;
	
	
	@RequestMapping(path = "/")
	public String returnIndex() {
		
		return "index";
		
	}
	
	@RequestMapping(path = "/home")
	public String returnHomePage(HttpServletRequest req , HttpServletResponse res) {
		LOG.info("Home page loaded : " + req.getRemoteAddr());
		
		//If you didn't add jasper dependencies a home.jsp will be downloaded
		//Use tomcat core version
		//return "home.jsp";
		
		//Uses spring.mvc.view.suffix=.jsp property
		//return "home";
		//http://localhost:6900/home?name=miraj&country=SL
		HttpSession session = req.getSession();
		String name = req.getParameter("name");
		String country = req.getParameter("country");
		session.setAttribute("parmName", name);
		session.setAttribute("parmCountry", country);
		
		return "home";
		
	}
	
	@RequestMapping(path = "/about")
	public String returnAbout( @RequestParam("name") String pName, 
								@RequestParam("country") String pCountry, 
								HttpSession session) {
		LOG.info("About page loaded ");
		
		session.setAttribute("parmName", pName);
		session.setAttribute("parmCountry", pCountry);
		
		return "about";
		
	}
	
	@RequestMapping(path = "/contact")
	public ModelAndView returnContact( @RequestParam("name") String pName, 
								@RequestParam("country") String pCountry) {
		LOG.info("Contact page loaded ");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("parmName", pName);
		mv.addObject("parmCountry", pCountry);
		
		//retruns contact.jsp page
		mv.setViewName("contact");
		return mv;
		
	}
	
	//we should send variable and values as parameters
	@RequestMapping(path = "/customer")
	public ModelAndView returnCustomer( Customer customer) {
		LOG.info("Contact page loaded ");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("cus", customer);
		
		//retruns contact.jsp page
		mv.setViewName("customer");
		return mv;
		
	}
	
	@RequestMapping(path = "/addCustomer")
	public ModelAndView formRequest( Customer customer) {
		LOG.info("addCustomer controller executed ");
		
		cusRepo.save(customer);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("cus", customer);
		
		//retruns customer.jsp page
		mv.setViewName("customer");
		return mv;
		
	}
	
	//http://localhost:6900/findById?id=100
	@RequestMapping(path = "/findById")
	public ModelAndView findCustomer( Customer customer) {
		LOG.info("find customer id: " + customer.getId());
		
		Customer cus = (Customer) cusRepo.findById(customer.getId());
		List<Customer> cusList = cusRepo.findByIdGreaterThan(customer.getId());
		
		System.out.println( "CustList greater than : " +cusList);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("cus", cus);
		
		//retruns customer.jsp page
		mv.setViewName("customer");
		return mv;
		
	}
	
	//API's
	//This will always communicate with json response
	@RequestMapping(path = "/findAll")
	@ResponseBody
	public void findCustomer(HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
	    response.setCharacterEncoding("utf-8");
	    
	    List<Customer> cusList = (List<Customer>)cusRepo.findAll();

	    Gson gson = new Gson();
	    String jsonData = gson.toJson(cusList);
	    PrintWriter out = response.getWriter();
	    try {
	        out.println(jsonData);
	    } finally {
	        out.close();
	    }
	}
	
	
	//with quality negosiation
	//use jakson dataformat with same jackson core version
	//use Accept applicaion/json or application/xml
	//if you dont use Accept header by default it will be JSON
	@RequestMapping(path = "/findAll2")
	public ResponseEntity<CustomerResponse> findAll2() {
		
		Random random = new Random();
		int x =  random.nextInt(10);
	    
		if( x > 5) {
		   
			List<Customer> cusList = (List<Customer>)cusRepo.findAll();
		    
		    CustomerResponse response = new CustomerResponse();
		    response.setCusList(cusList);
		    response.setMessage("Success");
	    	return new ResponseEntity<CustomerResponse>(response, HttpStatus.OK);
	   
		}else {
		   
			CustomerResponse response = new CustomerResponse();
			response.setMessage("error message");
			return new ResponseEntity<CustomerResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		   
	}

	@RequestMapping(path = "/findAll3")
	@ResponseBody
	public Iterable<Customer> findAll3() {

	    return cusRepo.findAll();

	}

}
