import React, { Component } from 'react';
import { toast } from 'react-toastify';
import './../css/CrearProgramaFormacion.css';
import './inputfoto.jsx';
import InputFoto from './inputfoto.jsx';
import no_image from './../res/no_image.jpg';
import ApiRequest2 from '../js/api2';

export default class CrearProgramaFormacion extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: null,
            "existePrograma": false
        }
    }

    guardar=(file)=>{
        this.setState({
            "file" : file
        })
    }

    handleAceptar = () => {
        let api = new ApiRequest2();
        let nom_programa = document.getElementById('input-nombre-crear-programa-formacion').value
        let fechaInicio = document.getElementById('input-fecha-ini-crear-programa-formacion').value.split('-');
        let fechaFin = document.getElementById('input-fecha-fin-crear-programa-formacion').value.split('-');
        if (this.fechaMenorQue(fechaInicio[2], fechaInicio[1], fechaInicio[0], fechaFin[2], fechaFin[1], fechaFin[0])) {
            fechaInicio = fechaInicio[2] + '/' + fechaInicio[1] + '/' + fechaInicio[0];
            fechaFin = fechaFin[2] + '/' + fechaFin[1] + '/' + fechaFin[0];
            let descripcion = document.getElementById('input-descripcion-crear-programa-formacion').value
            let source = ''
            let formData = new FormData();
            if(this.state.file!==null){
                let ip = window.Configs.ip;
                let port= window.Configs.port;
                let extension = this.state.file.name.split(".")
                formData.append("file", this.state.file);
                formData.append("nombre", nom_programa + "." + extension[1]);
                source = "http://" + ip + ":" + port + "/edext-server-web/images/" + nom_programa + "." + extension[1]
            }
            api.altaPrograma(nom_programa, descripcion, fechaInicio, fechaFin, source).then(
                (respuesta) => {
                    if (respuesta.data.hasOwnProperty('ERROR')) {
                        toast.error(respuesta.data.ERROR);
                    } else {
                        if(this.state.file!==null){
                            api.enviarImagen(formData);
                        }
                        this.setState({
                            file: null
                        });
                        toast.info(respuesta.data.OK);
                    }
                    this.limpiarCampos();
                }
            ).catch(
                (respuestaError) => {
                    console.log(respuestaError.data);
                    toast.error("Ha ocurrido un error inesperado");
                }
            );
        } else {
            toast.error("La fecha de inicio es igual o posterior a la fecha de fin");
        }
        // console.log(api.altaPrograma('prog', 'des', '1/1/2000', '31/12/2000', 'src'));
    }

    limpiarCampos = () => {
        document.getElementById('input-nombre-crear-programa-formacion').value = "";
        document.getElementById('input-fecha-ini-crear-programa-formacion').value = "";
        document.getElementById('input-fecha-fin-crear-programa-formacion').value = "";
        document.getElementById('input-descripcion-crear-programa-formacion').value = "";
    }

    fechaMenorQue = (dia1, mes1, anio1, dia2, mes2, anio2) => {
        if (anio1 > anio2) {
            return false;
        } else if (anio1 == anio2) {
            if (mes1 > mes2) {
                return false;
            } else if (mes1 == mes2) {    
                if (dia1 >= dia2) {
                    return false;
                }
            }
        }
        return true;
    }

    existePrograma = () => {
		let api = new ApiRequest2();
		api.validarDatosPrograma(document.getElementById('input-nombre-crear-programa-formacion').value)
		.then(response => {
			if (response.data.hasOwnProperty('OK')) {
				if (response.data.OK === 'true') {
					this.setState({
						"existePrograma": true
					});
				} else {
					this.setState({
						"existePrograma": false
					});
				}
			}
		})
		.catch(errorResponse => {
			console.log(errorResponse)
		})
	}

	alertaProgramaYaExiste = () => {
		if (this.state.existePrograma) {
			return(
				<div>
					<p className="programa-ya-existe-3498">¡El programa ya existe!</p>
				</div>
			)
		}
    }
    
    cancelar = () => {
        this.setState({
            file: null,
            "existePrograma": false
        });
        window.location.reload();
    }

    render()
    {
        return (
            <div className="modal crear-programa-formacion" id="modal-crear-programa" tabIndex="-1" role="dialog" aria-labelledby="modal-crear-programa-label" aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="modal-crear-programa-label">Crear Programa de Formacion</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close" onClick={this.cancelar}>
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="foto-prog">
                            {document.getElementById("modal-crear-programa")!==null && 
                            document.getElementById("modal-crear-programa").classList.contains("show")?
                            <InputFoto img={ no_image } subir={ this.guardar } nombre="juan"/>:null}
                            </div>
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="input-nombre-crear-programa-formacion" className="col-sm-3 col-form-label">Nombre</label>
                                    <div className="col-sm-9">
                                        <input type="email" className="form-control" id="input-nombre-crear-programa-formacion" placeholder="Nombre" onBlur={ this.existePrograma }/>
                                    </div>
                                </div>
                                { this.alertaProgramaYaExiste() }
                                <div className="form-group row">
                                    <label htmlFor="input-fecha-ini-crear-programa-formacion" className="col-sm-3 col-form-label">Fecha Inicio</label>
                                    <div className="col-sm-9">
                                        <input type="date" className="form-control" id="input-fecha-ini-crear-programa-formacion" placeholder="Fecha inicio"/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="input-fecha-fin-crear-programa-formacion" className="col-sm-3 col-form-label">Fecha Fin</label>
                                    <div className="col-sm-9">
                                        <input type="date" className="form-control" id="input-fecha-fin-crear-programa-formacion" placeholder="Fecha fin"/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="input-descripcion-crear-programa-formacion" className="col-sm-3 col-form-label">Descripcion</label>
                                    <div className="col-sm-9">
                                        <textarea className="form-control" id="input-descripcion-crear-programa-formacion" rows="3"></textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal" onClick={ this.cancelar }>Cancelar</button>
                            <button type="button" onClick={this.handleAceptar} className="btn btn-aceptar btn-primary">Aceptar</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}