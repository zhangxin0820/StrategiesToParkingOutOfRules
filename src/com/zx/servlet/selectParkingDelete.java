package com.zx.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zx.dao.ParkingincidentDao;

/**
 * Servlet implementation class selectParkingDelete
 */
@WebServlet("/selectParkingDelete")
public class selectParkingDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public selectParkingDelete() {
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
		//doGet(request, response);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String parking_state = request.getParameter("parking_state").toString();
		String parking_year = request.getParameter("parking_year").toString();
		String parking_density = request.getParameter("parking_density").toString();
		String parking_coverage1 = request.getParameter("parking_coverage1").toString();
		String parking_coverage2 = request.getParameter("parking_coverage2").toString();
		String state = request.getParameter("state").toString();
		
		String del = request.getParameter("ck");
		
		if (del != null) {
			
			String[] delName = del.split(",");
			try {
				ParkingincidentDao.delParkingincident(delName);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.sendRedirect("selectParking.jsp?parking_state" + parking_state + "&parking_year=" 
							+ parking_year + "&parking_density=" + parking_density + "&parking_coverage1="
							+ parking_coverage1 + "&parking_coverage2=" + parking_coverage2
							+ "&state=" + state);
		
	}

}
