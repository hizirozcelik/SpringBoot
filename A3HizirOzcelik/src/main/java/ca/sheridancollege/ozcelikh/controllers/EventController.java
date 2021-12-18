package ca.sheridancollege.ozcelikh.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.ozcelikh.beans.Event;
import ca.sheridancollege.ozcelikh.database.DatabaseAccess;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

	private DatabaseAccess da;

//	Retrieve a Event with id
	@GetMapping(value = "/{id}") // "value" only here to illustrate our Mappings can do more!
	public Event getIndividualEvent(@PathVariable Long id) {

		return da.findById(id);
	}

//	Retrieve all Events
	@GetMapping
	public List<Event> getEventCollection() {
		System.out.println("All retrieved");
		return da.findAll();

	}

//	Add Item 
	@PostMapping(consumes = "application/json")
	public String postEvent(@RequestBody Event event) {
		return "http://localhost:8080/events/" + da.save(event);

	}

}
