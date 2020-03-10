package presentacion;

import java.util.List;

import javax.swing.table.DefaultTableModel;

public class ModelTablaAgregarCursoPresentacion extends DefaultTableModel{
		private List<String> yaIngresados;
	
	   /** Primera columna Boolean, segunda Integer y el resto Object */
	   public Class getColumnClass(int columna)
	   {
	      if (columna == 2) return Boolean.class;
	      return Object.class;
	   }
	   
	   public boolean isCellEditable (int row, int column)
	   {
	       // Aquí devolvemos true o false según queramos que una celda
	       // identificada por fila,columna (row,column), sea o no editable
	       if (column == 2 && !yaIngresados.contains(this.getValueAt(row, 0).toString()))
	          return true;
	       return false;
	   }
	   
	   public void actualizarYaIngresados(List<String> nuevos) {
		   yaIngresados = nuevos;
	   }
}
