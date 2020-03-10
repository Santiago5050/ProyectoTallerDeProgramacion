package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DtComentario {
		private String id;
		private String nickname;
		private String nombre;
		private String apellido;
		private String comentario;
		private Calendar fechaComentario;
		private String src;
		private int valoracion;
		private List<DtComentario> comentariosRespuesta;
		
		public DtComentario(String id, String nickname, String nombre, String apellido, String comentario, Calendar fechaComentario) {
			this.id = id;
			this.nickname = nickname;
			this.nombre = nombre;
			this.apellido = apellido;
			this.comentario = comentario;
			this.fechaComentario = fechaComentario;
			comentariosRespuesta = new ArrayList<DtComentario>();
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getNombre() {
			return nombre;
		}

		public String getApellido() {
			return apellido;
		}

		public String getComentario() {
			return comentario;
		}

		public Calendar getFechaComentario() {
			return fechaComentario;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public List<DtComentario> getComentariosRespuesta() {
			return comentariosRespuesta;
		}
		
		public void agregarRespuesta(DtComentario nuevo) {
			this.comentariosRespuesta.add(nuevo);
		}
		
		public void setValoracion(int val) {
			this.valoracion = val;
		}
		
		public int getValoracion() {
			return this.valoracion;
		}
		
}
