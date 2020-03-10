package presentacion;


import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Calendar;
import java.util.Date;

import logica.Factory;
import logica.InterfazControladorProgramaFormacion;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class CrearProgramaDeFormacion extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JDateChooser fechaInicio;
	private JDateChooser fechaFin;
	private JTextPane textPane;

	/**
	 * Create the frame.
	 * @throws PropertyVetoException 
	 */
	public CrearProgramaDeFormacion() throws PropertyVetoException {
		setResizable(false);
		setClosable(true);
		setBounds(100, 100, 572, 448);
		setTitle("Crear Programa de Formación");
		setVisible(true);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("PopupMenu.border"), "Ingresar datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 538, 156);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		fechaFin = new JDateChooser();
		fechaFin.setBounds(87, 122, 189, 19);
		panel.add(fechaFin);
		
		fechaInicio = new JDateChooser();
		fechaInicio.setBounds(87, 79, 189, 19);
		panel.add(fechaInicio);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(12, 35, 70, 15);
		panel.add(lblNewLabel);
		
		JLabel lblInicio = new JLabel("Inicio:");
		lblInicio.setBounds(12, 79, 57, 15);
		panel.add(lblInicio);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(87, 33, 189, 19);
		panel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblFin = new JLabel("Fin:");
		lblFin.setBounds(12, 122, 41, 15);
		panel.add(lblFin);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(423, 379, 114, 25);
		getContentPane().add(btnCancelar);
		setVisible(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(280, 379, 114, 25);
		getContentPane().add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmdCrearProgramaFormacion(arg0);
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Descripci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 180, 538, 144);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 22, 514, 110);
		panel_1.add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		setVisible(true);
	
	}
	
	protected void cmdCrearProgramaFormacion(ActionEvent arg0) {
		// Verifica campos vacíos.
		boolean ok = true;
		String nombre = this.textFieldNombre.getText();
		if (nombre.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Nombre vacío");
		}
		Date dateInicio = this.fechaInicio.getDate();
		if (dateInicio == null) {
			JOptionPane.showMessageDialog(this, "ERROR: Fecha de inicio vacía");
			ok = false;
		}
		Date dateFin = this.fechaFin.getDate();
		if (dateFin == null) {
			JOptionPane.showMessageDialog(this, "ERROR: Fecha fin vacía");
			ok = false;
		}
		String descripcion = textPane.getText();
		if (descripcion.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ERROR: Descripción vacía");
			ok = false;
		}
		
		if (ok) {
			Calendar inicio = Calendar.getInstance();
			inicio.setTime(dateInicio);
			Calendar fin = Calendar.getInstance();
			fin.setTime(dateFin);
			// Verifica precedencia de fechas.
			if (fin.before(inicio)) {
				JOptionPane.showMessageDialog(this, "ERROR: fecha de fin anterior a fecha de inicio");
				ok = false;
			}
			if (ok) {
				Factory factory = new Factory();
				InterfazControladorProgramaFormacion icpf = factory.getInterfazControladorProgramaFormacion();
				boolean creo = icpf.crearPrograma(nombre, descripcion, inicio, fin);
				if (creo) {
					limpiar();
					JOptionPane.showMessageDialog(this, "Programa creado");
				} else {
					JOptionPane.showMessageDialog(this, "Ya existe un programa con ese nombre");
				}
			}
		}
	}
	
	public void limpiar() {
		textFieldNombre.setText("");
		fechaInicio.setDate(null);
		fechaFin.setDate(null);
		textPane.setText("");
	}
}
