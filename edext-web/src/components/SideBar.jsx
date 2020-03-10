import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import '../css/general.css';
import '../css/simple-sidebar.css';
import ApiRequest from '../js/api';
import ApiRequest2 from '../js/api2';



class SideBar extends Component {


  constructor(props) {
    super(props);
    this.node= React.createRef();
    this.state = {
      institutos: null,
      errorInstitutos: null,
      categorias: null,
      errorCategorias: null
    }
  }

  componentDidMount() {
    this.apiCallCategorias();
    this.apiCallInstitutos();
  }

  apiCallCategorias = () => {
    let api = new ApiRequest2();
    api.getCategorias()
    .then(response => {
      this.setState({
        categorias: response.data
      })
    })
    .catch(errorResponse => {
      
    });
  }

  apiCallInstitutos = () => {
    let api = new ApiRequest2();
    api.getInstitutos()
    .then(response => {
      this.setState({
        institutos: response.data
      })
    })
    .catch(errorResponse => {
    
    });
  }
  

  loadPanelDeControl = () => {
    if (this.props.state.user === null) {
      return(
        <Link to="/ConsultaUsuarioUno" className="list-group-item " onBlur={ this.props.toggle }>Consultar usuario</Link>
      )
    } else if (this.props.state.user.tipo === "docente") {
      return(
        <div className="card-body">
          <Link to={{
            pathname: "/ConsultaUsuarioDos",
            state: {
                nombre: this.props.state.user.nickname
            }
          }} className="list-group-item " onBlur={ this.props.toggle }>Mi perfil</Link>
          <Link className="list-group-item " onBlur={ this.props.toggle } data-toggle="modal" data-target="#modal-crear-programa">Crear Programa Formacion</Link>
          <Link to="/ConsultaUsuarioUno" className="list-group-item " onBlur={ this.props.toggle }>Consultar usuario</Link>
          <Link to="/altacurso" className="list-group-item " onBlur={ this.props.toggle }>Alta curso</Link>
          <Link to="/" onClick={this.props.cerrarSesion} className="list-group-item " onBlur={ this.props.toggle }>Salir</Link>
        </div>
      )
    } else if (this.props.state.user.tipo === "estudiante") {
      return(
        <div className="card-body">
          <Link to={{
            pathname: "/ConsultaUsuarioDos",
            state: {
                nombre: this.props.state.user.nickname
            }
          }} className="list-group-item " onBlur={ this.props.toggle }>Mi perfil</Link>
          <Link to="/ConsultaUsuarioUno" className="list-group-item " onBlur={ this.props.toggle }>Consultar usuario</Link>
          <Link to="/" onClick={this.props.cerrarSesion} className="list-group-item " onBlur={ this.props.toggle }>Salir</Link>
        </div>
      )
    }
  }

  loadInstitutos = (institutos) => {
    if (!(institutos === null)) {
      return institutos.map(instituto => {
        return(
            <Link to={{pathname:"/", 
            state:{
              tipo: "instituto",
              nombre:instituto.nombre
            }}} className="list-group-item " key={ instituto.nombre }>{ instituto.nombre }</Link>
        )
      })
    } else {
      return(
        <p>ERROR!</p>
      )
    }
  }

  loadCategorias = (categorias) => {
    if (!(categorias === null)) {
      return categorias.map(categoria => {
        return(
          <Link to={{pathname:"/",
          state:{
            tipo:"categoria",
            nombre: categoria.nombre
          }}} className="list-group-item " key={ categoria.nombre }>{ categoria.nombre }</Link>
        )
      })
    } else {
      return(
        <p>ERROR!</p>
      )
    }
  }

  toggleBlur = (e) =>{

    this.props.toggle()

  }



  render() {
    return (
      <div ref={node => this.node=node}>
        <div className="bg-light border-right" id="sidebar-wrapper">
          <div className="sidebar-heading">edExt </div>
          <div id="accordion" role="tablist" className="list-group list-group-flush">

            <div className="card">
              <div className="card-header" role="tab" id="headingOne">
                <h5 className="mb-0">
                  <a data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    Panel de control
                  </a>
                </h5>
              </div>
              <div id="collapseOne" className="collapse show" role="tabpanel" aria-labelledby="headingOne">
                { this.loadPanelDeControl() }
              </div>
            </div>

            <div className="card">
              <div className="card-header" role="tab" id="headingTwo">
                <h5 className="mb-0">
                  <a className="collapsed" data-toggle="collapse" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    Institutos
                  </a>
                </h5>
              </div>
              <div id="collapseTwo" className="collapse" role="tabpanel" aria-labelledby="headingTwo">
                <div className="card-body">
                  { this.loadInstitutos(this.state.institutos) }
                </div>
              </div>
            </div>

            <div className="card" id="catcard">
              <div className="card-header" role="tab" id="headingThree">
                <h5 className="mb-0">
                  <a className="collapsed" data-toggle="collapse" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                    Categorías
                  </a>
                </h5>
              </div>
              <div id="collapseThree" className="collapse" role="tabpanel" aria-labelledby="headingThree">
                <div className="card-body">
                 { this.loadCategorias(this.state.categorias) }
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    );
  }
}

export default SideBar;
