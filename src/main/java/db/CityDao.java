package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import entity.City;

public class CityDao {
	public Connection getConnection() {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			DataSource ds = (DataSource) initContext.lookup("java:comp/env/" + "jdbc/world"); // 뒤에 것을 사용자의 환경에 따라
			conn = ds.getConnection();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public City getCity(int id) {
		
		Connection conn = getConnection();
		String sql = "select * from city where id=?";
		City city = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				city = new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getInt(5));
				
			}
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return city;
	}
	
	public City getCity(String cname) {
		
		Connection conn = getConnection();
		String sql = "select * from city where CountryCode='KOR' AND NAME=?";
		City city = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cname);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				city = new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getInt(5));
				
			}
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return city;
	}
	
	public List<City> getCityList() {
		String sql = "SELECT * FROM city where CountryCode='KOR' ORDER BY population DESC LIMIT 10";
		List<City> list = new ArrayList<City>();
		
		Connection conn = getConnection();
		try {
			Statement  stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new City(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public void insertCity(City city) {
		Connection conn = getConnection();
		String sql = "insert into city values(default, ?, ?, ?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, city.getName());
			pstmt.setString(2, city.getCountryCode());
			pstmt.setString(3, city.getDistrict());
			pstmt.setInt(4, city.getPopulation());
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateCity(City city) {
		Connection conn = getConnection();
		String sql = "update city set name=?, countrycode=?, district=?, population=? where id=?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, city.getName());
			pstmt.setString(2, city.getCountryCode());
			pstmt.setString(3, city.getDistrict());
			pstmt.setInt(4, city.getPopulation());
			pstmt.setInt(5, city.getId());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCity(int id) {
		Connection conn = getConnection();
		String sql = "delete from city where id=?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
