CREATE TABLE event(
		eventId 	BIGINT PRIMARY KEY AUTO_INCREMENT, 
		title 		VARCHAR(255),
		eventDate 	DATE,
		eventTime 	TIME,
		details 	VARCHAR(255),
		category 	VARCHAR(255),
		tag			VARCHAR(255)
);
