package ca.sheridancollege.ozcelikh.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Amenity {
	
	private Long amenityId;
	private String amenityName;
	private Long locId;
	

}
