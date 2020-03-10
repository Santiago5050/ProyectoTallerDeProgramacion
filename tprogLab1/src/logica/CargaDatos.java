package logica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CargaDatos {
	
	public CargaDatos() {
		
	}
	
	public void cargar() throws Exception {
		
		// Carga de institutos.
		Factory factory = new Factory();
		InterfazControladorCurso icc = factory.getInterfazControladorCurso();
	//	try {
			icc.ingresarInstituto("INCO");
			icc.ingresarInstituto("IMERL");
			icc.ingresarInstituto("Física");
			icc.ingresarInstituto("IMPII");
			icc.ingresarInstituto("Eléctrica");
			icc.ingresarInstituto("DISI");
	//	} catch (Exception e) {
		//	e.getStackTrace();
	//	};
		
		// Carga de cursos.
	//	try {
			
			// Curso 1.
			icc.altaCursoSrc("IMERL", "Talleres plenarios", "*Talleres plenarios*: presentados por cuatro reconocidos\n" + 
					"matemáticos uruguayos, plantearán diversos tópicos de matemática\n" + 
					"en el marco de los cuales se realizarán actividades fomentando la\n" + 
					"integración entre estudiantes, docentes e investigadores.\n" + 
					"", "3 semanas", 15, 1, " www.tmu.edu.uy", new ArrayList<String>(), "http://bit.ly/2kPof8A");
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(2019, Calendar.FEBRUARY, 1);
			ManejadorCurso manejadorCurso = ManejadorCurso.getInstance();
			Curso curso = manejadorCurso.getCurso("Talleres plenarios");
			curso.setFechaRegistro(calendar1);
			
			// Curso 2.
			ArrayList<String> previas2 = new ArrayList<String>();
			previas2.add("Talleres plenarios");
			icc.altaCursoSrc("IMERL", "Seminarios de Resolución de Problemas", "Seminario, *todos los jueves* en Facultad de Ingeniería a\n" + 
					"partir del jueves 25 de Julio, en las áreas en que se desarrollan los\n" + 
					" problemas de las Olimpíadas de Matemática.\n" + 
					"", "5 semanas", 30, 2, "www.tmu.edu.uy", previas2, "http://bit.ly/2mnDYwd");
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(2019, Calendar.JULY, 12);
			curso = manejadorCurso.getCurso("Seminarios de Resolución de Problemas");
			curso.setFechaRegistro(calendar2);
			
			// Curso 3.
			ArrayList<String> previas3 = new ArrayList<String>();
			previas3.add("Talleres plenarios");
			icc.altaCursoSrc("IMPII", "Dalavuelta", "Dalavuelta es un proyecto de extensión que nace en el Instituto de\n" + 
					"Ingeniería Mecánica y Producción Industrial (IIMPI) de Fing, que, si\n" + 
					"bien inicia su trabajo en el desarrollo de bicicletas accesibles para\n" + 
					"personas en situación de discapacidad motriz a partir de bicicletas\n" + 
					"abandonadas, se propuso diseñar otras herramientas para fomentar\n" + 
					"la accesibilidad.\n" + 
					"\n" + 
					"", "10 semanas", 60, 4, "https://eva.fing.edu.uy/course/view.php?id=783#section-2", previas3, "http://bit.ly/2lRnEU1");
			Calendar calendar3 = Calendar.getInstance();
			calendar3.set(2017, Calendar.JUNE, 25);
			curso = manejadorCurso.getCurso("Dalavuelta");
			curso.setFechaRegistro(calendar3);
			
			// Curso 4.
			ArrayList<String> previas4 = new ArrayList<String>();
			previas4.add("Talleres plenarios");
			icc.altaCursoSrc("IMPII", "Extensionismo Industrial", "El proyecto tiene como objetivo desarrollar intervenciones\n" + 
					"curriculares en pequeños emprendimientos productivos de\n" + 
					"diferentes sectores de la industria nacional.La metodologías de\n" + 
					"trabajo permite articular diversas intervenciones, combinando\n" + 
					"actividades de enseñanza, extensión e investigación por parte de\n" + 
					"docentes del IMPII.\n" + 
					"", "12 semanas", 75, 5, "https://eva.fing.edu.uy/course/view.php?id=783#section-2", previas4, "http://bit.ly/2mngdEx");
			Calendar calendar4 = Calendar.getInstance();
			calendar4.set(2018, Calendar.JUNE, 16);
			curso = manejadorCurso.getCurso("Extensionismo Industrial");
			curso.setFechaRegistro(calendar4);
			
			// Curso 5.
			icc.altaCursoSrc("IMPII", "Inclusión Energética", "En el proyecto se conjuga el trabajo de docentes y estudiantes de la\n" + 
					"carrera Ingeniería Industrial Mecánica a través del Módulo de\n" + 
					"Extensión, en donde se trabaja en el diseño, construcción y prueba\n" + 
					"de un prototipo de colector solar adquiriendo conocimientos\n" + 
					"relevantes para luego poder replicarlos junto a las familias en los\n" + 
					"talleres. Las premisas fundamentales a la hora de pensar los diseños\n" + 
					"son: por un lado el bajo costo de los materiales y por otro la fácil\n" + 
					"construcción de forma de poder construirlos ellos mismos.\n" + 
					"", "6 semanas", 45, 3, " https://eva.fing.edu.uy", new ArrayList<String>(), "http://bit.ly/2kPGN8K");
			Calendar calendar5 = Calendar.getInstance();
			calendar5.set(2019, Calendar.FEBRUARY, 1);
			curso = manejadorCurso.getCurso("Inclusión Energética");
			curso.setFechaRegistro(calendar5);
			
			// Curso 6.
			icc.altaCursoSrc("DISI", "Flor del Ceibo", "Flor de Ceibo es un proyecto central de la Universidad de la\n" + 
					"República, que tiene misión por movilizar la participación de\n" + 
					" estudiantes universitarios en diversas tareas vinculadas con la\n" + 
					" puesta en funcionamiento del Plan Ceibal en el territorio nacional.\n" + 
					"", "15 semanas", 150, 10, "http://www.flordeceibo.edu.uy/", new ArrayList<String>(), "http://bit.ly/2mmRXCk");
			Calendar calendar6 = Calendar.getInstance();
			calendar6.set(2008, Calendar.JULY, 27);
			curso = manejadorCurso.getCurso("Flor del Ceibo");
			curso.setFechaRegistro(calendar6);
			
			// Curso 7.
			icc.altaCursoSrc("INCO", "Taller de robótica educativa", "La asignatura se organiza en dos etapas. La primer etapa se dicta a\n" + 
					"través de clases teóricoprácticas, donde se espera además que cada\n" + 
					"estudiante le dedique horas de estudio.\n" + 
					" La segunda etapa consiste en que los estudiantes trabajen en grupo\n" + 
					" sobre el diseño e implementación de una experiencia didáctica de\n" + 
					" inclusión del robot Butiá en el aula, utilizando los conocimientos\n" + 
					"aprendidos en clase.\n" + 
					"Se propone desarrollar una aplicación interactiva para tablet\n" + 
					"", "8 semanas", 90, 6, "https://eva.fing.edu.uy/course/view.php?id=1187", new ArrayList<String>(), "http://localhost:8080/edext-server-web/images/red4.png");
			Calendar calendar7 = Calendar.getInstance();
			calendar7.set(2017, Calendar.JULY, 2);
			curso = manejadorCurso.getCurso("Taller de robótica educativa");
			curso.setFechaRegistro(calendar7);
			
			// Curso 8.
			icc.altaCurso("INCO", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", "Se propone desarrollar una aplicación interactiva para tablet\n" + 
					"Android basada en el juego de tablero Komikan (versión web del\n" + 
					"juego\n" + 
					"https://codepen.io/Borborem/full/OvZBvZ/), que incluya los\n" + 
					"distintos aspectos concernientes al juego, así como a situaciones\n" + 
					"específicas particulares.", "9 semanas", 45, 3, "https://eva.fing.edu.uy/mod/folder/view.php?id=89398", new ArrayList<String>());
			Calendar calendar8 = Calendar.getInstance();
			calendar8.set(2019, Calendar.JUNE, 15);
			curso = manejadorCurso.getCurso("Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela");
			curso.setFechaRegistro(calendar8);
			
			// Curso 9.
			icc.altaCurso("INCO", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Se realizarán visitas a escuelas rurales participantes en un proyecto\n" + 
					"conjunto del grupo PLN y el Programa de Políticas Lingüísticas de\n" + 
					"ANEP, en el marco del cual se desarrollaron diferentes\n" + 
					"herramientas para uso de maestros que enseñan inglés con apoyo\n" + 
					"remoto de profesores especializados desde Montevideo.\n" + 
					"", "12 semanas", 60, 4, "https://eva.fing.edu.uy/mod/folder/view.php?id=89398", new ArrayList<String>());
			Calendar calendar9 = Calendar.getInstance();
			calendar9.set(2019, Calendar.MAY, 24);
			curso = manejadorCurso.getCurso("“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”");
			curso.setFechaRegistro(calendar9);
			
			// Curso 10.
			icc.altaCursoSrc("Eléctrica", "MicroBit", "El Centro Ceibal se encuentra distribuyendo placas micro:bit\n" + 
					"(https://microbit.ceibal.edu.uy/) para que estudiantes de primaria\n" + 
					"y secundaria aprendan nociones básicas de robótica, electrónica y\n" + 
					"programación de forma autónoma y lúdica. Estas placas se basan en\n" + 
					"un microcontrolador y cuentan con leds, botones, acelerómetro,\n" + 
					"brújula, bluetooth y otros sensores. Además, las placas se\n" + 
					"programan fácilmente con lenguaje tipo “scratch” y python, por lo\n" + 
					"que son muy útiles para un primer acercamiento a la temática.\n" + 
					"", "15 semanas", 105, 7, "https://www.fing.edu.uy/noticias/extension/modulo-de-tallerextension-microbit", new ArrayList<String>(), "http://bit.ly/2kOZ6ec");
			Calendar calendar10 = Calendar.getInstance();
			calendar10.set(2019, Calendar.MARCH, 13);
			curso = manejadorCurso.getCurso("MicroBit");
			curso.setFechaRegistro(calendar10);
			
//		} catch (CursoYaExisteException | InstitutoNoExisteException | CursoNoExisteException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
		
		// Carga de usuarios.
		InterfazControladorUsuario icu = factory.getInterfazControladorUsuario();
		Calendar calUsuario = Calendar.getInstance();
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1971, Calendar.DECEMBER, 31);
		icu.altaEstudianteSrc("eleven11", "Eleven", "", "eleven11@gmail.com", calUsuario, "LzrZzge5", "bit.ly/11Eleven11");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1983, Calendar.NOVEMBER, 15);
		icu.altaEstudianteSrc("costas", "Gerardo", "Costas", "gcostas@gmail.com", calUsuario, "nQ57u5az", "http://bit.ly/1yTfPav");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1975, Calendar.AUGUST, 2);
		icu.altaEstudianteSrc("roro", "Rodrigo", "Cotelo", "rcotelo@yahoo.com", calUsuario, "Xci95w9i", "http://bit.ly/Xwsg0F");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1987, Calendar.SEPTEMBER, 12);
		icu.altaEstudianteSrc("chechi", "Cecilia", "Garrido", "cgarrido@hotmail.com", calUsuario, "RqF7U579", "http://bit.ly/1AtwVOA");

		calUsuario = Calendar.getInstance();
		calUsuario.set(1964, Calendar.NOVEMBER, 27);
		icu.altaEstudiante("jeffw", "Jeff", "Williams", "jwilliams@gmail.com", calUsuario, "CQQ8MqpJ");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1992, Calendar.MAY, 21);
		icu.altaEstudianteSrc("Juanse", "Juan", "Segura", "juanse92@gmail.com", calUsuario, "qwer456", "http://bit.ly/1uPV2XC");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1978, Calendar.DECEMBER, 23);
		icu.altaEstudianteSrc("weiss", "Adrian", "Weiss", "aweiss@hotmail.com", calUsuario, "CN3zuf8Y", "http://bit.ly/1uPV2XC");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1970, Calendar.APRIL, 7);
		icu.altaEstudianteSrc("Anab", "Ana", "Belen", "ana_belen@lapuerta.es", calUsuario, "awsedrf678", "http://bit.ly/1uPV2XC");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1970, Calendar.JANUARY, 3);
		icu.altaEstudianteSrc("Steveh", "Steve", "Harris", "steve@iron.com", calUsuario, "ZxCv123", "https://bit.ly/32zinQG");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1985, Calendar.MAY, 24);
		icu.altaEstudianteSrc("nadiav", "Nadia", "Vulvokov", "vulvokov01@yahoo.es", calUsuario, "098okjH", "https://bit.ly/32yAupV");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1992, Calendar.MAY, 12);
		icu.altaEstudianteSrc("Keating", "Annalise", "Keating", "analise43@hotmail.com", calUsuario, "099ftgyh", "https://bit.ly/2X1pe45");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1995, Calendar.JANUARY, 6);
		icu.altaEstudianteSrc("saul", "Saul", "Goodman", "goodman@hotmail.com", calUsuario, "VbN567", "https://bit.ly/33zo0zF");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1994, Calendar.MARCH, 15);
		icu.altaEstudianteSrc("Sallyo", "Sally", "Owens", "sallyo33@elcaldero.com", calUsuario, "kjh45XC", "https://bzfd.it/2X24XeS");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1995, Calendar.JUNE, 21);
		icu.altaEstudianteSrc("Otism", "Otis", "Milburn", "otism95@gmail.com", calUsuario, "sexEd101", "https://bit.ly/36TRKsX");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1956, Calendar.MARCH, 7);
		icu.altaDocenteSrc("heisenberg", "Walter", "White", "heisenberg@gmail.com", calUsuario, "INCO", "zDUTwJD7", "http://bit.ly/2kR3Csv");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1914, Calendar.APRIL, 2);
		icu.altaDocenteSrc("benkenobi", "Obi-Wan", "Kenobi", "benKenobi@gmail.com", calUsuario, "INCO", "5aM8CGch", "http://bit.ly/2lU9e5y");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1990, Calendar.APRIL, 15);
		icu.altaDocenteSrc("watson", "Emma", "Watson", "e.watson@gmail.com", calUsuario, "INCO", "ahtL8FzL", "http://bit.ly/2kEQI0Y");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1959, Calendar.MAY, 15);
		icu.altaDocenteSrc("house", "Gregory", "House", "greghouse@gmail.com", calUsuario, "Eléctrica", "XTMWJ8iT", "http://bit.ly/2lTUCDg");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1960, Calendar.NOVEMBER, 1);
		icu.altaDocenteSrc("timmy", "Tim", "Cook", "tim.cook@apple.com", calUsuario, "IMERL", "6McCUA9g", "http://bit.ly/2kRed6K");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1963, Calendar.JULY, 5);
		icu.altaDocenteSrc("danny", "Daniel", "Riccio", "dan.riccio@gmail.com", calUsuario, "IMERL", "4WgRR6nL", "http://bit.ly/2kRnpYR");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1961, Calendar.OCTOBER, 7);
		icu.altaDocenteSrc("phils", "Philip", "Schiller", "schiller@gmail.com", calUsuario, "IMPII", "4KJeAikG", "http://bit.ly/2kQ4Cxc");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1959, Calendar.DECEMBER, 3);
		icu.altaDocenteSrc("bruces", "Bruce", "Sewell", "sewell@gmail.com", calUsuario, "DISI", "7KTCP7YL", "http://bit.ly/2mjQAEl");
		
		calUsuario = Calendar.getInstance();
		calUsuario.set(1978, Calendar.JULY, 28);
		icu.altaDocente("adri", "Adriana", "García", "agarcia@gmail.com", calUsuario, "DISI", "EB6Vq6Dj");
		
		// Carga ediciones.
//		try {
			Calendar ini = Calendar.getInstance();
			Calendar fin = Calendar.getInstance();
			Calendar alta = Calendar.getInstance();
			ini.set(2010, Calendar.MARCH, 15);
			fin.set(2010, Calendar.JULY, 7);
			alta.set(2010, Calendar.FEBRUARY, 16);
			ArrayList<String> docentes = new ArrayList<String>();
			docentes.add("bruces");
			icc.altaEdicionCursoSrc("Flor del Ceibo - 2010", ini, fin, 0, "Flor del Ceibo", docentes, "DISI", "http://localhost:8080/edext-server-web/images/red4.png");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2012, Calendar.AUGUST, 1);
			fin.set(2012, Calendar.NOVEMBER, 20);
			alta.set(2012, Calendar.JULY, 10);
			docentes = new ArrayList<String>();
			docentes.add("bruces");
			docentes.add("adri");
			icc.altaEdicionCursoSrc("Flor del Ceibo - 2012", ini, fin, 0, "Flor del Ceibo", docentes, "DISI", "http://bit.ly/2kqYgEm");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2018, Calendar.APRIL, 10);
			fin.set(2018, Calendar.AUGUST, 7);
			alta.set(2018, Calendar.MARCH, 6);
			docentes = new ArrayList<String>();
			docentes.add("bruces");
			docentes.add("adri");
			icc.altaEdicionCursoSrc("Flor del Ceibo - 2018", ini, fin, 0, "Flor del Ceibo", docentes, "DISI", "http://bit.ly/2lSYzrY");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2017, Calendar.AUGUST, 20);
			fin.set(2017, Calendar.NOVEMBER, 10);
			alta.set(2017, Calendar.JULY, 20);
			docentes = new ArrayList<String>();
			docentes.add("phils");
			icc.altaEdicionCursoSrc("Dalavuelta - 2018", ini, fin, 5, "Dalavuelta", docentes, "IMPII", "http://bit.ly/2lSzxcu");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2018, Calendar.AUGUST, 10);
			fin.set(2018, Calendar.NOVEMBER, 10);
			alta.set(2018, Calendar.JULY, 8);
			docentes = new ArrayList<String>();
			docentes.add("phils");
			icc.altaEdicionCurso("Extensionismo Industrial - 2018", ini, fin, 5, "Extensionismo Industrial", docentes, "IMPII");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.MARCH, 15);
			fin.set(2019, Calendar.APRIL, 30);
			alta.set(2019, Calendar.FEBRUARY, 20);
			docentes = new ArrayList<String>();
			docentes.add("phils");
			icc.altaEdicionCursoSrc("Inclusión Energética - 2019", ini, fin, 5, "Inclusión Energética", docentes, "IMPII", "http://bit.ly/2lTiqqY");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2017, Calendar.MARCH, 10);
			fin.set(2017, Calendar.MAY, 10);
			alta.set(2017, Calendar.FEBRUARY, 15);
			docentes = new ArrayList<String>();
			docentes.add("heisenberg");
			docentes.add("watson");
			icc.altaEdicionCursoSrc("Taller de robótica educativa - 2017", ini, fin, 5, "Taller de robótica educativa", docentes, "INCO", "http://bit.ly/2kFn9fI");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.MARCH, 10);
			fin.set(2019, Calendar.MAY, 10);
			alta.set(2019, Calendar.FEBRUARY, 15);
			docentes = new ArrayList<String>();
			docentes.add("heisenberg");
			docentes.add("benkenobi");
			icc.altaEdicionCursoSrc("Taller de robótica educativa - 2019", ini, fin, 5, "Taller de robótica educativa", docentes, "INCO", "http://bit.ly/2kOYlln");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.SEPTEMBER, 10);
			fin.set(2019, Calendar.NOVEMBER, 28);
			alta.set(2019, Calendar.AUGUST, 15);
			docentes = new ArrayList<String>();
			docentes.add("watson");
			docentes.add("benkenobi");
			icc.altaEdicionCursoSrc("Taller de robótica educativa - 2019-2", ini, fin, 5, "Taller de robótica educativa", docentes, "INCO", "http://bit.ly/2kOYpS9");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.JULY, 29);
			fin.set(2019, Calendar.OCTOBER, 7);
			alta.set(2019, Calendar.JULY, 10);
			docentes = new ArrayList<String>();
			docentes.add("watson");
			icc.altaEdicionCurso("Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", ini, fin, 5, "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", docentes, "INCO");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.SEPTEMBER, 15);
			fin.set(2019, Calendar.DECEMBER, 15);
			alta.set(2019, Calendar.JUNE, 2);
			docentes = new ArrayList<String>();
			docentes.add("heisenberg");
			icc.altaEdicionCurso("Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", ini, fin, 5, "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", docentes, "INCO");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.AUGUST, 12);
			fin.set(2019, Calendar.DECEMBER, 5);
			alta.set(2019, Calendar.JULY, 2);
			docentes = new ArrayList<String>();
			docentes.add("house");
			icc.altaEdicionCursoSrc("MicroBit - 2019", ini, fin, 5, "MicroBit", docentes, "Eléctrica", "http://bit.ly/2ki18mK");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.MARCH, 10);
			fin.set(2019, Calendar.MARCH, 30);
			alta.set(2019, Calendar.MARCH, 2);
			docentes = new ArrayList<String>();
			docentes.add("timmy");
			docentes.add("danny");
			icc.altaEdicionCursoSrc("Talleres plenarios - 2019", ini, fin, 0, "Talleres plenarios", docentes, "IMERL", "http://bit.ly/2ki1oSK");
			
			ini = Calendar.getInstance();
			fin = Calendar.getInstance();
			alta = Calendar.getInstance();
			ini.set(2019, Calendar.SEPTEMBER, 10);
			fin.set(2019, Calendar.OCTOBER, 20);
			alta.set(2019, Calendar.JULY, 12);
			docentes = new ArrayList<String>();
			docentes.add("timmy");
			icc.altaEdicionCursoSrc("Seminarios de Resolución de Problemas - 2019", ini, fin, 0, "Seminarios de Resolución de Problemas", docentes, "IMERL", "http://bit.ly/2kF5zIN");
//		} catch (InstitutoNoExisteException | InstitutoNoBrindaCursoException | UsuarioNoExisteException | CursoNoExisteException | DocenteNoPerteneceMismoInstitutoException | EstudianteComoDocenteException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
		// Programas de Formación.
		InterfazControladorProgramaFormacion icf = factory.getInterfazControladorProgramaFormacion();
		Calendar progIni = Calendar.getInstance();
		Calendar progFin = Calendar.getInstance();
		
		progIni = Calendar.getInstance();
		progFin = Calendar.getInstance();
		progIni.set(2019, Calendar.MAY, 1);
		progFin.set(2019, Calendar.OCTOBER, 31);
		icf.crearProgramaSource("EFI Ingeniería Mecánica", "Programa mecánica", progIni, progFin, "http://bit.ly/2lUzDAc");
		
		progIni = Calendar.getInstance();
		progFin = Calendar.getInstance();
		progIni.set(2019, Calendar.JULY, 15);
		progFin.set(2020, Calendar.JANUARY, 1);
		icf.crearPrograma("Formación integral", "Programa varios institutos", progIni, progFin);
		
		progIni = Calendar.getInstance();
		progFin = Calendar.getInstance();
		progIni.set(2019, Calendar.SEPTEMBER, 3);
		progFin.set(2019, Calendar.NOVEMBER, 18);
		icf.crearProgramaSource("EFI Robótica", "Programa robótica", progIni, progFin, "http://bit.ly/2kFcGRu");
		
		// Agrego cursos a programas.
//		try {
			icf.agregarCursoAPrograma("EFI Ingeniería Mecánica", "Dalavuelta");
			icf.agregarCursoAPrograma("EFI Ingeniería Mecánica", "Extensionismo Industrial");
			icf.agregarCursoAPrograma("EFI Ingeniería Mecánica", "Inclusión Energética");
			icf.agregarCursoAPrograma("Formación integral", "Seminarios de Resolución de Problemas");
			icf.agregarCursoAPrograma("Formación integral", "Extensionismo Industrial");
			icf.agregarCursoAPrograma("Formación integral", "Flor del Ceibo");
			icf.agregarCursoAPrograma("Formación integral", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela");
			icf.agregarCursoAPrograma("EFI Robótica", "Taller de robótica educativa");
			icf.agregarCursoAPrograma("EFI Robótica", "MicroBit");
//		} catch (ProgramaNoExisteException | CursoNoExisteException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		
		// Inscribo estudiantes a cursos.
	//	try {
			Calendar insc = Calendar.getInstance();
			insc.set(2010, Calendar.FEBRUARY, 20);
			icc.inscribirEstudianteACurso("eleven11", "Flor del Ceibo", "Flor del Ceibo - 2010", insc, "https://www.youtube.com/embed/fryZlu3DUPo");
			
			insc = Calendar.getInstance();
			insc.set(2010, Calendar.FEBRUARY, 25);
			icc.inscribirEstudianteACurso("chechi", "Flor del Ceibo", "Flor del Ceibo - 2010", insc, "https://www.youtube.com/embed/b89CnP0Iq30");
			
			insc = Calendar.getInstance();
			insc.set(2012, Calendar.JULY, 12);
			icc.inscribirEstudianteACurso("costas", "Flor del Ceibo", "Flor del Ceibo - 2012", insc, "https://www.youtube.com/embed/eIvqPPcMepU");
			
			insc = Calendar.getInstance();
			insc.set(2012, Calendar.JULY, 15);
			icc.inscribirEstudianteACurso("roro", "Flor del Ceibo", "Flor del Ceibo - 2012", insc, "https://www.youtube.com/embed/vfc42Pb5RA8");
			
			insc = Calendar.getInstance();
			insc.set(2012, Calendar.JULY, 30);
			icc.inscribirEstudianteACurso("weiss", "Flor del Ceibo", "Flor del Ceibo - 2012", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.MARCH, 10);
			icc.inscribirEstudianteACurso("roro", "Flor del Ceibo", "Flor del Ceibo - 2018", insc, "https://www.youtube.com/embed/3cZrMUtmTLQ");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.MARCH, 15);
			icc.inscribirEstudianteACurso("jeffw", "Flor del Ceibo", "Flor del Ceibo - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.MARCH, 2);
			icc.inscribirEstudianteACurso("Otism", "Flor del Ceibo", "Flor del Ceibo - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.MARCH, 11);
			icc.inscribirEstudianteACurso("Keating", "Flor del Ceibo", "Flor del Ceibo - 2018", insc, "https://www.youtube.com/embed/PUD36wlkmJ4");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.MARCH, 10);
			icc.inscribirEstudianteACurso("nadiav", "Flor del Ceibo", "Flor del Ceibo - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.JULY, 25);
			icc.inscribirEstudianteACurso("chechi", "Dalavuelta", "Dalavuelta - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.JULY, 28);
			icc.inscribirEstudianteACurso("eleven11", "Dalavuelta", "Dalavuelta - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.AUGUST, 2);
			icc.inscribirEstudianteACurso("roro", "Dalavuelta", "Dalavuelta - 2018", insc, "https://www.youtube.com/embed/7EyyqpLQAYA");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.AUGUST, 10);
			icc.inscribirEstudianteACurso("costas", "Dalavuelta", "Dalavuelta - 2018", insc, "https://www.youtube.com/embed/FOQvGCFjpXo");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.AUGUST, 15);
			icc.inscribirEstudianteACurso("jeffw", "Dalavuelta", "Dalavuelta - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.JULY, 18);
			icc.inscribirEstudianteACurso("costas", "Extensionismo Industrial", "Extensionismo Industrial - 2018", insc, "https://www.youtube.com/embed/p6RKNMlXoyk");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.JULY, 20);
			icc.inscribirEstudianteACurso("chechi", "Extensionismo Industrial", "Extensionismo Industrial - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.JULY, 29);
			icc.inscribirEstudianteACurso("eleven11", "Extensionismo Industrial", "Extensionismo Industrial - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2018, Calendar.AUGUST, 5);
			icc.inscribirEstudianteACurso("weiss", "Extensionismo Industrial", "Extensionismo Industrial - 2018", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.FEBRUARY, 23);
			icc.inscribirEstudianteACurso("roro", "Inclusión Energética", "Inclusión Energética - 2019", insc, "https://www.youtube.com/embed/Pn_bGOSSvYU");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.FEBRUARY, 25);
			icc.inscribirEstudianteACurso("weiss", "Inclusión Energética", "Inclusión Energética - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.FEBRUARY, 28);
			icc.inscribirEstudianteACurso("chechi", "Inclusión Energética", "Inclusión Energética - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.MARCH, 3);
			icc.inscribirEstudianteACurso("eleven11", "Inclusión Energética", "Inclusión Energética - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.FEBRUARY, 18);
			icc.inscribirEstudianteACurso("weiss", "Taller de robótica educativa", "Taller de robótica educativa - 2017", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.FEBRUARY, 20);
			icc.inscribirEstudianteACurso("roro", "Taller de robótica educativa", "Taller de robótica educativa - 2017", insc, "https://www.youtube.com/embed/EllYgcWmcAY");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.MARCH, 3);
			icc.inscribirEstudianteACurso("eleven11", "Taller de robótica educativa", "Taller de robótica educativa - 2017", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2017, Calendar.MARCH, 5);
			icc.inscribirEstudianteACurso("chechi", "Taller de robótica educativa", "Taller de robótica educativa - 2017", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.FEBRUARY, 18);
			icc.inscribirEstudianteACurso("jeffw", "Taller de robótica educativa", "Taller de robótica educativa - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.FEBRUARY, 22);
			icc.inscribirEstudianteACurso("costas", "Taller de robótica educativa", "Taller de robótica educativa - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.FEBRUARY, 23);
			icc.inscribirEstudianteACurso("roro", "Taller de robótica educativa", "Taller de robótica educativa - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 18);
			icc.inscribirEstudianteACurso("weiss", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 22);
			icc.inscribirEstudianteACurso("chechi", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.SEPTEMBER, 3);
			icc.inscribirEstudianteACurso("roro", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.SEPTEMBER, 1);
			icc.inscribirEstudianteACurso("Keating", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "https://www.youtube.com/embed/6ZKoXTOqenw");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.SEPTEMBER, 2);
			icc.inscribirEstudianteACurso("saul", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 31);
			icc.inscribirEstudianteACurso("Anab", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.SEPTEMBER, 1);
			icc.inscribirEstudianteACurso("Sallyo", "Taller de robótica educativa", "Taller de robótica educativa - 2019-2", insc, "https://www.youtube.com/embed/Y7tBpv6sjiw");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 13);
			icc.inscribirEstudianteACurso("chechi", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 20);
			icc.inscribirEstudianteACurso("weiss", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 22);
			icc.inscribirEstudianteACurso("roro", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JUNE, 4);
			icc.inscribirEstudianteACurso("weiss", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 1);
			icc.inscribirEstudianteACurso("Steveh", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 18);
			icc.inscribirEstudianteACurso("Juanse", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 23);
			icc.inscribirEstudianteACurso("nadiav", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "https://www.youtube.com/embed/VGovcwfV7A4");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 1);
			icc.inscribirEstudianteACurso("saul", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 17);
			icc.inscribirEstudianteACurso("Otism", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 18);
			icc.inscribirEstudianteACurso("eleven11", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 20);
			icc.inscribirEstudianteACurso("jeffw", "“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", "Herramientas de apoyo a la enseñanza de inglés. Instalación y evaliación - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 12);
			icc.inscribirEstudianteACurso("chechi", "MicroBit", "MicroBit - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 14);
			icc.inscribirEstudianteACurso("roro", "MicroBit", "MicroBit - 2019", insc, "https://www.youtube.com/embed/MGvt_-zxocM");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 25);
			icc.inscribirEstudianteACurso("eleven11", "MicroBit", "MicroBit - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 5);
			icc.inscribirEstudianteACurso("jeffw", "MicroBit", "MicroBit - 2019", insc, "https://www.youtube.com/embed/oZrCVCk_lw4");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JANUARY, 5);
			icc.inscribirEstudianteACurso("costas", "Talleres plenarios", "Talleres plenarios - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.MARCH, 4);
			icc.inscribirEstudianteACurso("weiss", "Talleres plenarios", "Talleres plenarios - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.MARCH, 7);
			icc.inscribirEstudianteACurso("roro", "Talleres plenarios", "Talleres plenarios - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 15);
			icc.inscribirEstudianteACurso("weiss", "Seminarios de Resolución de Problemas", "Seminarios de Resolución de Problemas - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 20);
			icc.inscribirEstudianteACurso("costas", "Seminarios de Resolución de Problemas", "Seminarios de Resolución de Problemas - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 6);
			icc.inscribirEstudianteACurso("roro", "Seminarios de Resolución de Problemas", "Seminarios de Resolución de Problemas - 2019", insc, "");
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 30);
			icc.inscribirEstudianteACurso("chechi", "Seminarios de Resolución de Problemas", "Seminarios de Resolución de Problemas - 2019", insc, "");
			
			
			// Aceptar o rechazar estudiantes.
			Map<String, Boolean> eleccion = new HashMap<String, Boolean>();
			//eleccion.put("eleven11", true);
			//eleccion.put("chechi", true);
			//icu.inscribirEstudiantesPorUnDocente("bruces", "Flor del Ceibo - 2010", eleccion);
			
			//eleccion = new HashMap<String, Boolean>();
			//eleccion.put("costas", true);
			//eleccion.put("roro", false);
			//eleccion.put("weiss", true);
			//icu.inscribirEstudiantesPorUnDocente("bruces", "Flor del Ceibo - 2012", eleccion);
			
			//eleccion = new HashMap<String, Boolean>();
			//eleccion.put("roro", true);
			//eleccion.put("", true);
			//icu.inscribirEstudiantesPorUnDocente("jeffw", "Flor del Ceibo - 2018", eleccion);
			
			eleccion = new HashMap<String, Boolean>();
			eleccion.put("chechi", true);
			eleccion.put("eleven11", false);
			eleccion.put("roro", true);
			eleccion.put("costas", true);
			eleccion.put("jeffw", false);
			icu.inscribirEstudiantesPorUnDocente("phils", "Dalavuelta - 2018", eleccion);
			
			eleccion = new HashMap<String, Boolean>();
			eleccion.put("costas", true);
			eleccion.put("chechi", true);
			eleccion.put("eleven11", true);
			eleccion.put("weiss", true);
			icu.inscribirEstudiantesPorUnDocente("phils", "Extensionismo Industrial - 2018", eleccion);
			
			eleccion = new HashMap<String, Boolean>();
			eleccion.put("roro", true);
			eleccion.put("weiss", false);
			eleccion.put("chechi", true);
			eleccion.put("eleven11", false);
			icu.inscribirEstudiantesPorUnDocente("phils", "Inclusión Energética - 2019", eleccion);
			
			eleccion = new HashMap<String, Boolean>();
			eleccion.put("weiss", false);
			eleccion.put("roro", false);
			eleccion.put("eleven11", true);
			icu.inscribirEstudiantesPorUnDocente("heisenberg", "Taller de robótica educativa - 2017", eleccion);
			
			eleccion = new HashMap<String, Boolean>();
			eleccion.put("jeffw", true);
			eleccion.put("costas", true);
			eleccion.put("roro", false);
			icu.inscribirEstudiantesPorUnDocente("heisenberg", "Taller de robótica educativa - 2019", eleccion);
			
			eleccion = new HashMap<String, Boolean>();
			eleccion.put("chechi", true);
			eleccion.put("weiss", true);
			eleccion.put("roro", true);
			icu.inscribirEstudiantesPorUnDocente("watson", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", eleccion);
			
			//eleccion = new HashMap<String, Boolean>();
			//eleccion.put("costas", false);
			//eleccion.put("weiss", true);
			//eleccion.put("roro", true);
			//icu.inscribirEstudiantesPorUnDocente("timmy", "Talleres plenarios - 2019", eleccion);
			
			//eleccion = new HashMap<String, Boolean>();
			//eleccion.put("weiss", true);
			//eleccion.put("costas", false);
			//eleccion.put("roro", true);
			//eleccion.put("chechi", false);
			//icu.inscribirEstudiantesPorUnDocente("timmy", "Seminarios de Resolución de Problemas - 2019", eleccion);
			
			
			// Inscribir estudiantes a programas
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.APRIL, 15);
			icu.inscribirEstudiantePrograma("eleven11", "EFI Ingeniería Mecánica", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.APRIL, 20);
			icu.inscribirEstudiantePrograma("chechi", "EFI Ingeniería Mecánica", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 10);
			icu.inscribirEstudiantePrograma("costas", "Formación integral", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 10);
			icu.inscribirEstudiantePrograma("chechi", "Formación integral", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 12);
			icu.inscribirEstudiantePrograma("eleven11", "Formación integral", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 15);
			icu.inscribirEstudiantePrograma("weiss", "Formación integral", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.JULY, 18);
			icu.inscribirEstudiantePrograma("roro", "Formación integral", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 11);
			icu.inscribirEstudiantePrograma("costas", "EFI Robótica", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 15);
			icu.inscribirEstudiantePrograma("chechi", "EFI Robótica", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 22);
			icu.inscribirEstudiantePrograma("roro", "EFI Robótica", insc);
			
			insc = Calendar.getInstance();
			insc.set(2019, Calendar.AUGUST, 30);
			icu.inscribirEstudiantePrograma("jeffw", "EFI Robótica", insc);
			
			
			// Alta de categorías
			icc.altaCategoria("Social");
			icc.altaCategoria("Industrial");
			icc.altaCategoria("Educativo");
			icc.altaCategoria("Interdisciplinario");
			
			
			// Agregar categorías a cursos.
			ArrayList<String> categorias = new ArrayList<String>();
			categorias.add("Social");
			icc.agregarCategoriasACurso("Dalavuelta", categorias);
			icc.agregarCategoriasACurso("Taller de robótica educativa", categorias);
			icc.agregarCategoriasACurso("“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", categorias);
			categorias.clear();
			categorias.add("Industrial");
			icc.agregarCategoriasACurso("Extensionismo Industrial", categorias);
			icc.agregarCategoriasACurso("Inclusión Energética", categorias);
			icc.agregarCategoriasACurso("MicroBit", categorias);
			categorias.clear();
			categorias.add("Educativo");
			icc.agregarCategoriasACurso("Flor del Ceibo", categorias);
			icc.agregarCategoriasACurso("Taller de robótica educativa", categorias);
			icc.agregarCategoriasACurso("Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", categorias);
			icc.agregarCategoriasACurso("“Herramientas de apoyo a la enseñanza de inglés. Instalación y evaluación”", categorias);
			icc.agregarCategoriasACurso("MicroBit", categorias);
			categorias.clear();
			categorias.add("Interdisciplinario");
			icc.agregarCategoriasACurso("Flor del Ceibo", categorias);
			
			
			
			icc.valorarUnCurso("Flor del Ceibo", 3, "eleven11");
			icc.valorarUnCurso("Flor del Ceibo", 4, "chechi");
			icc.valorarUnCurso("Flor del Ceibo", 5, "costas");
			icc.valorarUnCurso("Flor del Ceibo", 4, "weiss");
			icc.valorarUnCurso("Flor del Ceibo", 3, "roro");
			icc.valorarUnCurso("Flor del Ceibo", 3, "jeffw");
			
			icc.valorarUnCurso("Dalavuelta", 2, "chechi");
			icc.valorarUnCurso("Dalavuelta", 5, "roro");
			icc.valorarUnCurso("Dalavuelta", 4, "costas");
			
			icc.valorarUnCurso("Extensionismo Industrial", 1, "costas");
			icc.valorarUnCurso("Extensionismo Industrial", 2, "chechi");
			icc.valorarUnCurso("Extensionismo Industrial", 3, "eleven11");
			icc.valorarUnCurso("Extensionismo Industrial", 1, "weiss");
			
			icc.valorarUnCurso("Inclusión Energética", 5, "roro");
			icc.valorarUnCurso("Inclusión Energética", 5, "chechi");
			
			icc.valorarUnCurso("Taller de robótica educativa", 4, "eleven11");
			icc.valorarUnCurso("Taller de robótica educativa", 5, "jeffw");
			
			icc.valorarUnCurso("Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", 4, "chechi");
			icc.valorarUnCurso("Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", 4, "weiss");
			icc.valorarUnCurso("Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela", 4, "roro");
			
			icc.valorarUnCurso("Talleres plenarios", 1, "weiss");
			
			icc.valorarUnCurso("Seminarios de Resolución de Problemas", 2, "roro");
			
			
			
			
			
			
			
			// Seguir usuarios.
			icu.seguirUsuario("eleven11", "roro");
			icu.seguirUsuario("eleven11", "heisenberg");
			icu.seguirUsuario("eleven11", "watson");
			
			icu.seguirUsuario("costas", "chechi");
			icu.seguirUsuario("costas", "jeffw");
			icu.seguirUsuario("costas", "benkenobi");
			icu.seguirUsuario("costas", "danny");
			icu.seguirUsuario("costas", "phils");
			
			icu.seguirUsuario("roro", "costas");
			icu.seguirUsuario("roro", "weiss");
			
			icu.seguirUsuario("chechi", "eleven11");
			icu.seguirUsuario("chechi", "jeffw");
			icu.seguirUsuario("chechi", "house");
			icu.seguirUsuario("chechi", "Anab");
			
			icu.seguirUsuario("jeffw", "eleven11");
			
			icu.seguirUsuario("weiss", "costas");
			icu.seguirUsuario("weiss", "heisenberg");
			icu.seguirUsuario("weiss", "timmy");
			
			icu.seguirUsuario("Juanse", "jeffw");
			icu.seguirUsuario("Juanse", "Steveh");
			icu.seguirUsuario("Juanse", "saul");
			
			icu.seguirUsuario("Anab", "Otism");
			icu.seguirUsuario("Anab", "timmy");
			
			icu.seguirUsuario("Steveh", "eleven11");
			icu.seguirUsuario("Steveh", "watson");
			
			icu.seguirUsuario("nadiav", "Juanse");
			icu.seguirUsuario("nadiav", "Steveh");
			icu.seguirUsuario("nadiav", "Sallyo");
			icu.seguirUsuario("nadiav", "house");
			
			icu.seguirUsuario("Keating", "Steveh");
			icu.seguirUsuario("Keating", "timmy");
			
			icu.seguirUsuario("saul", "chechi");
			icu.seguirUsuario("saul", "Juanse");
			icu.seguirUsuario("saul", "watson");
			
			icu.seguirUsuario("Sallyo", "nadiav");
			
			icu.seguirUsuario("Otism", "Steveh");
			icu.seguirUsuario("Otism", "nadiav");
			
			icu.seguirUsuario("heisenberg", "roro");
			icu.seguirUsuario("heisenberg", "Keating");
			
			icu.seguirUsuario("benkenobi", "danny");
			
			icu.seguirUsuario("watson", "roro");
			icu.seguirUsuario("watson", "weiss");
			icu.seguirUsuario("watson", "heisenberg");
			
			icu.seguirUsuario("house", "jeffw");
			icu.seguirUsuario("house", "weiss");
			icu.seguirUsuario("house", "benkenobi");
			
			icu.seguirUsuario("timmy", "eleven11");
			icu.seguirUsuario("timmy", "chechi");
			icu.seguirUsuario("timmy", "jeffw");
			
			icu.seguirUsuario("danny", "costas");
			icu.seguirUsuario("danny", "weiss");
			icu.seguirUsuario("danny", "heisenberg");
			
			icu.seguirUsuario("phils", "bruces");
			icu.seguirUsuario("phils", "Keating");
			
			icu.seguirUsuario("bruces", "phils");
			icu.seguirUsuario("bruces", "nadiav");
			
			icu.seguirUsuario("adri", "heisenberg");
			
			
			ControladorCurso cCurso = new ControladorCurso();
			Calendar fechaComentario = Calendar.getInstance();
			fechaComentario.set(2010, Calendar.JULY, 1);
			cCurso.agregarComentario("Flor del Ceibo", "Flor del Ceibo - 2010", "eleven11", "Eleven", "", "Me Gusto!", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2010, Calendar.JULY, 2);
			cCurso.agregarComentario("Flor del Ceibo", "Flor del Ceibo - 2010", "chechi", "Cecilia", "Garrido", "Aceptable", fechaComentario);
			
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2018, Calendar.DECEMBER, 1);
			cCurso.agregarComentario("Extensionismo Industrial", "Extensionismo Industrial - 2018", "eleven11", "Eleven", "", "Horriible, nos mataron en las pruebas!", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2018, Calendar.DECEMBER, 2);
			cCurso.responderComentario("Extensionismo Industrial", "Extensionismo Industrial - 2018", "0","chechi", "Cecilia", "Garrido", "Que penal, te fue muy mal?", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2018, Calendar.DECEMBER, 2);
			cCurso.responderComentario("Extensionismo Industrial", "Extensionismo Industrial - 2018", "00", "eleven11", "Eleven", "", "Espantoso!! Perdí como en la guerra", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2018, Calendar.DECEMBER, 3);
			cCurso.responderComentario("Extensionismo Industrial", "Extensionismo Industrial - 2018", "000","chechi", "Cecilia", "Garrido", "Mejor suerte para la proxima!", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2018, Calendar.DECEMBER, 1);
			cCurso.agregarComentario("Extensionismo Industrial", "Extensionismo Industrial - 2018", "costas", "Gerardo", "Costas", "Para mi no estuvo tan mal, si estudias la salvas!", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2018, Calendar.DECEMBER, 2);
			cCurso.responderComentario("Extensionismo Industrial", "Extensionismo Industrial - 2018", "1", "eleven11", "Eleven", "", "Ahh, vos porque sos un extraterrestre! Fue una carniceria", fechaComentario);
			
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2019, Calendar.MAY, 4);
			cCurso.agregarComentario("Taller de robótica educativa", "Taller de robótica educativa - 2019", "costas", "Gerardo", "Costas", "Muy buen curso, recomendable!", fechaComentario);
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2019, Calendar.MAY, 5);
			cCurso.responderComentario("Taller de robótica educativa", "Taller de robótica educativa - 2019", "0", "jeffw", "Jeff", "Williams", "Demas, me aceptaron. Tenes material para compatir?", fechaComentario);
			
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2019, Calendar.MARCH, 28);
			cCurso.agregarComentario("Talleres plenarios", "Talleres plenarios - 2019", "weiss", "Adrian", "Weiss", "Una perdida de tiempo!", fechaComentario);
			
			fechaComentario = Calendar.getInstance();
			fechaComentario.set(2019, Calendar.OCTOBER, 19);
			cCurso.agregarComentario("Seminarios de Resolución de Problemas", "Seminarios de Resolución de Problemas - 2019", "roro", "Rodrigo", "Cotelo", "En realidad nunca fui a clases y me fue mal", fechaComentario);
			
			ControladorUsuario cUsuario = new ControladorUsuario();
			Calendar fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2010, Calendar.JULY, 14);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2010", "eleven11", 6, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2010", "chechi", 8, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2012, Calendar.NOVEMBER, 27);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2012", "costas", 9, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2012", "weiss", 0, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2018, Calendar.AUGUST, 14);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2018", "roro", 9, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2018", "jeffw", 6, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2018", "Otism", 10, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("bruces", "Flor del Ceibo - 2018", "Keating", 9, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2017, Calendar.NOVEMBER, 17);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Dalavuelta - 2018", "chechi", 7, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Dalavuelta - 2018", "roro", 7, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Dalavuelta - 2018", "costas", 6, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2018, Calendar.NOVEMBER, 17);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Extensionismo Industrial - 2018", "costas", 9, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Extensionismo Industrial - 2018", "chechi", 9, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Extensionismo Industrial - 2018", "eleven11", 1, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Extensionismo Industrial - 2018", "weiss", 11, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2019, Calendar.MAY, 7);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Inclusión Energética - 2019", "roro", 7, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("phils", "Inclusión Energética - 2019", "chechi", 11, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2017, Calendar.MAY, 17);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("watson", "Taller de robótica educativa - 2017", "eleven11", 0, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2019, Calendar.MAY, 17);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("benkenobi", "Taller de robótica educativa - 2019", "jeffw", 8, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("benkenobi", "Taller de robótica educativa - 2019", "costas", 10, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2019, Calendar.NOVEMBER, 17);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("watson", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", "chechi", 0, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("watson", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", "weiss", 0, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("watson", "Participación en investigación sobre el empleo del juego Komikan como recurso didáctico en la Escuela - 2019", "roro", 0, fechaInscripcion);
			fechaInscripcion = Calendar.getInstance();
			fechaInscripcion.set(2019, Calendar.APRIL, 7);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("danny", "Talleres plenarios - 2019", "weiss", 6, fechaInscripcion);
			cUsuario.ingresarResultadosDeEvaluacionesFecha("danny", "Talleres plenarios - 2019", "roro", 7, fechaInscripcion);
			
			
			
			
			
			
			
		//} catch (CursoNoExisteException | EdicionNoExisteException | UsuarioNoExisteException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		
		
		
	}
}

