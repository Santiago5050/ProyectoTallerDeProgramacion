package presentacion;

import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import excepciones.ProgramaNoExisteException;
import logica.DtProgramaFormacion;
import logica.Factory;
import logica.InterfazControladorProgramaFormacion;
import java.util.List;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class ConsultaProgramaFormacion extends JInternalFrame {
	private JPanel panel_2; 
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JComboBox comboBox;
	private JTextArea textArea;
	
	public ConsultaProgramaFormacion() {
		// TODO Auto-generated constructor stub
		super("Consulta Programa de Formacion");
		setSize(500,600);
		setVisible(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		setClosable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.weighty = 0.1;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		getContentPane().add(panel_3, gbc_panel_3);
		
		JButton btnAceptar = new JButton("Aceptar");
		panel_3.add(btnAceptar);
		
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		JButton btnCancelar = new JButton("Cancelar");
		panel_3.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.weighty = 20.0;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel_2 = new JPanel();
		getContentPane().add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Cursos", "Categorias"
			}
		));
		table.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(table);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Informacion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.weighty = 0.1;
		gbc_panel_1.weightx = 2.0;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel_1.add(lblNombre, gbc_lblNombre);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(10, 5, 10, 10);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescripcion.gridx = 2;
		gbc_lblDescripcion.gridy = 0;
		panel_1.add(lblDescripcion, gbc_lblDescripcion);
		
		JLabel lblInicio = new JLabel("Inicio:");
		GridBagConstraints gbc_lblInicio = new GridBagConstraints();
		gbc_lblInicio.anchor = GridBagConstraints.EAST;
		gbc_lblInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblInicio.gridx = 0;
		gbc_lblInicio.gridy = 1;
		panel_1.add(lblInicio, gbc_lblInicio);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(10, 5, 10, 10);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel_1.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblFin = new JLabel("Fin:");
		GridBagConstraints gbc_lblFin = new GridBagConstraints();
		gbc_lblFin.anchor = GridBagConstraints.EAST;
		gbc_lblFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFin.gridx = 0;
		gbc_lblFin.gridy = 2;
		panel_1.add(lblFin, gbc_lblFin);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(10, 5, 10, 10);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel_1.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JLabel lblAlta = new JLabel("Alta:");
		GridBagConstraints gbc_lblAlta = new GridBagConstraints();
		gbc_lblAlta.anchor = GridBagConstraints.EAST;
		gbc_lblAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlta.gridx = 0;
		gbc_lblAlta.gridy = 3;
		panel_1.add(lblAlta, gbc_lblAlta);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(10, 5, 10, 10);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 3;
		panel_1.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.weightx = 5.0;
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weighty = 0.01;
		gbc_panel.insets = new Insets(5, 5, 5, 5);
		gbc_panel.weightx = 1.0;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblProgramaDeformacion = new JLabel("Programa de Formacion:");
		GridBagConstraints gbc_lblProgramaDeformacion = new GridBagConstraints();
		gbc_lblProgramaDeformacion.insets = new Insets(0, 0, 0, 5);
		gbc_lblProgramaDeformacion.anchor = GridBagConstraints.EAST;
		gbc_lblProgramaDeformacion.gridx = 0;
		gbc_lblProgramaDeformacion.gridy = 0;
		panel.add(lblProgramaDeformacion, gbc_lblProgramaDeformacion);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (row >= 0 && col >= 0) {
				   String string = (String) table.getValueAt(row, col);
				   MainWindow mWindow = (MainWindow) SwingUtilities.getWindowAncestor(getContentPane());
				   if (col==0) {
					   mWindow.cargarCurso(string);
				   }
				}
			}
			
		});
		
		
		
			consultaPrograma();
		
		

	}
	
	public void cargarTabla(Object[][] s) {
		table.setModel(new DefaultTableModel(
			s,
			new String[] {
				"Cursos", "Categorias"
			}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	private DtProgramaFormacion pFormacion;
	
	public void consultaPrograma() {
		Factory factory = new Factory();
		
		InterfazControladorProgramaFormacion iFormacion = factory.getInterfazControladorProgramaFormacion();
		List<String> strings = iFormacion.listarProgramasFormacion();
		if(strings.size()==0) {
			JOptionPane.showMessageDialog(this, "No hay Programas de Formacion en el sistema.");
			dispose();
		}else {
			comboBox.setModel(new DefaultComboBoxModel(strings.toArray()));
			
			textField.setEditable(false);
			textField_1.setEditable(false);
			textField_2.setEditable(false);
			textField_3.setEditable(false);
			textArea.setEditable(false);
			
			
			
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						JComboBox jComboBox = (JComboBox) e.getSource();
						
						pFormacion= iFormacion.mostrarProgramaFormacion((String)jComboBox.getSelectedItem());
						textField.setText(pFormacion.getNombre());
						String inicio;
						String fin;
						String alta;
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						inicio = sdf.format(pFormacion.getFechaInicio().getTime());
						fin = sdf.format(pFormacion.getFechaFin().getTime());
						alta = sdf.format(pFormacion.getFechaAlta().getTime());
						textField_1.setText(inicio);
						textField_2.setText(fin);
						textField_3.setText(alta);
						
						List<String> cursos = pFormacion.getCursos();
						List<String> categorias = pFormacion.getCategorias();
						int max = Math.max(cursos.size(), categorias.size());
						Object[][] objects = new Object[max][2];
						
						for (int i = 0; i < cursos.size(); i++) {
							try {
								objects[i][0] = cursos.get(i);
							} catch (Exception e2) {
							}
							try {
								objects[i][1] = categorias.get(i);
							} catch (Exception e2) {
							}
						}
						cargarTabla(objects);
						textArea.setText(pFormacion.getDescripcion());
					} catch (ProgramaNoExisteException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(getContentPane(), e1.getMessage());
						
					}
				}
				
			});
		}
		
		
		
	}
	
	public void cargarPrograma(String s) {
		comboBox.setSelectedItem(s);
		comboBox.setEnabled(false);
	}
	

}
