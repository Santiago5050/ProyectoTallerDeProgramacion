import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import '../css/ConsultaUsuarioDos.css'
import no_image from './../res/no_image.jpg';
import ModalConsultaUsuario from './ModalConsultaUsuario';
import ApiRequest from '../js/api';
import InputFoto from './inputfoto';
import ApiRequest2 from '../js/api2';
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer, toast} from 'react-toastify';
import Loader from './Loader.jsx';
import ErrorPage from './errorPage.jsx'


class ConsultaUsuarioDos extends Component {

    constructor(props) {
        super(props);
        this.state = {
            handle: null,
            usuario: null,
            errorUsuario: null,
            ediciones: null,
            errorEdiciones: null,
            programas: null,
            errorProgramas: null,
            seguidos: null,
            seguidosError: null,
            logueado: null
        }
    }

    componentDidMount() {
        const handle = this.props.location.state.nombre;
        this.state.handle = handle;
        this.apiCallUsuario(handle);
        this.apiCallEdiciones(handle);
        this.apiCallProgramas(handle);
        this.apiCallSeguidos();
        this.apiCallLogueado();
    }

    componentDidUpdate() {
        let handle;
        if (this.props.location !== undefined) {
            handle = this.props.location.state.nombre;
        }
        if (this.state.usuario === null || this.state.usuario.nickname !== handle) {
            this.state.handle = handle;
            this.apiCallUsuario(handle);
            this.apiCallEdiciones(handle);
            this.apiCallProgramas(handle);
            this.apiCallSeguidos();
            this.apiCallLogueado();
        }
    }

    apiCallLogueado = () => {
        if (!(this.props.data.user === null)) {
            let api = new ApiRequest2();
            api.getUsuario(this.props.data.user.nickname)
            .then(response => {
                if (response.data.hasOwnProperty('ERROR')) {
                    //this.setState({
                    //    errorUsuario: response.data.ERROR
                    //})
                } else {
                    this.setState({
                        logueado: response.data
                    });
                }
            })
            .catch(errorResponse => {
                //this.setState({
                //    errorUsuario: errorResponse
                //});
            });
        }
    }

    apiCallUsuario = (handle) => {
        let api = new ApiRequest2();
        api.getUsuario(handle)
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                this.setState({
                    errorUsuario: response.data.ERROR
                })
            } else {
                this.setState({
                    usuario: response.data
                });
            }
        })
        .catch(errorResponse => {
            this.setState({
                errorUsuario: errorResponse
            });
        });
    }

    apiCallEdiciones = (handle) => {
        let api = new ApiRequest2();
        api.getEdicionesUsuario(handle)
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                this.setState({
                    errorEdiciones: response.data.ERROR
                })
            } else {
                this.setState({
                    ediciones: response.data
                });
            }
        })
        .catch(errorResponse => {
            this.setState({
                errorEdiciones: errorResponse
            });
        });
    }

    apiCallProgramas = (handle) => {
        let api = new ApiRequest2();
        api.getProgramasUsuario(handle)
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                this.setState({
                    errorProgramas: response.data.ERROR
                })
            } else {
                this.setState({
                    programas: response.data
                });
            }
        })
        .catch(errorResponse => {
            this.setState({
                errorProgramas: errorResponse
            });
        });
    }

    apiCallSeguidos = () => {
        let api = new ApiRequest2();
        api.getUsuarios()
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                this.setState({
                    seguidosError: response.data.ERROR
                })
            } else {
                this.setState({
                    seguidos: response.data
                });
            }
        })
        .catch(errorResponse => {
            this.setState({
                seguidosError: errorResponse
            });
        });
    }

    componentWillUnmount() {
        this.props.search('');
    }

    searchBarEdiciones = () => {
        this.props.search('tabla-menu1-ediciones');
    }

    searchBarProgramas = () => {
        this.props.search('tabla-menu2-programas');
    }

    searchBarSeguidos = () => {
        this.props.search('tabla-menu3-seguidos');
    }

    searchBarNull = () => {
        this.props.search('');
    }

    loadEdiciones = (ediciones, usuario) => {
        if (usuario.hasOwnProperty('programas')) {
            return ediciones.map(edicion => {
                return (
                    <tr key={ edicion.nombre }>
                    <td>{edicion.nombre}</td>
                    <td>{edicion.fechaInicio.dayOfMonth + "/" + (edicion.fechaInicio.month+1) + "/" + edicion.fechaInicio.year}</td>
                    <td>{edicion.fechaFin.dayOfMonth + "/" + (edicion.fechaFin.month+1) + "/" + edicion.fechaFin.year}</td>
                    <td>{edicion.estado}</td>
                    <td><Link to={{
                        pathname: "/consulta-edicion-curso",
                        state: {
                            nombre: edicion.nombre
                        }
                    }}>Ver más</Link></td>
                    </tr>
                );
            })
        } else {
            return ediciones.map(edicion => {
                return (
                    <tr key={ edicion.nombre }>
                    <td>{edicion.nombre}</td>
                    <td>{edicion.fechaInicio.dayOfMonth + "/" + (edicion.fechaInicio.month+1) + "/" + edicion.fechaInicio.year}</td>
                    <td>{edicion.fechaFin.dayOfMonth + "/" + (edicion.fechaFin.month+1) + "/" + edicion.fechaFin.year}</td>
                    <td><Link to={{
                        pathname: "/consulta-edicion-curso",
                        state: {
                            nombre: edicion.nombre
                        }
                    }}>Ver más</Link></td>
                    </tr>
                );
            })
        }
        
    }

    tabEdiciones = () => {
        if (!(this.state.ediciones === null)) {
            return(
                <div id="menu1" className="container tab-pane fade">
                    <table className="table table-hover" id="tabla-menu1-ediciones">
                    <thead>
                        <tr>
                        <th>Nombre</th>
                        <th>Fecha de inicio</th>
                        <th>Fecha de fin</th>
                        { this.columnaEstado() }
                        <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        { this.loadEdiciones(this.state.ediciones, this.state.usuario) }
                    </tbody>
                    </table>
                </div>
            )
        }
    }

    loadProgramas = (programas) => {
        return programas.map(programa => {
            return (
                <tr key={ programa.nombre }>
                <td>{programa.nombre}</td>
                <td>{programa.fechaInicio.dayOfMonth + "/" + (programa.fechaInicio.month+1) + "/" + programa.fechaInicio.year}</td>
                <td>{programa.fechaFin.dayOfMonth + "/" + (programa.fechaFin.month+1) + "/" + programa.fechaFin.year}</td>
                <td><Link to={{
                    pathname: "/programa",
                    state: {
                        nombre: programa.nombre
                    }
                }}>Ver más</Link></td>
                </tr>
            );
        });
    }

    botonEdicion = (fieldName, usuario) => {
        if (this.props.data.user != null) {
            if (usuario.nickname === this.props.data.user.nickname) {
                if (fieldName === "editableFieldNombre") {
                    return(
                        <span className="editList"><i className="fas fa-edit" data-toggle="modal" data-target="#consultaUsuarioNombreModal"></i></span>
                    )
                } else if (fieldName === "editableFieldApellido") {
                    return(
                        <span className="editList"><i className="fas fa-edit" data-toggle="modal" data-target="#consultaUsuarioApellidoModal"></i></span>
                    )
                } else if (fieldName === "editableFieldFechaNacimiento") {
                    return(
                        <span className="editList"><i className="fas fa-edit" data-toggle="modal" data-target="#consultaUsuarioNacimientoModal"></i></span>
                    )
                }
            }
        }
    }

    tabProgramasBoton = () => {
        if (!(this.state.programas === null)) {
            return(
                <li className="nav-item">
                <a className="nav-link" data-toggle="pill" href="#menu2" onClick={ this.searchBarProgramas }>Programas de Formación</a>
                </li>
            )
        }
    }

    tabProgramasContenido = () => {
        if (!(this.state.programas === null)) {
            return(
                /* Contenido de tercera tab */
                <div id="menu2" className="container tab-pane fade">
                <table className="table table-hover" id="tabla-menu2-programas">
                    <thead>
                        <tr>
                        <th>Nombre</th>
                        <th>Fecha de inicio</th>
                        <th>Fecha de fin</th>
                        <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        { this.loadProgramas(this.state.programas) }
                    </tbody>
                    </table>
                </div>
            ) 
        }
    }

    loadSeguidos = () => {
        if (this.state.seguidos) {
            return this.state.seguidos.map(usuario => {
                if (this.state.usuario.sigue.includes(usuario.nickname)) {
                    return(
                        <tr key={ usuario.nickname }>
                        <td>{usuario.nickname}</td>
                        <td>{usuario.nombre}</td>
                        <td>{usuario.apellido}</td>
                        <td><Link to={{
                            pathname: "/ConsultaUsuarioDos",
                            state: {
                                nombre: usuario.nickname
                            }
                        }}>Ver más</Link></td>
                        </tr>
                    );
                }
            });
        }
    }

    tabSeguidosBoton = () => {
        if (!(this.state.usuario.sigue === null) && (this.state.usuario.sigue.length > 0)) {
            return(
                <li className="nav-item">
                <a className="nav-link" data-toggle="pill" href="#menu3" onClick={ this.searchBarSeguidos }>Seguidos</a>
                </li>
            )
        }
    }

    tabSeguidosContenido = () => {
        if (this.state.usuario.sigue.length > 0) {
            return(
                 /* Contenido de cuarta tab */
                 <div id="menu3" className="container tab-pane fade">
                 <table className="table table-hover" id="tabla-menu3-seguidos">
                     <thead>
                         <tr>
                         <th>Nickname</th>
                         <th>Nombre</th>
                         <th>Apellido</th>
                         <th>Opciones</th>
                         </tr>
                     </thead>
                     <tbody>
                         { this.loadSeguidos() }
                     </tbody>
                     </table>
                 </div>
            )
        }
    }

    activarSeguidoPorBoton = () => {
        let retorno = false;
        if (this.state.seguidos) {
            this.state.seguidos.map(usuario => {
                if (usuario.sigue.includes(this.state.usuario.nickname)) {
                    retorno = true;
                }
            });
        }
        return retorno;
    }

    tabSeguidoPorBoton = () => {
        let activar = this.activarSeguidoPorBoton();
        if (activar) {
            return(
                <li className="nav-item">
                <a className="nav-link" data-toggle="pill" href="#menu4" onClick={ this.searchBarSeguidos }>Seguido por</a>
                </li>
            )
        }
    }

    tabSeguidoPorContenido = () => {
        return(
            /* Contenido de cuarta tab */
            <div id="menu4" className="container tab-pane fade">
            <table className="table table-hover" id="tabla-menu4-seguidos">
                <thead>
                    <tr>
                    <th>Nickname</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Opciones</th>
                    </tr>
                </thead>
                <tbody>
                    { this.loadSeguidoPor() }
                </tbody>
                </table>
            </div>
        )
    }

    loadSeguidoPor = () => {
        if (this.state.seguidos) {
            return this.state.seguidos.map(usuario => {
                if (usuario.sigue.includes(this.state.usuario.nickname)) {
                    return(
                        <tr key={ usuario.nickname }>
                        <td>{usuario.nickname}</td>
                        <td>{usuario.nombre}</td>
                        <td>{usuario.apellido}</td>
                        <td><Link to={{
                            pathname: "/ConsultaUsuarioDos",
                            state: {
                                nombre: usuario.nickname
                            }
                        }}>Ver más</Link></td>
                        </tr>
                    );
                }
            });
        }
    }

    columnaEstado = () => {
        if (!(this.state.programas === null)) {
            return (
                <th>Estado</th>
            );
        }
    }

    fotoUsuario = (usuario) => {
        if ((this.props.data.user != null) && (usuario.nickname === this.props.data.user.nickname)) {
            return (
                <InputFoto img={ usuario.src } subir={ this.guardar } />
            )
        } else {
            return (
                <img src={ usuario.src === "" ? no_image : usuario.src } id ="foto-perfil-consulta" alt="Imagen Usuario" height="200" width="200"/>
            )
        }
    }

    guardar = (foto) => {
        let ip = window.Configs.ip
		let port= window.Configs.port
        let extension = foto.name.split(".");
        let formData = new FormData();
        formData.append("file", foto);
        formData.append("nombre", this.state.usuario.nickname + "." + extension[1]);
        let source = 'http://'+ ip +':'+ port +'/edext-server-web/images/' + this.state.usuario.nickname + "." + extension[1];
        let api = new ApiRequest2();
        api.enviarImagen(formData);
        let fechaNacimiento = this.state.usuario.fechaNacimiento.year + "-" + (this.state.usuario.fechaNacimiento.month+1) + "-" + this.state.usuario.fechaNacimiento.dayOfMonth;
        api.modificarUsuario(this.state.usuario.nombre, this.state.usuario.apellido, this.state.usuario.mail, fechaNacimiento, source)
        .then(response => {
            if (response.data.hasOwnProperty('OK')) {
                toast.info(response.data.OK);
            } else {
                toast.error(response.data.ERROR);
            }
        })
        .catch(errorResponse => {
            console.log("Error", errorResponse)
        })
    }

    buscarEnArray = (nickname) => {
        return nickname === nickname;
    }

    botonSeguir = () => {
        if (!(this.props.data.user === null) && !(this.state.usuario.nickname === this.props.data.user.nickname)) {
            if (!(this.state.logueado === null)) {
                if (this.state.logueado.sigue.includes(this.state.usuario.nickname)) {
                    // Ofrecer dejar de seguir
                    return(
                        <button type="button" className="btn btn-primary boton-seguir" onClick={ this.dejarDeSeguir }>Dejar de seguir</button>
                    )
                } else {
                    // Ofrecer seguir
                    return(
                        <button type="button" className="btn btn-primary boton-seguir" onClick={ this.seguir }>Seguir</button>
                    )
                }
            }
        }
    }

    seguir = () => {
        let api = new ApiRequest2();
        api.seguirUsuario(this.state.usuario.nickname)
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                toast.error(response.data.ERROR)
            } else {
                this.apiCallLogueado();
                this.apiCallSeguidos();
            }
        })
        .catch(errorResponse => {
            toast.error(errorResponse)
        });
    }

    dejarDeSeguir = () => {
        let api = new ApiRequest2();
        api.dejarDeSeguirUsuario(this.state.usuario.nickname)
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                toast.error(response.data.ERROR)
            } else {
                this.apiCallLogueado();
                this.apiCallSeguidos();
            }
        })
        .catch(errorResponse => {
            toast.error(errorResponse)
        });
    }

    instituto = () => {
        if (this.state.usuario.hasOwnProperty("instituto")) {
            return(
                <li><span className="titleList">Instituto:</span> <span className="valueList">{ this.state.usuario.instituto }</span></li>
            );
        }
    }

    render() {
        if (!(this.state.errorUsuario === null) || !(this.state.errorEdiciones === null)) {
            return(
                <div>
                    <ErrorPage mensaje={this.state.errorUsuario}/>
                </div>
            )
        }
        if (!(this.state.usuario === null) && !(this.state.ediciones === null)) {
            return(
                <div className="container tabla-diego ConsultaUsuarioDos">
                    <ModalConsultaUsuario data={ this.state.usuario } />
                    <div className="userHeader">
                        <div className="row justify-content-md-left">
                            <div className="fotoPerfil column-6">
                                { this.fotoUsuario(this.state.usuario) }
                            </div>
                            <div className="nombrePerfil column-6">
                                <h3>{ this.state.usuario.nombre } <span className="headerName">{ this.state.usuario.apellido }</span> </h3>
                                <p><i className="far fa-envelope"></i> <span className="headerName">{ this.state.usuario.mail }</span> </p>
                                <span>{ this.botonSeguir() }</span>
                            </div>
                        </div>
                    </div>

                    <div className="tabs">
                        { /* Botones de las tabs. */ }
                        <div className="row justify-content-md-left">
                            <ul className="nav nav-pills" role="tablist">
                                <li className="nav-item">
                                <a className="nav-link active" data-toggle="pill" href="#home" onClick={ this.searchBarNull }>General</a>
                                </li>
                                <li className="nav-item">
                                <a className="nav-link" data-toggle="pill" href="#menu1" onClick={ this.searchBarEdiciones }>Ediciones de Cursos</a>
                                </li>
                                { this.tabProgramasBoton() }
                                { this.tabSeguidosBoton() }
                                { this.tabSeguidoPorBoton() }
                            </ul>
                        </div>

                        { /* Contenido de las tabs. */ }
                        <div className="row justify-content-md-left">
                            <div className="tab-content">

                                { /* Contenido de primer tab */ }
                                <div id="home" className="container tab-pane active">
                                    <ul>
                                        <li><span className="titleList">Usuario:</span> <span className="valueList">{ this.state.usuario.nickname }</span></li>
                                        <li><span className="titleList">Nombre:</span> <span className="valueList" id="editableFieldNombre">{ this.state.usuario.nombre }</span> { this.botonEdicion("editableFieldNombre", this.state.usuario) } </li>
                                        <li><span className="titleList">Apellido:</span> <span className="valueList" id="editableFieldApellido">{ this.state.usuario.apellido }</span> { this.botonEdicion("editableFieldApellido", this.state.usuario) } </li>
                                        { this.instituto() }
                                        <li><span className="titleList">Mail:</span> <span className="valueList">{ this.state.usuario.mail }</span></li>
                                        <li><span className="titleList">Fecha de nacimiento:</span> <span className="valueList" id="editableFieldFechaNacimiento">{ this.state.usuario.fechaNacimiento.dayOfMonth + "/" + (this.state.usuario.fechaNacimiento.month + 1) + "/" + this.state.usuario.fechaNacimiento.year }</span> { this.botonEdicion("editableFieldFechaNacimiento", this.state.usuario) } </li>
                                    </ul>
                                </div>

                                { /* Contenido de segunda tab */ }
                                { this.tabEdiciones() }

                                { /* Contenido de tercera tab */ }
                                { this.tabProgramasContenido() }

                                { /* Contenido de la cuarta tab */ }
                                { this.tabSeguidosContenido() }

                                { /* Contenido de la cuarta tab */ }
                                { this.tabSeguidoPorContenido() }
                                
                            </div>
                        </div>
                    </div>

                </div>
            )
        } else {
            return(
                <div>
                    <Loader />
                </div>
            )
        }
    }

}

export default ConsultaUsuarioDos;
