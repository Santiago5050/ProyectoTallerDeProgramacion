import React, { Component } from 'react';
import { isMobile } from "react-device-detect";

class SortBar extends Component {
    constructor(props){
        super(props);

        this.state= {
            "active1": false,
            "active2": false,
            "selected" : null
        }
    }

    toggleFunc1 = () => {
        this.props.boton1()
        this.setState({
            "active1" : !this.state.active1
        })
    }

    toggleFunc2 = () => {
        this.props.boton2()
        this.setState({
            "active2" : !this.state.active2
        })
    }

    handleChange = e=>{
        this.props.ordenar(e.target.value)
        this.setState({
            "selected" : e.target.value
        })
    }


    render() {
        return (
            <div className="btn-group sortbar" role="group" aria-label="Button group with nested dropdown">
                
                {isMobile?null:<label className={"btn btn-secondary " + (this.state.active1?"check-off":"check-on")}>
                    <input type="checkbox" className="check-hide" onChange={this.toggleFunc1} autoComplete="off"/> 
                    Cursos
                </label>}
                {isMobile?null:<label className={"btn btn-secondary " + (this.state.active2?"check-off":"check-on")}>
                    <input type="checkbox" className="check-hide" onChange={this.toggleFunc2} autoComplete="off"/> 
                    Programas
                </label>}
                <div class="dropdown dropdown-ordenar" id="div-dropdownmenu1">
                    <button class="btn btn-secondary dropdown-toggle"
                            type="button" id="dropdownMenu1" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        Ordenar
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        <div className="dropdown-item ">
                        <label className="drop-item">
                            <input name="ordenar" type="radio" value="A-Z" onChange={this.handleChange}/>
                                Ordenar A-Z
                        </label>
                        </div>
                        <div className="dropdown-item ">
                        <label className="drop-item">
                            <input  name="ordenar" type="radio" value="anio" onChange={this.handleChange}/>
                                Ordenar Año
                        </label>
                        </div>
                        <div className="dropdown-item ">
                        <label className="drop-item">
                            <input  name="ordenar" type="radio" value="tendencias" onChange={this.handleChange}/>
                                Tendencias
                        </label>
                        </div>
                        
                    </div>
                </div>

                {/*<div className="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button"
                            className="btn btn-secondary dropdown-toggle"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Dropdown
                    </button>
                    <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                    <a className="dropdown-item" href="#!">Dropdown link</a>
                    <a className="dropdown-item" href="#!">Dropdown link</a>
                    </div>
                </div>*/}
            </div>
        );
    }
}

export default SortBar;