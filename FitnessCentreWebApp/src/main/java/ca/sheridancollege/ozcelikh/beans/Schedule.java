package ca.sheridancollege.ozcelikh.beans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Schedule {
	
	private Long scheduleId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate scheduleDate;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime scheduleTime;
	private String className;
	private BigDecimal classPrice;
	private String locName;
	private String roomName;

}
