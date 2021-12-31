package ca.sheridancollege.ozcelikh.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Locations {
	
	private Long locId;
	private String locName;
	private String locAddress;
	private String locPhone;
	private String locEmail;

}
