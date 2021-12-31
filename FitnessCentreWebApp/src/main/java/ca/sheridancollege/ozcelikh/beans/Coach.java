package ca.sheridancollege.ozcelikh.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Coach {
	
	private Long coachId;
	private String coachName;
	private Integer coachLevel;
	private Integer coachRating;
	private String aboutMe;

}
