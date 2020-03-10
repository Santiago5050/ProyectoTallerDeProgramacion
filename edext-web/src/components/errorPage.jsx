import React, { Component } from 'react';



class ErrorPage extends Component {
    render() {
        return (
            <div id="pagina-error">
                <i className="fas fa-exclamation-triangle"></i>
                <h1>Ooops! Algo ha salido mal.</h1>
                {this.props.mensaje===undefined ||this.props.mensaje===null?null:<p>{this.props.mensaje}</p>}
            </div>
        );
    }
}



export default ErrorPage;
