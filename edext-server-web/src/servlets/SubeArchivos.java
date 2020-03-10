package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;







/**
 * Servlet implementation class SubeArchivos
 */
@WebServlet("/api/SubeArchivos")
@MultipartConfig
public class SubeArchivos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubeArchivos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Credentials", "true");
		request.setCharacterEncoding("UTF-8");
		if(ServletFileUpload.isMultipartContent(request)) {
			Part filePart = request.getPart("file");
			String fileName = request.getParameter("nombre");
			String pathString = "/ens/devel01/tpgr12/apache-tomcat-001/images";
			File pathFile = new File(pathString);
			if(!pathFile.exists())
				pathFile.mkdir();
			InputStream input = filePart.getInputStream();
			File fileOut = new File(pathString+File.separator+ fileName);
			OutputStream outputStream = new FileOutputStream(fileOut);
	        
	        int read = 0;
	        byte[] bytes = new byte[1024];
	        while ((read = input.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	        }
		}
		
	}

}
