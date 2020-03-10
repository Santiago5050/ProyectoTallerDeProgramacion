package presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import logica.CargaDatos;

public class MainWindow extends JFrame implements StringListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar;
	private JDesktopPane jPane;
	private JMenu mnUsuario;
	private JMenuItem mntmCrearProgramaDe;
	private JMenuItem mntmAltaDeUsuario;
	private JMenuItem mntmConsultaDeUsuario;
	private JMenu mnCurso;
	private JMenu mnProgramaDeFormacin;
	private JMenu mnCargarDatos;
	private boolean cargoDatos;
	
	public MainWindow() {
		// TODO Auto-generated constructor stub
		super("edExt");
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		setMinimumSize(new Dimension(900,700));
		setPreferredSize(new Dimension(900,700));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jMenuBar = new JMenuBar();
		getContentPane().add(jMenuBar,BorderLayout.NORTH);
		cargoDatos = false;
		
		mnUsuario = new JMenu("Usuario");
		jMenuBar.add(mnUsuario);
		
		mntmAltaDeUsuario = new JMenuItem("Alta de Usuario");
		mnUsuario.add(mntmAltaDeUsuario);		
		mntmAltaDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AltaDeUsuario ventana = new AltaDeUsuario();
					jPane.add(ventana);
					ventana.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		mnCurso = new JMenu("Curso");
		jMenuBar.add(mnCurso);
		
		JMenuItem mntmAltaDeCurso = new JMenuItem("Alta de Curso");
		mntmAltaDeCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AltaCurso ventanAltaCurso = new AltaCurso();
				jPane.add(ventanAltaCurso);
				try {
					ventanAltaCurso.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mnCurso.add(mntmAltaDeCurso);
		
		JMenuItem mntmAgregarCursoA = new JMenuItem("Agregar Curso a Programa de Formacion");
		mntmAgregarCursoA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgregarCursoAPrograma ventanAgregarCursoAPrograma = new AgregarCursoAPrograma();
				jPane.add(ventanAgregarCursoAPrograma);
				try {
					ventanAgregarCursoAPrograma.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnCurso.add(mntmAgregarCursoA);
		
		JMenuItem mntmAltaEdicionDe = new JMenuItem("Alta Edicion de Curso");
		mntmAltaEdicionDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AltaEdicionCurso vetnanaAltaEdicionCurso = new AltaEdicionCurso();
				jPane.add(vetnanaAltaEdicionCurso);
				try {
					vetnanaAltaEdicionCurso.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnCurso.add(mntmAltaEdicionDe);
		
		mnProgramaDeFormacin = new JMenu("Programa de Formación");
		JMenuItem mntmConsultaDeCurso = new JMenuItem("Consulta de Curso");
		mnCurso.add(mntmConsultaDeCurso);
		mntmConsultaDeCurso.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ConsultaCurso ins = new ConsultaCurso();
				jPane.add(ins);
				try {
					ins.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmInscripcionAEdicion = new JMenuItem("Inscripcion A Edicion De Curso");
		
		mntmInscripcionAEdicion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InscripcionAEdicionCurso ins = new InscripcionAEdicionCurso();
				jPane.add(ins);
				try {
					ins.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnCurso.add(mntmInscripcionAEdicion);
		
		JMenuItem mntmConsultaEdicinDe = new JMenuItem("Consulta Edición de Curso");
		mnCurso.add(mntmConsultaEdicinDe);
		
		JMenuItem mntmAltaCategoria = new JMenuItem("Alta Categoria");
		mnCurso.add(mntmAltaCategoria);
		
		mntmAltaCategoria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AltaCategoria ventana = new AltaCategoria();
					jPane.add(ventana);
					ventana.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		mntmConsultaEdicinDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ConsultaEdicionDeCurso ventana = new ConsultaEdicionDeCurso();
					jPane.add(ventana);
					ventana.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JMenu mnProgramaDeFormacin = new JMenu("Programa de Formación");
		jMenuBar.add(mnProgramaDeFormacin);
		
		mntmCrearProgramaDe = new JMenuItem("Crear Programa de Formación");
		mnProgramaDeFormacin.add(mntmCrearProgramaDe);
		
		JMenuItem mntmConsultaProgramaDe = new JMenuItem("Consulta Programa De Formacion");
		mnProgramaDeFormacin.add(mntmConsultaProgramaDe);
		mntmCrearProgramaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CrearProgramaDeFormacion ventana = new CrearProgramaDeFormacion();
					jPane.add(ventana);
					ventana.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		mntmConsultaProgramaDe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ConsultaProgramaFormacion cpf = new ConsultaProgramaFormacion();
				jPane.add(cpf);
				try {
					cpf.setSelected(true);
				} catch (Exception e3) {
					
				}
			}
		});
		
		JMenu mnCargarDatos = new JMenu("Cargar Datos");
		mnCargarDatos = new JMenu("Cargar Datos");
		jMenuBar.add(mnCargarDatos);
		
		JMenuItem mntmRealizarCarga = new JMenuItem("Realizar carga");
		mnCargarDatos.add(mntmRealizarCarga);
		jPane = new JDesktopPane();
		getContentPane().add(jPane, BorderLayout.CENTER);
		mntmRealizarCarga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!cargoDatos) {
						CargaDatos cargarDatos = new CargaDatos();
						cargarDatos.cargar();
						JOptionPane.showMessageDialog(getContentPane(), "Datos cargados con éxito", "Carga de datos", getDefaultCloseOperation());
						cargoDatos = true;
					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Los datos de prueba ya fueron cargados.", "Carga de datos", getDefaultCloseOperation());
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		mntmConsultaDeUsuario = new JMenuItem("Consulta de Usuario");
		mnUsuario.add(mntmConsultaDeUsuario);
		
		JMenuItem mntmModificarDatosDe = new JMenuItem("Modificar Datos de Usuario");
		mntmModificarDatosDe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ModificarDatosUsuario mdf=new ModificarDatosUsuario();
				jPane.add(mdf);
				try {
					mdf.setSelected(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnUsuario.add(mntmModificarDatosDe);
		mntmConsultaDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ConsultaDeUsuario ventana = new ConsultaDeUsuario();
					jPane.add(ventana);
					ventana.setSelected(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}

	@Override
	public void cargarPrograma(String s) {
		ConsultaProgramaFormacion cpf = new ConsultaProgramaFormacion();
		jPane.add(cpf);
		cpf.cargarPrograma(s);
		try {
			cpf.setSelected(true);
		} catch (Exception e) {
			
		}
		
		
	}

	@Override
	public void cargarCurso(String s) {
		ConsultaCurso cc = new ConsultaCurso();
		jPane.add(cc);
		cc.cargarCurso(s);
		try {
			cc.setSelected(true);
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public void cargarEdicion(String s) {
		ConsultaEdicionDeCurso ce;
			try {
				ce = new ConsultaEdicionDeCurso();
				jPane.add(ce);
				ce.cargarEdicion(s);
				try {
					ce.setSelected(true);
				} catch (Exception e) {
					
				}
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		
	}
}
