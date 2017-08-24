package com.zx.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;

import com.zx.model.Parkingincident;

/**
 * Servlet implementation class importExcel
 */
@WebServlet("/importExcel")
public class importExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long FILE_MAX_SIZE = 4 * 1024 * 1024;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public importExcel() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		if (!ServletFileUpload.isMultipartContent(request)) {
			System.out.println("失败1");
		}
		String fileTempPath = this.getServletContext().getRealPath("/") + "filetemp";
		File tempDir = new File(fileTempPath);
		if (!tempDir.exists()) {
			tempDir.mkdirs();
		}
		FileItemFactory factory = new DiskFileItemFactory(4096, tempDir);

		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(FILE_MAX_SIZE);

		List<FileItem> fileItems = null;

		try {
			fileItems = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
			if (e instanceof SizeLimitExceededException) {

			}
			return;
		}
		if (fileItems == null || fileItems.size() == 0) {
			System.out.println("失败2");
			return;
		}

		Workbook rwb;
		try {
			rwb = WorkbookFactory.create(fileItems.get(0).getInputStream());
			//int sheetCount = rwb.getNumberOfSheets();
			Sheet rs = rwb.getSheetAt(0);
			int rows = rs.getLastRowNum(); // 行
			int cols = 10; // 列 -- getCell（列，行）
			System.out.println(rows + "-*-*-*-" + cols);
			for (int i = 1; i < rows; i++) {

				for (int j = 0; j < 10; j++) {

					// Cell cell = rs.getCellComment(arg0);
					System.out.print(rs.getCellComment(new CellAddress(i, j)).toString() + "-*-");
				}

				Parkingincident parkingincident = new Parkingincident();
				
				parkingincident.setParking_year(Integer.parseInt(rs.getCellComment(new CellAddress(i, 0)).toString()));
				parkingincident.setParking_state(rs.getCellComment(new CellAddress(i, 1)).toString());
				parkingincident.setParking_attack_now(Float.parseFloat(rs.getCellComment(new CellAddress(i, 2)).toString()));
				parkingincident.setParking_catch(Float.parseFloat(rs.getCellComment(new CellAddress(i, 3)).toString()));
				parkingincident.setParking_attack_past(Float.parseFloat(rs.getCellComment(new CellAddress(i, 4)).toString()));
				parkingincident.setParking_coverage(Float.parseFloat(rs.getCellComment(new CellAddress(i, 5)).toString()));
				parkingincident.setParking_density(Float.parseFloat(rs.getCellComment(new CellAddress(i, 6)).toString()));
				parkingincident.setParking_time(Float.parseFloat(rs.getCellComment(new CellAddress(i, 7)).toString()));
				parkingincident.setParking_distance1(Float.parseFloat(rs.getCellComment(new CellAddress(i, 8)).toString()));
				parkingincident.setParking_distance2(Float.parseFloat(rs.getCellComment(new CellAddress(i, 9)).toString()));
				
				// System.out.print(rs.getCell(1, i).getContents() + "-*-");
				// System.out.print(rs.getCell(2, i).getContents() + "-*-");
				// System.out.print(rs.getCell(3, i).getContents() + "-*-");
				// System.out.print(rs.getCell(4, i).getContents() + "-*-");
				// System.out.print(rs.getCell(5, i).getContents() + "-*-");
				// System.out.print(rs.getCell(6, i).getContents() + "-*-");
				// System.out.print(rs.getCell(7, i).getContents() + "-*-");
				// System.out.print(rs.getCell(8, i).getContents() + "-*-");
				// System.out.print(rs.getCell(9, i).getContents() + "-*-");
				System.out.println();
			}
		} catch (EncryptedDocumentException | InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fileItems.get(0).getInputStream().close();
		}
		
		response.sendRedirect("showParkingInfo.jsp");

	}

}
