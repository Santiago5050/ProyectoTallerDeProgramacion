package presentacion;


import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import logica.InterfazControladorUsuario;
import logica.Factory;
import javax.swing.JPasswordField;

public class AltaDeUsuario extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JComboBox<String> comboBox;
	private JRadioButton rdbtnDocente;
	private JRadioButton rdbtnEstudiante;
	private JDateChooser dateChooser;
	private JPasswordField passwordFieldClave;
	private JPasswordField passwordFieldRepetirClave;

	/**
	 * Create the frame.
	 * @throws PropertyVetoException 
	 */
	public AltaDeUsuario() throws PropertyVetoException {
		setResizable(false);
		setClosable(true);
		setBounds(100, 100, 568, 432);
		setTitle("Alta de Usuario");
		setVisible(true);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos del usuario:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 12, 536, 329);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nickname:");
		lblNombre.setBounds(12, 42, 91, 15);
		panel.add(lblNombre);
		
		textField = new JTextField();
		textField.setBounds(100, 40, 149, 19);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNombre_1 = new JLabel("Nombre:");
		lblNombre_1.setBounds(12, 81, 66, 15);
		panel.add(lblNombre_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(100, 79, 149, 19);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(12, 120, 78, 15);
		panel.add(lblApellido);
		
		textField_2 = new JTextField();
		textField_2.setBounds(100, 118, 149, 19);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(283, 42, 47, 15);
		panel.add(lblMail);
		
		textField_3 = new JTextField();
		textField_3.setBounds(348, 40, 167, 19);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaDeNacimiento.setBounds(100, 181, 159, 15);
		panel.add(lblFechaDeNacimiento);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(283, 177, 166, 19);
		panel.add(dateChooser);
		
		JLabel lblTipoDeUsuario = new JLabel("Tipo de usuario:");
		lblTipoDeUsuario.setBounds(100, 230, 124, 15);
		panel.add(lblTipoDeUsuario);
		
		rdbtnEstudiante = new JRadioButton("Estudiante");
		rdbtnEstudiante.setBounds(242, 226, 113, 23);
		panel.add(rdbtnEstudiante);
		rdbtnEstudiante.addItemListener(evt -> {
			comboBox.setEnabled(false);
			rdbtnDocente.setSelected(false);
		});
		
		rdbtnDocente = new JRadioButton("Docente");
		rdbtnDocente.setBounds(372, 226, 91, 23);
		panel.add(rdbtnDocente);
		rdbtnDocente.addItemListener(evt -> {
			comboBox.setEnabled(true);
			rdbtnEstudiante.setSelected(false);
		});
		
		comboBox = new JComboBox<String>();
		comboBox.setEnabled(false);
		comboBox.setBounds(244, 267, 205, 24);
		panel.add(comboBox);
		
		JLabel lblRepetirClave = new JLabel("Repetir clave:");
		lblRepetirClave.setBounds(283, 120, 106, 15);
		panel.add(lblRepetirClave);
		
		passwordFieldRepetirClave = new JPasswordField();
		passwordFieldRepetirClave.setBounds(399, 118, 116, 19);
		panel.add(passwordFieldRepetirClave);
		
		JLabel lblClave = new JLabel("Clave:");
		lblClave.setBounds(283, 81, 54, 15);
		panel.add(lblClave);
		
		passwordFieldClave = new JPasswordField();
		passwordFieldClave.setBounds(348, 79, 167, 19);
		panel.add(passwordFieldClave);
		cargarCombo();
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(434, 361, 114, 25);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(308, 361, 114, 25);
		getContentPane().add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmdAltaDeUsuario(arg0);
			}
		});

	}
	
	protected void cmdAltaDeUsuario(ActionEvent arg0) {
		// Obtiene los datos y los chequea.
		boolean ok = true;
		String nickname = this.textField.getText();
		if (nickname.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Nickname vacío");
		}
		String nombre = this.textField_1.getText();
		if (nombre.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Nombre vacío");
		}
		String apellido = this.textField_2.getText();
		if (apellido.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Apellido vacío");
		}
		String mail = this.textField_3.getText();
		if (mail.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Mail vacío");
		}
		@SuppressWarnings("deprecation")
		String password = this.passwordFieldClave.getText();
		if (password.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Clave vacía");
		}
		@SuppressWarnings("deprecation")
		String repetirPassword = this.passwordFieldRepetirClave.getText();
		if (repetirPassword.isEmpty()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Repita la clave");
		}
		if (!password.equals(repetirPassword)) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Las claves no coinciden");
		}
		Date nacimiento = this.dateChooser.getDate();
		if (nacimiento == null) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: Fecha de nacimiento vacía");
		}
		if (!rdbtnDocente.isSelected() && !rdbtnEstudiante.isSelected()) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: No ha elegido un tipo de usuario");
		}
		if (rdbtnDocente.isSelected() && (comboBox.getSelectedItem() == null)) {
			ok = false;
			JOptionPane.showMessageDialog(this, "ERROR: No ha elegido un instituto para el docente");
		}
		
		// Si todo es correcto prosigue.
		if (ok) {
			
			// Convierte Date a Calendar.
			Calendar fechaNacimiento = Calendar.getInstance();
			fechaNacimiento.setTime(nacimiento);
			
			// Invoca a la interfaz para realizar las operaciones necesarias.
			Factory f = new Factory();
			InterfazControladorUsuario icu = f.getInterfazControladorUsuario();
			boolean creo = false;
			if (rdbtnDocente.isSelected()) {
				String instituto = comboBox.getSelectedItem().toString();
				creo = icu.altaDocente(nickname, nombre, apellido, mail, fechaNacimiento, instituto, password);
			} else {
				creo = icu.altaEstudiante(nickname, nombre, apellido, mail, fechaNacimiento, password);
			}
			if (creo) {
				limpiar();
				JOptionPane.showMessageDialog(this, "Usuario creado");
			} else {
				JOptionPane.showMessageDialog(this, "Ya existe un usuario con ese nombre/mail");
			}
		}
	}
	
	public void limpiar() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		dateChooser.setDate(null);
		rdbtnDocente.setSelected(false);
		rdbtnEstudiante.setSelected(false);
		comboBox.setEnabled(false);
		passwordFieldClave.setText("");
		passwordFieldRepetirClave.setText("");
	}
	
	protected void cargarCombo() {	
		Factory f = new Factory();
		InterfazControladorUsuario icu = f.getInterfazControladorUsuario();
		List<String> itemsCombo = icu.listarInstitutos();
		if (itemsCombo.isEmpty()) {
			this.comboBox.setEnabled(false);
			this.rdbtnDocente.setEnabled(false);
		} else {
			comboBox.setModel(new DefaultComboBoxModel(itemsCombo.toArray()));
		}
	}
}
