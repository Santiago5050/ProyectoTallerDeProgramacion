package servlets;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.*;

@WebServlet("/api/Edicion")
public class Edicion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Edicion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		java.io.PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
    	servidor.Publicador port = service.getPublicadorPort();
    	String map = gson.toJson(request.getParameterMap());
    	String respuesta = port.edicionDoGet(map);
    	out.println(respuesta);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		java.io.PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);
		String enviar = gson.toJson(json);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
    	servidor.Publicador port = service.getPublicadorPort();
    	String respuesta = port.edicionDoPost(enviar);
    	out.println(respuesta);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		java.io.PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        HttpSession session = request.getSession();
		response.addHeader("Access-Control-Allow-Credentials", "true");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject json = gson.fromJson(request.getReader(), JsonObject.class);
		String enviar = gson.toJson(json);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
    	servidor.Publicador port = service.getPublicadorPort();
    	String respuesta = port.edicionDoPut(enviar, (String) session.getAttribute("Nickname"));
    	out.println(respuesta);
	}
	
}
