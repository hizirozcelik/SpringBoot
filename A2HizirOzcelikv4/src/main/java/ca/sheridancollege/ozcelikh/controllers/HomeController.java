package ca.sheridancollege.ozcelikh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.ozcelikh.beans.User;
import ca.sheridancollege.ozcelikh.beans.UserSchedule;
import ca.sheridancollege.ozcelikh.database.DatabaseAccess;

/**
 * 
 * @author Hizir Ozcelik, November 2021
 */

@Controller
public class HomeController {

	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/secure")
	public String secureIndex(Model model, Authentication authentication) {
		
		String email = authentication.getName();
		
		String name = da.findUserAccount(email).getName();
		String lastName = da.findUserAccount(email).getLastName();
		model.addAttribute("name", name);
		model.addAttribute("lastName", lastName);
		
		return "/secure/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/secure/userSchedule")
	public String userSchedule(Model model) {

		model.addAttribute("userSchedule", new UserSchedule());

		return "/secure/userSchedule";
	}

	@PostMapping("/secure/addUserSchedule")
	public String addUserSchedule(Model model, Authentication authentication, UserSchedule userSchedule) {

		String userName = authentication.getName();

		User currentUser = da.findUserAccount(userName);

		if (da.getUserSchedule(userName) != null) {

			da.updateUserSchedule(userSchedule, userName);
			model.addAttribute("userScheduleList", da.getUserScheduleList());

		} else {
			da.addUserSchedule(currentUser, userSchedule);

			model.addAttribute("userScheduleList", da.getUserScheduleList());

		}

		return "/secure/userScheduleList";
	}

	@GetMapping("/secure/userScheduleList")
	public String weeklySchedule(Model model) {

		model.addAttribute("userScheduleList", da.getUserScheduleList());

		return "/secure/userScheduleList";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}

	@GetMapping("/about")
	public String getAbout() {
		return "about";
	}

	@PostMapping("/register")
	public String postRegister(@RequestParam String name, @RequestParam String lastName, @RequestParam String username,
			@RequestParam String password) {

// Checking if user already has an account before new account creation.
		if (da.findUserAccount(username) == null) {
			da.addUser(name, lastName, username, password);

			Long userId = da.findUserAccount(username).getUserId();

			da.addRole(userId, Long.valueOf(1));

			return "accountCreated";
		} else
			return "userFound";
	}

}
