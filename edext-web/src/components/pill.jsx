import React, { Component } from 'react';

class Pill extends Component {

    tipoEstilo = ()=>{
        return this.props.type==="tipo"?"":"pildora";
    }
    render() {
        return (
            <div>
                <span className={"badge badge-pill badge-primary "+ this.tipoEstilo()}
                >{this.props.type==="tipo"?<i className="fas fa-info-circle"></i>
                :<i className="far fa-list-alt"></i>} {this.props.texto}</span>      
            </div>
        );
    }
}

export default Pill;