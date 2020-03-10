import React, { Component } from 'react';
import ApiRequest2 from '../js/api2';
import 'react-toastify/dist/ReactToastify.css';
import {ToastContainer, toast} from 'react-toastify';
import '../css/ModalVideoEdicion.css';

class ModalVideoEdicion extends Component {
    constructor(props) {
        super(props);
        this.state = {
            "URL": ""
        }
    }

    handleChange = (element) => {
        this.setState({
            "URL": element.target.value
        });
    }

    inscribirme = () => {
        let api = new ApiRequest2();
        let fecha = new Date();
        fecha = fecha.getDate() + "/" + fecha.getMonth() + "/" + fecha.getFullYear();
        api.inscripcionEstudianteEdicion(this.props.datosEdicion.curso, this.props.datosEdicion.nombre, fecha, this.state.URL).then(
            (respuesta) => {
                this.setState({
                    loadingEdicion: false
                });
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    // toast.info(respuesta.data.OK);
                    window.location.reload();
                    toast.info(respuesta.data.OK);
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError);
            }
        );
    }

    limpiarFormulario = () => {
        this.setState({
            "URL": ""
        });
    }

    render() {
        return (
            <div className="modal modalVideo" id="videoModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div className="modal-dialog" role="document">
					<div className="modal-content">
						<div className="modal-header">
							<h5 className="modal-title" id="exampleModalLabel">Video</h5>
							<button type="button" className="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div className="modal-body">
                            <p>Si desea agregar un video a su inscripción, ingrese la URL. De lo contrario, deje el campo vacío.</p>
							<form>
                            <label htmlFor="formGroupExampleInput">URL</label>
                            <input type="text" className="form-control" id="videoURL" name="nombre" onChange={ this.handleChange } value={ this.state.URL }/>
							</form>
						</div>
						<div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={ this.limpiarFormulario }>Cancelar</button>
							<button type="button" className="btn btn-primary" onClick={ this.inscribirme }>Aceptar</button>
                        </div>
					</div>
				</div>
            </div>
        );
    }
}

export default ModalVideoEdicion;