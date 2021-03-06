package com.zx.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zx.dao.ParkingpersonDao;
import com.zx.model.Parkingperson;

/**
 * Servlet implementation class addparking_person
 */
@WebServlet("/addparking_person")
public class addparking_person extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addparking_person() {
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
		request.setCharacterEncoding("utf-8");
		
		//int person_id = Integer.parseInt(request.getParameter("person_id"));
		int have_car_id = Integer.parseInt(request.getParameter("have_car_id"));
		String person_name = new String(request.getParameter("person_name").toString());
		
		String tempparking_date = new String(request.getParameter("parking_date").toString());
		Date parking_date = Date.valueOf(tempparking_date);
		
		String person_gender = new String(request.getParameter("person_gender").toString());
		int person_age = Integer.parseInt(request.getParameter("person_age"));
		String person_nationality = new String(request.getParameter("person_nationality").toString());
		
		String tempperson_birthday = new String(request.getParameter("person_birthday").toString());
		Date person_birthday = Date.valueOf(tempperson_birthday);
		
		String person_job = new String(request.getParameter("person_job").toString());
		String person_education = new String(request.getParameter("person_education").toString());
		String person_hkadr = new String(request.getParameter("person_hkadr").toString());
		int person_height = Integer.parseInt(request.getParameter("person_height"));
		int person_weight = Integer.parseInt(request.getParameter("person_weight"));
		String person_nativeplace = new String(request.getParameter("person_nativeplace").toString());
		String person_maritalstatus = new String(request.getParameter("person_maritalstatus").toString());
		String person_politicalstatus = new String(request.getParameter("person_politicalstatus").toString());
		
		Parkingperson parkingperson = new Parkingperson();
		
		//parkingperson.setPerson_id(person_id);
		parkingperson.setHave_car_id(have_car_id);
		parkingperson.setParking_date(parking_date);
		parkingperson.setPerson_name(person_name);
		parkingperson.setPerson_gender(person_gender);
		parkingperson.setPerson_age(person_age);
		parkingperson.setPerson_nationality(person_nationality);
		parkingperson.setPerson_birthday(person_birthday);
		parkingperson.setPerson_job(person_job);
		parkingperson.setPerson_education(person_education);
		parkingperson.setPerson_hkadr(person_hkadr);
		parkingperson.setPerson_height(person_height);
		parkingperson.setPerson_weight(person_weight);
		parkingperson.setPerson_nativeplace(person_nativeplace);
		parkingperson.setPerson_maritalstatus(person_maritalstatus);
		parkingperson.setPerson_politicalstatus(person_politicalstatus);
		
		try {
			ParkingpersonDao.addParkingpersonAll(parkingperson);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("showParking_personInfo.jsp?id = " + have_car_id);
		
		//doGet(request, response);
	}

}
