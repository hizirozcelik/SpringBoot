package ca.sheridancollege.ozcelikh.beans;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLocation {
	
	private String locName;
	private int numOfVisit;

}
