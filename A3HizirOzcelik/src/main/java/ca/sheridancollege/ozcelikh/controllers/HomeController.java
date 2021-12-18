package ca.sheridancollege.ozcelikh.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import ca.sheridancollege.ozcelikh.beans.Event;

@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model, RestTemplate restTemplate) {

		ResponseEntity<Event[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/events",
				Event[].class);

		model.addAttribute("event", new Event());
		model.addAttribute("eventList", responseEntity.getBody());
		return "index";
	}

	@GetMapping(value = "/getEvent/{id}", produces = "application/json")
	@ResponseBody
	public Event getEvent(@PathVariable Long id, RestTemplate restTemplate) {
		ResponseEntity<Event> responseEntity = restTemplate.getForEntity("http://localhost:8080/events/{id}",
				Event.class, id);
		return responseEntity.getBody();
	}

	@PostMapping("/postEvent")
	public String postEvent(Model model, @ModelAttribute Event event, RestTemplate restTemplate) {

		String url = restTemplate.postForObject("http://localhost:8080/events", event, String.class);
		model.addAttribute("url", url);

		Event[] eventList = restTemplate.getForObject("http://localhost:8080/events", Event[].class);
		model.addAttribute("eventList", eventList);

		return "index";

	}

}
