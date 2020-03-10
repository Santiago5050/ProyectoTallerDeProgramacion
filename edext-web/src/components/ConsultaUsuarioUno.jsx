import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/Tables.css'
import ApiRequest2 from '../js/api2';
import '../css/ConsultaUsuarioUno.css';
import Loader from './Loader.jsx';
import ErrorPage from './errorPage.jsx'


class ConsultaUsuarioUno extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: null,
            error: null
        }
    }

    componentDidMount() {
        this.props.search('tabla-crear-usuario-uno');
        this.apiCall();
    }

    componentWillUnmount() {
        this.props.search('');

    }

    apiCall = () => {
        let api = new ApiRequest2();
        api.getUsuarios()
        .then(response => {
            if (response.data.hasOwnProperty('ERROR')) {
                this.setState({
                    error: response.data.ERROR
                })
            } else {
                this.setState({
                    data: response.data
                });
            }
        })
        .catch(errorResponse => {
            this.setState({
                error: errorResponse
            });
        });
    }

    loadTable = (usuarios) => {
        return usuarios.map(u => {
            return (
            <tr key={ u.nickname }>
            <td>{u.nickname}</td>
            <td>{u.nombre}</td>
            <td>{u.apellido}</td>
            <td>{u.mail}</td>
            { /* <td><Link to={ "/ConsultaUsuarioDos/" + u.nickname }>Ver más</Link></td> */ }
            <td><Link to={{
                pathname: "/ConsultaUsuarioDos",
                state: {
                    nombre: u.nickname
                }
            }}>Ver más</Link></td>
            </tr>
            );
        })
    }

    render() {
        if (this.state.data === null && this.state.error === null) {
            return(
                <div>
                    <Loader />
                </div>
            )
        } else if (!(this.state.error === null)){
            return(
                <div>
                    <ErrorPage mensaje={this.state.error}/>
                </div>
            )
        } else {
            return(
                <div className="container tabla-diego ConsultaUsuarioUno">
                    <div className="row justify-content-md-left" id ="header-consulta-usuario-uno">
                        <h2 className="column-12 cabezal"> Usuarios registrados en el sistema </h2>
                        <hr></hr>
                    </div>
                    <div className="row justify-content-md-center" id="tabla-consulta-usuario-uno">
                        <div className="table column-12 tabla">
                            <table className="table table-hover" id="tabla-crear-usuario-uno">
                            <thead>
                                <tr>
                                <th>Usuario</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Mail</th>
                                <th>Opciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                { this.loadTable(this.state.data) }
                            </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            )
        }
    }
}

export default ConsultaUsuarioUno;
