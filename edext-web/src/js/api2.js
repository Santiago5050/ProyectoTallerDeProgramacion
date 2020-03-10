import axios from 'axios';

let ip = window.Configs.ip
let port= window.Configs.port

let api = axios.create({
    baseURL: 'http://'+ip+':'+port+'/edext-server-web/api/',
  })

class ApiRequest2 {

    getInstitutos = () => {
        return api.get("InstitutosCategorias", {
            "params": {
                "Institutos": ""
            }
        })
    }

    getCategorias = () => {
        return api.get("InstitutosCategorias", {
            "params": {
                "Categorias": ""
            }
        })
    }
    
   
    getEdiciones=() => {
        return api.get('Edicion')
    }

    getEdicionesCurso = curso => {
        return api.get('Edicion', {
            "params": {
                "Curso": curso
            }
        })
    }

    getEdicionesUsuario = usuario => {
        return api.get('Edicion', {
            "params": {
                "Usuario": usuario
            }
        })
    }

    getEdicion = (curso, edicion) => {
        return api.get('Edicion', {
            "params": {
                "Curso": curso,
                "Edicion": edicion
            }
        })
    }

    altaEdicion = (nombre, diaInicio, mesInicio, anioInicio, diaFin, mesFin, anioFin, cupo, curso, docentes, instituto, source) => {
        return api.post('Edicion', {
            "Nombre": nombre,
            "DiaInicio": diaInicio,
            "MesInicio": mesInicio,
            "AnioInicio": anioInicio,
            "DiaFin": diaFin,
            "MesFin": mesFin,
            "AnioFin": anioFin,
            "Cupo": cupo,
            "Curso": curso,
            "Docentes": docentes,
            "Instituto": instituto,
            "Source": source
        })
    }

    // Era getUsuariosEdicion() antes.
    getEstudiantesEdicion = (nom_edicion) => {
        return api.get('InscripcionDocente', {
            "params": {
                "Edicion": nom_edicion,
                "Tipo": "estudiante"
            }
        })
    }

    getDocentesEdicion = (nom_edicion) => {
        return api.get('InscripcionDocente', {
            "params": {
                "Edicion": nom_edicion,
                "Tipo": "docente"
            }
        })
    }

    inscribirUsuariosEdicion = (docente, edicion, inscripciones) => {
        return api.post('InscripcionDocente', {
            "Docente": docente,
            "Edicion": edicion,
            "Inscripciones": inscripciones
        })
    }

    getCurso = (nom_curso) => {
        return api.get('Curso', {"params":{
            "Curso" : nom_curso
        }
        })
    }

    getCursosInstituto = (nom_instituto) => {
        return api.get('Curso', {"params":{
            "Instituto" : nom_instituto
        } 
        })
    }

    getCursos = () => {
        return api.get('Curso')
    }

    getCursoDeEdicion = nom_edicion =>{
        return api.get('Curso', {
            "params":{
                "Edicion" : nom_edicion
            }
        })
    }

    

    altaCurso = (instituto, nom_curso, descripcion,duracion, cantHoras, creditos,url, previas, src, categorias) =>{
        return api.post('Curso',{
            "Instituto":instituto,
            "Nombre" : nom_curso,
            "Descripcion" : descripcion,
            "Duracion" : duracion,
            "CantHoras" : cantHoras,
            "Creditos" : creditos,
            "url" : url,
            "Previas" : previas,
            "src" : src,
            "categorias": categorias
        })
    }

    inscripcionEstudianteEdicion = (nom_curso, nom_edicion, fecha, url) =>{
        return api.post('InscripcionEstudianteEdicion',{    
            "Curso" : nom_curso,
            "Edicion" : nom_edicion,
            "Fecha-Inscripcion" : fecha,
            "URL": url
        })
    }

    desistirEstudianteEdicion = (nickname, edicion) => {
        return api.put('InscripcionEstudianteEdicion', {
            "Nickname": nickname,
            "Edicion": edicion
        })
    }

    inscripcionEstudiantePrograma = (nom_programa,fecha) =>{
        return api.post('InscripcionEstudiantePrograma',{    
            "Programa" : nom_programa,
            "Fecha-Inscripcion" : fecha
        })
    }

    getPrevias = (previas) =>{
        return api.get('Curso', {"params":{
            "Previas" : previas
        } 
        })
    } 
    getCursosPrograma = (programa) =>{
        return api.get('Curso', {"params":{
            "Programa" : programa
        } 
        })
    } 

    getResultadoInscripciones = () => {
        return api.get('InscripcionEstudianteEdicion')
    }

    getInscriptosAedicion = (nom_curso,nom_edicion) =>{
        return api.get('InscripcionEstudianteEdicion',{
            "params" : {
                "Curso" : nom_curso,
                "Edicion" : nom_edicion
            }
        })
    }

    login = (nick, pass) => {
        return api.post('Login', {    
            "Nickname" : nick,
            "Pass" : pass
        })
    }

    loginMovil = (nick, pass) => {
        return api.post('Login', {    
            "Nickname" : nick,
            "Pass" : pass,
            "Movil": "Movil"
        })
    }

    getUsuarios = () => {
        return api.get('Usuario');
    }

    getDocentes = () => {
        return api.get('Usuario', {
            "params" : {
                "type" : "docente"
            }
        });
    }

    getDocentesInstituto = (instituto) => {
        return api.get('Usuario', {
            "params" : {
                "instituto" : instituto
            }
        });
    }

    getEstudiantes = () => {
        return api.get('Usuario', {
            "params" : {
                "type" : "estudiante"
            }
        });
    }

    getUsuario = (nickname) => {
        return api.get('Usuario', {
            "params" : {
                "nickname" : nickname
            }
        });
    }

    altaEstudiante = (nickname, nombre, apellido, mail, fechaNacimiento, password, src) => {
        return api.post('Usuario', {
            "nickname" : nickname,
            "nombre" : nombre,
            "apellido" : apellido,
            "mail" : mail,
            "fechaNacimiento" : fechaNacimiento, 
            "password" : password,
            "src" : src
        });
    }

    altaDocente = (instituto, nickname, nombre, apellido, mail, fechaNacimiento, password, src) => {
        return api.post('Usuario', {
            "instituto" : instituto,
            "nickname" : nickname,
            "nombre" : nombre,
            "apellido" : apellido,
            "mail" : mail,
            "fechaNacimiento" : fechaNacimiento, 
            "password" : password,
            "src" : src
        });
    }

    modificarUsuario = (nombre, apellido, mail, fechaNacimiento, src) => {
        return api.put('Usuario', {
            "nombre" : nombre,
            "apellido" : apellido,
            "mail" : mail,
            "fechaNacimiento" : fechaNacimiento, 
            "src" : src
        });
    }

    getPrograma = (nombre) => {
        return api.get('Programa', {
            "params" : {
                "nombre" : nombre
            }
        });
    }

    getProgramas = () => {
        return api.get('Programa');
    }

    getProgramasCurso = (nom_curso) => {
        return api.get('Programa', {
            "params" : {
                "curso" : nom_curso
            }
        });
    }

    getProgramasUsuario = (nickname) => {
        return api.get('Programa', {
            "params" : {
                "user" : nickname
            }
        });
    }

    altaPrograma = (nombre, descripcion, fechaInicio, fechaFin, src) => {
        return api.post('Programa', {
            "nombre" : nombre,
            "descripcion" : descripcion,
            "fechaInicio" : fechaInicio,// formato: dia/mes/año
            "fechaFin" : fechaFin,// formato: dia/mes/año
            "src" : src
        });
    }

    agregarCursosAPrograma = (programa, cursos) => {
        return api.put('Programa', {
            "programa" : programa,
            "cursos" : cursos
        });
    }

    seguirUsuario = (nickDest) => {
        return api.post('Seguir', {
            "accion" : "follow",
            "nickDest" : nickDest
        });
    }

    dejarDeSeguirUsuario = (nickDest) => {
        return api.post('Seguir', {
            "accion" : "unfollow",
            "nickDest" : nickDest
        });
    }

    ingresarResultado = (nom_edicion,nom_estudiante,nota) =>{
        return api.put('InscripcionDocente',{
            "Edicion" : nom_edicion,
            "Estudiante":nom_estudiante,
            "Nota":nota
        });
    }

    cerrarEdicion = (nom_edicion) =>{
        return api.put('Edicion',{
            "Edicion" : nom_edicion
        });
    }

    /* Funcion para la pagina de inicio */
     getInicio= async () =>{
        let cursos = await this.getCursos();
        
        let programas = await this.getProgramas();
       
        let res =[]
        let temp;
        for(var key in cursos.data){
            if(cursos.data.hasOwnProperty(key)){
                temp=cursos.data[key];
                temp.tipo = "Curso"
                res.push(temp)
            }
        }
        for(key in programas.data){
            if(programas.data.hasOwnProperty(key)){
                temp=programas.data[key];
                temp.tipo = "Programa"
                res.push(temp)
            }
        }
        res.sort((a,b) =>{
            var x = a.nombre.toLowerCase()
            var y = b.nombre.toLowerCase()
            if(x<y){ return -1}
            if(x>y){ return 1}
            return 0
        })
       
        return res;
    } 

    //enviar imagen
    enviarImagen= (imagen) => {
        axios({
            method: 'post',
            url: 'http://'+ ip +':'+ port +'/edext-server-web/api/SubeArchivos',
            data: imagen,
            config: { headers: {'Content-Type': 'multipart/form-data' }}
            })
    }

    //update login
    updateLogin= () =>{
        return api.get('Login')
    }

    validarDatosNickname = (nickname) => {
        return api.get('ValidarDatos', {
            "params" : {
                "Nickname" : nickname
            }
        });
    }

    validarDatosMail = (mail) => {
        return api.get('ValidarDatos', {
            "params" : {
                "Mail" : mail
            }
        });
    }

    validarDatosCurso = (curso) => {
        return api.get('ValidarDatos', {
            "params" : {
                "Curso" : curso
            }
        });
    }

    validarDatosEdicion = (edicion) => {
        return api.get('ValidarDatos', {
            "params" : {
                "Edicion" : edicion
            }
        });
    }

    validarDatosPrograma = (programa) => {
        return api.get('ValidarDatos', {
            "params" : {
                "Programa" : programa
            }
        });
    }

    //valorar
    valorarCurso=(nom_curso, valoracion)=>{
        return api.put('Curso',{
            Curso:nom_curso,
            Rating:valoracion
        })
    }

    //valorar
    visitaCurso=(nom_curso)=>{
        return api.put('Curso',{
            Curso:nom_curso,
        })
    }

    //cerrarsesion
    cerrarSesion = ()=>{
        api.put('Login')
    }

    //Comentarios
    obtenerComentarios = (nom_curso, nom_edicion) => {
        return api.get('Comentario', {
            "params" : {
                "curso" : nom_curso,
                "edicion" : nom_edicion
            }
        });
    }

    agregarComentario = (nom_curso, nom_edicion, nombre, apellido, comentario, fechaComentario) => {
        return api.post('Comentario', {
            "curso": nom_curso,
            "edicion": nom_edicion,
            "nombre": nombre,
            "apellido": apellido,
            "comentario": comentario,
            "fechaComentario": fechaComentario // dia/mes/año
        });
    }

    responderComentario = (nom_curso, nom_edicion, id, nombre, apellido, comentario, fechaComentario) => {
        return api.put('Comentario', {
            "curso": nom_curso,
            "edicion": nom_edicion,
            "nombre": nombre,
            "apellido": apellido,
            "comentario": comentario,
            "fechaComentario": fechaComentario, // dia/mes/año
            "id": id
        });
    }

    aproboPrograma = (nom_programa)=>{
        return api.get('Certificado',{
            "params":{
                "Programa":nom_programa
            }
        })
    }

    certificadoPrograma = (nom_programa)=>{
        return api.post('Certificado',{
                "Programa":nom_programa
            }, {responseType:'blob'}
        )
    }
}

export default ApiRequest2