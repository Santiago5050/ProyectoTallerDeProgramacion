import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import '../css/Tables.css';
import ApiRequest from  '../js/api';
import '../css/EstadoInscripciones.css';



class EstadoInscripciones extends Component {

    componentDidMount() {
        this.props.search('tabla-estado-inscripciones');
    }

    componentWillUnmount() {
        this.props.search('');
    }

    loadTable = (edicionesUsuario) => {
        return edicionesUsuario.map(ed => {
            return (
                <tr key={ ed.nombre }>
                <td>{ed.nombre}</td>
                <td>{ed.fechaInicio}</td>
                <td>{ed.fechaFin}</td>
                <td>{ed.cupo}</td>
                <td>{ed.fechaPublicacion}</td>
                <td>{ed.estado}</td>
                <td><Link to={ "/consulta-edicion-curso/" + ed.nombre }>Ver más</Link></td>
                </tr>
            )
        })   
    }

    render() {

        const { handle } = this.props.match.params;
        let api = new ApiRequest();
        let user = api.getUsuario(handle);
        if (user.nickname === 404) {
            return (
                <Redirect to='/home'/>
            )
        };
        let edicionesUsuario = api.getEdicionesUsuario(handle);

        return(
            <div className="container tabla-diego EstadoInscripciones">
                <div className="row justify-content-md-left" id ="header-estado-inscripciones">
                    <h2 className="column-12 cabezal"> Estado de las inscripciones a cursos </h2>
                    <hr></hr>
                </div>
                <div className="row justify-content-md-center" id="div-tabla-estado-inscripciones">
                    <div className="table column-12 tabla">
                        <table className="table table-hover" id="tabla-estado-inscripciones">
                        <thead>
                            <tr>
                            <th>Nombre Edición</th>
                            <th>Fecha Inicio</th>
                            <th>Fecha Fin</th>
                            <th>Cupo</th>
                            <th>Fecha Publicación</th>  
                            <th>Estado</th>
                            <th>Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            { this.loadTable(edicionesUsuario) }
                        </tbody>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}

export default EstadoInscripciones;
