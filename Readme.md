# Proyecto EdExt

#### Acerca de
A modo de resumen el proyecto trata de una aplicacion web utilizando tecnologia de servlets de JavaEE, y el fronted desarrollado con la libreria de Javascript React.js. El proyecto se realizo en el año 2019 para la materia "Taller de Programacion", la misma es impartida por la Facultad de Ingeneria - UDELAR. El mismo fue desarrollado por un equipo de 4 programadores.

#### Resumen
EdExt es una plataforma educativa y social para cursos de extensión universitaria, en la misma se peuden efectuar las siguientes:
 - Registrar oferta de cursos, ediciones y programas de formación.
 - Gestionar inscripciones a cursos, calificaciones y evaluaciones de cursos.
 - Características sociales: seguir usuarios, comentar cursos y brindar recomendaciones.

#### Descripcion del problema
El proyecto consta de 3 elementos:
 - El `Servidor Central` el cual contiene toda la informacion del sistema y es el encargado de gestionar la logica del mismo. Ademas abra una terminal conectada a este servidor, la misma sera manipulada por el administrador y podra ejecutar acciones de gran relevancia en el sistema. Ademas el servidor brinda ciertas funcionalidades al `Servidor Web` utilizando web services de tipo SOAP.
 - El `Servidor Web` sera el responsable de brindar la aplicacion a todos los clientes que se conecten al mismo a travez de un navegador web o de un dispositivo movil. 
 - La `Aplicacion Web` que veran los clientes que se conecten al `Servidor Web` ya sea desde un movil o un navegador desde una pc.

 ![Imagen de la arquitectura del proyecto](https://raw.githubusercontent.com/Santiago5050/ProyectoTallerDeProgramacion/master/imagenes_readme/001.png)

#### Descripcion de la solucion
El `Servidor Central` se construyo utilizando Java, la logica del sistema esta encapsulada y es independiente del resto. De esta forma se consiguen mejores niveles de mantenibilidad y seguridad. La terminal que utiliza el administrador contiene ademas acceso a una interfaz desarrollada con la biblioteca de java `javax.swing`. Para la comunicacion del servidor con el `Servidor Web` se utilizaron web services de tipo SOAP, intercambiando informacion a travez de objetos JSON con este ultimo.

El `Servidor Web` se desarrollo con la tecnologia de `Servlets` proporsionada por JavaEE, ademas se utilizaron objetos JSON para el intercambio de informacion entre los clientes (web o movile) y el servidor.

Por ultimo la `Aplicacion Web` fue desarrollada con la libreria de de Javascript React.js. La app evalua si el cliente esta corriendo la aplicacion desde una pc de escritorio o desde un movil, y se adapta brindando diferentes funcionalidades. Tambien tiene un sistema de login para los distintos roles de la organizacion y brinda acceso a todas las funcionalidades del `Servidor Central` (exceptuando aquellas reservadas exclusivamente para el adminstrador en la terminal).

#### Requisitos de instalacion
Para el correcto funcionamiento del sistema se necesitan los siguientes:
 - Servidor Tomcat v8
 - jdk 8.0
 - JavaEE 7
 - Maven

#### Compilacion

Para la compilacion es necesario ejecutar el comando `mvn package` en el directorio raiz del proyecto (`/tpgr12/`). Una vez realizado esto los archivos compilados se encontraran en la carpeta `application` del directorio raiz del proyecto.

#### Configuracion

Para la ejecucion del proyecto es necesario colocar el archivo `edext-server-web.war` en el directorio `/webapps/` de la instalacion de tomcat y ejecutar tomcat normalmente, una vez realizado esto, es posbile modificar los ajustes de conexion en la carpeta (`/webapps/edext-server-web/`) generada por tomcat.

Para esto existen dos archivos de configuracion, el primero ubicado en la raiz de este directorio llamado `Config.js` en el mismo es posible ajustar la IP y puerto a la que realizara los pedidos el frontend web.

Ademas, dentro del archivo `web.xml` dentro del directorio `/WEB-INF/`es psible ajustar los parametros `webseviceip` y `webservieport` seteando asi la ip y puerto donde se encuentra el webservice.

En el archivo `config.properties` se indica la IP y Puerto donde escucharan los webservice del `Servidor Central`. Ademas este archivo debe estar ubicado en el mismo directorio que el archivo `edext-backend.jar`.

#### Ejecucion

En primer lugar levante el servidor tomcat con el `Servidor Web` configurado como se expliuco en la seccion anterior. Luego para ejecutar el `Servidor Central` se debe ejecutar el archivo `edext-backend.jar` con el comando `java -jar`.
Una vez realizado esto la app se ejecutara correctamente.
