package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import excepciones.CursoNoExisteException;
import excepciones.ProgramaNoExisteException;
import logica.DtCurso;
import logica.Factory;
import logica.InterfazControladorProgramaFormacion;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AgregarCursoAPrograma extends JInternalFrame {
	private JTable tableDatos;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JComboBox<String> cbProgramas;
	private JScrollPane scrollPane;
	private int filaSeleccionada = -1;


	/**
	 * Create the frame.
	 */
	public AgregarCursoAPrograma() {
		setTitle("Agregar Curso a Programa de Formacion");
		setBounds(100, 100, 479, 361);
		getContentPane().setLayout(null);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(UIManager.getBorder("PopupMenu.border"), "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(12, 12, 448, 55);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblProgramaDeFormacion = new JLabel("Programa de Formacion:");
		GridBagConstraints gbc_lblProgramaDeFormacion = new GridBagConstraints();
		gbc_lblProgramaDeFormacion.insets = new Insets(0, 0, 0, 5);
		gbc_lblProgramaDeFormacion.gridx = 1;
		gbc_lblProgramaDeFormacion.gridy = 0;
		panel.add(lblProgramaDeFormacion, gbc_lblProgramaDeFormacion);
		
		cbProgramas = new JComboBox();
		cbProgramas.setPrototypeDisplayValue("xxxxxxxxxxxx");
		Factory fabrica = new Factory();
		InterfazControladorProgramaFormacion iPrograma = fabrica.getInterfazControladorProgramaFormacion();
		List<String> arregloProgramas = iPrograma.listarProgramasFormacion();
		String[] arregloModelCombo = new String[arregloProgramas.size() + 1];
		arregloModelCombo[0] = "";
		for (int i = 1; i < arregloModelCombo.length; i++) {
			arregloModelCombo[i] = arregloProgramas.get(i-1);
		}
		cbProgramas.setModel(new DefaultComboBoxModel<String>(arregloModelCombo));
		
		cbProgramas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbProgramas.getSelectedIndex() != 0) {
					btnAceptar.setEnabled(true);
					
					ModelTablaAgregarCursoPresentacion tableModel = new ModelTablaAgregarCursoPresentacion();
					tableModel.addColumn("Nombre");
					tableModel.addColumn("Descripcion");
					tableModel.addColumn("Agregar Curso");
					Map<String, DtCurso> arregloCursos = iPrograma.listarCursos();
					List<String> cursosYaAgregados = new ArrayList<String>();
					for (Map.Entry<String, DtCurso> element : arregloCursos.entrySet()) {
						Object[] fila = new Object[3];
						fila[0] = element.getValue().getNombre();
						fila[1] = element.getValue().getDescripcion();
						if (element.getValue().getProgramas().contains(cbProgramas.getSelectedItem().toString())) {
							fila[2] = new Boolean(true);
							cursosYaAgregados.add(element.getValue().getNombre());
						} else {
							fila[2] = new Boolean(false);	
						}
						tableModel.addRow(fila);
					}
					tableModel.actualizarYaIngresados(cursosYaAgregados);
							
					tableDatos.setModel(tableModel);
				} else {
					btnAceptar.setEnabled(false);
					tableDatos.setModel(new DefaultTableModel(new Object[][]{}, new String[]{}));
				}
			}
		});
		GridBagConstraints gbc_cbProgramas = new GridBagConstraints();
		gbc_cbProgramas.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbProgramas.insets = new Insets(0, 0, 0, 5);
		gbc_cbProgramas.gridx = 2;
		gbc_cbProgramas.gridy = 0;
		panel.add(cbProgramas, gbc_cbProgramas);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 79, 445, 205);
		getContentPane().add(scrollPane);
		
		tableDatos = new JTable();
		tableDatos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableDatos.getSelectedColumn() == 2 && tableDatos.getSelectedRow() != filaSeleccionada && tableDatos.isCellEditable(tableDatos.getSelectedRow(), 2)) {
					if (filaSeleccionada != -1)
						tableDatos.setValueAt(false, filaSeleccionada, 2);
		            filaSeleccionada = tableDatos.getSelectedRow();
				}
			}
		});
		
		scrollPane.setViewportView(tableDatos);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(343, 296, 114, 25);
		getContentPane().add(btnCancelar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setEnabled(false);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (filaSeleccionada != -1) {
						boolean resultado = iPrograma.agregarCursoAPrograma(cbProgramas.getSelectedItem().toString(), tableDatos.getValueAt(filaSeleccionada,0).toString());
						if (resultado) {
							JOptionPane.showMessageDialog(getContentPane(), "Se agrego el curso al Programa de Formacion", "Exito", JOptionPane.INFORMATION_MESSAGE);
							cbProgramas.setSelectedIndex(0);
							filaSeleccionada = -1;
						}
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "No ha seleccionado ningun curso", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (ProgramaNoExisteException | CursoNoExisteException e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage().toString(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(getContentPane(), e2.getMessage().toString(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(216, 296, 114, 25);
		getContentPane().add(btnAceptar);

	}
}
