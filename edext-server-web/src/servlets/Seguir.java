package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

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
 * Servlet implementation class Seguir
 */
@WebServlet("/api/Seguir")
public class Seguir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Seguir() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession();	
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.setCharacterEncoding("UTF-8");
			PrintWriter respuesta = response.getWriter();
			BufferedReader reader = request.getReader();

			if(session.getAttribute("Nickname") != null) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonObject jsonRequest = gson.fromJson(reader, JsonObject.class);
				servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
				servidor.Publicador port = service.getPublicadorPort();
				String jsonRespuesta = port.seguirDoPost((String) session.getAttribute("Nickname"), jsonRequest.toString());
				respuesta.println(jsonRespuesta);
			} else {
				JsonObject jsonRespuesta = new JsonObject();
				jsonRespuesta.addProperty("ERROR", "Ha ocurrido un error al obtener la sesión del usuario");
				respuesta.println(jsonRespuesta);
			}
		} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			response.getWriter().println(jsonError);
		}
	}

}
