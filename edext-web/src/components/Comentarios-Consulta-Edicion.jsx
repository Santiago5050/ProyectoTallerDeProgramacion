import React, { Component } from 'react'
import Comentario from './Comentario-Consulta-Edicion.jsx';
import './../css/Comentarios-ConsultaEdicion.css';

export default class Comentarios extends Component {
    constructor(props) {
        super(props);
        
    }
    
    render() {
        return (
            <div className="row fila-comentarios">
                <h4 className="titulo">Comentarios</h4>
                <hr style={{
                    width: '100%',
                    borderColor: '#59bf81',
                    borderTop: '2px solid #59bf81',
                    marginTop: '0px !important'
                }}/>
                {this.props.comentarios.map((element, index) => {
                    return <Comentario tipo={this.props.tipo} data={element} responder={this.props.responder}/> 
                })}
            </div>
        )
    }
}
