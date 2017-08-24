package com.zx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zx.db.DBConn;
import com.zx.model.Parkingincident;

public class ParkingincidentDao {

	public static void addParkingincident(Parkingincident park) throws Exception {

		Connection conn = DBConn.getConnection();
		String sql = "insert into parking_incident (parking_year,parking_state,parking_attack_now,"
				+ "parking_catch,parking_attack_past,parking_coverage,parking_density,parking_time,"
				+ "parking_distance1,parking_distance2) values(?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		//ptmt.setInt(1, park.getGet_person_id());
		ptmt.setInt(1, park.getParking_year());
		ptmt.setString(2, park.getParking_state());
		ptmt.setFloat(3, park.getParking_attack_now());
		ptmt.setFloat(4, park.getParking_catch());
		ptmt.setFloat(5, park.getParking_attack_past());
		ptmt.setFloat(6, park.getParking_coverage());
		ptmt.setFloat(7, park.getParking_density());
		ptmt.setFloat(8, park.getParking_time());
		ptmt.setFloat(9, park.getParking_distance1());
		ptmt.setFloat(10, park.getParking_distance2());
		ptmt.executeUpdate();
	}

	public static void updateParkingincident(Parkingincident park) throws SQLException {

		Connection conn = DBConn.getConnection();
		String sql = "update parking_incident set parking_year=?,parking_state=?,"
				+ "parking_attack_now=?,parking_catch=?,parking_attack_past=?,parking_coverage=?,"
				+ "parking_density=?,parking_time=?,parking_distance1=?,parking_distance2=? where id=?";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		//ptmt.setInt(1, park.getGet_person_id());
		ptmt.setInt(1, park.getParking_year());
		ptmt.setString(2, park.getParking_state());
		ptmt.setFloat(3, park.getParking_attack_now());
		ptmt.setFloat(4, park.getParking_catch());
		ptmt.setFloat(5, park.getParking_attack_past());
		ptmt.setFloat(6, park.getParking_coverage());
		ptmt.setFloat(7, park.getParking_density());
		ptmt.setFloat(8, park.getParking_time());
		ptmt.setFloat(9, park.getParking_distance1());
		ptmt.setFloat(10, park.getParking_distance2());
		ptmt.execute();
	}

	public static void delParkingincident(String[] id) throws SQLException {

		Connection conn = DBConn.getConnection();
		int n = id.length;

		for (int i = 0; i < n; i++) {

			int tempId = Integer.parseInt(id[i]);

			String sql = "delelte from parking_incident where id = ?";
			String sub_sql = "delete from sub_incident where sub_event_id = ?";
			String sub_person_sql = "select sub_id from sub_incident where sub_event_id = ?";
			String person_sql = "delelte from parking_person where have_car_id = ？";

			PreparedStatement ptmt = conn.prepareStatement(sql);
			PreparedStatement ptmt_sub = conn.prepareStatement(sub_sql);
			PreparedStatement ptmt_sub_person_id = conn.prepareStatement(sub_person_sql);
			PreparedStatement ptmt_person = conn.prepareStatement(person_sql);

			List<Integer> result = new ArrayList<Integer>();

			ptmt.setInt(1, tempId);
			ptmt_sub.setInt(1, tempId);
			ptmt_sub_person_id.setInt(1, tempId);

			ResultSet rs = ptmt_sub_person_id.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt(1));
			}

			for (int j = 0; j < result.size(); j++) {
				ptmt_person.setInt(1, result.get(j));
				ptmt_person.execute();
			}

			ptmt_sub.execute();
			ptmt.execute();

		}
	}

	public static List<Parkingincident> getOne(Integer id) throws SQLException {
		
		List<Parkingincident> result = new LinkedList<Parkingincident>();
		
		Connection conn = DBConn.getConnection();
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where id = ?");
		
		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setInt(1, id);
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {

			Parkingincident park = new Parkingincident();

			park.setId(rs.getInt("id"));
			//park.setId(rs.getInt("get_person_id"));
			park.setParking_year(rs.getInt("parking_year"));
			park.setParking_state(rs.getString("parking_state"));
			park.setParking_attack_now(rs.getFloat("parking_attack_now"));
			park.setParking_catch(rs.getFloat("parking_catch"));
			park.setParking_attack_past(rs.getFloat("parking_attack_past"));
			park.setParking_coverage(rs.getFloat("parking_coverage"));
			park.setParking_density(rs.getFloat("parking_density"));
			park.setParking_time(rs.getFloat("parking_time"));
			park.setParking_distance1(rs.getFloat("parking_distance1"));
			park.setParking_distance2(rs.getFloat("parking_distance2"));

			result.add(park);
		}
		
		return result;
	}
	
	public static List<Parkingincident> getAll() throws SQLException {

		List<Parkingincident> result = new LinkedList<Parkingincident>();

		Connection conn = DBConn.getConnection();
		String sql = "select * from parking_incident order by parking_year desc";
		
		PreparedStatement ptmt = conn.prepareStatement(sql);
		ResultSet rs = ptmt.executeQuery();

		while (rs.next()) {

			Parkingincident park = new Parkingincident();

			park.setId(rs.getInt("id"));
			//park.setId(rs.getInt("get_person_id"));
			park.setParking_year(rs.getInt("parking_year"));
			park.setParking_state(rs.getString("parking_state"));
			park.setParking_attack_now(rs.getFloat("parking_attack_now"));
			park.setParking_catch(rs.getFloat("parking_catch"));
			park.setParking_attack_past(rs.getFloat("parking_attack_past"));
			park.setParking_coverage(rs.getFloat("parking_coverage"));
			park.setParking_density(rs.getFloat("parking_density"));
			park.setParking_time(rs.getFloat("parking_time"));
			park.setParking_distance1(rs.getFloat("parking_distance1"));
			park.setParking_distance2(rs.getFloat("parking_distance2"));

			result.add(park);
		}

		return result;
	}

	public static List<Parkingincident> getAll(String state) throws SQLException {

		List<Parkingincident> result = new LinkedList<Parkingincident>();

		Connection conn = DBConn.getConnection();
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where parking_state = ? order by parking_year desc");

		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setString(1, state);
		ResultSet rs = ptmt.executeQuery();

		while (rs.next()) {

			Parkingincident park = new Parkingincident();

			park.setId(rs.getInt("id"));
			//park.setId(rs.getInt("get_person_id"));
			park.setParking_year(rs.getInt("parking_year"));
			park.setParking_state(rs.getString("parking_state"));
			park.setParking_attack_now(rs.getFloat("parking_attack_now"));
			park.setParking_catch(rs.getFloat("parking_catch"));
			park.setParking_attack_past(rs.getFloat("parking_attack_past"));
			park.setParking_coverage(rs.getFloat("parking_coverage"));
			park.setParking_density(rs.getFloat("parking_density"));
			park.setParking_time(rs.getFloat("parking_time"));
			park.setParking_distance1(rs.getFloat("parking_distance1"));
			park.setParking_distance2(rs.getFloat("parking_distance2"));

			result.add(park);
		}

		return result;
	}
	
	public static List<Parkingincident> getYear(Integer year) throws SQLException {
		
		List<Parkingincident> result = new LinkedList<Parkingincident>();
		
		Connection conn = DBConn.getConnection();
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where parking_year = ? order by parking_year desc");
		
		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setInt(1, year);
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {
			
			Parkingincident park = new Parkingincident();

			park.setId(rs.getInt("id"));
			//park.setId(rs.getInt("get_person_id"));
			park.setParking_year(rs.getInt("parking_year"));
			park.setParking_state(rs.getString("parking_state"));
			park.setParking_attack_now(rs.getFloat("parking_attack_now"));
			park.setParking_catch(rs.getFloat("parking_catch"));
			park.setParking_attack_past(rs.getFloat("parking_attack_past"));
			park.setParking_coverage(rs.getFloat("parking_coverage"));
			park.setParking_density(rs.getFloat("parking_density"));
			park.setParking_time(rs.getFloat("parking_time"));
			park.setParking_distance1(rs.getFloat("parking_distance1"));
			park.setParking_distance2(rs.getFloat("parking_distance2"));

			result.add(park);
		}
		
		return result;
	}
	
	//查询某个人口密度大于等于某个值的所有地点
	public static List<Parkingincident> getDensity(Float density) throws SQLException {
		
		List<Parkingincident> result = new LinkedList<Parkingincident>();
		
		Connection conn = DBConn.getConnection();
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where parking_density >= ? order by parking_year desc");
		
		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setFloat(1, density);
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {
			
			Parkingincident park = new Parkingincident();

			park.setId(rs.getInt("id"));
			//park.setId(rs.getInt("get_person_id"));
			park.setParking_year(rs.getInt("parking_year"));
			park.setParking_state(rs.getString("parking_state"));
			park.setParking_attack_now(rs.getFloat("parking_attack_now"));
			park.setParking_catch(rs.getFloat("parking_catch"));
			park.setParking_attack_past(rs.getFloat("parking_attack_past"));
			park.setParking_coverage(rs.getFloat("parking_coverage"));
			park.setParking_density(rs.getFloat("parking_density"));
			park.setParking_time(rs.getFloat("parking_time"));
			park.setParking_distance1(rs.getFloat("parking_distance1"));
			park.setParking_distance2(rs.getFloat("parking_distance2"));

			result.add(park);
		}
		
		return result;
	}
	
	//查询巡逻概率位于某个区间的所有地点
	public static List<Parkingincident> getCoverage(Float coverage1, Float coverage2) throws SQLException {
		
		List<Parkingincident> result = new LinkedList<Parkingincident>();
		
		Connection conn = DBConn.getConnection();
		StringBuilder build = new StringBuilder();
		build.append("select * from parking_incident where parking_coverage between ? and ? order by parking_year desc");
		
		PreparedStatement ptmt = conn.prepareStatement(build.toString());
		ptmt.setFloat(1, coverage1);
		ptmt.setFloat(2, coverage2);
		ResultSet rs = ptmt.executeQuery();
		
		while (rs.next()) {
			
			Parkingincident park = new Parkingincident();

			park.setId(rs.getInt("id"));
			//park.setId(rs.getInt("get_person_id"));
			park.setParking_year(rs.getInt("parking_year"));
			park.setParking_state(rs.getString("parking_state"));
			park.setParking_attack_now(rs.getFloat("parking_attack_now"));
			park.setParking_catch(rs.getFloat("parking_catch"));
			park.setParking_attack_past(rs.getFloat("parking_attack_past"));
			park.setParking_coverage(rs.getFloat("parking_coverage"));
			park.setParking_density(rs.getFloat("parking_density"));
			park.setParking_time(rs.getFloat("parking_time"));
			park.setParking_distance1(rs.getFloat("parking_distance1"));
			park.setParking_distance2(rs.getFloat("parking_distance2"));

			result.add(park);
		}
		
		return result;
	}
	
	//为了直接查询人物信息
	public static Integer getStateId(Integer getPerId) throws SQLException {
		
		Connection conn = DBConn.getConnection();
		int get_subId = 0;
		int get_stateId = 0;
		
		String sql = "select have_car_id from parking_person where person_id = ?";
		String state_sql = "select sub_event_id from sub_incident where sub_id = ?";
		
		PreparedStatement ptmt = conn.prepareStatement(sql);
		PreparedStatement ptmt_sub = conn.prepareStatement(state_sql);
		
		ptmt.setInt(1, getPerId);
		ResultSet rs = ptmt.executeQuery();
		while (rs.next()) {
			get_subId = rs.getInt(1);
		}
		
		ptmt_sub.setInt(1, get_subId);
		ResultSet rs_state = ptmt.executeQuery();
		while (rs_state.next()) {
			get_stateId = rs_state.getInt(1);
		}
		
		return get_stateId;
	}
	
}
