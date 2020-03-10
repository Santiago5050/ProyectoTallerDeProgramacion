import React, { Component } from 'react';
import logo from '../res/no_image.jpg';
import '../css/Tables.css'

class TableSelPrevias extends Component {


    constructor(props){
        super(props);
        this.state={
            "tableMobile": window.innerWidth <= 760
        }
    }

    componentDidMount(){
        window.addEventListener("resize", this.resize.bind(this))
        this.resize()
        this.props.search('tabla-elegir-previas')
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
                  <table className="table tabla-agus" id="tabla-elegir-previas">
                            <thead className="thead-light">
                                <th>
                                    Imagen
                                </th>
                                <th>
                                    Nombre Curso
                                </th>
                                <th>
                                    +
                                </th>
                            </thead>
                            <tbody >


                                {
                                    this.props.cursos.map(c =>{
                                        return (
                                            <tr>
                                            <td><img src={c.src===""?logo:c.src} width="30" height="30"/></td>
                                             <td>{c.nombre}</td>
                                            {this.state.tableMobile?null:<td>{c.descripcion}</td>}
                                            <td><input type="checkbox" onChange={()=>this.props.handleChange(c.nombre)} /></td>
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

export default TableSelPrevias;