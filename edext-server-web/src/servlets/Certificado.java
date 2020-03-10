package servlets;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



/**
 * Servlet implementation class Certificado
 */
@WebServlet("/api/Certificado")
public class Certificado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Certificado() {
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
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String paramsString = gson.toJson(request.getParameterMap(), Map.class);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://"+getServletContext().getInitParameter("webserviceip")+":"+getServletContext().getInitParameter("webserviceport")+"/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		HttpSession session= request.getSession();
		String respuesta;
		if(session.getAttribute("Nickname")!=null)
			respuesta = port.aproboPrograma(paramsString, (String) session.getAttribute("Nickname"));
		else
			respuesta = port.aproboPrograma(paramsString, "");
		response.getWriter().write(respuesta);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
		String jString =gson.toJson(jsonObject);
		servidor.PublicadorService service = new servidor.PublicadorService(new URL("http://" + getServletContext().getInitParameter("webserviceip") + ":" + getServletContext().getInitParameter("webserviceport") + "/publicador?wsdl"));
		servidor.Publicador port = service.getPublicadorPort();
		HttpSession session= request.getSession();
		String respuesta = port.aproboPrograma(jString, (String) session.getAttribute("Nickname"));
		
		
		JsonObject resJsonObject =gson.fromJson(respuesta, JsonObject.class);
		Document document = new Document();
		ByteArrayOutputStream pdf = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, pdf).setInitialLeading(20);
			document.open();
			Paragraph title = new Paragraph("Certificado Aprobación de Programa",FontFactory.getFont("arial", 22, Font.ITALIC));
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);
			Calendar cale = gson.fromJson(resJsonObject.get("fechaAprobacion"), Calendar.class);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dat = sdf.format(cale.getTime());
			document.add(new Paragraph("En la fecha: " + dat));
			document.add(new Paragraph("Se certifica que el alumno:"));
			document.add(new Paragraph(resJsonObject.get("nombre").getAsString()+" "+resJsonObject.get("apellido").getAsString()));
			document.add(new Paragraph("Ha aprobado el programa de formación: " + resJsonObject.get("nomPrograma").getAsString()));
			document.add(new Paragraph("Que consta de los cursos:"));
			PdfPTable tabla = new PdfPTable(3);
			JsonArray jArray = resJsonObject.get("cursos").getAsJsonArray();
			JsonObject jObject = resJsonObject.get("edicionesAprobadas").getAsJsonObject();
			tabla.addCell("Curso");
			tabla.addCell("Edición");
			tabla.addCell("Fecha de Aprobación");
			int i=0;
			for(Map.Entry<String, JsonElement> eds: jObject.entrySet()) {
				tabla.addCell(jArray.get(i).getAsString());
				tabla.addCell(eds.getKey());
				Calendar cal = gson.fromJson(eds.getValue(), Calendar.class);
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
				String date = sdf2.format(cal.getTime());
				tabla.addCell(date);
				i++;
				
			}
			document.add(new Paragraph("\n\n"));
			document.add(tabla);
			document.close();
			response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(pdf.size());
			OutputStream oStream = response.getOutputStream();
			pdf.writeTo(oStream);
			oStream.flush();
			oStream.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
