package presentacion;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import excepciones.CursoNoExisteException;
import excepciones.InstitutoNoExisteException;
import logica.DtCurso;
import logica.Factory;
import logica.InterfazControladorCurso;

public class ConsultaCurso extends JInternalFrame{
	
	
	private JPanel panel_2; 
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JTextArea textArea;
	private JTextArea textArea_1;
	
	public ConsultaCurso() {
		// TODO Auto-generated constructor stub
		super("Consulta de Curso");
		setSize(900,700);
		setVisible(true);
		//setResizable(true);
		//setMinimumSize(new Dimension(700, 600));
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
		gbc_panel_2.weighty = 5.0;
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
				"Ediciones de Curso", "Programas de Formacion", "Previas"
			}
		));
		table.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(table);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Informacion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.weighty = 1.5;
		gbc_panel_1.weightx = 10.0;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
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
		
		JLabel lblDuracion = new JLabel("Duracion:");
		GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
		gbc_lblDuracion.anchor = GridBagConstraints.EAST;
		gbc_lblDuracion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuracion.gridx = 2;
		gbc_lblDuracion.gridy = 0;
		panel_1.add(lblDuracion, gbc_lblDuracion);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(10, 5, 10, 10);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 0;
		panel_1.add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblInicio = new JLabel("Horas:");
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
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 2;
		gbc_lblDescripcion.gridy = 1;
		panel_1.add(lblDescripcion, gbc_lblDescripcion);
		
		JLabel lblFin = new JLabel("Registro");
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
		
		JLabel lblAlta = new JLabel("URL:");
		GridBagConstraints gbc_lblAlta = new GridBagConstraints();
		gbc_lblAlta.anchor = GridBagConstraints.EAST;
		gbc_lblAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlta.gridx = 0;
		gbc_lblAlta.gridy = 3;
		panel_1.add(lblAlta, gbc_lblAlta);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.weightx = 0.5;
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 2;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(10, 5, 10, 10);
		gbc_scrollPane_2.gridheight = 2;
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 1;
		gbc_scrollPane_2.gridy = 3;
		panel_1.add(scrollPane_2, gbc_scrollPane_2);
		
		 textArea_1 = new JTextArea();
		 textArea_1.setEditable(false);
		scrollPane_2.setViewportView(textArea_1);
		
		JLabel lblCreditos = new JLabel("Creditos:");
		GridBagConstraints gbc_lblCreditos = new GridBagConstraints();
		gbc_lblCreditos.anchor = GridBagConstraints.EAST;
		gbc_lblCreditos.insets = new Insets(10, 5, 10, 10);
		gbc_lblCreditos.gridx = 0;
		gbc_lblCreditos.gridy = 5;
		panel_1.add(lblCreditos, gbc_lblCreditos);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(10, 5, 10, 10);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 5;
		panel_1.add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
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
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblProgramaDeformacion = new JLabel("Instituto:");
		GridBagConstraints gbc_lblProgramaDeformacion = new GridBagConstraints();
		gbc_lblProgramaDeformacion.weightx = 0.1;
		gbc_lblProgramaDeformacion.insets = new Insets(0, 0, 0, 5);
		gbc_lblProgramaDeformacion.anchor = GridBagConstraints.EAST;
		gbc_lblProgramaDeformacion.gridx = 0;
		gbc_lblProgramaDeformacion.gridy = 0;
		panel.add(lblProgramaDeformacion, gbc_lblProgramaDeformacion);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.weightx = 0.6;
		gbc_comboBox.insets = new Insets(5, 5, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);
		
		JLabel lblCurso = new JLabel("Curso:");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.weightx = 0.1;
		gbc_lblCurso.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.gridx = 2;
		gbc_lblCurso.gridy = 0;
		panel.add(lblCurso, gbc_lblCurso);
		
		comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.weightx = 0.4;
		gbc_comboBox_1.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 0;
		panel.add(comboBox_1, gbc_comboBox_1);
		comboBox_1.setPrototypeDisplayValue("XXXXXXXXXXXXXXX");
		
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
				   if(string!=null) {
					   if (col==0) {
						   mWindow.cargarPrograma(string);
					   }else if (col==1) {
						   mWindow.cargarEdicion(string);
					   }else if (col ==2){
						   mWindow.cargarCurso(string);
					   }
				   }
			
			   }
			}
		});
		
		consultarCurso();
		

	}
	
	@SuppressWarnings("serial")
	public void cargarTabla(Object[][] s) {
		table.setModel(new DefaultTableModel(
			s,
			new String[] {
				"Programas De Formacion", "Ediciones", "Previas", "Categorias"
			}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
	
	
	public void consultarCurso() {
		Factory factory = new Factory();
		
		InterfazControladorCurso iCurso = factory.getInterfazControladorCurso();
		List<String> strings = iCurso.listarInstitutos();
		if (strings.size()==0) {
			JOptionPane.showMessageDialog(this, "No hay Institutos en el sistema.");
			dispose();
			
		}else {
			boolean first = true;
			comboBox.setModel(new DefaultComboBoxModel(strings.toArray()));
			
			textField.setEditable(false);
			textField_1.setEditable(false);
			textField_2.setEditable(false);
			textArea.setEditable(false);
			
			
			
			comboBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						JComboBox jComboBox = (JComboBox) e.getSource();
						List<String> cursos;
						cursos= iCurso.mostrarCursosEnInstituto((String)jComboBox.getSelectedItem());
						
						if (cursos.size()==0) {
							JOptionPane.showMessageDialog(getContentPane(), "No hay Cursos en el instituto seleccionado.");
						}
						
						comboBox_1.setModel(new DefaultComboBoxModel(cursos.toArray()));
						 
						for( ActionListener al : comboBox_1.getActionListeners() ) {
						        comboBox_1.removeActionListener( al );
						    }
						
						comboBox_1.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e2) {
								// TODO Auto-generated method stub
								JComboBox jBox = (JComboBox) e2.getSource();
								try {
									DtCurso curso = iCurso.mostrarCurso((String) jBox.getSelectedItem());
									textField.setText(curso.getNombre());
									textField_1.setText(String.valueOf(curso.getCantidadHoras()));
									textArea_1.setText(curso.getUrl());
									textField_4.setText(curso.getDuracion());
									textField_5.setText(String.valueOf(curso.getCreditos()));
									String alta;
									
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									alta = sdf.format(curso.getFechaDeRegistro().getTime());
									textField_2.setText(alta);
									
									
									textArea.setText(curso.getDescripcion());
								
									
									List<String> prstrings = curso.getProgramas();
									List<String> pstrings = curso.getPrevias();
									List<String> edstrings = curso.getEdiciones();
									List<String> catstrings = curso.getCategorias();
									
									
									
									int max = Math.max(edstrings.size(),Math.max(prstrings.size(), Math.max(catstrings.size(), pstrings.size())));
									
									
									Object[][] objects = new Object[max][4];
									for(int i =0; i<max;i++) {
										try {
											objects[i][1]=edstrings.get(i);
										} catch (Exception e3) {
										}
										try {
											objects[i][0]=prstrings.get(i);
										} catch (Exception e3) {
										}
										try {
											objects[i][2]=pstrings.get(i);
										} catch (Exception e3) {
										}
										try {
											objects[i][3]=catstrings.get(i);
										} catch (Exception e3) {
										}
									}
									
									cargarTabla(objects);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
								}
							}
						});
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(getContentPane(), e1.getMessage());
					}
					comboBox_1.setSelectedIndex(0);
				}
				
				
			});
			comboBox.setSelectedIndex(0);
			
		}
		
		
			
	}
	
	public void cargarCurso(String s) {
		Factory factory = new Factory();
		InterfazControladorCurso iCurso = factory.getInterfazControladorCurso();
		List<String> instituti = iCurso.listarInstitutos();
		int i = 0;
		try {
			while (!iCurso.mostrarCursosEnInstituto(instituti.get(i)).contains(s)) {
				i++;
			}
		} catch (InstitutoNoExisteException | CursoNoExisteException e) {
		}
		comboBox.setSelectedItem(instituti.get(i));
		comboBox_1.setSelectedItem(s);
		
		comboBox.setEnabled(false);
		comboBox_1.setEnabled(false);
	}
	
	
	
	

}
