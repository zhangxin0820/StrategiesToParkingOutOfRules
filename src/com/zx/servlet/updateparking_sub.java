package com.zx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zx.dao.SubincidentDao;
import com.zx.model.Subincident;

/**
 * Servlet implementation class updateparking_sub
 */
@WebServlet("/updateparking_sub")
public class updateparking_sub extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateparking_sub() {
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
		
		int sub_id = Integer.parseInt(request.getParameter("sub_id"));
		//int sub_event_id = Integer.parseInt(request.getParameter("sub_event_id"));
		
		String tempcar_date = new String(request.getParameter("car_date").toString());
		Date car_date = Date.valueOf(tempcar_date);
		
		String car_id = new String(request.getParameter("car_id").toString());
		String car_type = new String(request.getParameter("car_type").toString());
		
		Subincident subincident = new Subincident();
		
		subincident.setSub_id(sub_id);
		//subincident.setSub_event_id(sub_event_id);
		subincident.setCar_date(car_date);
		subincident.setCar_id(car_id);
		subincident.setCar_type(car_type);
		
		try {
			SubincidentDao.updateSubincident(subincident);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = "{\"status\":\"更新成功!\"}";
		PrintWriter out = response.getWriter();
		out.write(result);
		
	}

}
