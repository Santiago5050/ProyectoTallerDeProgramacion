import React, { Component } from 'react';
import Select from 'react-dropdown-select';
import TableSelPrevias from './tableSelPrevias';
import '../css/altacurso.css'
import InputFoto from './inputfoto'
import no_image from './../res/no_image.jpg';
import ApiRequest2 from '../js/api2';
import { toast } from 'react-toastify';
import Loader from './Loader.jsx'
import ErrorPage from './errorPage.jsx'
import { Link } from 'react-router-dom'


class AltaCurso extends Component {

    constructor(props){
        super(props)

        this.imagen = React.createRef();
        this.state = {
            "showCupo" : false,
            "mobile": window.innerWidth <= 760,
            "previas" : null,
            "categorias" : null,
            "cursos" : null,
            "file" : null,
            "error" : null,
            "existeCurso": false
        }
        this.dur = React.createRef();
        this.cantH = React.createRef();
        this.creditos = React.createRef();
        this.desc = React.createRef();
        this.cats = React.createRef();
        this.nom = React.createRef();
        this.url = React.createRef();
        this.image = React.createRef();
    }

    componentDidMount(){
        window.addEventListener("resize", this.resize.bind(this))
        this.resize()
        let api = new ApiRequest2();
        api.getCategorias().then(
            response =>{
                if(response.data.hasOwnProperty("ERROR")){
                    this.setState({
                        "error" : response.data.ERROR
                    })
                }else{
                    let i =0;
                    let cats = response.data
                    for(let k=0; k<cats.length;k++){
                            cats[k].value=i;
                            i++;     
                    }
                    this.setState({
                        "categorias" : cats
                    })
                }
            }
        )

        api.getCursos().then(
            response =>{
                if(response.data.hasOwnProperty("ERROR")){
                    this.setState({
                        "error" : response.data.ERROR
                    })
                }else{
                    this.setState({
                        "cursos" : response.data
                    })
                }
               
            }
        )
    }

    resize= ()=>{
        this.setState({
            "mobile": window.innerWidth <= 760
        })
    }

    onChange = (values) =>{
        return;
    }

    toggleNumber= () => {
        this.setState({
            "showCupo" : !this.state.showCupo
        })
    }

    sendCurso= () =>{
        //-----
        if (this.dur.current.value !== "" && this.cantH.current.value !== "" && this.creditos.current.value !== "" && this.desc.current.value !== "" && this.nom.current.value !== "") {
            let api = new ApiRequest2();
            let source = ''
            if(this.state.file!==null){
                let ip = window.Configs.ip;
                let port= window.Configs.port;
                let extension = this.state.file.name.split(".")
                let formData = new FormData();
                formData.append("file", this.state.file);
                formData.append("nombre", this.nom.current.value + "." + extension[1]);
                source = "http://" + ip + ":" + port + "/edext-server-web/images/" +  this.nom.current.value + "." + extension[1]
                api.enviarImagen(formData);
            }
            
            //----
            api.altaCurso(this.props.instituto,this.nom.current.value,
                this.desc.current.value,this.dur.current.value,this.cantH.current.value,this.creditos.current.value,
                this.url.current.value,this.state.previas,source,this.cats.current.state.values).then(
                    response=>{
                        if(response.data.hasOwnProperty("OK")){
                            window.location.reload()
                            toast.info("Curso añadido");
                        }  else if(response.data.hasOwnProperty("ERROR"))
                        toast.error(response.data.ERROR)
                    }
                ).catch(
                    ()=>{
                        toast.error("Error");
                    }
                )
            } else {
                toast.error("No completo todos los campos");
            }
        
    }

    addRemPrevias= (nom)=>{
        console.log("dsa", nom)
        if(this.state.previas!==null && this.state.previas.includes(nom)){
            let pr = this.state.previas;
            let num = pr.indexOf(nom)
            pr.splice(num,num)
            this.setState({
                "previas" : pr
            })
        }else if(this.state.previas!==null){
            this.setState({
                "previas" : [...this.state.previas, nom]
            })
        }else{
            this.setState({
                "previas" : [nom]
            })
        }
    }

    guardar=(file)=>{
        this.setState({
            "file" : file
        })
    }
  
    existeCurso = () => {
		let api = new ApiRequest2();
		api.validarDatosCurso(this.nom.current.value)
		.then(response => {
			if (response.data.hasOwnProperty('OK')) {
				if (response.data.OK === 'true') {
					this.setState({
						"existeCurso": true
					});
				} else {
					this.setState({
						"existeCurso": false
					});
				}
			}
		})
		.catch(errorResponse => {
			console.log(errorResponse)
		})
    }
    
    alertaCursoYaExiste = () => {
		if (this.state.existeCurso) {
			return(
				<div>
					<p className="curso-ya-existe-3498">¡El curso ya existe!</p>
				</div>
			)
		}
    }

    render() {
 
        if(this.state.error!==null){
            return(<div>
                <ErrorPage mensaje={this.state.error}/>
            </div>)
        }else if(this.state.cursos===null || this.state.categorias===null){
            return(<div><Loader/></div>)
        }
        else{       return (
            <div className="container contenedor-alta">
                <div className="row row-alta titulo-alta">
                    <div className="col-sm-8 col-titulo ">
                        <h2>Ingrese un curso:</h2>
                    </div>
                        {this.state.mobile?null:(
                        <div className="col-sm-4 botones">
                            <Link to="/"><button className="btn btn-secondary btn-cancelar">Cancelar</button></Link>
                            <button type="button"  className="btn btn-primary btn-agregar" onClick={this.sendCurso}><i class="fas fa-plus"></i> Agregar</button>
                        </div>)}
                    
                </div>
                <div className="row">
                
                    <div className="col-sm-12 alta-content">
                        <form className="container" id="form-alta-curso">
                            <div className="form-group row">
                                <div className="col-sm-3">
                                    <h4>
                                        Seleccione una imagen:
                                    </h4>
                                </div>
                                <div className="col-sm-3">
                                    <InputFoto img={no_image} subir={ this.guardar }/>
                                </div>
                                <div className="col-sm-6" id="colCID">
                                    <div className="container" id="contCID">
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label"> Instituto: </label>
                                            <label className="col-sm-9 col-form-label">{this.props.instituto}</label>
                                        </div>
                                        <div className="form-group row">
                                            <label className="col-sm-3 col-form-label"> Duracion: </label>
                                            <div className="col-sm-9">
                                                <input className="form-control" type="text" ref={this.dur}/>
                                            </div>
                                            
                                        </div>
                                        <div className="form-group row">
                                                <label className="col-sm-3 col-form-label"> Cantidad de Horas: </label>
                                                <div className="col-sm-9">
                                                    <input className="form-control" type="number" ref={this.cantH}/>
                                                </div>
                                            </div>
                                    </div>
                                </div>
                                

                            </div>

                            
                            
                           
                            
                           
                            <div className="form-group row">
                                    <label className="col-sm-1 col-form-label"> Nombre: </label>
                                    <div className="col-sm-5">
                                        <input className="form-control" type="text" ref={this.nom} onBlur={ this.existeCurso }/>
                                    </div>
                                <label className="col-sm-1 col-form-label"> Creditos: </label>
                                
                                <div className="col-sm-5" id="cupo">
                                    <input className="form-control" type="number" ref={this.creditos}/>
                                </div>
                            </div>
                            { this.alertaCursoYaExiste() }
                            <div className="form-group row">
                                    <label className="col-sm-1 col-form-label"> URL: </label>
                                    <div className="col-sm-11">
                                        <input className="form-control" type="text" ref={this.url}/>
                                    </div>
                            </div>
                            <div class="form-group row">
                                <div className="col-sm-12">
                                    <label for="exampleTextarea"> Descripcion:</label>
                                    <textarea className="form-control" id="exampleTextarea" rows="3" ref={this.desc}></textarea>
                                </div>
                            </div>
                            <div className="form-group row">
                                <div className="col-sm-12">
                                <Select
                                placeholder="Categorias..."
                                multi
                                options={this.state.categorias}
                                onChange={(values)=>{}}
                                labelField="nombre"
                                ref={this.cats}
                                />
                                </div>
                                
                            </div>
                            
                        </form>
                        
                       
                    </div>
                    
                </div>
                <div className="row">
                    <div className="col-sm-12 col-tabla">
                    <ul className="nav nav-tabs nav-info">
                            <li className="nav-item"><a className="nav-link active info-active solo-tab">Añadir previas</a></li>

                    </ul>
                      {this.state.cursos===null?null:<TableSelPrevias cursos={this.state.cursos} search={this.props.search} handleChange={this.addRemPrevias}/>}    
                    </div>
                </div>
                
                {this.state.mobile?(
                    <div className="row mobile-mode">
                        <div className="col-xs-6"><button className="btn btn-secondary btn-cancelar">Cancelar</button></div>
                        <div className="col-xs-6"><button type="submit" form="form-alta-curso" className="btn btn-primary btn-agregar"><i class="fas fa-plus"></i> Agregar</button></div>
                 
                    </div>
                ):null}
            </div>
        );}
    }
}

export default AltaCurso;