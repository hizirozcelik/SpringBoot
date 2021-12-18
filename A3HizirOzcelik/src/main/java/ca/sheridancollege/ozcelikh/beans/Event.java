package ca.sheridancollege.ozcelikh.beans;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	
	private final String[] CATEGORIES = {"Personal", "Work", "Family", "Birthday", "Anniversary"};
	private final String[] TAGS = {"Red", "Green", "Yellow", "Blue"};
	
	private Long eventId;
	private String title;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate eventDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime eventTime;
	private String details;
	private String category;
	private String tag;

}
