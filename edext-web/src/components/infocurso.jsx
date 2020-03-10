import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import Pill from './pill';
import TableEdicion from './tableEdicion';
import TablePrograma from './tablePrograma';
import TableCurso from './tableCurso';
import ApiRequest from '../js/api';
import logo from '../res/no_image.jpg';
import '../css/infocursoprograma.css';
import ApiRequest2 from '../js/api2';
import Loader from './Loader.jsx'
import ErrorPage from './errorPage.jsx'
import { isMobile } from 'react-device-detect'
import Chart from 'react-apexcharts'
import StarRatings from 'react-star-ratings'
import { SSL_OP_TLS_ROLLBACK_BUG } from 'constants';
import { toast } from 'react-toastify';

class InfoCurso extends Component {
    constructor(props){
        super(props);
        this.state={
            "active1" : true,
            "active2" : false,
            "active3" : false,
            "change" : false,
            "data" : null,
            "visitas":0,
            "error" : null,
            "ediciones" :null,
            "programas" : null,
            "previas" : null,
            "options" : {
                        chart:{
                            toolbar:{
                                show:false
                            }
                        },
                        plotOptions: {
                                bar: {
                                    horizontal: true,
                                    distributed:true
                                }
                            },
                        dataLabels: {
                                enabled: false
                            },
                        xaxis: {  
                            
                            
                            floating:true,
                            labels:{
                              show:false
                            },
                             axisTicks: {
                              show: false
                            },
                            axisBorder: {
                              show: false
                            },
                                categories: ['5','4','3','2','1'],
                            },
                        
                        yaxis:{
                           
                            floating: true,
                          
                            axisTicks: {
                              show: false
                            },
                            axisBorder: {
                              show: false
                            },
                            labels: {
                              show: true
                            },
                        },
                        grid:{
                            yaxis: {
                                lines:{
                                  show:false
                                }
                              }
                        }
                    }
        }

       
    }

    handleClick1= ()=>{
        this.setState({
            "active1" : true,
            "active2" : false,
            "active3" : false,
        })
    }

    handleClick2= ()=>{
        this.setState({
            "active1" : false,
            "active2" : true,
            "active3" : false,
        })
    }

    handleClick3= ()=>{
        this.setState({
            "active1" : false,
            "active2" : false,
            "active3" : true,
        })
    }

    tablaTab = () =>{
        if(this.state.active1 && this.state.ediciones!==null){
            return(<TableEdicion ediciones={this.state.ediciones} instituto={this.state.data.instituto} curso={this.state.data.nombre} search={this.props.search}/>)
        }else if(this.state.active2 && this.state.programas!==null){
            
            return(<TablePrograma programas={this.state.programas} search={this.props.search}/>)
        }else if(this.state.active3 && this.state.previas!==null){
            
            return(<TableCurso cursos={this.state.previas} search={this.props.search}/>)
        }
    }



   componentDidMount(){
       console.log(this.props.location.state)
       const n = this.props.location.state.nombre
       let api = new ApiRequest2()
       api.getCurso(n).then(
           response =>{
               if(response.data.hasOwnProperty("ERROR")){
                   this.setState({
                       "error" : response.data.error,
                   })
               }else{
                   this.setState({
                       "data" : response.data
                   })
                   api.visitaCurso(response.data.nombre).then(
                       r=>{
                           if(r.data.hasOwnProperty("OK"))
                                this.setState({
                                        visitas : r.data.OK
                                })
                    }
                   )
               }
               api.getEdicionesCurso(response.data.nombre).then(
                r =>{
                    this.setState({
                        "ediciones" : r.data
                    })
                }
               ).catch(
                   errorresponse =>{
                    this.setState({
                        "error" : errorresponse,
                    }) 
                   }
               )
               api.getProgramasCurso(response.data.nombre).then(
                r =>{
                    this.setState({
                        "programas" : r.data
                    })
                }
               ).catch(
                errorresponse =>{
                    this.setState({
                        "error" : errorresponse,
                    }) 
                   }
               )
               console.log(response)
               api.getPrevias(response.data.nombre).then(
                r =>{
                    this.setState({
                        "previas" : r.data
                    })
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

   }

 componentDidUpdate(){
       
    let n;
    if(this.props.location!==undefined)
        n = this.props.location.state.nombre

    if(this.state.data===null || n!==this.state.data.nombre){
        let api = new ApiRequest2()
    api.getCurso(n).then(
        response =>{
            if(response.data.hasOwnProperty("ERROR")){
                this.setState({
                    "error" : response.data.error,
                })
            }else{
                this.setState({
                    "data" : response.data
                })
                api.visitaCurso(response.data.nombre).then(
                    r=>{
                        if(r.data.hasOwnProperty("OK"))
                             this.setState({
                                     visitas : r.data.OK
                             })
                 }
                )
            }
            api.getEdicionesCurso(response.data.nombre).then(
             r =>{
                 this.setState({
                     "ediciones" : r.data
                 })
             }
            ).catch(
                errorresponse =>{
                 this.setState({
                     "error" : errorresponse,
                 }) 
                }
            )
            api.getProgramasCurso(response.data.nombre).then(
             r =>{
                 this.setState({
                     "programas" : r.data
                 })
             }
            ).catch(
             errorresponse =>{
                 this.setState({
                     "error" : errorresponse,
                 }) 
                }
            )
            console.log(response)
            api.getPrevias(response.data.nombre).then(
             r =>{
                 this.setState({
                     "previas" : r.data
                 })
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
    )}
   } 

   valoroCurso = (valoracion)=>{
       let api = new ApiRequest2();
       api.valorarCurso(this.state.data.nombre,valoracion).then(
           (response)=>{
                if(response.data.hasOwnProperty("OK")){
                    toast.info("Se ha enviado su calificacion")
                    api.getCurso(this.state.data.nombre).then(
                        (response)=>{
                            this.setState({
                                data : response.data
                            })
                        }
                    )
                }else
                    toast.error(response.data.ERROR)
           }
       ).catch(
        (erroresponse)=>{
            toast.error("ERROR")
        }
       )

   }


    render() {
     
        

        if(this.state.error !== null){
            return <div>
                <ErrorPage mensaje={this.state.error}/>
            </div>
        }else if(this.state.data===null){
            return <div><Loader/></div>
        }else{
            let series = [{
                name: 'Cantidad de calificaciones',
                data: [this.state.data.rating[4],this.state.data.rating[3],this.state.data.rating[2],this.state.data.rating[1],this.state.data.rating[0]]
            }]
            let promedio = 0
            let total =0
            for(let i=0;i<5;i++){
                promedio=promedio+this.state.data.rating[i]*(i+1)
                total = total + this.state.data.rating[i]
            }
            if(total!==0)   
                promedio=promedio/total
            else
                promedio=0
            let cargado = (
                <div className="container contenedor-info">
                    <div className="row info-curso">
                        <div className="col-lg-3 img-container">
                            <img src={this.state.data.src===""?logo:this.state.data.src} width="200" height="200"/>
                        </div>
                        <div className="col-lg-9">
    
                            <div className="container">
                                <div className="row row-principal titulo-info">
                                    <div className="col-lg-8 titulo-curso">
                                        <h2>{this.state.data.nombre}</h2>
                                    </div>
                                    <div className="col-lg-4 botones-info">
                                        {   this.props.tipo!==null &&
                                            this.props.tipo.tipo==='docente' && this.props.tipo.instituto===this.state.data.instituto?
                                                <Link to={{
                                                    pathname: '/alta-edicion-curso',
                                                    state: {
                                                        instituto: this.state.data.instituto,
                                                        curso: this.state.data.nombre
                                                    }
                                                }}><button className="btn btn-primary btn-agregar"><i className="fas fa-plus"></i> Agregar Edicion</button></Link>
                                            :
                                                null
                                        }
                                    </div>
                                </div>
                                <div className="row extra-info">
                                    <div className="col-lg-6">
                                        <p><i class="fas fa-university"></i> {this.state.data.instituto}</p>
                                        <p><i class="fas fa-globe-americas"></i> <a href="#">{this.state.data.url}</a></p>
                                        <p><i class="far fa-clock"></i> {this.state.data.cantidadHoras} horas</p>
                                        <p><i class="fas fa-hourglass-half"></i> {this.state.data.duracion}</p>
                                        
                                    </div>
                                    <div className="col-lg-6">
                                        <h4>Descripcion</h4>
                                        <p>{this.state.data.descripcion}</p>
                                        <p><i class="fas fa-graduation-cap"></i> {this.state.data.creditos} creditos</p>
                                        <p><i class="fas fa-calendar-alt"></i> {this.state.data.fechaDeRegistro.dayOfMonth}/{this.state.data.fechaDeRegistro.month+1}/{this.state.data.fechaDeRegistro.year}</p>
                                    </div>
    
                                </div>
                            </div>
                            
                            
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-sm-12">
                            <h5> Rating </h5>
                            <div className="pill-box info-pill-box">
                                        <div className="container">
                                            <div className="row">
                                                <div className="col-lg-6">
                                                <Chart options={this.state.options} series={series} type="bar" width={isMobile?250:500} height={210}/>
                                                </div>
                                                <div className="col-lg-6">
                                                    <h5>Calificación Promedio</h5>
                                                    <h2>{promedio.toFixed(1)}</h2>
                                                    <StarRatings rating={promedio} changeRating={(r)=>{this.valoroCurso(r)}} starHoverColor="rgb(235, 157, 12)" starRatedColor="rgb(0, 229, 255)"starDimension="30px"/>
                                                    <p>Visitas: {this.state.visitas}</p>
                                                </div>
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
                            <ul className="nav nav-tabs nav-info" id="tabs">
                                <li onClick={this.handleClick1} className={"nav-item"+ (this.state.active1?null:"active")}>
                                    <p className={"nav-link "+(this.state.active1?"active info-active":null)}>Ediciones</p>
                                </li>
                                {isMobile?null:<li onClick={this.handleClick2} className={"nav-item"+ (this.state.active2?null:"active")}>
                                    <p className={"nav-link "+(this.state.active2?"active info-active":null)}>Programas</p>
                                </li>}
                                <li onClick={this.handleClick3} className={"nav-item"+ (this.state.active3?null:"active")}>
                                    <p className={"nav-link "+(this.state.active3?"active info-active":null)}>Previas</p>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div className="row tabla-info-row">
                        <div className="col-sm-12 tabla-info">
                                {this.tablaTab(this.state.data)}
                        </div>
                    </div>
                    
                </div>
            );
            return cargado;
        }
    }
}

export default InfoCurso;