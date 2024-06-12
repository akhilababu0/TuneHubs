package com.kodnest.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kodnest.tunehub.entity.Song;
import com.kodnest.tunehub.entity.User;
import com.kodnest.tunehub.service.SongService;
import com.kodnest.tunehub.serviceimplmnt.UserServiceImplmnt;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	UserServiceImplmnt serviceImplmnt;
	
	@Autowired
	SongService songService;

	@PostMapping("/register")
	public String adduser(@ModelAttribute User user) {		
		//email taken from registration form
		String email = user.getEmail();
		//checking if email as entered in registration form is present in db or not
		boolean status = serviceImplmnt.emailExists(email);

		if(status == false) {
			serviceImplmnt.addUser(user);
			System.out.println("User Added");			
		}
		else {
			System.out.println("User already exists");
		}
		return "login";
	}
	@PostMapping("/validate")
	public String validate(@RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session, Model model) {

		if (serviceImplmnt.validateUser(email, password)== true) {		
			String role = serviceImplmnt.getRole(email);
			session.setAttribute("email", email);
			if(role.equals("admin")) {				
				return "adminhome";
			}
			else {
				
				User user = serviceImplmnt.getUser(email);
				boolean userstatus = user.isIspremium();
				if(userstatus == true) {
					List<Song> fetchAllSongs = songService.fetchAllSongs();
					model.addAttribute("songs", fetchAllSongs);
					
					model.addAttribute("ispremium", userstatus);
					return "tunehome";
				}
				else {
					return "tunecustomer";
				}
				
			}
		}
		else {
			return "login";
		}
		
	}
    
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}

}
