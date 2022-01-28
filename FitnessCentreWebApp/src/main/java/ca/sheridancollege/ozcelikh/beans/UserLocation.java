package ca.sheridancollege.ozcelikh.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLocation {
	
	private Long locId;
	private String locName;
	private int numOfVisit;

}
