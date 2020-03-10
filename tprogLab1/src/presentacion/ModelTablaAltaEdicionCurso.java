package presentacion;

import javax.swing.table.DefaultTableModel;

public class ModelTablaAltaEdicionCurso extends DefaultTableModel{
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
	       if (column == 2)
	          return true;
	       return false;
	   }
}
