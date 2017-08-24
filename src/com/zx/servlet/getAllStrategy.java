package com.zx.servlet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zx.dao.GetEmDao;
import com.zx.dao.GetLrDao;

/**
 * Servlet implementation class getAllStrategy
 */
@WebServlet("/getAllStrategy")
public class getAllStrategy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAllStrategy() {
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
		request.setCharacterEncoding("utf-8");
		
		String typeStrategy = request.getParameter("getSituation");
		String typeYear = request.getParameter("getYear");
		
		System.out.println(typeStrategy);

		float[] result = new float[100];

		if (typeStrategy.equals("normal")) {

			int i = 0;
			try {
//				result = GetEmDao.getEM(Integer.parseInt(typeYear));
				BufferedReader bf = new BufferedReader(new FileReader("/Users/zhangxin/Desktop/EM.txt"));
				String line = null;
				
				while ((line = bf.readLine()) != null && i < 100) {
					result[i] = Float.parseFloat(line);
					i++;
				}
				
				bf.close();
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (typeStrategy.equals("fixed")) {
			
			try {
				result = GetLrDao.letter_recog(Integer.parseInt(typeYear));
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		request.setAttribute("result", result);
		request.getRequestDispatcher("getStrategy.jsp").forward(request, response);
	}

}
