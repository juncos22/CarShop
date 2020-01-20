package com.dev.springcar.controllers;

import com.dev.springcar.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("car-shop")
public class HomeController {
	
	@GetMapping
	public String home(Model m, HttpServletRequest request) {
		m.addAttribute("title", "Pagina Principal");
		User user = (User) request.getSession().getAttribute("usuario");
		if (user != null) {
			m.addAttribute("usuario", user);
		}
		return "home";
	}
}
