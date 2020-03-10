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
 * Servlet implementation class Login
 */
@WebServlet("/api/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		HttpSession session = request.getSession();
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		String respuesta;
		if(session.getAttribute("Nickname")!=null) {
			jsonObject.addProperty("nickname", (String) session.getAttribute("Nickname"));
			String jString =gson.toJson(jsonObject);
			servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
			servidor.Publicador port = service.getPublicadorPort();
			respuesta = port.usuarioDoGet(jString);
			JsonObject jObject = gson.fromJson(respuesta,JsonObject.class);
			jObject.addProperty("tipo", (String) session.getAttribute("Tipo"));
			respuesta = gson.toJson(jObject);
		}else {
			JsonObject jsonObject2 = new JsonObject();
			jsonObject2.addProperty("ERROR", "No esta logeado");
			respuesta = jsonObject2.toString();
		}
		response.getWriter().println(respuesta);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		HttpSession session = request.getSession(true);
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
		String jString =gson.toJson(jsonObject);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		String respuesta = port.loginPost(jString);
		JsonObject jsonObject2 = gson.fromJson(respuesta, JsonObject.class);
		if(!jsonObject2.has("ERROR")) {
			session.setAttribute("Nickname", jsonObject2.get("nickname").getAsString());
			session.setAttribute("Tipo", jsonObject2.get("tipo").getAsString());
		}
	
		
		response.getWriter().println(respuesta);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getSession().invalidate();
	}


}
