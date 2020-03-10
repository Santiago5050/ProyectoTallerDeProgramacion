import React, { Component } from 'react';
import '../css/ConsultaUsuarioDos.css'
import ApiRequest2 from '../js/api2';
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer, toast} from 'react-toastify';

class ModalConsultaUsuario extends Component {

    constructor(props) {
        super(props);
        this.state = {
            nombre: null,
            apellido: null,
            fechaNacimiento: null,
            error: null
        }
    }

    limpiarFormulario = () => {
        document.getElementById("formGroupExampleInputModificar1").value = "";
        document.getElementById("formGroupExampleInputModificar2").value = "";
        document.getElementById("formGroupExampleInputModificar3").value = "";
    }

    modificar = () => {

        let nombre = this.props.data.nombre;
        let apellido = this.props.data.apellido;
        let mail = this.props.data.mail;
        let fechaNacimiento = this.props.data.fechaNacimiento.year + "-" + (this.props.data.fechaNacimiento.month + 1) + "-" + this.props.data.fechaNacimiento.dayOfMonth;
        let src = this.props.data.src;

        if (this.state.nombre) {
            nombre = this.state.nombre;
        }
        if (this.state.apellido) {
            apellido = this.state.apellido;
        }
        if (this.state.fechaNacimiento) {
            fechaNacimiento = this.state.fechaNacimiento;
        }

        let api = new ApiRequest2();
        api.modificarUsuario(nombre, apellido, mail, fechaNacimiento, src)
        .then(response => {
            if (response.data.hasOwnProperty('OK')) {
                api.updateLogin()
                .then(response => {
                    localStorage.setItem('user', JSON.stringify(response.data));
                    window.location.reload();
                    this.limpiarFormulario();
                })
            } else {
                toast.error(response.data.ERROR);
            }
        })
        .catch(errorResponse => {
            this.setState({
                error: errorResponse
            })
        })
    }

    handleChange = (e) => {
		this.setState({[e.target.name]: e.target.value});
        //this.setState({"botonAceptar": this.habilitarAceptar()});
    }
    
    habilitarAceptarNombre = () => {
        return !this.state.nombre
    }

    habilitarAceptarApellido = () => {
        return !this.state.apellido
    }

    habilitarAceptarFecha = () => {
        return !this.state.fechaNacimiento
    }

	render() {
		return (
            <div className="ModalConsultaUsuaruio">

            <div className="modal" id="consultaUsuarioNombreModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div className="modal-dialog" role="document">
					<div className="modal-content">
						<div className="modal-header">
							<h5 className="modal-title" id="exampleModalLabel">Editar</h5>
							<button type="button" className="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div className="modal-body">
							<form>
								<div className="form-group">
									<label htmlFor="formGroupExampleInput"> Nombre </label>
									<input type="text" className="form-control" id="formGroupExampleInputModificar1" placeholder="nombre" name="nombre" onChange={ this.handleChange }/>
								</div>
							</form>
						</div>
						<div className="modal-footer">
							<button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={ this.limpiarFormulario }>Cancelar</button>
							<button type="button" className="btn btn-primary" disabled={ this.habilitarAceptarNombre() } onClick={ this.modificar }>Aceptar</button>
						</div>
					</div>
				</div>
            </div>

            <div className="modal" id="consultaUsuarioApellidoModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Editar</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form>
                            <div className="form-group">
                                <label htmlFor="formGroupExampleInput"> Apellido </label>
                                <input type="text" className="form-control" id="formGroupExampleInputModificar2" placeholder="apellido" name="apellido" onChange={ this.handleChange }/>
                            </div>
                        </form>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={ this.limpiarFormulario }>Cancelar</button>
                        <button type="button" className="btn btn-primary" disabled={ this.habilitarAceptarApellido() } onClick={ this.modificar }>Aceptar</button>
                    </div>
                </div>
            </div>
            </div>

            <div className="modal" id="consultaUsuarioNacimientoModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog" role="document">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Editar</h5>
                        <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div className="modal-body">
                        <form>
                            <div className="form-group">
                                <label htmlFor="formGroupExampleInput"> Fecha de nacimiento </label>
                                <input type="date" className="form-control" id="formGroupExampleInputModificar3" name="fechaNacimiento" onChange={ this.handleChange }/>
                            </div>
                        </form>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={ this.limpiarFormulario }>Cancelar</button>
                        <button type="button" className="btn btn-primary" disabled={ this.habilitarAceptarFecha() } onClick={ this.modificar }>Aceptar</button>
                    </div>
                </div>
            </div>
            </div>

            </div>
		);
	}
}

export default ModalConsultaUsuario;