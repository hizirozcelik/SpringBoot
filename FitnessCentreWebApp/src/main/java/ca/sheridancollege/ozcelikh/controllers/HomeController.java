package ca.sheridancollege.ozcelikh.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.ozcelikh.beans.Locations;
import ca.sheridancollege.ozcelikh.beans.User;
import ca.sheridancollege.ozcelikh.beans.UserLocation;
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

	@GetMapping("/secure/schedule")
	public String schedule(Model model, Authentication authentication) {

		model.addAttribute("schedule", da.getSchedule());
		return "/secure/schedule";
	}

	@GetMapping("/secure/locations")
	public String locations(Model model) {

		model.addAttribute("locations", da.getLocations());
		return "/secure/locations";
	}

	@GetMapping("/secure/workoutDetails")
	public String myLocation(Model model, Authentication authentication) {

		String email = authentication.getName();
		User currentUser = da.findUserAccount(email);

		model.addAttribute("myLocationList", da.getMyLocation(currentUser.getUserId()));
		model.addAttribute("otherLocationList", da.getOtherLocation(currentUser.getUserId()));

		return "/secure/workoutDetails";
	}

	@GetMapping("/secure/location/{locId}")
	public String getLocation(Model model, @PathVariable Long locId) {

		model.addAttribute("location", da.getLocationById(locId));
		model.addAttribute("amenityList", da.getAmenityListByLocation(locId));
		model.addAttribute("coachList", da.getCoachListByLocation(locId));
		

		return "/secure/location";
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
