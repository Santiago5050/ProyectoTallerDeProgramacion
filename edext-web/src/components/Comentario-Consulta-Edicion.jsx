import React, { Component } from 'react';
import './../css/Comentario-ConsultaEdicion.css';
import no_image from './../res/no_image.jpg';
import StarRatings from 'react-star-ratings';
import {isMobile} from 'react-device-detect';

export default class Comentario extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.data.id,
            nickname: this.props.data.nickname,
            nombre: this.props.data.nombre,
            apellido: this.props.data.apellido,
            texto: this.props.data.comentario,
            fechaComentario: this.props.data.fechaComentario,
            src: this.props.data.src,
            valoracion: this.props.data.valoracion,
            comentariosRespuesta: this.props.data.comentariosRespuesta,
        }
    }
    
    render() {
        return (
            <div className="container">
                <div class="comentario card col-12">
                    <div class="row no-gutters fila-comentario">
                        {isMobile ? null : (
                            <div class="col-2 container-imagen">
                                <img src={this.state.src === "" ? no_image : this.state.src} class="card-img" alt="Imagen de usuario"/>
                            </div>
                        )}
                        <div class={isMobile ? "col-12" : "col-8"}>
                            <div class="card-body">
                                <h5 class="card-title">{this.state.nombre} {this.state.apellido}</h5>
                                <hr/>
                                <p class="card-text">{this.state.texto}</p>
                                <div className="pie-comentario">
                                    <p class="card-text"><small class="text-muted">{"@" + this.state.nickname + " - " + this.state.fechaComentario.dayOfMonth + "/" + (this.state.fechaComentario.month + 1) + "/" + this.state.fechaComentario.year + "   "}{isMobile && this.state.valoracion >= 0 ? <span><i class="fas fa-star"></i> {this.state.valoracion}</span> : null}</small></p>
                                    {this.props.tipo === "estudiante" && !isMobile ? <a href="# " style={{fontSize: '1.5em'}} onClick={(e)=>{e.preventDefault(); this.props.responder(this.state.id)}}><i class="fas fa-reply"></i></a> : null}
                                </div>
                            </div>
                        </div>
                        {isMobile ? null : (
                            <div className="col-2 container-stars container">
                                {this.state.valoracion >= 0 ? (
                                    <div className="container-valoracion">
                                        <h6>Valoracion</h6>
                                        <StarRatings rating={this.state.valoracion} starDimension="20px"/>
                                    </div>
                                ) : (
                                    <h6>Sin Valorar</h6>
                                )}
                            </div>
                        )}
                    </div>
                </div>
                <div className="row ml-1">
                    {this.state.comentariosRespuesta.map((element, index) => {
                        return <Comentario  tipo={this.props.tipo} data={element} responder={this.props.responder} /> 
                    })}
                </div>
            </div>
        )
    }
}
