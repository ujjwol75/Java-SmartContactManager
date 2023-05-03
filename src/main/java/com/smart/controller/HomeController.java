package com.smart.controller;


/*import javax.servlet.http.HttpSession;*/

/*import javax.servlet.http.HttpSession;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;



@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home- Smart Contact Manager ");
		return "home";
		
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About- Smart Contact Manager ");
		return "about";
		
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "SignUp- Smart Contact Manager ");
		model.addAttribute("user", new User());
		return "signup";
		
	}
	
	//handler for registering user
	@RequestMapping(value="/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model) throws Exception {
		
		try {
			if(!agreement) {
				System.out.println("Agreement is not checked!!");
				throw new Exception("You have not agreed the terms and conditions");
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println(agreement);
			System.out.println(user);
			
			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			System.out.println(result);
//			session.setAttribute("message", "alert-success!!");
			return "signup";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			model.addAttribute("user", user);
//			session.setAttribute("message", "alert-error!!");
			return "signup";
		}
	}
	
	
	// handler for custom login
		@GetMapping("/signin")
		public String customLogin(Model model) {
			model.addAttribute("title", "Login Page");
			return "login";
		}

}

	

