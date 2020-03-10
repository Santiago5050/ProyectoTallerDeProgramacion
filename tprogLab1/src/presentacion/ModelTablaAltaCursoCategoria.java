package presentacion;

import javax.swing.table.DefaultTableModel;

public class ModelTablaAltaCursoCategoria extends DefaultTableModel{
	   /** Primera columna Boolean, segunda Integer y el resto Object */
	   public Class getColumnClass(int columna)
	   {
	      if (columna == 1) return Boolean.class;
	      return Object.class;
	   }
	   
	   public boolean isCellEditable (int row, int column)
	   {
	       // Aquí devolvemos true o false según queramos que una celda
	       // identificada por fila,columna (row,column), sea o no editable
	       if (column == 1)
	          return true;
	       return false;
	   }
}