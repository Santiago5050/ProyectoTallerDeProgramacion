package presentacion;

import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import excepciones.CursoNoExisteException;
import excepciones.EdicionNoExisteException;
import excepciones.EstudianteYaInscripto;
import excepciones.InstitutoNoExisteException;
import excepciones.UsuarioNoExisteException;
import logica.DtEdicion;
import logica.Factory;
import logica.InterfazControladorCurso;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class InscripcionAEdicionCurso extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private int col;
	private JDateChooser dateChooser;

	public InscripcionAEdicionCurso() {
		// TODO Auto-generated constructor stub
		super("Incripcion a Edicion de Curso");
		setSize(500,600);
		setVisible(true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[]{66, 0, 40, 192, 0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Seleccionar", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weighty = 0.1;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel label = new JLabel("Instituto:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.weightx = 0.1;
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_1.add(label, gbc_label);
		
		 comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.weightx = 0.6;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(5, 5, 0, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);
		
		JLabel label_1 = new JLabel("Curso:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.weightx = 0.1;
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 0;
		panel_1.add(label_1, gbc_label_1);
		
		comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.weightx = 0.4;
		gbc_comboBox_1.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 0;
		panel_1.add(comboBox_1, gbc_comboBox_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Edicion Vigente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label_2 = new JLabel("Nombre:");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(10, 5, 10, 10);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 0;
		panel.add(label_2, gbc_label_2);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		
		JLabel label_5 = new JLabel("Cupo:");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.anchor = GridBagConstraints.EAST;
		gbc_label_5.insets = new Insets(0, 5, 5, 10);
		gbc_label_5.gridx = 2;
		gbc_label_5.gridy = 0;
		panel.add(label_5, gbc_label_5);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 0;
		panel.add(textField_1, gbc_textField_1);
		
		JLabel label_3 = new JLabel("Inicio:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.weighty = 0.5;
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 5, 5, 10);
		gbc_label_3.gridx = 0;
		gbc_label_3.gridy = 1;
		panel.add(label_3, gbc_label_3);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 1;
		panel.add(textField_2, gbc_textField_2);
		
		JLabel label_4 = new JLabel("Fin:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 5, 5, 10);
		gbc_label_4.gridx = 2;
		gbc_label_4.gridy = 1;
		panel.add(label_4, gbc_label_4);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 1;
		panel.add(textField_3, gbc_textField_3);
		
		JLabel label_6 = new JLabel("Publicacion:");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.EAST;
		gbc_label_6.insets = new Insets(0, 5, 5, 10);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 2;
		panel.add(label_6, gbc_label_6);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 2;
		panel.add(textField_4, gbc_textField_4);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Ingresar Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.weighty = 0.1;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 2;
		getContentPane().add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblInscripcion = new JLabel("Inscripcion:");
		GridBagConstraints gbc_lblInscripcion = new GridBagConstraints();
		gbc_lblInscripcion.insets = new Insets(5, 10, 5, 5);
		gbc_lblInscripcion.gridx = 0;
		gbc_lblInscripcion.gridy = 0;
		panel_4.add(lblInscripcion, gbc_lblInscripcion);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.insets = new Insets(10, 10, 10, 240);
		gbc_dateChooser.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooser.gridx = 1;
		gbc_dateChooser.gridy = 0;
		panel_4.add(dateChooser, gbc_dateChooser);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.weighty = 0.2;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		getContentPane().add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{241, 0};
		gbl_panel_3.rowHeights = new int[]{3, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_3.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Estudiante","Agregar", 
			}
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(93);
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 4;
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
		
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		setClosable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		cargarCombo();
		comboBox_1.setPrototypeDisplayValue("XXXXXXXXXXXXXXX");
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(!e.getValueIsAdjusting()) {
					int col2 = table.getSelectedRow();
					if(col!=-1) {
						DefaultTableModel model = (DefaultTableModel) table.getModel();
						model.setValueAt(false, col, 1);
					}
					col=col2;
				}
			}
		});
		
		dateChooser.setDate(Calendar.getInstance().getTime());
		dateChooser.setDateFormatString("dd/MM/yyyy");
	}
	
	@SuppressWarnings("serial")
	public void cargarTabla(Object[][] s) {
		col=-1;
		table.setModel(new DefaultTableModel(
			s,
			new String[] {
					"Estudiante","Agregar", 
			}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				if(column==1)
					return true;
				return false;
			}
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if(columnIndex==1)
					return Boolean.class;
				return super.getColumnClass(columnIndex);
			}
			
			
		});
	}
	
	private void aceptar() {
		// TODO Auto-generated method stub
		Factory factory = new Factory();
		InterfazControladorCurso iCurso = factory.getInterfazControladorCurso();
		Calendar calendar = Calendar.getInstance();
		if (dateChooser.getDate()==null) {
			JOptionPane.showMessageDialog(this, "ERROR: Inserte una fecha.");
		}else {
			calendar.setTime(dateChooser.getDate());
			if(col!=-1 && textField.getText().length()!=0) {
				if ((boolean) table.getValueAt(col, 1)) {
					try {
						
						iCurso.inscribirEstudianteACurso((String) table.getValueAt(col, 0), (String) comboBox_1.getSelectedItem(), textField.getText(),calendar, "");
						JOptionPane.showMessageDialog(this, "El estudiante " + (String) table.getValueAt(col, 0)+ " fue inscripto correctamente.");
					} catch (CursoNoExisteException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this, e.getMessage());
					} catch (EdicionNoExisteException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this, e.getMessage());
					} catch (UsuarioNoExisteException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(this, e.getMessage());
					}catch (EstudianteYaInscripto e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, e.getMessage());
					}catch (Exception e) {
						// TODO: handle exception
					}
					dispose();
					
				}else {
					JOptionPane.showMessageDialog(this, "Seleccione un estudiante.");
				}

			}else if(textField.getText().length()==0){
				JOptionPane.showMessageDialog(this, "El curso no posee una edicion vigente.");
			} else{
				JOptionPane.showMessageDialog(this, "Seleccione un estudiante.");
			
		}
		
	}
		
	}
	
	
	
	public void cargarCombo() {
		Factory factory = new Factory();
		InterfazControladorCurso iCurso = factory.getInterfazControladorCurso();
		List<String> strings = iCurso.listarInstitutos();
		if(strings.size()==0) {
			JOptionPane.showMessageDialog(this, "No hay Institutos en el sistema.");
			dispose();
		}else {
			comboBox.setModel(new DefaultComboBoxModel(strings.toArray()));
			
			comboBox.setSelectedItem(null);
			comboBox.addActionListener(new ActionListener() {
				
			
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JComboBox jBox = (JComboBox) e.getSource();
					
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					textField_3.setText("");
					textField_4.setText("");
					table.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
									"Estudiante","Agregar", 
							}
					));
					try {
						
						List<String> cursos = iCurso.mostrarCursosEnInstituto((String) jBox.getSelectedItem());
						
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
								textField.setText("");
								textField_1.setText("");
								textField_2.setText("");
								textField_3.setText("");
								textField_4.setText("");
								table.setModel(new DefaultTableModel(
										new Object[][] {
										},
										new String[] {
												"Estudiante","Agregar", 
										}
								));
								JComboBox jBox2 = (JComboBox) e2.getSource();
								try {
									DtEdicion dtEdicion = iCurso.mostrarEdicionVigenteDeCurso((String) jBox2.getSelectedItem());
									textField.setText(dtEdicion.getNombre());
									textField_1.setText(String.valueOf(dtEdicion.getCupo()));
									String inicio;
									String fin;
									String alta;
									
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									inicio = sdf.format(dtEdicion.getFechaInicio().getTime());
									fin = sdf.format(dtEdicion.getFechaFin().getTime());
									alta = sdf.format(dtEdicion.getFechaPublicacion().getTime());
									textField_2.setText(inicio);
									textField_3.setText(fin);
									textField_4.setText(alta);
									
									List<String> eStrings= iCurso.getListaDeEstudiantes();
									Object[][] objects = new Object[eStrings.size()][2];
									for (int i = 0; i < eStrings.size(); i++) {
										objects[i][0]=eStrings.get(i);
										objects[i][1]=false;
									}
									cargarTabla(objects);
								} catch (CursoNoExisteException e) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
								} catch (EdicionNoExisteException e) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
								} catch (UsuarioNoExisteException e) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(getContentPane(), e.getMessage());
								} catch (Exception e3) {
									// TODO: handle exception
									JOptionPane.showMessageDialog(getContentPane(), "Alguna otra excepcion.");
								}
							}
						});
					} catch (InstitutoNoExisteException | CursoNoExisteException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(getContentPane(), e1.getMessage());
					}
					
				}
			});
		}
		
		
	}
}
