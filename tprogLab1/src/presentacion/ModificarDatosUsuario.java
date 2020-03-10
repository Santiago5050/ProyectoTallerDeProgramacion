package presentacion;

import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.util.List;

import excepciones.UsuarioNoExisteException;
import logica.DtUsuario;
import logica.Factory;
import logica.InterfazControladorUsuario;

import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class ModificarDatosUsuario extends JInternalFrame {
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JComboBox comboBox;
	private JDateChooser dateChooser;
	
	public ModificarDatosUsuario() {
		// TODO Auto-generated constructor stub
		super("Modificar Datos de Usuario");
		setSize(500,350);
		setVisible(true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weighty = 0.1;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNickname = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.insets = new Insets(20, 10, 20, 10);
		gbc_lblNickname.anchor = GridBagConstraints.EAST;
		gbc_lblNickname.gridx = 0;
		gbc_lblNickname.gridy = 0;
		panel_1.add(lblNickname, gbc_lblNickname);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(20, 10, 20, 10);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weighty = 0.1;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(10, 5, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel.add(lblNombre, gbc_lblNombre);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 50);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 0;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbc_lblApellido = new GridBagConstraints();
		gbc_lblApellido.anchor = GridBagConstraints.WEST;
		gbc_lblApellido.insets = new Insets(0, 5, 5, 5);
		gbc_lblApellido.gridx = 0;
		gbc_lblApellido.gridy = 1;
		panel.add(lblApellido, gbc_lblApellido);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 50);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 1;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblMail = new JLabel("Mail:");
		GridBagConstraints gbc_lblMail = new GridBagConstraints();
		gbc_lblMail.weightx = 0.3;
		gbc_lblMail.anchor = GridBagConstraints.WEST;
		gbc_lblMail.insets = new Insets(0, 5, 5, 5);
		gbc_lblMail.gridx = 0;
		gbc_lblMail.gridy = 2;
		panel.add(lblMail, gbc_lblMail);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 50);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 2;
		panel.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.weightx = 0.05;
		gbc_lblFechaDeNacimiento.anchor = GridBagConstraints.WEST;
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 5, 5, 0);
		gbc_lblFechaDeNacimiento.gridwidth = 2;
		gbc_lblFechaDeNacimiento.gridx = 0;
		gbc_lblFechaDeNacimiento.gridy = 3;
		panel.add(lblFechaDeNacimiento, gbc_lblFechaDeNacimiento);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(0, 0, 0, 50);
		gbc_dateChooser.weightx = 6.0;
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 3;
		panel.add(dateChooser, gbc_dateChooser);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.weighty = 0.2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		getContentPane().add(panel_2, gbc_panel_2);
		
		JButton btnAceptar = new JButton("Aceptar");
		panel_2.add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				aceptar();
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		panel_2.add(btnCancelar);
		setClosable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				limpiar();
			}
		});
		
		modificarUsuario();
		
	}
	
	private void aceptar() {
		Factory factory = new Factory();
		InterfazControladorUsuario iUsuario = factory.getInterfazControladorUsuario();
		Calendar naciCalendar = Calendar.getInstance();
		if (comboBox.getSelectedItem()==null) {
			JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
		}else {
			try {
				naciCalendar.setTime(dateChooser.getDate());
				iUsuario.modificarDatosUsuario(comboBox.getSelectedItem().toString(), textField_1.getText(), 
						textField_2.getText(), naciCalendar);
				JOptionPane.showMessageDialog(this, "Usuario modificado con exito.");
				limpiar();
			} catch (UsuarioNoExisteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e.getMessage());
			}catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(this, "Seleccione una fecha.");
			}
		}	
	}
	private void limpiar() {
		// TODO Auto-generated method stub
		dispose();
	}
	
	public void modificarUsuario() {
		Factory factory = new Factory();
		InterfazControladorUsuario iUsuario = factory.getInterfazControladorUsuario();
		try {
			List<String> strings = iUsuario.listarUsuarios();
			comboBox.setModel(new DefaultComboBoxModel(strings.toArray()));
			comboBox.setSelectedItem(null);
		} catch (UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e.getMessage());
			dispose();
		}
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox jComboBox = (JComboBox) e.getSource();
				try {
					DtUsuario dtUsuario= iUsuario.informacionDeUsuario((String) jComboBox.getSelectedItem());
					textField_1.setText(dtUsuario.getNombre());
					textField_2.setText(dtUsuario.getApellido());
					textField_3.setText(dtUsuario.getMail());
					dateChooser.setDate(dtUsuario.getFechaNacimiento().getTime());
					dateChooser.setDateFormatString("dd/MM/yyyy");
				} catch (UsuarioNoExisteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
	}

}
