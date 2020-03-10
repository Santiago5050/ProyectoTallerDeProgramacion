import React, { Component } from 'react';
import '../css/inputfoto.css'


class InputFoto extends Component {


    constructor(props){
        super(props)
        this.state= {
            "image" : null
        }
        this.readFile.bind(this);
    }

    readFile = (file) =>{
  

        let fReader = new FileReader();
        if(file.length>0){
           
            fReader.readAsDataURL(file[0])
            fReader.onloadend = (e) =>{
                this.setState({
                    "image" : e.target.result
                })
            
            }
            
            this.props.subir(file[0])
        }
        
        
    }



    render() {
       
        return (
            <div className="img-input">
            <label htmlFor="file-input">
                <div>
                    <figure className="pushup">
                            <img src={this.state.image===null?this.props.img:this.state.image}  
                            alt="Aqui va una imagen"
                            width="200" height="200"
                            />
                            <figcaption>
                            <i className="fas fa-plus agregar-simbolo"></i>
                            <h5>Agregar Foto</h5>
                            </figcaption>
                    </figure>
                </div>
                
                
                
            </label>
            <input id="file-input" type="file" onChange={(e)=>{ this.readFile(e.target.files)}} accept="image/png, image/jpeg, image/jpg"/>
        </div>
        );
    }
}

export default InputFoto;