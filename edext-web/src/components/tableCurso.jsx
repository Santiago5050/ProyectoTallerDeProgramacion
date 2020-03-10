import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import logo from '../res/no_image.jpg';
import '../css/Tables.css';
class TableCurso extends Component {


    constructor(props){
        super(props);
        this.state={
            "tableMobile": window.innerWidth <= 760
        }
    }

    componentDidMount(){
        window.addEventListener("resize", this.resize.bind(this))
        this.resize()
        this.props.search('tabla-despliega-cursos')
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
                  <table className="table tabla-agus" id="tabla-despliega-cursos">
                            <thead className="thead-light">
                                <th>
                                    Imagen
                                </th>
                                <th>
                                    Nombre Curso
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
                                    this.props.cursos.map(c =>{
                                        return (
                                            <tr>
                                            <td><img src={c.src==""?logo:c.src} width="30" height="30"/></td>
                                             <th scope="row">{c.nombre}</th>
                                            {this.state.tableMobile?null:<td>{c.descripcion}</td>}
                                            <td><Link to={{
                                                pathname: "/curso",
                                                state : {
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

export default TableCurso;