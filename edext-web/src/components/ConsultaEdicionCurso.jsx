import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import {ToastContainer, toast } from 'react-toastify';
import ApiRequest2 from '../js/api2.js';
import no_image from './../res/no_image.jpg';
import './../css/ConsultaEdicionCurso.css';
import Loader from './Loader.jsx';
import {isMobile} from 'react-device-detect';
import Comentarios from './Comentarios-Consulta-Edicion.jsx';
import ModalVideoEdicion from './ModalVideoEdicion';
import ModalReproducirVideo from './ModalReproducirVideo';
import 'react-toastify/dist/ReactToastify.css';

//Se debe de proveer el nombre del curso y el de la edicion en las prop nomCurso y nomEdicion respectivamente
//Se debe de provver el nombre del usuario que esta logueado en el sistema
export default class ConsultaEdicionCurso extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: props.user,
            usuarioLogueado: props.userLogued,
            mostrarAceptados: false,
            loadingEdicion: true,
            loadingDocentes: true,
            loadingEstudiantes: true,
            loadingComentarios: true,
            datosEdicion: null,
            estudiantesEdicion: null,
            docentesEdicion: null,
            comentariosEdicion: null,
            idResp: '',
            video: null
        }
        this.comentar = React.createRef()
    }

    cerrarEdicion = () => {
        let haySinNota = false;
        let hayInscriptos = false;
        this.state.estudiantesEdicion.map((user, index) => {
            if(user.estadoEdiciones[this.state.datosEdicion.nombre].estado.toLowerCase() === 'aceptada' && user.estadoEdiciones[this.state.datosEdicion.nombre].nota < 0) {
                haySinNota = true;
            }
            if(user.estadoEdiciones[this.state.datosEdicion.nombre].estado.toLowerCase() === 'inscripto') {
                hayInscriptos = true;
            }
        });
        if (!haySinNota && !hayInscriptos){
            let api = new ApiRequest2();
            this.setState({
                loadingEdicion: true
            });
            api.cerrarEdicion(this.state.datosEdicion.nombre).then(
                (respuesta) => {
                    api.getEdicion(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                        (respuesta) => {
                            if (respuesta.data.hasOwnProperty('ERROR')) {
                                toast.error(respuesta.data.ERROR);
                            } else {
                                respuesta.data.instituto = this.state.datosEdicion.instituto;
                                respuesta.data.curso = this.state.datosEdicion.curso;
                                this.setState({
                                    datosEdicion: respuesta.data,
                                    loadingEdicion: false
                                });
                            }
                        }
                    ).catch(
                        (respuestaError) => {
                            console.log(respuestaError);
                        }
                    );
                    if (respuesta.data.hasOwnProperty('ERROR')) {
                        toast.error(respuesta.data.ERROR);
                    } else {
                        // toast.info(respuesta.data.OK);
                        // window.location.reload();
                        toast.info(respuesta.data.OK);
                    }
                }
            ).catch(
                (respuestaError) => {
                    console.log(respuestaError);
                }
            );
        } else {
            toast.error("Se deben de calificar a todos los estudiantes antes de cerrar la edicion")
        }
    }

    inscribirEstudiante = (userEstudiante, index) => {
        let api = new ApiRequest2();
        let inscripcion = {
            Nombre: userEstudiante,
            Estado: true
        };
        this.setState({
            loadingEstudiantes: true
        });
        api.inscribirUsuariosEdicion(this.props.user.nickname, this.state.datosEdicion.nombre, [inscripcion]).then(
            (respuesta) => {
                api.getInscriptosAedicion(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                estudiantesEdicion: respuesta.data,
                                loadingEstudiantes: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    // toast.info(respuesta.data.OK);
                    // window.location.reload();
                    toast.info("EXITO, El estudiante " + userEstudiante + " ha sido inscripto");
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    rechazarEstudiante = (userEstudiante, index) => {
        let api = new ApiRequest2();
        let inscripcion = {
            Nombre: userEstudiante,
            Estado: false
        };
        this.setState({
            loadingEstudiantes: true
        });
        api.inscribirUsuariosEdicion(this.props.user.nickname, this.state.datosEdicion.nombre, [inscripcion]).then(
            (respuesta) => {
                api.getInscriptosAedicion(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                estudiantesEdicion: respuesta.data,
                                loadingEstudiantes: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    // toast.info(respuesta.data.OK);
                    // window.location.reload();
                    toast.info("EXITO, EL estudiante " + userEstudiante + " ha sido rechazado");
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    calificar = (index, nick_estudiante) => {
        let api = new ApiRequest2();
        let nota = document.getElementById('nota-consulta-edicion-' + index).value
        if (nota !== null && nota !== "") {
            this.setState({
                loadingEdicion: true
            });
            api.ingresarResultado(this.state.datosEdicion.nombre, nick_estudiante,nota).then(
                (respuesta) => {
                    api.getInscriptosAedicion(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                        (respuesta) => {
                            if (respuesta.data.hasOwnProperty('ERROR')) {
                                toast.error(respuesta.data.ERROR);
                            } else {
                                this.setState({
                                    estudiantesEdicion: respuesta.data,
                                    loadingEdicion: false
                                });
                            }
                        }
                    ).catch(
                        (respuestaError) => {
                            console.log(respuestaError);
                        }
                    );
                    if (respuesta.data.hasOwnProperty('ERROR')) {
                        toast.error(respuesta.data.ERROR);
                    } else {
                        // toast.info(respuesta.data.OK);
                        toast.info(respuesta.data.OK);
                    }
                }
            ).catch(
                (respuestaError) => {
                    console.log(respuestaError);
                }
            );
        } else {
            toast.error("Error, no ingreso ninguna nota")
        }
    }

    desistir = () => {
        let api = new ApiRequest2();
        this.setState({
            loadingEdicion: true
        });
        api.desistirEstudianteEdicion(this.props.user.nickname, this.state.datosEdicion.nombre).then(
            (respuesta) => {
                api.getInscriptosAedicion(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                estudiantesEdicion: respuesta.data,
                                loadingEdicion: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    // toast.info(respuesta.data.OK);
                    toast.info(respuesta.data.OK);
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    ordenearRanking = (desendente) => {
        let tabla = document.getElementById('tabla-estudiantes-consulta-edicion');
        let cambio = true;
        let i, huboCambio;
        while (cambio) {
            cambio = false;
            huboCambio = false;
            i = 1;
            while(!huboCambio && i < (tabla.rows.length - 1)) {
                let nickA = tabla.rows[i].getElementsByTagName('td')[0].innerHTML;
                let nickB = tabla.rows[i + 1].getElementsByTagName('td')[0].innerHTML;
                let rankA = 0;
                let rankB = 0;
                this.state.estudiantesEdicion.map((element, index) => {
                    if (element.nickname.toLowerCase() == nickA.toLowerCase()) {
                        rankA = element.Ranking;
                    } else if (element.nickname.toLowerCase() == nickB.toLowerCase()) {
                        rankB = element.Ranking;
                    }
                });
                if (desendente && rankA < rankB) {
                    huboCambio = true;
                } else if (!desendente && rankA > rankB) {
                    huboCambio = true;
                } else {
                    i = i + 1;
                }
            }
            if (huboCambio) {
                tabla.rows[i].parentNode.insertBefore(tabla.rows[i+1], tabla.rows[i]);
                cambio = true;
            }
        }
    }

    fechaMenorQue = (dia1, mes1, anio1, dia2, mes2, anio2) => {
        if (anio1 > anio2) {
            return false;
        } else if (mes1 > mes2) {
            return false;
        } else if (dia1 >= dia2) {
            return false;
        } else {
            return true;
        }
    }

    ordenarFecha = (desendente) => {
        let tabla = document.getElementById('tabla-estudiantes-consulta-edicion');
        let cambio = true;
        let i, huboCambio;
        while (cambio) {
            cambio = false;
            huboCambio = false;
            i = 1;
            while(!huboCambio && i < (tabla.rows.length - 1)) {
                let nickA = tabla.rows[i].getElementsByTagName('td')[0].innerHTML;
                let nickB = tabla.rows[i + 1].getElementsByTagName('td')[0].innerHTML;
                let fechaA = null;
                let fechaB = null;
                this.state.estudiantesEdicion.map((element, index) => {
                    if (element.nickname.toLowerCase() == nickA.toLowerCase()) {
                        fechaA = element.estadoEdiciones[this.state.datosEdicion.nombre].fecha;
                    } else if (element.nickname.toLowerCase() == nickB.toLowerCase()) {
                        fechaB = element.estadoEdiciones[this.state.datosEdicion.nombre].fecha;
                    }
                });
                if (desendente && this.fechaMenorQue(fechaA.dayOfMonth, fechaA.month + 1, fechaA.year, fechaB.dayOfMonth, fechaB.month + 1, fechaB.year)) {
                    huboCambio = true;
                } else if (!desendente && this.fechaMenorQue(fechaB.dayOfMonth, fechaB.month + 1, fechaB.year, fechaA.dayOfMonth, fechaA.month + 1, fechaA.year)) {
                    huboCambio = true;
                } else {
                    i = i + 1;
                }
            }
            if (huboCambio) {
                tabla.rows[i].parentNode.insertBefore(tabla.rows[i+1], tabla.rows[i]);
                cambio = true;
            }
        }
    }

    mostrarSoloAceptados = () => {
        let mostrar = !this.state.mostrarAceptados
        this.setState({
            mostrarAceptados: mostrar
        });

        var tablaDatos = document.getElementById('tabla-estudiantes-consulta-edicion');
        for (var i = 1; i < tablaDatos.rows.length; i++) {
            var celdas = tablaDatos.rows[i].getElementsByTagName('td');
            var estado = celdas[3].innerHTML;
            if (estado !== 'ACEPTADA' && mostrar)
            {
                tablaDatos.rows[i].style.display = 'none';
            } 
            else 
            {
                tablaDatos.rows[i].style.display = '';
            }
        }
    }

    componentDidMount() {
        const edicion = this.props.location.state.nombre;
        let api = new ApiRequest2();
        api.getCursoDeEdicion(edicion).then(
            (respuesta) => {
                let instituto = respuesta.data.instituto;
                let curso = respuesta .data.nombre;
                api.getEdicion(curso, edicion).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            respuesta.data.instituto = instituto;
                            respuesta.data.curso = curso;
                            this.setState({
                                datosEdicion: respuesta.data,
                                loadingEdicion: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                api.obtenerComentarios(curso, edicion).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                comentariosEdicion: respuesta.data,
                                loadingComentarios: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                api.getDocentesEdicion(edicion).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                docentesEdicion: respuesta.data,
                                loadingDocentes: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                api.getInscriptosAedicion(curso, edicion).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                estudiantesEdicion: respuesta.data,
                                loadingEstudiantes: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    filtrosTabla = () => {
        let retorno = null;
        if(!isMobile) {
            retorno = (
                <div className="row fila-check">
                    <div className="col-xs-2">
                        <div className="custom-control custom-checkbox check-aceptados-contenedor">
                            <input type="checkbox" onChange={this.mostrarSoloAceptados} className="custom-control-input" id="check-aceptados"/>
                            <label className="custom-control-label" htmlFor="check-aceptados">Mostrar solo aceptados</label>
                        </div>
                    </div>
                    <div className="col-xs-2">
                        <div className="custom-control custom-checkbox check-aceptados-contenedor">
                            <input type="radio" onChange={() => {this.ordenearRanking(true)}} className="custom-control-input" name="radio-group" id="check-ranking-Mm"/>
                            <label className="custom-control-label" htmlFor="check-ranking-Mm">Orden por ranking <i class="fas fa-arrow-circle-down"></i></label>
                        </div>
                    </div>
                    <div className="col-xs-2">
                        <div className="custom-control custom-checkbox check-aceptados-contenedor">
                            <input type="radio" onChange={() => {this.ordenearRanking(false)}} className="custom-control-input" name="radio-group" id="check-ranking-mM"/>
                            <label className="custom-control-label" htmlFor="check-ranking-mM">Orden por ranking <i class="fas fa-arrow-circle-up"></i></label>
                        </div>
                    </div>
                    <div className="col-xs-2">
                        <div className="custom-control custom-checkbox check-aceptados-contenedor">
                            <input type="radio" onChange={() => {this.ordenarFecha(true)}} className="custom-control-input" name="radio-group" id="check-fecha-Mm"/>
                            <label className="custom-control-label" htmlFor="check-fecha-Mm">Orden por fecha inscipcion <i class="fas fa-arrow-circle-down"></i></label>
                        </div>
                    </div>
                    <div className="col-xs-4">
                        <div className="custom-control custom-checkbox check-aceptados-contenedor">
                            <input type="radio" onChange={() => {this.ordenarFecha(false)}} className="custom-control-input" name="radio-group" id="check-fecha-mM"/>
                            <label className="custom-control-label" htmlFor="check-fecha-mM">Orden por fecha inscripcion <i class="fas fa-arrow-circle-up"></i></label>
                        </div>
                    </div>
                </div>
            );
        }
        return retorno;
    }

    imprimirNota = (index, nota, estado, habilitadoDocente, nickname) => {
        let retorno = null;
        if(!isMobile && habilitadoDocente){
            if(estado === "aceptada") {
                if(!this.state.datosEdicion.cerrada) {
                    retorno = (
                        <td>
                            <div className="campo-nota input-group-sm">
                                <input className="form-control" id={"nota-consulta-edicion-" + index} type="number" name="quantity" min="0" max="12" defaultValue={nota < 0 ? "S/N" : nota}></input>
                                <a href="# "  onClick={(e)=>{e.preventDefault(); this.calificar(index, nickname)}}><i class="fas fa-plus-circle"></i></a>
                            </div>
                        </td>
                    );
                } else {
                    retorno = (
                        <td>{nota}</td>
                    );
                }
            } else {
                retorno = <td>S/N</td>;
            }
        }
        return retorno;
    }

    imprimirComentar = (estado) => {
        let retorno = null;
        if (estado === 'aceptada') {
            retorno = (
                <div className="row fila-comentar mb-2">
                    <div className="container">
                        <div class="form-group row">
                            <label for="consulta-edicion-comentar">{this.state.idResp === '' ? "Agregar Comentario:" : "Responder Comentario:"}</label>
                            <textarea class="form-control" rows="5" id="consulta-edicion-comentar"></textarea>
                        </div>
                        <div className="d-flex justify-content-end row" ref={this.comentar}>
                            {this.state.idResp === '' ? null : <button onClick={this.cancelarRespuesta} type="button" className="btn btn-outline-danger mr-3">CANCELAR</button>}
                            {this.state.idResp === '' ? (
                                <button onClick={this.enviarRespuesta} onEnter={this.enviarRespuesta} type="btn btn-outline-danger mr-3" className="btn btn-outline-success">COMENTAR</button>
                            ) : (
                                <button onClick={this.enviarComentario} onEnter={this.enviarComentario} type="button" className="btn btn-outline-success">RESPONDER</button>
                            )}
                        </div>
                    </div>
                </div>
            );
        }
        return retorno;
    }

    responderComentario = (id) => {
        this.setState({
            idResp: id
        });
        if(this.comentar.current){
            this.comentar.current.scrollIntoView({ 
               behavior: "smooth", 
               block: "nearest"
            })
        }
    }

    cancelarRespuesta = () => {
        this.setState({
            idResp: ''
        });
    }

    enviarRespuesta = () => {
        let api = new ApiRequest2();
        let comentario = document.getElementById('consulta-edicion-comentar').value
        let fecha = new Date();
        fecha = fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear();
        this.setState({
            loadingComentarios: true
        });
        api.agregarComentario(this.state.datosEdicion.curso, this.state.datosEdicion.nombre, this.props.user.nombre, this.props.user.apellido, comentario, fecha).then(
            (respuesta) => {
                api.obtenerComentarios(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                comentariosEdicion: respuesta.data,
                                loadingComentarios: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    // toast.info(respuesta.data.OK);
                    // window.location.reload();
                    toast.info(respuesta.data.OK);
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    enviarComentario = () => {
        let api = new ApiRequest2();
        let comentario = document.getElementById('consulta-edicion-comentar').value
        let fecha = new Date();
        fecha = fecha.getDate() + "/" + (fecha.getMonth() + 1) + "/" + fecha.getFullYear();
        this.setState({
            loadingComentarios: true
        });
        api.responderComentario(this.state.datosEdicion.curso, this.state.datosEdicion.nombre, this.state.idResp, this.props.user.nombre, this.props.user.apellido, comentario, fecha).then(
            (respuesta) => {
                api.obtenerComentarios(this.state.datosEdicion.curso, this.state.datosEdicion.nombre).then(
                    (respuesta) => {
                        if (respuesta.data.hasOwnProperty('ERROR')) {
                            toast.error(respuesta.data.ERROR);
                        } else {
                            this.setState({
                                comentariosEdicion: respuesta.data,
                                idResp: '',
                                loadingComentarios: false
                            });
                        }
                    }
                ).catch(
                    (respuestaError) => {
                        console.log(respuestaError);
                    }
                );
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    // toast.info(respuesta.data.OK);
                    // window.location.reload();
                    toast.info(respuesta.data.OK);
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    setVideo = (video) => {
        console.log("setVideo");
        this.setState({
            "video": video
        });
    }

    render()
    {
        //Render condicional cuando esten los datos
        let renderizar;
        let estado = '';
        if (!this.state.loadingComentarios && !this.state.loadingDocentes && !this.state.loadingEstudiantes && !this.state.loadingEdicion) {
            // Evalua el boton a mostrar, dependendiendo del estado de la inscripcion del estudiante
            let tipoBoton = null;
            if(this.props.user !== null && this.props.user.tipo === "estudiante")
            {
                let nota = 0;
                let element = null;
                this.state.estudiantesEdicion.map((user, index) => {
                    if(user.nickname == this.props.user.nickname) {
                        element = user.estadoEdiciones[this.state.datosEdicion.nombre];
                    }
                });
                if (element != null) {
                    if (element.estado.toLowerCase() === 'inscripto') {
                        estado = 'inscripto';
                    }
                    else if (element.estado.toLowerCase() === 'aceptada')
                    {
                        if(element.nota != 0) {
                            nota = element.nota;
                        }
                        estado = 'aceptada';
                    }
                    else
                    {
                        estado = 'Rechazado';
                    }
                }

                if (!isMobile && estado === 'inscripto') {
                    tipoBoton = (<button onClick={this.desistir} type="button" className="btn btn-outline-success">Desistir</button>);
                } else if (!isMobile && estado === '' && this.state.datosEdicion.vigencia){
                    tipoBoton = (<button data-toggle="modal" data-target="#videoModal" type="button" className="btn btn-outline-success">Inscribirme</button>);
                } else if(estado === 'aceptada' && nota >= 0 && this.state.datosEdicion.cerrada){                    
                    tipoBoton = (<p className="text-monospace" style={{fontSize: '1.3em'}}>Nota final:<span className="msj">{nota}</span></p>);
                } else if(estado === 'Rechazado') {
                    tipoBoton = (<p className="text-monospace" style={{fontSize: '1.3em'}}>Estado Inscripcion: <span className="msj">{estado}</span></p>);
                } else {
                    tipoBoton = null;
                }
            }
            // Evalua si el docente logueado esta habilitado para inscribir/rechazar alumnos
            let opcionDocenteAceptar = false;
            let opcionDocenteRechazar = false;
            let opcionNotas = false;
            let opcionVideo = false;
            if(this.props.user !== null && this.props.user.tipo === "docente")
            {
                let pertenece = false;
                for (let i = 0; i < this.props.user.ediciones.length; i++) {
                    let element = this.props.user.ediciones[i];
                    if (element === this.state.datosEdicion.nombre) {
                        pertenece = true;
                    }
                }
                if (pertenece) {
                    opcionVideo = !this.state.datosEdicion.cerrada;
                    opcionDocenteAceptar = !this.state.datosEdicion.cerrada;
                    opcionDocenteRechazar = !this.state.datosEdicion.cerrada;
                    this.state.estudiantesEdicion.map((user, index) => {
                        let hayInscripto = false;
                        if(user.estadoEdiciones[this.state.datosEdicion.nombre].estado.toLowerCase() === 'inscripto') {
                            hayInscripto = true;
                        }
                        opcionNotas = !hayInscripto;
                    });
                    if(opcionNotas && !this.state.datosEdicion.cerrada) {
                        tipoBoton = (<button onClick={this.cerrarEdicion} type="button" className="btn btn-outline-success">Cerrar Edicion</button>);
                    }
                }
            }
            renderizar = (
                <div className="container consulta-edicion-curso">
                    <ModalVideoEdicion datosEdicion={this.state.datosEdicion} />
                    <ModalReproducirVideo video={ this.state.video } />
                    {/* fila con la imagen y el boton de inscribirse */}
                    <div className="row">
                        <div className="col-12 header-edicion">
                            <div>
                                <img src={this.state.datosEdicion.src === "" ? no_image : this.state.datosEdicion.src} alt="Imagen Edicion"/>
                            </div>
                            <div style={{width: '70%'}}>
                                <p>
                                    <span className="nombre-edicion">{this.state.datosEdicion.nombre}</span>
                                    <br/>
                                    <span className="instituto-edicion">-> {this.state.datosEdicion.curso}</span>
                                    <br/>
                                    <span className="instituto-edicion">-> {this.state.datosEdicion.instituto}</span>
                                </p>
                                {tipoBoton}
                            </div>
                        </div>
                    </div>
                    <div className="row fila-datos">
                        <div className="col-sm-12 col-lg-4 pl-0 mb-lg-0 pr-lg-3 pr-sm-0 mb-sm-3 container-datos-edicion">
                            <div className="datos-edicion col-xs-12">
                                <ul className="list-group">
                                    <li className="list-group-item"><span className="title-text">Fecha Inicio:</span> {this.state.datosEdicion.fechaInicio.dayOfMonth + "/" + (this.state.datosEdicion.fechaInicio.month + 1) + "/" + this.state.datosEdicion.fechaInicio.year}</li>
                                    <li className="list-group-item"><span className="title-text">Fecha Fin:</span> {this.state.datosEdicion.fechaFin.dayOfMonth + "/" + (this.state.datosEdicion.fechaFin.month + 1) + "/" + this.state.datosEdicion.fechaFin.year}</li>
                                    <li className="list-group-item"><span className="title-text">Fecha Publicacion:</span> {this.state.datosEdicion.fechaPublicacion.dayOfMonth + "/" + (this.state.datosEdicion.fechaPublicacion.month + 1) + "/" + this.state.datosEdicion.fechaPublicacion.year}</li>
                                    <li className="list-group-item"><span className="title-text">Cupo:</span> {this.state.datosEdicion.cupo}</li>
                                    <li className="list-group-item"><span className="title-text">Estado:</span> {this.state.datosEdicion.cerrada ? "Cerrada" : "Abierta"}</li>
                                </ul>
                            </div>
                        </div>
                        <div className="col-xs-12 col-lg-8 container-table-docentes">
                            <table className="table table-hover" id="tabla-docentes-consulta-edicion">
                                <thead className="">
                                    <tr>
                                        <th>Docente</th>
                                        <th>Nombre</th>
                                        <th>Apellido</th>
                                        {/* Render condicional cabezal de la tabla */}
                                        {isMobile ? null : <th>Opciones</th>}
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.docentesEdicion.map((element, index) => {
                                        return   (
                                                <tr key={index}>
                                                    <td className="col-xs-3">{element.nickname}</td>
                                                    <td className="col-xs-3">{element.nombre}</td>
                                                    <td className="col-xs-3">{element.apellido}</td>
                                                    {/* Render condicional de la tabla */}
                                                    { isMobile ? null : (
                                                        <td className="col-xs-3">
                                                            <Link to={{
                                                                pathname: '/ConsultaUsuarioDos',
                                                                state: {
                                                                    nombre: element.nickname
                                                                }
                                                            }}><i className="fas fa-info-circle"></i></Link>
                                                        </td>
                                                    )}
                                                </tr>
                                            )
                                        })
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div className="row fila-estudiantes">
                        <div className="col-12">
                            <table className="table table-hover" id="tabla-estudiantes-consulta-edicion">
                                <thead className="">
                                    <tr>
                                        <th>Usuario Estudiante</th>
                                        <th>Nombre</th>
                                        <th>Apellido</th>
                                        {/* Render condicional de la tabla */}
                                        {isMobile ? null : <th>Estado Inscripcion</th>}
                                        {/* {!isMobile && opcionNotas ? <th>Nota {this.state.datosEdicion.cerrada ? null : <a href="# "  onClick={(e)=>{e.preventDefault(); alert("eniando a todos...")}}><i class="fas fa-paper-plane"></i></a>}</th> : null} */}
                                        {!isMobile && opcionNotas ? <th>Nota</th> : null}
                                        {isMobile ? null : <th>Opciones</th>}
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.estudiantesEdicion.map((element, index) => {
                                        return   (
                                                <tr key={index}>
                                                    <td className="col-xs-3">{element.nickname}</td>
                                                    <td className="col-xs-3">{element.nombre}</td>
                                                    <td className="col-xs-3">{element.apellido}</td>
                                                    {/* Render Condicional de la tabla */}
                                                    {isMobile ? null : (
                                                        <td className="col-xs-2">{element.estadoEdiciones[this.state.datosEdicion.nombre].estado}</td>
                                                    )}
                                                    {this.imprimirNota(index, element.estadoEdiciones[this.state.datosEdicion.nombre].nota, element.estadoEdiciones[this.state.datosEdicion.nombre].estado.toLowerCase(), opcionNotas, element.nickname)}
                                                    {isMobile ? null : (
                                                        <td className="col-xs-1 tabla-opciones">
                                                            {(element.estadoEdiciones[this.state.datosEdicion.nombre].estado.toLowerCase() === "inscripto" && opcionDocenteAceptar) ? (<a href="# " onClick={(e)=>{e.preventDefault(); this.inscribirEstudiante(element.nickname,index)}}><i className="fas fa-check-circle"></i></a>) : null}
                                                            {(element.estadoEdiciones[this.state.datosEdicion.nombre].estado.toLowerCase() === "inscripto" && opcionDocenteRechazar) ? (<a href="# "  onClick={(e)=>{e.preventDefault(); this.rechazarEstudiante(element.nickname,index)}}><i className="fas fa-times-circle"></i></a>) : null}
                                                            {(element.estadoEdiciones[this.state.datosEdicion.nombre].video !== "" && opcionVideo) ? (<a href="# " onClick={(e)=>{e.preventDefault(); this.setVideo(element.estadoEdiciones[this.state.datosEdicion.nombre].video)} } data-toggle="modal" data-target="#reproductorVideoModal"><i className="fas fa-play-circle"></i></a>) : null}
                                                            <Link to={{
                                                                pathname: '/ConsultaUsuarioDos',
                                                                state: {
                                                                    nombre: element.nickname
                                                                }
                                                            }}><i className="fas fa-info-circle"></i></Link>
                                                        </td>
                                                    
                                                    )}
                                                </tr>
                                            )
                                        })
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                    {this.filtrosTabla()}
                    {this.props.user !== null && (this.props.user.tipo === "estudiante" || this.props.user.tipo === "docente") ? <Comentarios tipo={this.props.user.tipo} comentarios={this.state.comentariosEdicion} responder={this.responderComentario}/> : null}
                    {isMobile ? null : this.imprimirComentar(estado)}
                </div>
            );
        } else {
            renderizar = (<Loader/>);
        }

        return (
            <div>
                {renderizar}
            </div>
        );
    }
}