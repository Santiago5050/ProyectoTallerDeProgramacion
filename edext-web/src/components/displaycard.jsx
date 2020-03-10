import React, { Component } from 'react'
import logo from '../res/no_image.jpg';
import Pill from './pill';
import {Link} from 'react-router-dom';

class DisplayCard extends Component {
    constructor(props) { //eliminar
        super(props);
        this.state = {
            titulo: props.title
        }
    }

    


    render(){
            return(
                <div className="row">
                     
                        <div className="card display-card search-card" as={Link} to="/curso">
                        <Link className="link-invisible" to={{
                            pathname: '/'+this.props.tipo.toLowerCase(),
                            state : {
                                nombre : this.props.titulo
                            }
                        }}>
                            <div className="row card-body">
                            <img className="col-sm-3" width="50"     src={this.props.img===""?logo:this.props.img}></img>
                            <div className="col-sm-9">
                                <h4 className="card-title">{this.props.titulo}</h4>
                                <p className="card-text"> {this.props.descripcion}</p>
                                <Pill texto={this.props.tipo} type="tipo"/>
                                <div className="pill-box">
                                {this.props.categorias.map(
                                    c => {
                                        return <Pill texto={c} type="categoria"/>
                                    }
                                )}
                                </div>
                                
                            </div>
                        
                                
                            </div>
                            </Link> 
                        </div>
                    
                </div>
                
            )
    }
}

export default DisplayCard;