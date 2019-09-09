package com.miraj.basicWebApp.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(path = "home")
	public String returnHomePage(HttpServletRequest req , HttpServletResponse res) {
		LOG.info("Home page loaded : " + req.getRemoteAddr());
		
		//If you didn't add jasper dependencies a home.jsp will be downloaded
		//Use tomcat core version
		//return "home.jsp";
		
		//Uses spring.mvc.view.suffix=.jsp property
		//return "home";
		//http://localhost:6900/home?name=miraj&country=SL
		Cookie[] x = req.getCookies();
		HttpSession session = req.getSession();
		String name = req.getParameter("name");
		String country = req.getParameter("country");
		session.setAttribute("parmName", name);
		session.setAttribute("parmCountry", country);
		
		return "home";
		
	}
	
	@RequestMapping(path = "about")
	public String returnAbout( @RequestParam("name") String pName, 
								@RequestParam("country") String pCountry, 
								HttpSession session) {
		LOG.info("About page loaded ");
		
		session.setAttribute("parmName", pName);
		session.setAttribute("parmCountry", pCountry);
		
		return "about";
		
	}
	
	@RequestMapping(path = "contact")
	public ModelAndView returnContact( @RequestParam("name") String pName, 
								@RequestParam("country") String pCountry, 
								HttpSession session) {
		LOG.info("Contact page loaded ");
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("parmName", pName);
		mv.addObject("parmCountry", pCountry);
		
		//retruns contact.jsp page
		mv.setViewName("contact");
		return mv;
		
	}

}
