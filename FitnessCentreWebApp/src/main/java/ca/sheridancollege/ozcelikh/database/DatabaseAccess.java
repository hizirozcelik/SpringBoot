package ca.sheridancollege.ozcelikh.database;


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
import ca.sheridancollege.ozcelikh.beans.Location;
import ca.sheridancollege.ozcelikh.beans.Schedule;
import ca.sheridancollege.ozcelikh.beans.User;
import ca.sheridancollege.ozcelikh.beans.UserLocation;

/**
 * 
 * @author Hizir Ozcelik, January 2022
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
	
	public List<User> getUser() {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM sec_user";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<User>(User.class));
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

		String query = "SELECT s.scheduleId, s.scheduleDate, s.scheduleTime, f.className, f.classPrice, l.locName, r.roomName "
				+ "FROM fitnessClass f INNER JOIN schedule s ON f.classId = s.classId "
				+ "INNER JOIN location l ON s.locId = l.locId " + "INNER JOIN room r ON s.roomId = r.roomId "
				+ "ORDER BY s.scheduleTime";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Schedule>(Schedule.class));
	}

	public List<UserLocation> getMyLocation(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT l.locId, l.locName, COUNT(ul.dayOfVisit) AS numOfVisit FROM location l "
				+ "INNER JOIN userLocation ul ON l.locId = ul.locId "
				+ "INNER JOIN sec_user sc ON ul.userId = sc.userId "
				+ "WHERE sc.userId = :userId AND ul.isHomeLoc = 1 AND MONTH(ul.dayOfVisit) = MONTH(SYSDATE()) "
				+ "GROUP BY l.locId, l.locName " + "ORDER BY 2 DESC";

		namedParameters.addValue("userId", userId);

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<UserLocation>(UserLocation.class));

	}

	public List<UserLocation> getOtherLocation(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT l.locId, l.locName, COUNT(ul.dayOfVisit) AS numOfVisit FROM location l "
				+ "LEFT OUTER JOIN userLocation ul ON l.locId = ul.locId "
				+ "INNER JOIN sec_user sc ON ul.userId = sc.userId " + "WHERE sc.userId = :userId AND ul.isHomeLoc = 0 "
				+ "GROUP BY l.locId, l.locName " + "ORDER BY 3";

		namedParameters.addValue("userId", userId);

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<UserLocation>(UserLocation.class));

	}

	public List<Location> getLocations() {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM location";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Location>(Location.class));
	}

	public Location getLocationById(Long locId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM location WHERE locId = :locId";
		namedParameters.addValue("locId", locId);

		ArrayList<Location> locationList = (ArrayList<Location>) jdbc.query(query, namedParameters,
				new BeanPropertyRowMapper<Location>(Location.class));

		return locationList.get(0);

	}

	public List<Amenity> getAmenityListByLocation(Long locId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT amenityName FROM amenity WHERE locId = :locId ";
		namedParameters.addValue("locId", locId);

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Amenity>(Amenity.class));
	}

	public List<Coach> getCoachListByLocation(Long locId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT DISTINCT coachName FROM coach c " + "INNER JOIN workFrom w ON w.locId = :locId ";

		namedParameters.addValue("locId", locId);

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Coach>(Coach.class));
	}

	public List<String> getLocationNames() {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT locName FROM location ";

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<String>(String.class));
	}

	public void deleteLocationById(Long locId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "DELETE FROM workFrom WHERE locId = :locId";
		String query1 = "DELETE FROM schedule WHERE locId = :locId";
		String query2 = "DELETE FROM room WHERE locId = :locId";
		String query3 = "DELETE FROM amenity WHERE locId = :locId";
		String query4 = "DELETE FROM userLocation WHERE locId = :locId";
		String query5 = "DELETE FROM location WHERE locId = :locId";

		namedParameters.addValue("locId", locId);

		jdbc.update(query, namedParameters);
		jdbc.update(query1, namedParameters);
		jdbc.update(query2, namedParameters);
		jdbc.update(query3, namedParameters);
		jdbc.update(query4, namedParameters);
		jdbc.update(query5, namedParameters);

	}

	public void addLocation(Location location) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "INSERT INTO location(locName, locAddress, locPhone, locEmail)"
				+ "VALUES(:locName, :locAddress, :locPhone, :locEmail)";

		namedParameters.addValue("locName", location.getLocName());
		namedParameters.addValue("locAddress", location.getLocAddress());
		namedParameters.addValue("locPhone", location.getLocPhone());
		namedParameters.addValue("locEmail", location.getLocEmail());

		jdbc.update(query, namedParameters);

	}

}
