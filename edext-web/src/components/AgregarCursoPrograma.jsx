import React, { Component } from 'react';
import './../css/AgregarCursoPrograma.css';
import { toast } from 'react-toastify';
import ApiRequest2 from '../js/api2';
import Loader from './Loader.jsx';

//Se debe de proveer el nombre del programa de formacion en la prop nomPrograma
export default class AgregarCursoPrograma extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cursos: null,
            loading: true,
            nom_programa: ''
        }
        this.agregarCursos = this.agregarCursos.bind(this);
    }
    
    componentDidMount()
    {
        const programa = this.props.location.state.nombre;
        this.props.swithBar('tabla-agregar-curso-programa');
        let api = new ApiRequest2();
        api.getCursos().then(
            (respuesta) => {
                if (respuesta.data.hasOwnProperty('ERROR')) {
                    toast.error(respuesta.data.ERROR);
                } else {
                    this.setState({
                        loading: false,
                        cursos: respuesta.data,
                        nom_programa: programa
                    });
                }
            }
        ).catch(
            (respuestaError) => {
                console.log(respuestaError.data);
                toast.error("Ha ocurrido un error inesperado");
            }
        );
    }

    componentWillUnmount()
    {
        this.props.swithBar('');
    }

    agregarCursos()
    {
        //En esta funcion se agregaran los cursos!
        let tablaDatos = document.getElementById('tabla-agregar-curso-programa');
        let arreglo_cursos = [];
        for (var i = 1; i < tablaDatos.rows.length; i++) {
            var celdas = tablaDatos.rows[i].getElementsByTagName('td');
            if(celdas[2].getElementsByTagName('input')[0].checked)
            {
                arreglo_cursos.push(celdas[0].innerHTML);
            }
        }
        // console.log(arreglo_cursos); //Descomentar para ver los cursos seleccionados
        if(arreglo_cursos.length > 0) {
            let api = new ApiRequest2();
            this.setState({
                loading: true
            });
            api.agregarCursosAPrograma(this.state.nom_programa, arreglo_cursos).then(
                (respuesta) => {
                    this.setState({
                        loading: false,
                    });
                    if (respuesta.data.hasOwnProperty('ERROR')) {
                        toast.error(respuesta.data.ERROR);
                    } else {
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
            toast.error("ERROR: Debe de seleccionar algun curso");
        }
    }

    render()
    {
        let renderizar;
        if (this.state.cursos != null && !this.state.loading) {
            renderizar = (
                <div className="container agregar-curso-programa">
                    <div className="row fila-btn-aceptar">
                        <div className="col-xs-12 col-lg-10 col-datos">
                            <p>Agregar curso a programa de formacion</p>
                            <span>  > Programa de Formacion: {this.state.nom_programa}</span>
                        </div>
                        <div className="offset-xs-3 col-xs-6 col-lg-2 contenedor-btn-aceptar">
                            <button type="button" class="btn btn-secondary" onClick={this.agregarCursos}>Aceptar</button>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-xs-12 col-lg-12 contenedor-tabla">
                            <table class="table table-hover" id="tabla-agregar-curso-programa">
                                <thead className="thead-dark">
                                    <tr>
                                        <th>Nombre Curso</th>
                                        <th>Descripcion</th>
                                        <th>Agregar Curso</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.cursos.map((element, index) => {
                                        return   (
                                                <tr key={index}>
                                                    <td className="col-xs-5">{element.nombre}</td>
                                                    <td className="col-xs-5">{element.descripcion}</td>
                                                    <td className="col-xs-2">
                                                        <div className="custom-control custom-checkbox">
                                                            <input type="checkbox" class="custom-control-input" id={"agregar-curso-chbox-"+index}/>
                                                            <label className="custom-control-label" htmlFor={"agregar-curso-chbox-"+index}></label>
                                                        </div>
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