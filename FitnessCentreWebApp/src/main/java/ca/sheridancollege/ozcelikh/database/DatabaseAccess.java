package ca.sheridancollege.ozcelikh.database;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.ozcelikh.beans.Amenity;
import ca.sheridancollege.ozcelikh.beans.Coach;
import ca.sheridancollege.ozcelikh.beans.Locations;
import ca.sheridancollege.ozcelikh.beans.Schedule;
import ca.sheridancollege.ozcelikh.beans.User;
import ca.sheridancollege.ozcelikh.beans.UserLocation;

/**
 * 
 * @author Hizir Ozcelik, November 2021
 */

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User findUserAccount(String email) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM sec_user WHERE email = :email";
		namedParameters.addValue("email", email);

		ArrayList<User> userList = (ArrayList<User>) jdbc.query(query, namedParameters,
				new BeanPropertyRowMapper<User>(User.class));

		if (userList.size() > 0)
			return userList.get(0);
		else
			return null;

	}

	public List<String> getRolesById(Long userId) {

		ArrayList<String> roleList = new ArrayList<String>();

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT user_role.userId, sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId " + "AND userId = :userId";

		namedParameters.addValue("userId", userId);

		List<Map<String, Object>> rows = jdbc.queryForList(query, namedParameters);

		for (Map<String, Object> row : rows) {
			roleList.add((String) row.get("roleName"));
		}

		return roleList;

	}

	public void addUser(String name, String lastName, String email, String password) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "INSERT INTO sec_user" + "(name, lastName, email, encryptedPassword, enabled) "
				+ "VALUES (:name, :lastName, :email, :encryptedPassword, 1) ";

		namedParameters.addValue("name", name);
		namedParameters.addValue("lastName", lastName);
		namedParameters.addValue("email", email);
		namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));

		jdbc.update(query, namedParameters);

	}

	public void addRole(Long userId, Long roleId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "INSERT INTO user_role (userId, roleId) " + "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);

		jdbc.update(query, namedParameters);
	}

	public List<Schedule> getSchedule() {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT s.scheduleTime, f.className, f.classPrice, l.locName, r.roomName "
				+ "FROM fitnessClass f INNER JOIN schedule s ON f.classId = s.classId "
				+ "INNER JOIN location l ON s.locId = l.locId " + "INNER JOIN room r ON s.roomId = r.roomId "
				+ "ORDER BY 1";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Schedule>(Schedule.class));
	}

	public List<UserLocation> getMyLocation(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT l.locName, COUNT(ul.dayOfVisit) AS numOfVisit FROM location l "
				+ "INNER JOIN userLocation ul ON l.locId = ul.locId "
				+ "INNER JOIN sec_user sc ON ul.userId = sc.userId "
				+ "WHERE sc.userId = :userId AND ul.isHomeLoc = 1 AND MONTH(ul.dayOfVisit) = MONTH(SYSDATE()) "
				+ "GROUP BY l.locName "
				+ "ORDER BY 2 DESC";

		namedParameters.addValue("userId", userId);	
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<UserLocation>(UserLocation.class));

	}
	
	public List<UserLocation> getOtherLocation(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT l.locName, COUNT(ul.dayOfVisit) AS numOfVisit FROM location l "
				+ "INNER JOIN userLocation ul ON l.locId = ul.locId "
				+ "INNER JOIN sec_user sc ON ul.userId = sc.userId "
				+ "WHERE sc.userId = :userId AND ul.isHomeLoc = 0 AND MONTH(ul.dayOfVisit) = MONTH(SYSDATE()) "
				+ "GROUP BY l.locName "
				+ "HAVING numOfVisit > 0 "
				+ "ORDER BY 2";

		namedParameters.addValue("userId", userId);	
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<UserLocation>(UserLocation.class));

	}

	public List<Locations> getLocations() {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT * FROM location";
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Locations>(Locations.class));
	}

	public Locations getLocationById(Long locId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM location WHERE locId = :locId";
		namedParameters.addValue("locId", locId);

		ArrayList<Locations> locationList = (ArrayList<Locations>) jdbc.query(query, namedParameters,
				new BeanPropertyRowMapper<Locations>(Locations.class));

		
		return locationList.get(0);


	}

	public List<Amenity> getAmenityListByLocation(Long locId) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT amenityName FROM amenity WHERE locId = :locId ";
		namedParameters.addValue("locId",locId);
				
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Amenity>(Amenity.class));
	}

	public List<Coach> getCoachListByLocation(Long locId) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "SELECT DISTINCT coachName FROM coach c "
				+ "INNER JOIN workFrom w ON w.locId = :locId ";
		
		namedParameters.addValue("locId",locId);
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Coach>(Coach.class));
	}



}
