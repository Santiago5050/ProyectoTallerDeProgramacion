package presentacion;

import javax.swing.SwingUtilities;

import servidor.Publicador;

public class Main {

	public static void main(String[] args) {
		
		Publicador p = new Publicador();
        p.publicar();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new MainWindow();
			}
		});
	}

}
