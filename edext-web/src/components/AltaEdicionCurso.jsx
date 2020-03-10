import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';
import './../css/AltaEdicionCurso.css';
import './inputfoto.jsx';
import InputFoto from './inputfoto.jsx';
import ApiRequest2 from '../js/api2';
import no_image from './../res/no_image.jpg';
import Loader from './Loader.jsx';

export default class AltaEdicionCurso extends Component {
    constructor(props) {
        super(props);
        this.state = {
            nom_instituto: "",
            nom_curso: "",
            cupoChecked: false,
            docentes: null,
            loading: true,
            file: null,
            "existeEdicion": false
        }
        this.checkChange = this.checkChange.bind(this);
        this.handleAceptar = this.handleAceptar.bind(this);
        this.getDocentesAsignados = this.getDocentesAsignados.bind(this);
    }

    handleAceptar()
    {
        let api = new ApiRequest2();
        let nombre_edicion = document.getElementById('input-nombre-alta-edicion').value;
        let fechaInicio = document.getElementById('input-fecha-ini-alta-edicion').value.split("-");
        // day = fechaInicio[2];
        // month = fechaInicio[1];
        // year = fechaInicio[0];
        let fechaFin = document.getElementById('input-fecha-fin-alta-edicion').value.split("-");
        if (this.fechaMenorQue(fechaInicio[2], fechaInicio[1], fechaInicio[0], fechaFin[2], fechaFin[1], fechaFin[0])) {
            let cupo = 0;
            if (this.state.cupoChecked) {
                cupo = document.getElementById('nro-cupo-alta-edicion').value;
            }
            let docentes = this.getDocentesAsignados();
            this.setState({
                loading: true
            });
            let source = ''
            let formData = new FormData();
            if(this.state.file!==null){
                let ip = window.Configs.ip;
                let port= window.Configs.port;
                let extension = this.state.file.name.split(".")
                formData.append("file", this.state.file);
                formData.append("nombre", nombre_edicion + "." + extension[1]);
                source = "http://" + ip + ":" + port + "/edext-server-web/images/" + nombre_edicion + "." + extension[1]
            }
            // alert("nombre: " + nombre_edicion + "\nfechaIni: " + fechaInicio + "\nfechaFin: " + fechaFin + "\ncupo: " + cupo);
            api.altaEdicion(nombre_edicion, fechaInicio[2], fechaInicio[1], fechaInicio[0], fechaFin[2], fechaFin[1], fechaFin[0], cupo, this.state.nom_curso, docentes, this.state.nom_instituto, source).then(
                (respuesta) => {
                    this.setState({
                        loading: false,
                    });
                    if (respuesta.data.hasOwnProperty('ERROR')) {
                        toast.error(respuesta.data.ERROR);
                    } else {
                        if(this.state.file!==null){
                            api.enviarImagen(formData);
                        }
                        this.setState({
                            file: null,
                            cupoChecked: false
                        });
                        toast.info(respuesta.data.OK);
                    }
                }
            ).catch(
                (respuestaError) => {
                    this.setState({
                        loading: false
                    });
                    console.log(respuestaError.data);
                    toast.error("Ha ocurrido un error inesperado");
                }
            );
        } else {
            toast.error("La fecha de inicio es igual o posterior a la fecha de fin");
        }
    }

    guardar=(file)=>{
        this.setState({
            "file" : file
        })
    }

    getDocentesAsignados() {
        let docentes = [];
        let tablaDatos = document.getElementById('tabla-docentes-alta-edicion');
        for (let i = 1; i < tablaDatos.rows.length; i++) {
            let celdas = tablaDatos.rows[i].getElementsByTagName('td');
            let estado = celdas[3].getElementsByTagName('input');
            if (estado[0].checked)
            {
                docentes.push(celdas[0].innerHTML);
            }
        }
        return docentes;
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

    checkChange()
    {
        this.setState({
            cupoChecked: !this.state.cupoChecked
        });
    }

    componentDidMount() {
        const instituto = this.props.location.state.instituto;
        const curso = this.props.location.state.curso;
        // const {instituto, curso} = this.props.match.params;
        this.setState({
            nom_instituto: instituto,
            nom_curso: curso
        })
        let api = new ApiRequest2();
        api.getDocentesInstituto(instituto).then(
            (respuesta) => {
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    this.setState({
                        loading: false
                    });
                    toast.error(respuesta.data.ERROR);
                } else {
                    this.setState({
                        docentes: respuesta.data,
                        loading: false
                    });
                }
            }
        ).catch(
            (respuestaError) => {
                this.setState({
                    loading: false
                });
                console.log(respuestaError);
            }
        );
    }

    existeEdicion = () => {
		let api = new ApiRequest2();
		api.validarDatosEdicion(document.getElementById('input-nombre-alta-edicion').value)
		.then(response => {
			if (response.data.hasOwnProperty('OK')) {
				if (response.data.OK === 'true') {
					this.setState({
						"existeEdicion": true
					});
				} else {
					this.setState({
						"existeEdicion": false
					});
				}
			}
		})
		.catch(errorResponse => {
			console.log(errorResponse)
		})
    }
    
    alertaEdicionYaExiste = () => {
		if (this.state.existeEdicion) {
			return(
				<div>
					<p className="edicion-ya-existe-3498">¡La edicion ya existe!</p>
				</div>
			)
		}
    }

    render()
    {
        let renderizar;
        if (!this.state.loading && this.state.docentes != null) {
            renderizar = (
                <div className="container alta-edicion-curso">
                    <div className="row fila-btn-aceptar">
                        <div className="col-xs-12 col-lg-10 col-datos">
                            <p>Agregar una edicion</p>
                            <span>  > Instituto: {this.state.nom_instituto}</span>
                            <span>  > Curso: {this.state.nom_curso}</span>
                        </div>
                        <div className="offset-xs-3 col-xs-6 col-lg-2 contenedor-btn-aceptar">
                            <button onClick={this.handleAceptar} type="button" class="btn btn-primary btn-aceptar">Agregar</button>
                        </div>
                    </div>
                    <div className="row fila-datos">
                        <div className="col-xs-12 col-lg-4 contenedor-foto">
                            <InputFoto img={no_image} subir={ this.guardar }/>
                        </div>
                        <div className="col-xs-12 col-lg-8 columna-datos">
                            <form>
                                <div className="form-group row">
                                    <label htmlFor="input-nombre-alta-edicion" className="col-sm-2 col-form-label">Nombre</label>
                                    <div className="col-sm-10">
                                        <input type="email" className="form-control" id="input-nombre-alta-edicion" placeholder="Nombre" onBlur={ this.existeEdicion }/>
                                    </div>
                                </div>
                                { this.alertaEdicionYaExiste() }
                                <div className="form-group row">
                                    <label htmlFor="input-fecha-ini-alta-edicion" className="col-sm-2 col-form-label">Fecha Inicio</label>
                                    <div className="col-sm-10">
                                        <input type="date" className="form-control" id="input-fecha-ini-alta-edicion" placeholder="Fecha Inicio"/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="input-fecha-fin-alta-edicion" className="col-sm-2 col-form-label">Fecha Fin</label>
                                    <div className="col-sm-10">
                                        <input type="date" className="form-control" id="input-fecha-fin-alta-edicion" placeholder="Fecha Fin"/>
                                    </div>
                                </div>
                                <div className="form-group row">
                                    <label htmlFor="input-cupo-alta-edicion" className="col-sm-2 col-form-label">Cupo</label>
                                    <div className="col-sm-1 col-switch">
                                        <div class="custom-control custom-switch">
                                            <input onChange={this.checkChange} type="checkbox" class="custom-control-input" id="check-cupo-alta-edicion"/>
                                            <label class="custom-control-label" htmlFor="check-cupo-alta-edicion">Si</label>
                                        </div>
                                    </div>
                                    <div className="col-sm-2">
                                        {this.state.cupoChecked ? (<input id="nro-cupo-alta-edicion" type="number" name="quantity" min="1" ></input>) : null}
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="row fila-docentes">
                        <div className="col-xs-12  col-lg-12 container-table-docentes">
                            <table class="table table-hover" id="tabla-docentes-alta-edicion">
                                <thead className="">
                                    <tr>
                                        <th>Docente</th>
                                        <th>Nombre</th>
                                        <th>Apellido</th>
                                        <th>Opciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.docentes.map((element, index) => {
                                        return   (
                                                <tr key={index}>
                                                    <td className="col-xs-3">{element.nickname}</td>
                                                    <td className="col-xs-3">{element.nombre}</td>
                                                    <td className="col-xs-3">{element.apellido}</td>
                                                    <td className="col-xs-3 contenedor-opciones">
                                                        <div className="custom-control custom-checkbox">
                                                            <input type="checkbox" class="custom-control-input" id={"alta-edicion-chbox-"+index}/>
                                                            <label className="custom-control-label" htmlFor={"alta-edicion-chbox-"+index}>Agregar</label>
                                                        </div>
                                                        <Link to={{
                                                            pathname: '/ConsultaUsuarioDos',
                                                            state: {
                                                                nombre: element.nickname
                                                            }
                                                        }}><i class="fas fa-info-circle"></i></Link>
                                                    </td>
                                                </tr>
                                            )
                                        })
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            );
        } else {
            renderizar = (<Loader />);
        }

        return (
            <div>
                {renderizar}
            </div>
        );
    }
}