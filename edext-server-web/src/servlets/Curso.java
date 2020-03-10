package servlets;

import java.io.IOException;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;

import com.google.gson.JsonObject;

import java.net.URL;


/**
 * Servlet implementation class Curso
 */
@WebServlet("/api/Curso")
public class Curso extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Curso() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		
		Gson gson = new Gson();
		String paramsString = gson.toJson(request.getParameterMap(), Map.class);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta = port.cursoGet(paramsString);
		response.getWriter().println(respuesta);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
		String jString =gson.toJson(jsonObject);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta = port.cursoPost(jString);
		response.getWriter().println(respuesta);
		

		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		JsonObject jsonObject=gson.fromJson(req.getReader(), JsonObject.class);
		String jString =gson.toJson(jsonObject);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta;
		if(jsonObject.has("Rating")) {
			if(req.getSession().getAttribute("Nickname")!=null)
				respuesta = port.cursoPut(jString, (String) req.getSession().getAttribute("Nickname"));
			else
				respuesta= port.cursoPut(jString, "");
			
		}else {
			respuesta = port.cursoVisita(jString);
		}
		resp.getWriter().println(respuesta);
	}

}
