package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.awt.event.ItemEvent;
import excepciones.CursoNoExisteException;
import excepciones.DocenteNoPerteneceMismoInstitutoException;
import excepciones.EstudianteComoDocenteException;
import excepciones.InstitutoNoBrindaCursoException;
import excepciones.InstitutoNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.DtCurso;
import logica.DtDocente;
import logica.Factory;
import logica.InterfazControladorCurso;

import com.toedter.calendar.JDateChooser;
import javax.swing.JTable;

public class AltaEdicionCurso extends JInternalFrame {
	private JTextField txtNombre;
	private JSpinner spCupo;
	private JComboBox<String> cbInstitutos;
	private JComboBox<String> cbCursos;
	private JButton btnAceptar;
	private JTable tableDatos;
	private Map<String, DtDocente> arregloDocente;

	/**
	 * Create the frame.
	 */
	public AltaEdicionCurso() {
		setTitle("Alta Edicion de Curso");
		setClosable(true);
		setBounds(100, 100, 483, 501);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(UIManager.getBorder("PopupMenu.border"), "Ingresar Datos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(12, 125, 448, 101);
		getContentPane().add(panel);
		
		JLabel label = new JLabel("Nombre: ");
		label.setBounds(12, 29, 74, 15);
		panel.add(label);
		
		JLabel lblInicio = new JLabel("Inicio:");
		lblInicio.setBounds(12, 62, 74, 15);
		panel.add(lblInicio);
		
		JLabel lblFin = new JLabel("Fin:");
		lblFin.setBounds(238, 62, 64, 15);
		panel.add(lblFin);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(92, 27, 124, 19);
		panel.add(txtNombre);
		
		JCheckBox chCupo = new JCheckBox("Cupo?");
		chCupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					spCupo.setVisible(true);
					spCupo.setEnabled(true);
				} else {
					spCupo.setVisible(false);
					spCupo.setEnabled(false);
				}
					
			}
		});
		chCupo.setBounds(228, 25, 71, 23);
		panel.add(chCupo);
		
		spCupo = new JSpinner();
		spCupo.setEnabled(false);
		spCupo.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spCupo.setBounds(307, 27, 55, 20);
		panel.add(spCupo);
		
		JDateChooser dateInicio = new JDateChooser();
		dateInicio.setBounds(92, 58, 124, 19);
		panel.add(dateInicio);
		
		JDateChooser dateFin = new JDateChooser();
		dateFin.setBounds(307, 58, 124, 19);
		panel.add(dateFin);
		spCupo.setVisible(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("PopupMenu.border"), "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setBounds(12, 23, 448, 101);
		getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblCurso = new JLabel("Instituto:");
		lblCurso.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.gridx = 1;
		gbc_lblCurso.gridy = 0;
		panel_1.add(lblCurso, gbc_lblCurso);
		
		
		//Inicializo Fabrica e interfaz
		Factory fabrica = new Factory();
		InterfazControladorCurso iCurso = fabrica.getInterfazControladorCurso();
		
		//Cargo Combobox Institutos
		cbInstitutos = new JComboBox();
		cbInstitutos.setPrototypeDisplayValue("xxxxxxxxxxxx");
		List<String> arregloInstitutos = iCurso.listarInstitutos();
		String[] arregloModelInstitutos = new String[arregloInstitutos.size() + 1];
		arregloModelInstitutos[0] = "";
		for (int i = 1; i < arregloModelInstitutos.length; i++) {
			arregloModelInstitutos[i] = arregloInstitutos.get(i - 1);
		}
		cbInstitutos.setModel(new DefaultComboBoxModel<String>(arregloModelInstitutos));

		
		//Cargo Docentes a la presentacion
		Map<String, DtDocente> arregloDocente = iCurso.listarDocentes();
		
		cbInstitutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbInstitutos.getSelectedItem().toString().length() != 0) {
					cbCursos.setEnabled(true);
					btnAceptar.setEnabled(false);
					tableDatos.setModel(new DefaultTableModel(new Object[][] {} , new String[] {}));
	
					Map<String, DtCurso> arregloCursosMap = iCurso.listarCursosAsociados(cbInstitutos.getSelectedItem().toString());
					String[] arregloModelCursos = new String[arregloCursosMap.size() + 1];
					arregloModelCursos[0] = "";
					int i = 1;
					for (Map.Entry<String, DtCurso> element : arregloCursosMap.entrySet()) {
						arregloModelCursos[i] = element.getValue().getNombre();
						i++;
					}
					cbCursos.setModel(new DefaultComboBoxModel<String>(arregloModelCursos));
				} else {
					cbCursos.removeAllItems();
					cbCursos.setEnabled(false);
					tableDatos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
					btnAceptar.setEnabled(false);
				}
			}
		});
		
		GridBagConstraints gbc_cbInstitutos = new GridBagConstraints();
		gbc_cbInstitutos.insets = new Insets(0, 0, 5, 5);
		gbc_cbInstitutos.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbInstitutos.gridx = 2;
		gbc_cbInstitutos.gridy = 0;
		panel_1.add(cbInstitutos, gbc_cbInstitutos);
		
		JLabel lblCurso_1 = new JLabel("Curso:");
		lblCurso_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCurso_1 = new GridBagConstraints();
		gbc_lblCurso_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurso_1.gridx = 1;
		gbc_lblCurso_1.gridy = 1;
		panel_1.add(lblCurso_1, gbc_lblCurso_1);
		
		cbCursos = new JComboBox();
		cbCursos.setPrototypeDisplayValue("xxxxxxxxxxxx");
		cbCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbCursos.getSelectedIndex() != 0 && cbCursos.getSelectedIndex() != -1) {
					btnAceptar.setEnabled(true);
					ModelTablaAltaEdicionCurso tableModel = new ModelTablaAltaEdicionCurso();
					tableModel.addColumn("Nickname");
					tableModel.addColumn("Nombre");
					tableModel.addColumn("Agregar Docente");
					for (Map.Entry<String, DtDocente> element : arregloDocente.entrySet()) {
						if (element.getValue().getInstituto() == cbInstitutos.getSelectedItem().toString()) {
							Object[] fila = new Object[3];
							fila[0] = element.getValue().getNickname();
							fila[1] = element.getValue().getNombre() + " " + element.getValue().getApellido();
							fila[2] = new Boolean(false);	
							tableModel.addRow(fila);						
						}
					}
					tableDatos.setModel(tableModel);
				} else {
					tableDatos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
					btnAceptar.setEnabled(false);
				}
			}
		});
		cbCursos.setEnabled(false);
		GridBagConstraints gbc_cbCursos = new GridBagConstraints();
		gbc_cbCursos.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbCursos.insets = new Insets(0, 0, 0, 5);
		gbc_cbCursos.gridx = 2;
		gbc_cbCursos.gridy = 1;
		panel_1.add(cbCursos, gbc_cbCursos);
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 238, 448, 188);
		getContentPane().add(scrollPane_1);
		
		tableDatos = new JTable();
		scrollPane_1.setRowHeaderView(tableDatos);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtNombre.getText().length() != 0) {
						int cupo = 0;
						if (chCupo.isSelected())
							cupo = Integer.parseInt(spCupo.getValue().toString());
						if (dateInicio.getDate() != null) {
							Calendar fechaIni = dateInicio.getCalendar();
							if (dateFin.getDate() != null) {
								Calendar fechaFin = dateFin.getCalendar();
								if (fechaIni.before(fechaFin)) {
									ArrayList<String> docentes = new ArrayList<String>();
									TableModel tDatos = tableDatos.getModel();
									int maxFilas = tDatos.getRowCount();
									for (int i = 0; i < maxFilas; i++) {
										if ((boolean) tDatos.getValueAt(i, 2)) {
											docentes.add((String) tDatos.getValueAt(i, 0));
										}
									}
									boolean exito = iCurso.altaEdicionCurso(txtNombre.getText(), fechaIni, fechaFin, cupo, cbCursos.getSelectedItem().toString(), docentes, cbInstitutos.getSelectedItem().toString());
									if (exito) {
										JOptionPane.showMessageDialog(getContentPane(), "Se ha dado de alta la edicion", "Exito", JOptionPane.INFORMATION_MESSAGE);
										
										//Repintado de elementos
										txtNombre.setText("");
										dateInicio.setCalendar(null);
										dateFin.setCalendar(null);
										chCupo.setSelected(false);
										cbCursos.setSelectedIndex(0);
										cbCursos.setEnabled(false);
										btnAceptar.setEnabled(false);
										tableDatos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
										spCupo.setValue(0);
										cbInstitutos.setSelectedIndex(0);
									} else {
										JOptionPane.showMessageDialog(getContentPane(), "El nombre de edicion ingresado ya esta en uso", "Error", JOptionPane.ERROR_MESSAGE);							
									}
								} else {
									JOptionPane.showMessageDialog(getContentPane(), "La fecha de fin debe ser posterior a la fecha de inicio", "Error", JOptionPane.ERROR_MESSAGE);							
								}
							} else {
								JOptionPane.showMessageDialog(getContentPane(), "No ingreso una fecha de fin", "Error", JOptionPane.ERROR_MESSAGE);						
							}
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "No ingreso una fecha de inicio", "Error", JOptionPane.ERROR_MESSAGE);						
						}
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "No ingreso un nombre para la edicion", "Error", JOptionPane.ERROR_MESSAGE);						
					}
				} catch (InstitutoNoExisteException | InstitutoNoBrindaCursoException | UsuarioNoExisteException | CursoNoExisteException | DocenteNoPerteneceMismoInstitutoException | EstudianteComoDocenteException e2) {
					JOptionPane.showMessageDialog(getContentPane(), e2.getMessage().toString(), "Error", JOptionPane.ERROR_MESSAGE);						
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(getContentPane(), "Error inesperado: " + e2.getMessage().toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setEnabled(false);
		btnAceptar.setBounds(219, 438, 114, 25);
		getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(346, 438, 114, 25);
		getContentPane().add(btnCancelar);

	}
}
