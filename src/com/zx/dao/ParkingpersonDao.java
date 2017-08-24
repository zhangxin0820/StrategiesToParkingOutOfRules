package com.zx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zx.db.DBConn;
import com.zx.model.Parkingperson;

public class ParkingpersonDao {

	public static void addParkingperson(Parkingperson person) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "insert into parking_person (have_car_id,parking_date,person_name,person_gender,"
				+ "person_age,person_job) values (?,?,?,?,?,?)";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setInt(1, person.getHave_car_id());
		ptmt.setDate(2, person.getParking_date());
		ptmt.setString(3, person.getPerson_name());
		ptmt.setString(4, person.getPerson_gender());
		ptmt.setInt(5, person.getPerson_age());
		ptmt.setString(6, person.getPerson_job());
		ptmt.executeUpdate();
	}
	
	public static void addParkingpersonAll(Parkingperson person) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "insert into parking_person (have_car_id,parking_date,person_name,person_gender,"
				+ "person_age,person_nationality,person_birthday,person_job,person_education,"
				+ "person_hkadr,person_height,person_weight,person_nativeplace,person_maritalstatus,"
				+ "person_politicalstatus) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setInt(1, person.getHave_car_id());
		ptmt.setDate(2, person.getParking_date());
		ptmt.setString(3, person.getPerson_name());
		ptmt.setString(4, person.getPerson_gender());
		ptmt.setInt(5, person.getPerson_age());
		ptmt.setString(6, person.getPerson_nationality());
		ptmt.setDate(7, person.getPerson_birthday());
		ptmt.setString(8, person.getPerson_job());
		ptmt.setString(9, person.getPerson_education());
		ptmt.setString(10, person.getPerson_hkadr());
		ptmt.setInt(11, person.getPerson_height());
		ptmt.setInt(12, person.getPerson_weight());
		ptmt.setString(13, person.getPerson_nativeplace());
		ptmt.setString(14, person.getPerson_maritalstatus());
		ptmt.setString(15, person.getPerson_politicalstatus());
		ptmt.executeUpdate();
	}

	public static void updateParkingperson(Parkingperson person) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "update parking_person set parking_date=?,person_name=?,person_gender=?,"
				+ "person_age=?,person_job=? where person_id=?";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setDate(1, person.getParking_date());
		ptmt.setString(2, person.getPerson_name());
		ptmt.setString(3, person.getPerson_gender());
		ptmt.setInt(4, person.getPerson_age());
		ptmt.setString(5, person.getPerson_job());
		ptmt.setInt(6, person.getPerson_id());
		ptmt.execute();

	}
	
	public static void updateParkingpersonAll(Parkingperson person) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "update parking_person set parking_date=?,person_name=?,person_gender=?,"
				+ "person_age=?,person_nationality=?,person_birthday=?,person_job=?,person_education=?,"
				+ "person_hkadr=?,person_height=?,person_weight=?,person_nativeplace=?,"
				+ "person_maritalstatus=?,person_politicalstatus? where person_id=?";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setDate(1, person.getParking_date());
		ptmt.setString(2, person.getPerson_name());
		ptmt.setString(3, person.getPerson_gender());
		ptmt.setInt(4, person.getPerson_age());
		ptmt.setString(5, person.getPerson_nationality());
		ptmt.setDate(6, person.getPerson_birthday());
		ptmt.setString(7, person.getPerson_job());
		ptmt.setString(8, person.getPerson_education());
		ptmt.setString(9, person.getPerson_hkadr());
		ptmt.setInt(10, person.getPerson_height());
		ptmt.setInt(11, person.getPerson_weight());
		ptmt.setString(12, person.getPerson_nativeplace());
		ptmt.setString(13, person.getPerson_maritalstatus());
		ptmt.setString(14, person.getPerson_politicalstatus());
		ptmt.setInt(15, person.getPerson_id());
		ptmt.execute();

	}

	public static void delParkingperson(String[] id) throws SQLException {

		Connection conn = DBConn.getConnection();
		int n = id.length;

		for (int i = 0; i < n; i++) {

			int tempPerson_id = Integer.parseInt(id[i]);

			String sql = "delete from parking_person where person_id = ?";
			String car_sql = "delete from sub_incident where have_person_id = ?";
			PreparedStatement ptmt = conn.prepareStatement(sql);
			PreparedStatement ptmt_car = conn.prepareStatement(car_sql);

			ptmt.setInt(1, tempPerson_id);
			ptmt_car.setInt(1, tempPerson_id);

			ptmt.execute();
			ptmt_car.execute();
		}
	}
	
	public static List<Parkingperson> getPersonById(Integer id) throws SQLException {
		
		List<Parkingperson> result = new LinkedList<Parkingperson>();
		
		Connection conn = DBConn.getConnection();
		
		String sub_person_sql = "select sub_id from sub_incident where sub_event_id = ?";
		PreparedStatement ptmt_sub_person_id = conn.prepareStatement(sub_person_sql);

		List<Integer> temp = new ArrayList<Integer>();
		ptmt_sub_person_id.setInt(1, id);
		ResultSet rsTemp = ptmt_sub_person_id.executeQuery();

		while (rsTemp.next()) {
			temp.add(rsTemp.getInt(1));
		}
		
		String sql = "select * from parking_person where have_car_id = ?";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ResultSet rs = null;
		for (int i = 0; i < temp.size(); i++) {
			ptmt.setInt(1, temp.get(i));
			rs = ptmt.executeQuery();
			
			while (rs.next()) {
				
				Parkingperson person = new Parkingperson();

				person.setPerson_id(rs.getInt("person_id"));
				person.setHave_car_id(rs.getInt("have_car_id"));
				person.setParking_date(rs.getDate("parking_date"));
				person.setPerson_name(rs.getString("person_name"));
				person.setPerson_gender(rs.getString("person_gender"));
				person.setPerson_age(rs.getInt("person_age"));
				person.setPerson_nationality(rs.getString("person_nationality"));
				person.setPerson_birthday(rs.getDate("person_birthday"));
				person.setPerson_job(rs.getString("person_job"));
				person.setPerson_education(rs.getString("person_education"));
				person.setPerson_hkadr(rs.getString("person_hkadr"));
				person.setPerson_height(rs.getInt("person_height"));
				person.setPerson_weight(rs.getInt("person_weight"));
				person.setPerson_nativeplace(rs.getString("person_nativeplace"));
				person.setPerson_maritalstatus(rs.getString("person_maritalstatus"));
				person.setPerson_politicalstatus(rs.getString("person_politicalstatus"));

				result.add(person);
			}
		}
		
		return result;
	}
	
	public static List<Parkingperson> getOne(Integer person_id) throws SQLException {
		
		List<Parkingperson> result = new LinkedList<Parkingperson>();
		
		Connection conn = DBConn.getConnection();
		String sql = "select * from parking_person where person_id = ?";
		
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setInt(1, person_id);
		
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {
			
			Parkingperson person = new Parkingperson();
			
			person.setPerson_id(rs.getInt("person_id"));
			person.setHave_car_id(rs.getInt("have_car_id"));
			person.setParking_date(rs.getDate("parking_date"));
			person.setPerson_name(rs.getString("person_name"));
			person.setPerson_gender(rs.getString("person_gender"));
			person.setPerson_age(rs.getInt("person_age"));
			person.setPerson_job(rs.getString("person_job"));
			
			result.add(person);
		}
		
		return result;
	}
	
	public static List<Parkingperson> getAll() throws SQLException {
		
		List<Parkingperson> result = new LinkedList<Parkingperson>();

		Connection conn = DBConn.getConnection();
		String sql = "select * from parking_person";

		PreparedStatement ptmt = conn.prepareStatement(sql);
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {

			Parkingperson person = new Parkingperson();

			person.setPerson_id(rs.getInt("person_id"));
			person.setHave_car_id(rs.getInt("have_car_id"));
			person.setParking_date(rs.getDate("parking_date"));
			person.setPerson_name(rs.getString("person_name"));
			person.setPerson_gender(rs.getString("person_gender"));
			person.setPerson_age(rs.getInt("person_age"));
			person.setPerson_job(rs.getString("person_job"));

			result.add(person);
		}
		
		return result;
	}

	public static List<Parkingperson> getAll(Integer have_car_id) throws SQLException {

		List<Parkingperson> result = new LinkedList<Parkingperson>();

		Connection conn = DBConn.getConnection();
		String sql = "select * from parking_person where have_car_id = ?";

		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setInt(1, have_car_id);

		ResultSet rs = ptmt.executeQuery();

		while (rs.next()) {

			Parkingperson person = new Parkingperson();

			person.setPerson_id(rs.getInt("person_id"));
			person.setHave_car_id(rs.getInt("have_car_id"));
			person.setParking_date(rs.getDate("parking_date"));
			person.setPerson_name(rs.getString("person_name"));
			person.setPerson_gender(rs.getString("person_gender"));
			person.setPerson_age(rs.getInt("person_age"));
			person.setPerson_job(rs.getString("person_job"));

			result.add(person);
		}

		return result;
	}

	public static List<Parkingperson> getDetailed(Integer person_id) throws SQLException {

		List<Parkingperson> result = new LinkedList<Parkingperson>();

		Connection conn = DBConn.getConnection();
		String sql = "select * from parking_person where person_id = ?";
		
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setInt(1, person_id);

		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {
			
			Parkingperson person = new Parkingperson();

			person.setPerson_id(rs.getInt("person_id"));
			person.setHave_car_id(rs.getInt("have_car_id"));
			person.setParking_date(rs.getDate("parking_date"));
			person.setPerson_name(rs.getString("person_name"));
			person.setPerson_gender(rs.getString("person_gender"));
			person.setPerson_age(rs.getInt("person_age"));
			person.setPerson_nationality(rs.getString("person_nationality"));
			person.setPerson_birthday(rs.getDate("person_birthday"));
			person.setPerson_job(rs.getString("person_job"));
			person.setPerson_education(rs.getString("person_education"));
			person.setPerson_hkadr(rs.getString("person_hkadr"));
			person.setPerson_height(rs.getInt("person_height"));
			person.setPerson_weight(rs.getInt("person_weight"));
			person.setPerson_nativeplace(rs.getString("person_nativeplace"));
			person.setPerson_maritalstatus(rs.getString("person_maritalstatus"));
			person.setPerson_politicalstatus(rs.getString("person_politicalstatus"));

			result.add(person);
		}
		
		return result;
	}

}
