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

import ca.sheridancollege.ozcelikh.beans.User;
import ca.sheridancollege.ozcelikh.beans.UserSchedule;

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
	
	public void addUserSchedule(String name, String lastName, String email, String prefDay1, String prefTime1, String prefDay2, String prefTime2) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "INSERT INTO user_schedule" + "(name, lastName, email, prefDay1, prefTime1, prefDay2, prefTime2) "
				+ "VALUES (:name, :lastName, :email, :prefDay1, :prefTime1, :prefDay2, :prefTime2) ";
		
		namedParameters.addValue("name", name);
		namedParameters.addValue("lastName", lastName);
		namedParameters.addValue("email", email);
		namedParameters.addValue("prefDay1", prefDay1);
		namedParameters.addValue("prefTime1", prefTime1);
		namedParameters.addValue("prefDay2", prefDay2);
		namedParameters.addValue("prefTime2", prefTime2);
		
		jdbc.update(query, namedParameters);
		
	}
	
	public void deleteUserScheduleById(String email) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		String query = "DELETE FROM user_schedule WHERE user_schedule.email = email";
		
		namedParameters.addValue("email", email);
		
		
		jdbc.update(query, namedParameters);
		
	}
	
	
	
	public List<UserSchedule> getUserScheduleList() {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM user_schedule";
			
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<UserSchedule>(UserSchedule.class));

	}
	
public UserSchedule getUserSchedule(String email) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT * FROM user_schedule WHERE email = :email";
		namedParameters.addValue("email", email);
		
		
		ArrayList<UserSchedule> userSheduleList = (ArrayList<UserSchedule>) 
				jdbc.query(query, namedParameters, new BeanPropertyRowMapper<UserSchedule>(UserSchedule.class));

		if (userSheduleList.size() > 0)
			return userSheduleList.get(0);
		else
			return null;
	}
	
	

	public void addRole(Long userId, Long roleId) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "INSERT INTO user_role (userId, roleId) " + "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);

		jdbc.update(query, namedParameters);
	}
	
	public Long count(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		String query = "SELECT count(*) FROM user_schedule WHERE user_schedule.email = email";
		namedParameters.addValue("email", email);

		return jdbc.queryForObject(query, namedParameters, Long.TYPE);
	}

	public void addUserSchedule(User currentUser, UserSchedule userSchedule) {
		
		String name = currentUser.getName();
		String lastName = currentUser.getLastName();
		String email = currentUser.getEmail();
		String prefDay1 = userSchedule.getPrefDay1();
		String prefTime1 = userSchedule.getPrefTime1();
		String prefDay2 = userSchedule.getPrefDay2();
		String prefTime2 = userSchedule.getPrefTime2();
		
		
		addUserSchedule(name, lastName, email, prefDay1, prefTime1, prefDay2, prefTime2);
		
		
	}

	public void updateUserSchedule(UserSchedule userSchedule, String email) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		
		String prefDay1 = userSchedule.getPrefDay1();
		String prefTime1 = userSchedule.getPrefTime1();
		String prefDay2 = userSchedule.getPrefDay2();
		String prefTime2 = userSchedule.getPrefTime2();
		
		String query = "UPDATE user_schedule SET prefDay1 = :prefDay1, prefTime1 = :prefTime1, "
				+ "prefDay2 = :prefDay2, prefTime2 = :prefTime2 WHERE email = :email";
		
		
		namedParameters.addValue("email", email);
		namedParameters.addValue("prefDay1", prefDay1);
		namedParameters.addValue("prefTime1", prefTime1);
		namedParameters.addValue("prefDay2", prefDay2);
		namedParameters.addValue("prefTime2", prefTime2);
		
		jdbc.update(query, namedParameters);
				
	}

	

}
