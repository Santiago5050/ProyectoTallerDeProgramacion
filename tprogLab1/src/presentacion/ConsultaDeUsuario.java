package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import excepciones.UsuarioNoExisteException;
import logica.DtDocente;
import logica.DtEstudiante;
import logica.DtUsuario;
import logica.Factory;
import logica.InterfazControladorUsuario;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class ConsultaDeUsuario extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JComboBox<String> comboBoxUsuarios;
	private JComboBox<String> comboBoxEdiciones;
	private JComboBox<String> comboBoxProgramas;


	/**
	 * Create the frame.
	 * @throws PropertyVetoException 
	 */
	public ConsultaDeUsuario() throws PropertyVetoException {
		setBounds(100, 100, 634, 447);
		setTitle("Consulta de Usuario");
		setVisible(true);
		setResizable(false);
		setClosable(true);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 600, 60);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUsuarios = new JLabel("Usuarios:");
		lblUsuarios.setBounds(100, 24, 85, 24);
		panel.add(lblUsuarios);
		
		comboBoxUsuarios = new JComboBox<String>();
		comboBoxUsuarios.setBounds(203, 24, 247, 24);
		panel.add(comboBoxUsuarios);
		cargarComboUsuarios();
		comboBoxUsuarios.setSelectedItem(null);
		comboBoxUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				desplegarInformacion();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 84, 600, 133);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(12, 32, 92, 15);
		panel_1.add(lblNickname);
		
		textField = new JTextField();
		textField.setBounds(122, 30, 172, 19);
		panel_1.add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		
		textField_1 = new JTextField();
		textField_1.setBounds(416, 30, 172, 19);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setEditable(false);
		
		textField_2 = new JTextField();
		textField_2.setBounds(122, 61, 172, 19);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setEditable(false);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(122, 92, 172, 19);
		panel_1.add(textField_3);
		textField_3.setEditable(false);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(416, 61, 172, 19);
		panel_1.add(textField_4);
		textField_4.setEditable(false);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(416, 92, 172, 19);
		panel_1.add(textField_5);
		textField_5.setEditable(false);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(12, 63, 92, 15);
		panel_1.add(lblNombre);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(318, 32, 80, 15);
		panel_1.add(lblMail);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(318, 63, 80, 15);
		panel_1.add(lblApellido);
		
		JLabel lblInstituto = new JLabel("Instituto:");
		lblInstituto.setBounds(318, 94, 80, 15);
		panel_1.add(lblInstituto);
		
		JLabel lblNacimiento = new JLabel("Nacimiento:");
		lblNacimiento.setBounds(12, 94, 92, 15);
		panel_1.add(lblNacimiento);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(498, 378, 114, 25);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(372, 378, 114, 25);
		getContentPane().add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmdAceptar();
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Consultar informaci\u00F3n adicional", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 229, 600, 133);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblEdicionesDeCurso = new JLabel("Ediciones de Curso:");
		lblEdicionesDeCurso.setBounds(12, 38, 165, 15);
		panel_2.add(lblEdicionesDeCurso);
		
		JLabel lblProgramasDeFormacin = new JLabel("Programas de Formación:");
		lblProgramasDeFormacin.setBounds(12, 77, 196, 15);
		panel_2.add(lblProgramasDeFormacin);
		
		comboBoxEdiciones = new JComboBox<String>();
		comboBoxEdiciones.setBounds(264, 33, 260, 24);
		panel_2.add(comboBoxEdiciones);
		comboBoxEdiciones.setEnabled(false);
		comboBoxEdiciones.setSelectedItem(null);
		
		comboBoxProgramas = new JComboBox<String>();
		comboBoxProgramas.setBounds(264, 72, 260, 24);
		panel_2.add(comboBoxProgramas);
		comboBoxProgramas.setEnabled(false);
		comboBoxProgramas.setSelectedItem(null);

	}
	
	protected void cargarComboUsuarios() {
		Factory f = new Factory();
		InterfazControladorUsuario icu = f.getInterfazControladorUsuario();		
		try {
			List<String> itemsCombo = icu.listarUsuarios();
			for (int item = 0; item < itemsCombo.size(); item++) {
				comboBoxUsuarios.addItem(itemsCombo.get(item));
			}
		} catch (Exception e) {
			comboBoxUsuarios.setEnabled(false);
			JOptionPane.showMessageDialog(this, "No hay usuarios en el sistema");
			dispose();
		}
	}
	
	protected void desplegarInformacion() {
		String nickname = comboBoxUsuarios.getSelectedItem().toString();
		Factory f = new Factory();
		InterfazControladorUsuario icu = f.getInterfazControladorUsuario();
		try {
			DtUsuario informacion = icu.informacionDeUsuario(nickname);
			textField.setText(informacion.getNickname());
			textField_1.setText(informacion.getMail());
			textField_2.setText(informacion.getNombre());
			String fecha = informacion.getFechaNacimiento().get(Calendar.DAY_OF_MONTH) + "/" + (informacion.getFechaNacimiento().get(Calendar.MONTH)+1) + "/" + informacion.getFechaNacimiento().get(Calendar.YEAR);
			textField_3.setText(fecha);
			textField_4.setText(informacion.getApellido());
			if (informacion instanceof DtEstudiante) {
				// Limpia el instituto.
				textField_5.setText("");
				// Carga las Ediciones de Curso en el combobox.
				if(!((DtEstudiante) informacion).getEdiciones().isEmpty()) {
					comboBoxEdiciones.setEnabled(true);
					comboBoxEdiciones.setModel(new DefaultComboBoxModel(((DtEstudiante) informacion).getEdiciones().toArray()));
					comboBoxEdiciones.setSelectedItem(null);
				} else {
					comboBoxEdiciones.setEnabled(false);
					comboBoxEdiciones.setSelectedItem(null);
				}
				// Carga los Programas de Formación en el combobox.
				if (!((DtEstudiante) informacion).getProgramas().isEmpty()) {
					comboBoxProgramas.setEnabled(true);
					comboBoxProgramas.setModel(new DefaultComboBoxModel(((DtEstudiante) informacion).getProgramas().toArray()));
					comboBoxProgramas.setSelectedItem(null);
				} else {
					comboBoxProgramas.setEnabled(false);
					comboBoxProgramas.setSelectedItem(null);
				}
			} else if (informacion instanceof DtDocente ){
				textField_5.setText(((DtDocente) informacion).getInstituto());
				// Carga las Ediciones de Curso en el combobox.
				if (!((DtDocente) informacion).getEdiciones().isEmpty()) {
					comboBoxEdiciones.setEnabled(true);
					comboBoxEdiciones.setModel(new DefaultComboBoxModel(((DtDocente) informacion).getEdiciones().toArray()));
					comboBoxEdiciones.setSelectedItem(null);
				} else {
					comboBoxEdiciones.setEnabled(false);
					comboBoxEdiciones.setSelectedItem(null);
				}
			}
		} catch (UsuarioNoExisteException e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
		}
	}
	
	protected void cmdAceptar() {
		if ((comboBoxEdiciones.getSelectedItem() != null) && (comboBoxProgramas.getSelectedItem() != null)) {
			JOptionPane.showMessageDialog(this, "ERROR: Elija una edición o un curso a la vez");
			comboBoxEdiciones.setSelectedItem(null);
			comboBoxProgramas.setSelectedItem(null);
		} else if ((comboBoxEdiciones.getSelectedItem() == null) && (comboBoxProgramas.getSelectedItem() == null)) {
			JOptionPane.showMessageDialog(this, "ERROR: Elija al menos una edición o un programa");
		} else {
			// Acá va la invocación a los otros casos de uso.
			MainWindow mWindow = (MainWindow) SwingUtilities.getWindowAncestor(getContentPane());
			if (comboBoxEdiciones.getSelectedItem() != null) {
				mWindow.cargarEdicion(comboBoxEdiciones.getSelectedItem().toString());
			} else if (comboBoxProgramas.getSelectedItem() != null) {
				mWindow.cargarPrograma(comboBoxProgramas.getSelectedItem().toString());
			}
		}
	}
}
