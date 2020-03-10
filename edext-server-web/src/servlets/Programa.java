package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Programa
 */
@WebServlet("/api/Programa")
public class Programa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Programa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Configuracion inicial
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();

    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonParams = gson.toJson(request.getParameterMap(), Map.class);
			servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
			servidor.Publicador port = service.getPublicadorPort();
			String jsonRespuesta = port.programaDoGet(jsonParams);
			out.println(jsonRespuesta);
			
			out.flush();
		} catch (Exception e) {
			JsonObject jsonRespuesta = new JsonObject();
			jsonRespuesta.addProperty("ERROR", e.getMessage());
			response.getWriter().println(jsonRespuesta);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.addHeader("Access-Control-Allow-Credentials", "true");
			request.setCharacterEncoding("UTF-8");
			PrintWriter respuesta = response.getWriter();
			BufferedReader reader = request.getReader();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject jsonRequest = gson.fromJson(reader, JsonObject.class);
			servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
			servidor.Publicador port = service.getPublicadorPort();
			String jsonRespuesta = port.programaDoPost(jsonRequest.toString());
			
			respuesta.println(jsonRespuesta);
		} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			response.getWriter().println(jsonError);
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {			
			req.setCharacterEncoding("UTF-8");
			PrintWriter respuesta = resp.getWriter();
			BufferedReader reader = req.getReader();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject jsonRequest = gson.fromJson(reader, JsonObject.class);
			servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
			servidor.Publicador port = service.getPublicadorPort();
			String jsonRespuesta = port.programaDoPut(jsonRequest.toString());
			
			respuesta.println(jsonRespuesta);
		} catch (Exception e) {
			JsonObject jsonError = new JsonObject();
			jsonError.addProperty("ERROR", e.getMessage());
			resp.getWriter().println(jsonError);
		}
	}

}
