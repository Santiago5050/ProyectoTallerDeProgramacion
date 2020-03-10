package servlets;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/api/ValidarDatos")
public class ValidarDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ValidarDatos() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
    	servidor.Publicador port = service.getPublicadorPort();
    	String map = gson.toJson(request.getParameterMap());
    	String respuesta = port.validacionDoGet(map);
    	response.getWriter().println(respuesta);
	}

}
