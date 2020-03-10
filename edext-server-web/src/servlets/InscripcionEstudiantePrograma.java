package servlets;

import java.io.IOException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;



/**
 * Servlet implementation class InscripcionEstudiantePrograma
 */
@WebServlet("/api/InscripcionEstudiantePrograma")
public class InscripcionEstudiantePrograma extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscripcionEstudiantePrograma() {
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
		
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
		String jString =gson.toJson(jsonObject);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta="";
		if(session.getAttribute("Nickname")!=null) {			
			respuesta = port.inscripcionEstudianteProgramaPost(jString,(String)session.getAttribute("Nickname"),(String)session.getAttribute("Tipo"));
		}else {
			respuesta = port.inscripcionEstudianteProgramaPost(jString,"","");
		}
		response.getWriter().println(respuesta);

		
		
		
		
		
	}

}
