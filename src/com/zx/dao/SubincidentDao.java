package com.zx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.zx.db.DBConn;
import com.zx.model.Subincident;

public class SubincidentDao {

	public static void addSubincident(Subincident sub) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "insert into sub_incident (sub_event_id,have_person_id,car_date,car_id,car_type) values (?,?,?,?,?)";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setInt(1, sub.getSub_event_id());
		ptmt.setInt(2, sub.getHave_person_id());
		ptmt.setDate(3, sub.getCar_date());
		ptmt.setString(4, sub.getCar_id());
		ptmt.setString(5, sub.getCar_type());
		ptmt.executeUpdate();

	}

	public static void updateSubincident(Subincident sub) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "update sub_incident set car_date=?,car_id=?,car_type=? where sub_id=?";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setDate(1, sub.getCar_date());
		ptmt.setString(2, sub.getCar_id());
		ptmt.setString(3, sub.getCar_type());
		ptmt.setInt(4, sub.getSub_id());
		ptmt.execute();
	}

	public static void delSubincident(String[] sub_id) throws SQLException {

		Connection conn = DBConn.getConnection();
		int n = sub_id.length;

		for (int i = 0; i < n; i++) {

			int tempSub_id = Integer.parseInt(sub_id[i]);
			String sub_sql = "delete from sub_incident where sub_id = ?";
			String person_sql = "delete from parking_person where have_car_id = ?";

			PreparedStatement ptmt_sub = conn.prepareStatement(sub_sql);
			PreparedStatement ptmt_person = conn.prepareStatement(person_sql);

			ptmt_sub.setInt(1, tempSub_id);
			ptmt_person.setInt(1, tempSub_id);

			ptmt_person.execute();
			ptmt_sub.execute();

		}
	}
	
	public static List<Subincident> getAll() throws SQLException {
		
		List<Subincident> result = new LinkedList<Subincident>();
		
		Connection conn = DBConn.getConnection();
		String sql = "select * from sub_incident";
		
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {
			
			Subincident sub = new Subincident();
			
			sub.setSub_id(rs.getInt("sub_id"));
			sub.setSub_event_id(rs.getInt("sub_event_id"));
			sub.setHave_person_id(rs.getInt("have_person_id"));
			sub.setCar_date(rs.getDate("car_date"));
			sub.setCar_id(rs.getString("car_id"));
			sub.setCar_type(rs.getString("car_type"));

			result.add(sub);
		}
		
		return result;
	}

	public static List<Subincident> getAll(Integer sub_event_id) throws SQLException {

		List<Subincident> result = new LinkedList<Subincident>();

		Connection conn = DBConn.getConnection();
		String sql = "select * from sub_incident where sub_event_id = ?";

		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setInt(1, sub_event_id);

		ResultSet rs = ptmt.executeQuery();

		while (rs.next()) {

			Subincident sub = new Subincident();

			sub.setSub_id(rs.getInt("sub_id"));
			sub.setSub_event_id(rs.getInt("sub_event_id"));
			sub.setHave_person_id(rs.getInt("have_person_id"));
			sub.setCar_date(rs.getDate("car_date"));
			sub.setCar_id(rs.getString("car_id"));
			sub.setCar_type(rs.getString("car_type"));

			result.add(sub);
		}

		return result;
	}

	public static List<Subincident> getPersonCar(Integer have_person_id) throws SQLException {

		List<Subincident> result = new LinkedList<Subincident>();

		Connection conn = DBConn.getConnection();
		String sql = "select * from sub_incident where have_person_id = ?";

		PreparedStatement ptmt = conn.prepareStatement(sql);
		ptmt.setInt(1, have_person_id);

		ResultSet rs = ptmt.executeQuery();

		while (rs.next()) {

			Subincident sub = new Subincident();

			sub.setSub_id(rs.getInt("sub_id"));
			sub.setSub_event_id(rs.getInt("sub_event_id"));
			sub.setHave_person_id(rs.getInt("have_person_id"));
			sub.setCar_date(rs.getDate("car_date"));
			sub.setCar_id(rs.getString("car_id"));
			sub.setCar_type(rs.getString("car_type"));

			result.add(sub);
		}

		return result;
	}

}
