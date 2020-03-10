import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/simple-sidebar.css';
import '../css/NavBar.css';
import '../css/general.css';
import logo from '../res/logo2.svg';
import no_image from '../res/no_image.jpg';
import { isMobile } from "react-device-detect";





class NavBar extends Component {

    constructor(props){
        super(props)
        this.state = {
            "cerrado" : true,
            nom_tabla: props.nomTabla
        }
        this.functionPassed = this.functionPassed.bind(this);
        this.buscarTabla = this.buscarTabla.bind(this);
        this.buscar = this.buscar.bind(this);
    }

    functionPassed() {
        this.props.doSomething();
    }

    buscarTabla() 
    {
        var tablaDatos = document.getElementById(this.props.nomTabla);
        var searchText = document.getElementById('search-input').value.toLowerCase();
        for (var i = 1; i < tablaDatos.rows.length; i++) {
            var celdas = tablaDatos.rows[i].getElementsByTagName('td');
            var found = false;
            for (var j = 0; j < celdas.length && !found; j++) {
                if (celdas[j].getElementsByTagName('img').length  === 0 && celdas[j].getElementsByTagName('iput').length  === 0 && celdas[j].getElementsByTagName('a').length  === 0){
                    var textoComparar = celdas[j].innerHTML.toLowerCase();
                    if (searchText.length == 0 || (textoComparar.indexOf(searchText) > -1)) {
                        found = true;
                    }
                }
            }
            if (found) {
                tablaDatos.rows[i].style.display = '';
            } else {
                tablaDatos.rows[i].style.display = 'none';
            }
        }
    }

    buscar()
    {
        let cards = document.getElementsByClassName("search-card");
        let searchText = document.getElementById("search-input").value.toLowerCase();
        for (let i = 0; i < cards.length; i++) {
            let cardTitle = cards[i].getElementsByClassName('card-title')[0].innerHTML.toLocaleLowerCase();
            let found = false;
            if (searchText.length == 0 || (cardTitle.indexOf(searchText) > -1)) {
                cards[i].style.display = '';
            }else {
                cards[i].style.display = 'none';
            }
        }
    }

    botonSidebar = () => {
        if (!isMobile) {
            return (
                <button className={this.props.cerrado?"btn btn-outline-primary":"btn btn-outline-danger"} id="menu-toggle" onClick={this.functionPassed}><i className={this.props.cerrado? "fas fa-bars" : "fas fa-times"}></i></button>
            )
        } else {
            if (this.props.userLogued) {
                return (
                    <Link to="/" onClick={ this.props.cerrarSesion } className="btn btn-outline-primary" id="logout-mobile-button"><i class="fas fa-sign-out-alt"></i></Link>
                )
            }
        }
    }
    
    render(){

        let userOrButtons;
        if (!isMobile) {
            if(this.props.userLogued != null) {
                userOrButtons = (
                    <div id="info-user">
                        <p>{this.props.userLogued.nombre.toUpperCase()} {this.props.userLogued.apellido.toUpperCase()}</p>
                        <img src={this.props.userLogued.src === "" ? no_image : this.props.userLogued.src} alt="user image"/>
                    </div>
                );
            } else {
                userOrButtons = (
                    <div>
                        <p> <a href="#" className="login" data-toggle="modal" data-target="#iniciarSesionModal">Iniciar Sesión</a>  <a href="#" className="signup" data-toggle="modal" data-target="#registrarseModal">Registrarse</a>                       </p>
                    </div>
                );
            }
        } else {
            if(this.props.userLogued != null) {
                userOrButtons = (
                    <div id="info-user">
                        <img src={this.props.userLogued.src === "" ? no_image : this.props.userLogued.src} alt="user image"/>
                    </div>
                );
            }
        }

        return (
            <div>
                <nav className="navbar  navbar-expand-md bg-inverse navbar-dark bg-dark border-bottom barraPrincipal">
                    <div>
                        {/* Boton mostrar/ocultar menu */}
                        { this.botonSidebar() }
                        {/* Boton ocultar lupa responsive */}
                        <button className="navbar-toggler btn btn-outline-primary btn-searchBar" type="button" data-toggle="collapse" data-target="#searchBar" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i className="fas fa-search"></i></button>
                        {/* Logo */}
                        <Link to={{pathname:"/",
                        state:{
                            tipo:null,
                            nombre:null
                        }}}> <img src={logo} width="75"/> </Link>
                    </div>
                    {/* Barra de busqueda */}
                    <div className="collapse navbar-collapse contenedor-searchBar" id="searchBar">
                        <div className="input-group barra-buscar">
                            <input id="search-input" onChange={this.props.nomTabla !== '' ? this.buscarTabla : this.buscar} type="text" className="form-control" placeholder="" aria-label="" aria-describedby="basic-addon1"/>
                            <div className="input-group-append">
                                <button className="btn boton-buscar btn-outline-primary" type="button"><i className="fa fa-search"></i></button>
                            </div>
                        </div>
                    </div>
                    {/* Boton de iniciar sesion/registrarse */}
                    {userOrButtons}
                </nav>
            </div>
        )
    }

}

export default NavBar;