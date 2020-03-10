import React, { Component } from 'react';
import './../css/ModalIniciarSesion.css'
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ApiRequest from '../js/api';
import ApiRequest2 from '../js/api2';



//Recibe una funcion en la prop setUser, a la cual se llama para setear el usuario en el loguin
class ModalIniciarSesion extends Component {
	constructor(props) {
		super(props);
		this.signIn = this.signIn.bind(this);
	}

	signIn()
	{
		let api = new ApiRequest2();
		let usuario = document.getElementById('inputUser').value;
		let password = document.getElementById('inputPassword').value;
		if (usuario.length > 0 && password.length > 0)
		{
			let userObject = api.login(usuario, password).then(
				(respuesta) => {
					if (respuesta.data.hasOwnProperty('ERROR')) {
						toast.error(respuesta.data.ERROR);
					} else {
						toast.info("Bienvenido " + usuario);
						this.props.setUser(respuesta.data);
					}
				}
			).catch(
				(respuestaError) => {
					this.setState({
						error: respuestaError,
						loading: false
					});
				}
			);
			document.getElementById('inputUser').value = '';
			document.getElementById('inputPassword').value = '';
		}
		else
		{
			toast.error("Error, no se completaron todos los campos");
		}
		
	}
	
	render() {
		return (
			<div className="modal" id="iniciarSesionModal" tabIndex="-1" role="dialog" >
			<div className="modal-dialog" id="mymodal" role="document">
				<div className="modal-content">
					<div className="modal-header">
						<h5 className="modal-title" id="exampleModalLabel">Iniciar Sesion</h5>
						<button type="button" className="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div className="modal-body">
						<div className="container">
							<div className="row">
								<div className="col-xs-12 offset-lg-1 col-lg-10">
									<form>
										<div className="form-group">
											<label htmlFor="formGroupExampleInput">Usuario</label>
											<input type="text" className="form-control" id="inputUser"/>
										</div>
										<div className="form-group">
											<label htmlFor="formGroupExampleInput2">Contraseña</label>
											<input type="password" className="form-control" id="inputPassword"/>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div className="modal-footer">
						<button type="button" className="btn btn-secondary" data-dismiss="modal">Cancelar</button>
						<button type="button" className="btn btn-primary" onClick={this.signIn} data-dismiss="modal">Iniciar Sesion</button>
					</div>
				</div>
			</div>
			</div>
		);
	}
}

export default ModalIniciarSesion;