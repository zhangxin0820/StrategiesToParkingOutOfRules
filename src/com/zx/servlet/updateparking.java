package com.zx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zx.dao.ParkingincidentDao;
import com.zx.model.Parkingincident;

/**
 * Servlet implementation class updateparking
 */
@WebServlet("/updateparking")
public class updateparking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateparking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		int parking_year = Integer.parseInt(request.getParameter("parking_year"));
		String parking_state = new String(request.getParameter("parking_state"));
		Float parking_attack_now = Float.parseFloat(request.getParameter("parking_attack_now"));
		Float parking_catch = Float.parseFloat(request.getParameter("parking_catch"));
		Float parking_attack_past = Float.parseFloat(request.getParameter("parking_attack_past"));
		Float parking_coverage = Float.parseFloat(request.getParameter("parking_coverage"));
		Float parking_density = Float.parseFloat(request.getParameter("parking_density"));
		Float parking_time = Float.parseFloat(request.getParameter("parking_time"));
		Float parking_distance1 = Float.parseFloat(request.getParameter("parking_distance1"));
		Float parking_distance2 = Float.parseFloat(request.getParameter("parking_distance2"));
		
		Parkingincident parkingincident = new Parkingincident();
		
		parkingincident.setParking_year(parking_year);
		parkingincident.setParking_state(parking_state);
		parkingincident.setParking_attack_now(parking_attack_now);
		parkingincident.setParking_catch(parking_catch);
		parkingincident.setParking_attack_past(parking_attack_past);
		parkingincident.setParking_coverage(parking_coverage);
		parkingincident.setParking_density(parking_density);
		parkingincident.setParking_time(parking_time);
		parkingincident.setParking_distance1(parking_distance1);
		parkingincident.setParking_distance2(parking_distance2);
		parkingincident.setId(id);
		
		try {
			ParkingincidentDao.updateParkingincident(parkingincident);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = "{\"status\":\"更新成功!\"}";
		PrintWriter out = response.getWriter();
		out.write(result);
		
	}

}
