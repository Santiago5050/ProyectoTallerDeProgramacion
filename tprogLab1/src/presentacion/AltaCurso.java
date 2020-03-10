package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import excepciones.CursoNoExisteException;
import excepciones.CursoYaExisteException;
import excepciones.InstitutoNoExisteException;
import logica.Factory;
import logica.InterfazControladorCurso;
import logica.DtCurso;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AltaCurso extends JInternalFrame {
	private JTextField txtNombre;
	private JTextField txtDuracion;
	private JTextField txtUrl;
	private JTable tableDatos;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JComboBox<String> cbInstitutos;
	private JTable tablaCategoria;

	/**
	 * Create the frame.
	 */
	public AltaCurso() {
		setTitle("Alta de Curso");
		setClosable(true);
		setBounds(100, 100, 725, 442);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("PopupMenu.border"), "Ingresar Datos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel_1.setBounds(12, 70, 233, 293);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(12, 29, 74, 15);
		panel_1.add(lblNombre);
		
		JLabel lblDuracion = new JLabel("Duracion: ");
		lblDuracion.setBounds(12, 62, 75, 15);
		panel_1.add(lblDuracion);
		
		JLabel lblNewLabel = new JLabel("Horas: ");
		lblNewLabel.setBounds(12, 100, 74, 15);
		panel_1.add(lblNewLabel);
		
		JSpinner spHoras = new JSpinner();
		spHoras.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spHoras.setBounds(95, 98, 55, 20);
		panel_1.add(spHoras);
		
		JLabel lblCreditos = new JLabel("Creditos: ");
		lblCreditos.setBounds(12, 133, 74, 15);
		panel_1.add(lblCreditos);
		
		JSpinner spCreditos = new JSpinner();
		spCreditos.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spCreditos.setBounds(95, 131, 55, 20);
		panel_1.add(spCreditos);
		
		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setBounds(12, 166, 74, 15);
		panel_1.add(lblUrl);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(12, 193, 91, 15);
		panel_1.add(lblDescripcion);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(92, 27, 124, 19);
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDuracion = new JTextField();
		txtDuracion.setBounds(92, 60, 124, 19);
		panel_1.add(txtDuracion);
		txtDuracion.setColumns(10);
		
		txtUrl = new JTextField();
		txtUrl.setColumns(10);
		txtUrl.setBounds(92, 164, 124, 19);
		panel_1.add(txtUrl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 220, 209, 61);
		panel_1.add(scrollPane);
		
		JTextArea txtDescripcion = new JTextArea();
		scrollPane.setViewportView(txtDescripcion);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("PopupMenu.border"), "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setToolTipText("");
		panel.setBounds(12, 12, 692, 55);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[] {0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label = new JLabel("Instituto:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		cbInstitutos = new JComboBox();
		cbInstitutos.setPrototypeDisplayValue("xxxxxxxxxxxx");
		//Agrego el listar institutos
		Factory fabrica = new Factory();
		InterfazControladorCurso iCurso = fabrica.getInterfazControladorCurso();
		List<String> arregloInstitutos = iCurso.listarInstitutos();
		String[] arreglo = new String[arregloInstitutos.size() + 1];
		arreglo[0] = "";
		for (int i = 1; i < arreglo.length; i++) {
			arreglo[i] = arregloInstitutos.get(i-1);
		}
		cbInstitutos.setModel(new DefaultComboBoxModel<String>(arreglo));
		cbInstitutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbInstitutos.getSelectedIndex() != 0) {
					btnAceptar.setEnabled(true);
					
					Map<String,DtCurso> arregloPrevias = iCurso.listarCursos();
					ModelTablaAltaCurso model = new ModelTablaAltaCurso();
					tableDatos.setModel(model);

					model.addColumn("Nombre");
					model.addColumn("Descripcion");
					model.addColumn("Agregar Previa");
					
					for (Map.Entry<String, DtCurso> entry : arregloPrevias.entrySet()) {
						Object[] fila = new Object[3];
						fila[0] = entry.getValue().getNombre();
						fila[1] = entry.getValue().getDescripcion();
						fila[2] = new Boolean(false);
						model.addRow(fila);
					}
					
					
					List<String> arregloCategorias = iCurso.getListaCategorias();
					ModelTablaAltaCursoCategoria modelCategorias = new ModelTablaAltaCursoCategoria();
					tablaCategoria.setModel(modelCategorias);

					modelCategorias.addColumn("Categoria");
					modelCategorias.addColumn("Agregar Categoria");
					
					for (int i = 0; i < arregloCategorias.size(); i++) {
						Object[] fila = new Object[2];
						fila[0] = arregloCategorias.get(i);
						fila[1] = new Boolean(false);
						modelCategorias.addRow(fila);
					}
				} else {
					btnAceptar.setEnabled(false);
					tableDatos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
					tablaCategoria.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
				}
			}
		});
		
		
		GridBagConstraints gbc_cbInstitutos = new GridBagConstraints();
		gbc_cbInstitutos.insets = new Insets(0, 0, 0, 5);
		gbc_cbInstitutos.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbInstitutos.gridx = 2;
		gbc_cbInstitutos.gridy = 0;
		panel.add(cbInstitutos, gbc_cbInstitutos);
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(256, 79, 448, 137);
		getContentPane().add(scrollPane_1);
		
		tableDatos = new JTable();
		
		scrollPane_1.setViewportView(tableDatos);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Factory fabrica = new Factory();
				InterfazControladorCurso iCurso = fabrica.getInterfazControladorCurso();
				try {
					//Cargo arreglo de previas a ser añadidas al curso
					ArrayList<String> previas = new ArrayList<String>();
					TableModel tDatos = tableDatos.getModel();
					int maxFilas = tDatos.getRowCount();
					for (int i = 0; i < maxFilas; i++) {
						if ((boolean) tDatos.getValueAt(i, 2)) {
							previas.add((String) tDatos.getValueAt(i, 0));
						}
					}
					//Cargo arreglo de categorias a ser añadidas al curso
					ArrayList<String> categorias = new ArrayList<String>();
					tDatos = tablaCategoria.getModel();
					maxFilas = tDatos.getRowCount();
					for (int i = 0; i < maxFilas; i++) {
						if ((boolean) tDatos.getValueAt(i, 1)) {
							categorias.add((String) tDatos.getValueAt(i, 0));
						}
					}
					
					if (txtNombre.getText().length() != 0) {
						if (txtDescripcion.getText().length() != 0) {
							if (txtDuracion.getText().length() != 0) {
								if (txtUrl.getText().length() != 0) {
									iCurso.altaCurso(cbInstitutos.getSelectedItem().toString(), txtNombre.getText(), txtDescripcion.getText(), txtDuracion.getText(), Integer.parseInt(spHoras.getValue().toString()), Integer.parseInt(spCreditos.getValue().toString()), txtUrl.getText(), previas);					
									iCurso.agregarCategoriasACurso(txtNombre.getText(), categorias);
									JOptionPane.showMessageDialog(getContentPane(), "Curso agregado al sistema", "Exito", JOptionPane.INFORMATION_MESSAGE);					
									
									//Repintado de elementos
									txtNombre.setText("");
									txtDescripcion.setText("");
									txtDuracion.setText("");
									txtUrl.setText("");
									spCreditos.setValue(0);
									spHoras.setValue(0);
									cbInstitutos.setSelectedIndex(0);
								} else {
									JOptionPane.showMessageDialog(getContentPane(), "No ha ingresado una URL para el curso", "Error", JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(getContentPane(), "No ha ingresado una duración para el curso", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "No ha ingresado una descripción para el curso", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "No ha ingresado un nombre para el curso", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}catch (InstitutoNoExisteException | CursoNoExisteException | CursoYaExisteException e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage().toString(),"Error", JOptionPane.ERROR_MESSAGE);
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(getContentPane(), e2.getMessage().toString(),"Ha ocurrido un error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(448, 377, 114, 25);
		getContentPane().add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(575, 377, 114, 25);
		getContentPane().add(btnCancelar);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(256, 228, 448, 137);
		getContentPane().add(scrollPane_2);
		
		tablaCategoria = new JTable();
		scrollPane_2.setViewportView(tablaCategoria);

	}
}
