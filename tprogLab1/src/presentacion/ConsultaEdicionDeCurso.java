package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.util.List;

import excepciones.CursoNoExisteException;
import excepciones.EdicionNoExisteException;
import excepciones.InstitutoNoExisteException;
import logica.DtEdicion;
import logica.Factory;
import logica.InterfazControladorCurso;

public class ConsultaEdicionDeCurso extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldNombre;
	private JTextField textFieldInicio;
	private JTextField textFieldFin;
	private JTextField textFieldPublicacion;
	private JTextField textFieldCupo;
	private JTextField textFieldVigencia;
	private JComboBox<String> comboBoxInstituto;
	private JComboBox<String> comboBoxCurso;
	private JComboBox<String> comboBoxEdiciones;
	private JTextField textField;


	

	/**
	 * Create the frame.
	 */
	public ConsultaEdicionDeCurso() throws PropertyVetoException {
		setVisible(true);
		setResizable(false);
		setClosable(true);
		setTitle("Consulta Edicion de Curso");
		setBounds(100, 100, 485, 402);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Seleccionar:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 443, 146);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInstituto = new JLabel("Instituto:");
		lblInstituto.setBounds(12, 30, 94, 15);
		panel.add(lblInstituto);
		
		JLabel lblCurso = new JLabel("Curso:");
		lblCurso.setBounds(12, 68, 94, 15);
		panel.add(lblCurso);
		
		JLabel lblEdiciones = new JLabel("Ediciones:");
		lblEdiciones.setBounds(12, 104, 94, 15);
		panel.add(lblEdiciones);
		
		comboBoxInstituto = new JComboBox<String>();
		comboBoxInstituto.setBounds(145, 25, 235, 24);
		panel.add(comboBoxInstituto);
		cargarInstitutos();
		comboBoxInstituto.setSelectedItem(null);
		comboBoxInstituto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarCursos();
				comboBoxCurso.setEnabled(true);
			}
		});
		
		
		comboBoxCurso = new JComboBox<String>();
		comboBoxCurso.setBounds(145, 63, 235, 24);
		panel.add(comboBoxCurso);
		comboBoxCurso.setEnabled(false);
		comboBoxCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				cargarEdiciones();
				comboBoxEdiciones.setEnabled(true);
			}
		});
		
		comboBoxEdiciones = new JComboBox<String>();
		comboBoxEdiciones.setBounds(145, 99, 235, 24);
		panel.add(comboBoxEdiciones);
		comboBoxEdiciones.setEnabled(false);
		comboBoxEdiciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg2) {
				cargarDatosEdicion();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 162, 443, 169);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 25, 87, 15);
		panel_1.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(134, 23, 295, 19);
		panel_1.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		textFieldNombre.setEnabled(false);
		
		JLabel lblInicio = new JLabel("Inicio:");
		lblInicio.setBounds(10, 63, 60, 15);
		panel_1.add(lblInicio);
		
		textFieldInicio = new JTextField();
		textFieldInicio.setBounds(134, 61, 101, 19);
		panel_1.add(textFieldInicio);
		textFieldInicio.setColumns(10);
		textFieldInicio.setEnabled(false);
		
		JLabel lblFin = new JLabel("Fin:");
		lblFin.setBounds(260, 63, 50, 15);
		panel_1.add(lblFin);
		
		textFieldFin = new JTextField();
		textFieldFin.setColumns(10);
		textFieldFin.setBounds(328, 61, 101, 19);
		panel_1.add(textFieldFin);
		textFieldFin.setEnabled(false);
		
		JLabel lblPublicacin = new JLabel("Publicación:");
		lblPublicacin.setBounds(10, 103, 106, 15);
		panel_1.add(lblPublicacin);
		
		textFieldPublicacion = new JTextField();
		textFieldPublicacion.setColumns(10);
		textFieldPublicacion.setBounds(134, 101, 101, 19);
		panel_1.add(textFieldPublicacion);
		textFieldPublicacion.setEnabled(false);
		
		JLabel lblCupo = new JLabel("Cupo:");
		lblCupo.setBounds(260, 103, 50, 15);
		panel_1.add(lblCupo);
		
		textFieldCupo = new JTextField();
		textFieldCupo.setColumns(10);
		textFieldCupo.setBounds(328, 101, 101, 19);
		panel_1.add(textFieldCupo);
		textFieldCupo.setEnabled(false);
		
		JLabel lblVigencia = new JLabel("Vigencia:");
		lblVigencia.setBounds(10, 137, 60, 14);
		panel_1.add(lblVigencia);
		
		textFieldVigencia = new JTextField();
		textFieldVigencia.setEnabled(false);
		textFieldVigencia.setBounds(134, 134, 295, 20);
		panel_1.add(textFieldVigencia);
		textFieldVigencia.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(341, 336, 114, 25);
		getContentPane().add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg3) {
				setVisible(false);
				dispose();
			}
		});
		
		

	}
	
	protected void cargarInstitutos() {
		Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		List<String> institutos = icc.listarInstitutos();
		if (institutos.isEmpty()) {
			setVisible(false);
			JOptionPane.showMessageDialog(this, "No hay institutos en el sistema");
			dispose();
		} else {
			comboBoxInstituto.setModel(new DefaultComboBoxModel(institutos.toArray()));
			comboBoxInstituto.setSelectedItem(null);
		}
		
	}
	
	protected void cargarCursos() {
		Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		if (comboBoxInstituto.getSelectedItem() != null) {
			String institutoElegido = comboBoxInstituto.getSelectedItem().toString();
			try {
				List<String> cursos = icc.mostrarCursosEnInstituto(institutoElegido);
				comboBoxCurso.setModel(new DefaultComboBoxModel(cursos.toArray()));
				comboBoxCurso.setSelectedItem(null);
				comboBoxEdiciones.setModel(new DefaultComboBoxModel());
				comboBoxEdiciones.setSelectedItem(null);
			} catch (InstitutoNoExisteException | CursoNoExisteException noExisteInst) {
				setVisible(false);
				JOptionPane.showMessageDialog(this, noExisteInst.getMessage());
				dispose();
			}
		}
	}
	
	protected void cargarEdiciones() {
		Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		if (comboBoxCurso.getSelectedItem() != null) {
			String cursoElegido = comboBoxCurso.getSelectedItem().toString();
			try {
				List<String> ediciones = icc.mostrarEdicionesDeCurso(cursoElegido);
				comboBoxEdiciones.setModel(new DefaultComboBoxModel(ediciones.toArray()));
				comboBoxEdiciones.setSelectedItem(null);
			} catch (CursoNoExisteException | EdicionNoExisteException noExiste) {
				setVisible(false);
				JOptionPane.showMessageDialog(this, noExiste.getMessage());
				dispose();
			}
		}
	}
	
	protected void cargarDatosEdicion() {
		Factory f = new Factory();
		InterfazControladorCurso icc = f.getInterfazControladorCurso();
		if ((comboBoxCurso.getSelectedItem() != null) && (comboBoxEdiciones.getSelectedItem() != null)) {
			String cursoElegido = comboBoxCurso.getSelectedItem().toString();
			String edicionElegida = comboBoxEdiciones.getSelectedItem().toString();
			try {
				DtEdicion edicion = icc.darDatosEdicion(cursoElegido, edicionElegida);
				textFieldNombre.setText(edicion.getNombre());
				String cupo = "" + edicion.getCupo();
				textFieldCupo.setText(cupo);
				String inicio = edicion.getFechaInicio().get(Calendar.DAY_OF_MONTH) + "/" + (edicion.getFechaInicio().get(Calendar.MONTH)+1) + "/" + edicion.getFechaInicio().get(Calendar.YEAR);
				textFieldInicio.setText(inicio);
				String fin = edicion.getFechaFin().get(Calendar.DAY_OF_MONTH) + "/" + (edicion.getFechaFin().get(Calendar.MONTH)+1) + "/" + edicion.getFechaFin().get(Calendar.YEAR);
				textFieldFin.setText(fin);
				String publicacion = edicion.getFechaPublicacion().get(Calendar.DAY_OF_MONTH) + "/" + (edicion.getFechaPublicacion().get(Calendar.MONTH)+1) + "/" + edicion.getFechaPublicacion().get(Calendar.YEAR);
				textFieldPublicacion.setText(publicacion);
				boolean esVigente = edicion.getVigencia();
				if (esVigente) {
					textFieldVigencia.setText("Edicion del curso vigente");
				} else {
					textFieldVigencia.setText("Edicion no vigente");
				}
			} catch (CursoNoExisteException | EdicionNoExisteException noExisteDatosEdicion) {
				setVisible(false);
				JOptionPane.showMessageDialog(this, noExisteDatosEdicion.getMessage());
				dispose();
			}
		}
	}
	
	public void cargarEdicion(String s) {
		Factory factory = new Factory();
		InterfazControladorCurso iCurso = factory.getInterfazControladorCurso();
		List<String> instituti = iCurso.listarInstitutos();
		boolean flag=false;
		int j = 0;
		int k = 0;
		String string = "";
		try {
			
			while (!flag && j<instituti.size()) {
				List<String> temp = iCurso.mostrarCursosEnInstituto(instituti.get(j));
				k=0;
				
				while (!flag && k<temp.size()) {
					flag = iCurso.mostrarEdicionesDeCurso(temp.get(k)).contains(s);
					string = temp.get(k);
					k++;
				}
				j++;
			}
			
		} catch (Exception e) {
			dispose();
		}
		if(flag) {
			comboBoxInstituto.setSelectedItem(instituti.get(j-1));
			comboBoxCurso.setSelectedItem(string);
			comboBoxEdiciones.setSelectedItem(s);
			
			comboBoxCurso.setEnabled(false);
			comboBoxEdiciones.setEnabled(false);
			comboBoxInstituto.setEnabled(false);
		}else {
			dispose();
		}
	
		
		
	}
}
