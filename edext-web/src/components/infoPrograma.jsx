import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Pill from './pill'
import TableCurso from './tableCurso';
import ApiRequest from '../js/api';
import '../css/infocursoprograma.css';
import {Redirect} from 'react-router-dom';
import logo from '../res/no_image.jpg';
import ApiRequest2 from '../js/api2';
import Loader from './Loader.jsx'
import ErrorPage from './errorPage.jsx'
import { toast } from 'react-toastify';


export class InfoPrograma extends Component {

    constructor(props){
        super(props)
        this.state = {
            "data" : null,
            "cursos" : null,
            "error" : null,
            aprobo :false
        }
    }
    componentDidMount(){
        const n = this.props.location.state.nombre;
        let api = new ApiRequest2();
        api.getPrograma(n).then(
            response=> {
                if(response.data.hasOwnProperty("ERROR")){
                    this.setState({
                        "error" : response.data.ERROR
                    })
                }else{
                    this.setState({
                        "data" : response.data
                    })
                }
            api.getCursosPrograma(response.data.nombre).then(
                response=>{
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
            ).catch(
                errorresponse =>{
                    this.setState({
                        "error" : errorresponse,
                    }) 
                   }
            )
        }
        ).catch(
            errorresponse =>{
                this.setState({
                    "error" : errorresponse,
                }) 
               }
        )
        
            api.aproboPrograma(n).then(
                response =>{
                    if(response.data.hasOwnProperty("ERROR")){
                        this.setState({
                            aprobo:false
                        })
                    }else if(response.data.hasOwnProperty("OK")){
                        this.setState({
                            aprobo:true
                        })
                    }
                }
            )
        


    }

    certificadoPrograma=() =>{
        let api = new ApiRequest2()
        api.certificadoPrograma(this.state.data.nombre).then(
            response=>{
                var newBlob = new Blob([response.data],{type:'application/pdf'})
                const data = window.URL.createObjectURL(newBlob)
                var link = document.createElement('a')
                link.href=data;
                link.download="certificado.pdf"
                link.click()
                setTimeout(function(){
                    window.URL.revokeObjectURL(data)
                },100)
            }
        )
    }

    inscribirPrograma = () =>{
        let api = new ApiRequest2();
        var today = new Date();
        var date = today.getDate()+'/'+(today.getMonth()+1)+'/'+today.getFullYear();
        api.inscripcionEstudiantePrograma(this.state.data.nombre, date).then(
            response =>{
                if(response.data.hasOwnProperty("OK")){
                    toast.info("Te has inscripto.")
                }else if(response.data.hasOwnProperty("ERROR")){
                    toast.error(response.data.ERROR)
                }
                api.updateLogin().then(
                    response2 =>{
                        console.log('respuesta2',response2)
                        localStorage.setItem('user',JSON.stringify(response2.data))
                        window.location.reload();
                    }
                )
            }
        ).catch(
            errorresponse =>{
                toast.error("Error")
            }
        );
    }

    render() {
    
        if(this.state.data!==null){return (
            <div className="container contenedor-info">
                <div className="row  info-curso info-programa">
                    
                    <div className="col-sm-3 img-container">
                        <img src={this.state.data.src===""?logo:this.state.data.src} width="200" height="200"/>
                    </div>

                    
                    
                    <div className="col-sm-9  titulo-curso">

                        <div className="container">
                            <div className="row titulo-info">
                                <div className="col-sm-8 ">
                                    <h2>{this.state.data.nombre}</h2>
                                </div>
                                <div className="col-sm-4 botones-info">
                                        {this.props.tipo==="docente"?
                                            <Link to={{
                                                pathname: '/agregar-curso-programa',
                                                state: {
                                                    nombre: this.state.data.nombre
                                                }
                                            }}><button className="btn btn-primary btn-agregar"><i className="fas fa-plus"></i> Agregar Curso </button></Link>
                                        :
                                            (this.props.tipo==="estudiante" && !this.props.programas.includes(this.state.data.nombre)?
                                                <button className="btn btn-primary btn-agregar" onClick={this.inscribirPrograma}><i className="fas fa-plus" ></i> Inscribirme </button>
                                            :(this.state.aprobo===true)? <button className="btn btn-primary btn-agregar" onClick={this.certificadoPrograma}>Obtener certificado</button>:
                                                null
                                            )
                                        }
                                            
                                </div>
                            </div>

                            <div className="row info-prog-extra">
                                        <div className="col-sm-6">
                                            <p><i class="far fa-calendar"></i> Desde: {this.state.data.fechaInicio.dayOfMonth}/{this.state.data.fechaInicio.month+1}/{this.state.data.fechaInicio.year}</p>
                                            <p><i class="far fa-calendar"></i> Hasta: {this.state.data.fechaFin.dayOfMonth}/{this.state.data.fechaFin.month+1}/{this.state.data.fechaFin.year}</p>
                                        </div>
                                        <div className="col-sm-6">
                                            <h4>Descripcion</h4>
                                            <p>   {this.state.data.descripcion}</p>
                                        </div>
                            </div>
                        </div>
                        
                    </div>
             
                </div>
             
                <div className="row">
                    <div className="col-sm-12">
                        <h5> Categorias </h5>
                        <div className="pill-box info-pill-box">
                                {this.state.data.categorias.map(
                                    c => {
                                        return <Pill texto={c} type="categoria"/>
                                    }
                                )}
                                </div>
                    </div>
                </div>

                
                <div className="row">
                    <div className="col-sm-12">
                        <ul className="nav nav-tabs nav-info">
                                    <li className="nav-item"><a className="nav-link active info-active solo-tab">Cursos</a></li>

                        </ul>
                     {this.state.cursos===null?null: <TableCurso cursos={this.state.cursos} search={this.props.search}/>}
                    </div>
                </div>
                
            </div>
        );}else if(this.state.error!=null){
            return <div>
                <ErrorPage mensaje={this.state.error}/>
            </div>
        }else{
            return(
                <div>
                    <Loader/>
                </div>
            )
        }
    }
}

export default InfoPrograma;
