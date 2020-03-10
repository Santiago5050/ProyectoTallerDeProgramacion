import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Redirect } from 'react-router-dom';
import SideBar from './components/SideBar.jsx'
import NavBar from './components/NavBar.jsx'
import './css/simple-sidebar.css';
import './css/general.css';
import ModalIniciarSesion from './components/ModalIniciarSesion.jsx';
import ModalRegistrarse from './components/ModalRegistrarse.jsx';
import EstadoInscripciones from './components/EstadoInscripciones';
import ConsultaUsuarioUno from './components/ConsultaUsuarioUno.jsx';
import ConsultaUsuarioDos from './components/ConsultaUsuarioDos.jsx';
import Inicio from './components/inicio';
import InfoCurso from './components/infocurso.jsx';
import InfoPrograma from './components/infoPrograma.jsx';
import AltaCurso from './components/altacurso.jsx';
import AgregarCursoPrograma from './components/AgregarCursoPrograma.jsx';
import ConsultaEdicionCurso from './components/ConsultaEdicionCurso.jsx';
//Notificaciones
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import AltaEdicionCurso from './components/AltaEdicionCurso.jsx';
import CrearProgramaFormacion from './components/CrearProgramaFormacion.jsx';
import ApiRequest from './js/api.js';
import ApiRequest2 from './js/api2.js';

class App extends Component {

	constructor(props) {
		super(props);
		this.state = {
			"showHideNav" : "hidden",
			"cerrado" :true,
			objetiveSearch: '',
			user: null,
			"lastLogued": null,
			"rememberCheckbox": false,
		}
		this.setUserLogued = this.setUserLogued.bind(this);
		this.cerrarSesion = this.cerrarSesion.bind(this);
		this.swithSearch = this.swithSearch.bind(this);
		this.navbar = React.createRef();
	}

	getInitialState(){
		return {"showHideNav" : "hidden"}
    }
    
	toggleSideBar = () => {
		var css = (this.state.showHideNav === "hidden") ? "show" : "hidden";
		this.setState({
			"showHideNav" : css,
			"cerrado" : !this.state.cerrado
		});
	}

	//Setea en el campo user del estado el usuario que se acaba de loguear
	setUserLogued(usuario) {
		this.setState({
			user: usuario
		});
		console.log(usuario)
		localStorage.setItem('user', JSON.stringify(usuario))
	}

	swithSearch(nom_tabla) {
		this.setState({
			objetiveSearch: nom_tabla
		});
	}

	cerrarSesion() {
		toast.info("Sesion Cerrada");
		this.setState({
			user: null
		});
		let api = new ApiRequest2()
		api.cerrarSesion()
		localStorage.removeItem('user');
	}

	componentDidMount(){
		/* let us = localStorage.getItem('user')
		if(us!==null && us!==undefined){
			this.setState({
				user: JSON.parse(us)
			})
		} */
		let api = new ApiRequest2();
		api.updateLogin().then(
			response=>{
				if(response.data.hasOwnProperty("ERROR"))
					this.setState({
						user: null
					})
				else
					this.setState({
						user : response.data
					})
			}
		)
		
	}

	componentDidUpdate(){
		let api = new ApiRequest2();
		if(this.state.user===null && localStorage.getItem('user')!==undefined && localStorage.getItem('user')!==null)
			api.updateLogin().then(
				response=>{
					if(response.data.hasOwnProperty("ERROR")){
						this.setState({
							user: null
						})
						localStorage.removeItem('user')
					}else if(this.state.user===null){
						this.setState({
							user : response.data
						})
						localStorage.setItem('user',JSON.stringify(response.data))
					}
				}
			)
		/* if(us!==null && us!==undefined && this.state.user===null){
			this.setState({
				user: JSON.parse(us)
			})
		} */
	}

	render () {
		
		return ( 
			<Router basename={'/edext-server-web'}>
				<div className="d-flex" id="wrapper">
					{/* Siderbar */}
					<div className = {this.state.showHideNav + " sideBar"}><SideBar cerrarSesion={this.cerrarSesion} state={this.state} toggle={ this.toggleSideBar }/></div>

					{/* Page content wrapper  */}
					<div id="page-content-wrapper">
			
						{/* Navbar */}
						<NavBar userLogued={this.state.user} cerrado={this.state.cerrado} nomTabla={this.state.objetiveSearch} doSomething = {this.toggleSideBar}  cerrarSesion = {this.cerrarSesion}/>

						{/* Modal Iniciar Sesion */}
						<ModalIniciarSesion setUser={this.setUserLogued}/>

						{/* Modal Registrarse */}
						<ModalRegistrarse />

						{/* Modal Crear Programa Formacion */}
						<CrearProgramaFormacion />

						{/* Contenedor principal */}
						<div className="container formPrincipal">
							
							{/* Routes begin */}

							<Route exact path="/ConsultaUsuarioUno" render={(props) => <ConsultaUsuarioUno {...props} data={this.state} search={ this.swithSearch } />}  />
							<Route exact path="/ConsultaUsuarioDos" render={(props) => <ConsultaUsuarioDos {...props} data={this.state} search={ this.swithSearch } />} />
							{/* <Route exact path="/" component={Inicio} /> */}
							<Route exact path="/" render = { (props) => <Inicio {...props} userLogued={ this.state.user } setUser={ this.setUserLogued } /> } />
							<Route path="/curso" render={props => <InfoCurso {...props} tipo={this.state.user===null?null:this.state.user} search={this.swithSearch}/>}/>
							<Route path="/programa" render={props => <InfoPrograma {...props} tipo={this.state.user===null?null:this.state.user.tipo} programas={this.state.user===null?null:this.state.user.programas} search={this.swithSearch}/>}/>
							{this.state.user===null?null:<Route path="/altacurso" render={props=><AltaCurso{...props} search={this.swithSearch} instituto={this.state.user.instituto} />}/>}
							<Route exact path="/alta-edicion-curso" component={AltaEdicionCurso} />
							<Route exact path="/consulta-edicion-curso" render={props => <ConsultaEdicionCurso {...props} user={this.state.user}/>} />
							<Route exact path="/agregar-curso-programa" render={props => <AgregarCursoPrograma {...props} swithBar={this.swithSearch}/>} />
							{/* Routes end */}
							
							<ToastContainer />
						</div>
					{/* Close page content wrapper */}
					</div>
				</div>
			</Router>
		);
	}
}

ReactDOM.render(<App />, document.getElementById('root'));
