package ca.sheridancollege.ozcelikh.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.ozcelikh.beans.Event;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	public List<Event> findAll() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM event ORDER BY eventDate";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Event>(Event.class));
	}

	public Event findById(Long eventId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM event WHERE eventId = :eventId";
		namedParameters.addValue("eventId", eventId);

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Event>(Event.class)).get(0);
	}

	public Long save(Event event) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		String query = "INSERT INTO event(title, eventDate, eventTime, details, category, tag) "
				+ "VALUES(:title, :eventDate, :eventTime, :details, :category, :tag)";

		namedParameters.addValue("title", event.getTitle()).addValue("eventDate", event.getEventDate())
				.addValue("eventTime", event.getEventTime()).addValue("details", event.getDetails())
				.addValue("category", event.getCategory()).addValue("tag", event.getTag());

		jdbc.update(query, namedParameters, generatedKeyHolder);
		return (Long) generatedKeyHolder.getKey();
	}

	public void saveAll(List<Event> eventList) {
		for (Event s : eventList) {
			save(s);

		}
	}

	public Long count() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT count(*) FROM event";

		return jdbc.queryForObject(query, namedParameters, Long.TYPE);
	}

}