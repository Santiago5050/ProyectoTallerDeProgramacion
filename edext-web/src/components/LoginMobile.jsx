import React, { Component } from 'react';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ApiRequest2 from '../js/api2';
import '../css/LoginMobile.css';
import Loader from './Loader.jsx';

class LoginMobile extends Component {
	constructor(props) {
        super(props);
        let remember = localStorage.getItem('remember');
        if (remember === 'true') {
            this.state = {
                "usuario": localStorage.getItem('lastLoguedUser'),
                "password": localStorage.getItem('lastLoguedPassword'),
                "error": null,
                "recordarme": false
            }
        } else {
            this.state = {
                "usuario": null,
                "password": null,
                "error": null,
                "recordarme": false
            }
        }
    }

	signIn = () => {
        localStorage.setItem('lastLoguedUser', this.state.usuario);
        localStorage.setItem('lastLoguedPassword', this.state.password);
        if (this.state.recordarme) {
            localStorage.setItem('remember', 'true');
        } else {
            localStorage.setItem('remember', 'false');
        }
		let api = new ApiRequest2();
        let userObject = api.loginMovil(this.state.usuario, this.state.password).then(
            (respuesta) => {
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    this.props.setUser(respuesta.data);
                }
            }
        ).catch(
            (respuestaError) => {
                this.setState({
                    error: respuestaError,
                });
            }
        );		
    }

    limpiarFormulario = () => {
        document.getElementById('inputUser').value = '';
        document.getElementById('inputPassword').value = '';
    }
    
    handleChange = (e) => {
		this.setState({[e.target.name]: e.target.value});
    }
    
    habilitarLogin = () => {
		return !(this.state.usuario && this.state.password)
    }
    
    handleCheckboxChange = () => {
        this.setState({
            "recordarme": !this.state.recordarme
        });
    }
	
	render() {
        if (this.state.error == null) {
            return (
                <div className="container LoginMobile">
                    <div className="row">
                        <div className="col-xs-12 offset-lg-1 col-lg-10">
                            <form>
                                <div className="form-group">
                                    <label htmlFor="formGroupExampleInput">Usuario</label>
                                    <input type="text" className="form-control" id="inputUser" value={ this.state.usuario } name="usuario" onChange = { this.handleChange } />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="formGroupExampleInput2">Contraseña</label>
                                    <input type="password" className="form-control" id="inputPassword" value={ this.state.password } name="password" onChange = { this.handleChange } />
                                </div>
                                <div className="custom-control custom-checkbox check-aceptados-contenedor">
                                    <input type="checkbox" onChange={ this.handleCheckboxChange } className="custom-control-input" id="check-aceptados"/>
                                    <label className="custom-control-label" htmlFor="check-aceptados">Recordarme</label>
                                </div>
                                <div className="login-mobile-button">
                                    <button type="button" className="btn btn-primary" disabled={ this.habilitarLogin() } onClick={ this.signIn } data-dismiss="modal">Iniciar Sesion</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            );
        } else {
            return(
                <div>
                    <Loader />
                </div>
            )
        }
	}
}

export default LoginMobile;