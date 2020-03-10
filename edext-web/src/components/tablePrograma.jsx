import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import '../css/Tables.css'

class TablePrograma extends Component {


    constructor(props){
        super(props);
        this.state={
            "tableMobile": window.innerWidth <= 760
        }
    }

    componentDidMount(){
        window.addEventListener("resize", this.resize.bind(this))
        this.resize()
        this.props.search('tabla-programas-form')
    }

    componentWillUnmount(){
        this.props.search('')
    }

    resize(){
        this.setState({
            "tableMobile": window.innerWidth <= 760
        })
    }



    render() {
        return (
            <div className="table-responsive tabla-agus-border">
                  <table className="table tabla-agus" id="tabla-programas-form">
                            <thead className="thead-light">
                                <th>
                                    Nombre Programa
                                </th>
                                {
                                    this.state.tableMobile?null
                                    :<th>
                                    Descripcion
                                    </th>
                                }
                                <th>
                                    +
                                </th>
                            </thead>
                            <tbody >


                                {
                                    this.props.programas.map(c =>{
                                        return (
                                            <tr>
                                             <td>{c.nombre}</td>
                                            {this.state.tableMobile?null:<td>{c.descripcion}</td>}
                                            <td><Link to={{
                                                pathname: "/programa",
                                                state:{
                                                    nombre : c.nombre
                                                }
                                            }}>Ver mas..</Link></td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
            </div>
        );
    }
}

export default TablePrograma;