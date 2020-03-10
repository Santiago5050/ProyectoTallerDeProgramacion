import React, { Component } from 'react';
import InputFoto from './inputfoto';
import no_image from './../res/no_image.jpg';
import '../css/ModalRegistrarse.css';
import ApiRequest2 from '../js/api2';
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer, toast} from 'react-toastify';


class ModalRegistrarse extends Component {

	constructor(props) {
		super(props);
		this.state = {
			"usuario": null,
			"nombre": null,
			"apellido": null,
			"email": null,
			"contraseña": null,
			"confirmarContraseña": null,
			"fechaNacimiento": null,
			"source": null,
			"docente": false,
			"instituto": null,
			"combobox": null,
			"botonAceptar": true,
			"error": null,
			"existeNickname": false,
			"existeMail": false
		}
	}


	componentDidMount() {
		this.apiCall();
		
	}

	apiCall = () => {
		let api = new ApiRequest2();
        api.getInstitutos()
        .then(response => {
			this.setState({
				"combobox": response.data
			});
        })
        .catch(errorResponse => {
			this.setState({
				"combobox": null
			});
        });
	}

	checkbox = () => {
		this.setState({"docente": !this.state.docente});
		this.setState({"instituto": null});
	}

	loadCombobox = () => {
		if (this.state.docente === true) {
			return (
				<div className="dropdown column-6 combo">
					<select  name="instituto" onChange={ this.handleChange }>
						<option selected="">Institutos</option>
						{ this.loadInstitutos() }
					</select>
				</div>
			);
		}
	}

	loadInstitutos = () => {
		return this.state.combobox.map(instituto => {
			let inst = instituto.nombre;
			return(
				<option key={ inst } value={ inst }>{ inst }</option>
			)
		})
	}

	handleChange = (e) => {
		this.setState({[e.target.name]: e.target.value});
		this.setState({"botonAceptar": this.habilitarAceptar()});
	}

	contraseñasNoCoinciden = () => {
		if (!(this.state.contraseña === this.state.confirmarContraseña)) {
			return(
				<div>
					<p className="contrasenas-no-coinciden-3498">¡Las contraseñas no coinciden!</p>
				</div>
			)
		}
	}

	habilitarAceptar = () => {
		return !((this.state.usuario) && (this.state.nombre) && (this.state.apellido) 
		&& (this.state.email) && (this.state.contraseña)
		&& (this.state.contraseña === this.state.confirmarContraseña) && (this.state.fechaNacimiento) &&
		((this.state.docente && (this.state.instituto)) || (!this.state.docente)))
	}

	limpiarFormulario = () => {
		this.setState({
			"usuario": null,
			"nombre": null,
			"apellido": null,
			"email": null,
			"contraseña": null,
			"confirmarContraseña": null,
			"fechaNacimiento": null,
			"docente": false,
			"instituto": null,
			"source": null,
			"existeNickname": false,
			"existeMail": false
		});
		document.getElementById("formGroupExampleInput").value = "";
		document.getElementById("formGroupExampleInput2").value = "";
		document.getElementById("formGroupExampleInput3").value = "";
		document.getElementById("formGroupExampleInput4").value = "";
		document.getElementById("formGroupExampleInput5").value = "";
		document.getElementById("formGroupExampleInput6").value = "";
		document.getElementById("formGroupExampleInput7").value = "";
		document.getElementById("defaultCheck1").checked = false;
	}

	existeUsuario = () => {
		let api = new ApiRequest2();
		api.validarDatosNickname(this.state.usuario)
		.then(response => {
			if (response.data.hasOwnProperty('OK')) {
				if (response.data.OK === 'true') {
					this.setState({
						"existeNickname": true
					});
				} else {
					this.setState({
						"existeNickname": false
					});
				}
			}
		})
		.catch(errorResponse => {
			console.log(errorResponse)
		})
	}

	alertaUsuarioYaExiste = () => {
		if (this.state.existeNickname) {
			return(
				<div>
					<p className="usuario-ya-existe-3498">¡El usuario ya existe!</p>
				</div>
			)
		}
	}

	existeMail = () => {
		let api = new ApiRequest2();
		api.validarDatosMail(this.state.email)
		.then(response => {
			if (response.data.hasOwnProperty('OK')) {
				if (response.data.OK === 'true') {
					this.setState({
						"existeMail": true
					});
				} else {
					this.setState({
						"existeMail": false
					});
				}
			}
		})
		.catch(errorResponse => {
			console.log(errorResponse)
		})
	}

	alertaMailYaExiste = () => {
		if (this.state.existeMail) {
			return(
				<div>
					<p className="mail-ya-existe-3498">¡El mail ya existe!</p>
				</div>
			)
		}
	}

	aceptar = (e) => {
		let foto;
			if (this.state.source !== null) {
				foto = this.state.source;
			} else {
				foto = "";
			}
		let api = new ApiRequest2();
		if (this.state.docente && !(this.state.instituto === null)) {
			api.altaDocente(this.state.instituto, this.state.usuario, this.state.nombre, this.state.apellido, this.state.email, this.state.fechaNacimiento, this.state.contraseña, foto)
			.then(response => {
				if (response.data.hasOwnProperty('OK')) {
					toast.info(response.data.OK);
					this.limpiarFormulario();
				} else {
					toast.error(response.data.ERROR);
				}
			})
			.catch(errorResponse => {
				this.setState({
					error: errorResponse
				})
			})
		} else {
			api.altaEstudiante(this.state.usuario, this.state.nombre, this.state.apellido, this.state.email, this.state.fechaNacimiento, this.state.contraseña, foto)
			.then(response => {
				if (response.data.hasOwnProperty('OK')) {
					toast.info(response.data.OK);
					this.limpiarFormulario();
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
	}

	guardar = (foto) => {
		let ip = window.Configs.ip
		let port= window.Configs.port
		let extension = foto.name.split(".");
        let formData = new FormData();
        formData.append("file", foto);
        formData.append("nombre", this.state.usuario + "." + extension[1]);
		let source = 'http://'+ ip +':'+ port +'/edext-server-web/images/' + this.state.usuario + '.' + extension[1];
		this.state.source = source;
        let api = new ApiRequest2();
        api.enviarImagen(formData);
	}


	render() {
		let modal = "modal";
		// console.log(document.getElementById("registrarseModal"))
		return (
			<div className="modal modalRegistrarse" id="registrarseModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div className="modal-dialog" role="document">
					<div className="modal-content">
						<div className="modal-header">
							<h5 className="modal-title" id="exampleModalLabel">Registrarse</h5>
							<button type="button" className="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div className="modal-body">
							<form>
								<div>
									{document.getElementById("registrarseModal")!==null && document.getElementById("registrarseModal").classList.contains("show")?<InputFoto img={ no_image } subir={ this.guardar } nombre="juan"/>:null}
								</div>
								<div className="form-group">
									<label htmlFor="formGroupExampleInput">Usuario</label>
									<input type="text" className="form-control" id="formGroupExampleInput" name="usuario" onChange={ this.handleChange } onBlur={ this.existeUsuario }/>
								</div>
								{ this.alertaUsuarioYaExiste() }
								<div className="form-group">
									<label htmlFor="formGroupExampleInput">Nombre</label>
									<input type="text" className="form-control" id="formGroupExampleInput2" name="nombre" onChange={ this.handleChange }/>
								</div>
								<div className="form-group">
									<label htmlFor="formGroupExampleInput">Apellido</label>
									<input type="text" className="form-control" id="formGroupExampleInput3" name="apellido" onChange={ this.handleChange }/>
								</div>
								<div className="form-group">
									<label htmlFor="formGroupExampleInput">Email</label>
									<input type="mail" className="form-control" id="formGroupExampleInput4" name="email" onChange={ this.handleChange } onBlur={ this.existeMail }/>
								</div>
								{ this.alertaMailYaExiste() }
								<div className="form-group">
									<label htmlFor="formGroupExampleInput2">Contraseña</label>
									<input type="password" className="form-control" id="formGroupExampleInput5" name="contraseña" onChange={ this.handleChange }/>
								</div>
								<div className="form-group">
									<label htmlFor="formGroupExampleInput3">Confirmar Contraseña</label>
									<input type="password" className="form-control" id="formGroupExampleInput6" name="confirmarContraseña" onChange={ this.handleChange }/>
								</div>
								{ this.contraseñasNoCoinciden() }
								<div className="form-group">
									<label htmlFor="formGroupExampleInput3">Fecha de Nacimiento</label>
									<input type="date" className="form-control" id="formGroupExampleInput7" name="fechaNacimiento" onChange={ this.handleChange }/>
								</div>
								<div className="row">
									<div className="form-check column-6 checkbox">
										<input className="form-check-input" type="checkbox" value="" id="defaultCheck1" name="docente" onChange={ this.checkbox }/>
										<label className="form-check-label" htmlFor="defaultCheck1">
											Docente
										</label>
									</div>
									{ this.loadCombobox() }
								</div>
							</form>
						</div>
						<div className="modal-footer">
							<button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={ this.limpiarFormulario }>Cancelar</button>
							<button type="button" disabled={ this.habilitarAceptar() } className="btn btn-primary" onClick={ this.aceptar }>Aceptar</button>
						</div>
					</div>
				</div>
      </div>
		);
	}
}

export default ModalRegistrarse;