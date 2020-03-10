package servlets;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



/**
 * Servlet implementation class InscripcionEstudianteEdicion
 */
@WebServlet("/api/InscripcionEstudianteEdicion")
public class InscripcionEstudianteEdicion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscripcionEstudianteEdicion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setContentType("application/json");
		
		Gson gson = new Gson();
		String paramsString = gson.toJson(request.getParameterMap(), Map.class);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta="";
		if(session.getAttribute("Nickname")!=null) {
			respuesta = port.inscricpcionEstudianteEdicionGet(paramsString,(String)session.getAttribute("Nickname"),(String)session.getAttribute("Tipo"));
		}else {
			respuesta = port.inscricpcionEstudianteEdicionGet(paramsString,"","");
		}
		response.getWriter().println(respuesta);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		HttpSession session = request.getSession();
		
		
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
		String jString =gson.toJson(jsonObject);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta="";
		if(session.getAttribute("Nickname")!=null) {
			respuesta = port.inscripcionEstudianteEdicionPost(jString,(String)session.getAttribute("Nickname"),(String)session.getAttribute("Tipo"));
		}else {
			respuesta = port.inscripcionEstudianteEdicionPost(jString,"","");
		}
		
		response.getWriter().println(respuesta);	
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		java.io.PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);
		String enviar = gson.toJson(json);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
    	servidor.Publicador port = service.getPublicadorPort();
    	String respuesta = port.inscripcionEstudianteEdicionPut(enviar, (String) session.getAttribute("Nickname"));
    	out.println(respuesta);
	}

}
