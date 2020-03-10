import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import logo from '../res/no_image.jpg';
import '../css/Tables.css'
class TableEdicion extends Component {


    constructor(props){
        super(props);
        this.state={
            "tableMobile": window.innerWidth <= 740
        }
    }

    componentDidMount(){
        window.addEventListener("resize", this.resize.bind(this))
        this.resize()
        this.props.search('tabla-de-ediciones')
    }


    componentWillUnmount(){
        this.props.search('')
    }

    resize(){
        this.setState({
            "tableMobile": window.innerWidth <= 740
        })
    }



    render() {
        return (
            <div className="table-responsive tabla-agus-border">
                  <table className="table tabla-agus " id="tabla-de-ediciones">
                            <thead className="thead-light">
                                <th>
                                    Imagen
                                </th>
                                <th>
                                    Nombre Edicion
                                </th>
                                <th>
                                    +
                                </th>
                            </thead>
                            <tbody >


                                {
                                    this.props.ediciones.map(c =>{
                                        return (
                                            <tr>
                                            <td><img src={c.src===""?logo:c.src} width="30" height="30"/></td>
                                             <td>{c.nombre}</td>
                                            {/* <td><a href="#">Ver mas..</a></td> */}
                                            <td><Link to={{
                                                    pathname: '/consulta-edicion-curso',
                                                    state: {
                                                        nombre: c.nombre
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

export default TableEdicion;