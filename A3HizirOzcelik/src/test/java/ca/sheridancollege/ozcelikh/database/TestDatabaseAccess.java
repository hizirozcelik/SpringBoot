package ca.sheridancollege.ozcelikh.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import ca.sheridancollege.ozcelikh.beans.Event;

@SpringBootTest
@AutoConfigureTestDatabase
public class TestDatabaseAccess {

	@Autowired
	private DatabaseAccess da;

	@Test
	public void testSave_getEventListIncreasedByOne() {

		int size = da.findAll().size();
		Event event = new Event();
		event.setTitle("Test");
		event.setEventDate(LocalDate.of(2021, 12, 8));
		event.setEventTime(LocalTime.of(12, 00));
		event.setDetails("Test purposes");
		event.setCategory("Personal");
		event.setTag("Red");

		da.save(event);

		assertEquals(da.findAll().size(), ++size);

	}

	@Test
	public void testFindById_getTheLastEventAdded() {

		Event event = new Event();
		event.setTitle("Test");
		event.setEventDate(LocalDate.of(2021, 12, 8));
		event.setEventTime(LocalTime.of(12, 00));
		event.setDetails("Test purposes");
		event.setCategory("Personal");
		event.setTag("Red");

		Long id = da.save(event);

		assertEquals(da.findById(id).getTitle(), event.getTitle());

	}

}
