import React, { Component } from 'react';
import DisplayCard from './displaycard.jsx';
import SortBar from './sortbar.jsx';
import ApiRequest from '../js/api.js';
import ApiRequest2 from '../js/api2.js';
import  Loader  from './Loader.jsx';
import ErrorPage from './errorPage.jsx';
import LoginMobile from './LoginMobile'
import { isMobile } from "react-device-detect";

class Inicio extends Component {
	constructor(props){
		super(props);
		this.state = {
			"curso" : true,
			"programa" : true,
			"cards" : null,
			"error" :false,
			"mensajeerror" : null,
			"tipo" : null,
			"nombre":null
		}	
	}


	tipoCurso = () =>{
		this.setState({
			"curso" : !this.state.curso
		});
	}
	tipoPrograma = () =>{
		this.setState({
			"programa" : !this.state.programa
		});
	}

	cargarInicio = (api) =>{
		api.getInicio().then(
			response =>{
				if(!Array.isArray(response) && response.hasOwnProperty("ERROR")){
					this.setState({
						"error" : true,
						"mensajeerror" : response.ERROR
					})
				}else{
					this.setState({
						"cards" : response
					})
				}
			}
		).catch(
			errorrespones =>{
				this.setState({
					"error" : true
				})
			}
		)
	}
	componentDidMount(){
		
		if(this.props.location.state!==undefined){
			let handle  = this.props.location.state.tipo
			let handle2  = this.props.location.state.nombre
			console.log("tipo",handle)
			console.log("nombre", handle2)
			this.setState({
				"tipo" : handle,
				"nombre": handle2
			})
			console.log(this.state)
		}
		let api = new ApiRequest2();
		this.cargarInicio(api);
		
	}

	componentDidUpdate(){
		if(this.props.location.state!==undefined){
			let handle  = this.props.location.state.tipo
			let handle2  = this.props.location.state.nombre

			if(handle2!==this.state.nombre){
				this.setState({
					"tipo" : handle,
					"nombre": handle2
				})
			}
			

		}
	}
	ordenar = ordPor =>{
		if(ordPor==="A-Z"){
			var c = this.state.cards
			c.sort((a,b) =>{
				var x = a.nombre.toLowerCase()
				var y = b.nombre.toLowerCase()
				if(x<y){ return -1}
				if(x>y){ return 1}
				return 0
			})
			this.setState({
				"cards" : c
			})
		}else if(ordPor==="anio"){
			var p = JSON.parse(JSON.stringify(this.state.cards)) 
			p.sort((a,b) =>{
				var x;
				if(a.tipo==="Programa"){
					 x = a.fechaAlta.year
				}else if(a.tipo==="Curso"){
					x = a.fechaDeRegistro.year
				}
				var y;
				if(b.tipo==="Programa"){
					y = b.fechaAlta.year
			   }else if(b.tipo==="Curso"){
				   y = b.fechaDeRegistro.year
			   }
				if(x<y){ return 1}
				if(x>y){ return -1}
				return 0
			})
			
			this.setState({
				"cards" : p
			})

		}else{
			var d = this.state.cards
			d.sort((a,b) =>{
				if(a.tipo==="Curso"&& b.tipo==="Curso"){
				var x = a.visitas
				var y = b.visitas
				if(x<y){ return 1}
				if(x>y){ return -1}
				return 0
				}else if(a.tipo==="Programa" && b.tipo==="Curso")
					return 1
				else if(b.tipo==="Programa" && a.tipo==="Curso")
					return -1
				else 
					return 0
			})
			this.setState({
				"cards" : d
			})
		}
	}

	cargar = (handle, handle2)=>{
		if(this.state.error===true){
			return <div>
				<ErrorPage mensaje={this.state.mensajeerror}/>
			</div>
		}else
	 if(this.state.cards === null){
		return <div>
			<Loader/>
		</div>
		}else{
			 
			return this.state.cards.map(c =>{

			if(this.state.tipo === null){
				 
				if(c.tipo==="Curso" && this.state.curso){
					 
					return <DisplayCard tipo={c.tipo} titulo={c.nombre} descripcion={c.descripcion} categorias={c.categorias} img={c.src}/>
				}else if(c.tipo==="Programa" && this.state.programa && !isMobile)
					return <DisplayCard tipo={c.tipo} titulo={c.nombre} descripcion={c.descripcion} categorias={c.categorias} img={c.src}/>
				else 
					return null;
			}else{
				if(this.state.tipo==="instituto"){
					if(c.tipo==="Curso" && this.state.curso &&c.instituto===this.state.nombre)
						return <DisplayCard tipo={c.tipo} titulo={c.nombre} descripcion={c.descripcion} categorias={c.categorias} img={c.src}/>
					else 
						return null;
				}else if(this.state.tipo==="categoria"){
					if(c.tipo==="Curso" && this.state.curso && c.categorias.includes(this.state.nombre))
						return <DisplayCard tipo={c.tipo} titulo={c.nombre} descripcion={c.descripcion} categorias={c.categorias} img={c.src}/>
					else if(c.tipo==="Programa" && this.state.programa && c.categorias.includes(this.state.nombre))
						return <DisplayCard tipo={c.tipo} titulo={c.nombre} descripcion={c.descripcion} categorias={c.categorias} img={c.src}/>
					else 
						return null;
				}
			}
		});
		}
	}

	render() {
		if (isMobile && !this.props.userLogued) {
			return (
				<LoginMobile setUser = { this.props.setUser } />
			)
		} else {
			return (
				<div className="container">
					<div className= "row">
						<div className="col-sm-12 sortbar-container">
							<SortBar boton1={this.tipoCurso} boton2={this.tipoPrograma} ordenar={this.ordenar}/>
						</div>
					</div>
					{
						this.cargar()
					}
						
				</div>
				
			);
		}
	}
	
}

export default Inicio;