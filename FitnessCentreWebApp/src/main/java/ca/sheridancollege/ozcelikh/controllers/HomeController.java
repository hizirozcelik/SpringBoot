package ca.sheridancollege.ozcelikh.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.ozcelikh.beans.Location;
import ca.sheridancollege.ozcelikh.beans.Schedule;
import ca.sheridancollege.ozcelikh.beans.User;
import ca.sheridancollege.ozcelikh.database.DatabaseAccess;

/**
 * 
 * @author Hizir Ozcelik, January 2022
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
	public String schedule(Model model) {

		model.addAttribute("scheduleList", da.getSchedule());
		model.addAttribute("schedule", new Schedule());
		return "/secure/schedule";
	}

	@GetMapping("/secure/user")
	public String user(Model model) {
		
		model.addAttribute("userList", da.getUser());
		
		return "/secure/user";
	}

	@GetMapping("/secure/locations")
	public String locations(Model model) {

		model.addAttribute("locations", da.getLocations());
		model.addAttribute("location", new Location());
		
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

	@GetMapping("/secure/deleteLocationById/{locId}")
	public String deleteLocationById(Model model, @PathVariable Long locId) {

		da.deleteLocationById(locId);

		model.addAttribute("locations", da.getLocations());
		model.addAttribute("location", new Location());


		return "/secure/locations";
	}
	
	@PostMapping("/secure/addLocation")
	public String addLocation(Model model, @ModelAttribute Location location) {

		da.addLocation(location);

		model.addAttribute("locations", da.getLocations());
		model.addAttribute("location", new Location());

		return "/secure/locations";
	}

//	@PostMapping("/secure/addSchedule")
//	public String addSchedule(Model model, @ModelAttribute Schedule schedule) {
//		
//		da.addSchedule(schedule);
//		
//		model.addAttribute("scheduleList", da.getSchedule());
//		model.addAttribute("schedule", new Schedule());
//		
//		return "/secure/schedule";
//	}
	
	@GetMapping("/secure/editLocationById/{locId}")
	public String editLocationById(Model model, @PathVariable Long locId) {

		Location location = da.getLocationById(locId);
		model.addAttribute("location", location);

		da.deleteLocationById(locId);
		
		model.addAttribute("locations", da.getLocations());

		return "/secure/locations";
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
