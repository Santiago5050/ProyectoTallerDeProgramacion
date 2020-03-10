import axios from 'axios';

class ApiRequest {
    
    getInstitutos = () => {
        let res = require("../json/institutos.json");
        return res;
    }

    getCategorias = () => {
        let res = require("../json/categorias.json");
        return res;
    }

    getCursos = () => {
        let res = require("../json/cursos.json")
        return res;
    }

    getProgramas = () => {
        let res = require("../json/programas.json")
        return res;
    }

    getEdiciones=() => {
        let res = require("../json/ediciones.json")
        return res;
    }

    getProgramasCurso = c =>{
        let input = require("../json/programas.json")
        let res = []
        for(let i=0; i<input.length;i++){
            if(c.includes(input[i].nombre))
                res.push(input[i])
        }
        return res
    }

    getTestEdicion = () => {
        const response = axios.get('http://localhost:8080/edext-server-web/Edicion', {
            params: {
                Curso: "Taller de robótica educativa"
            }
        })
        .then(response => {
            console.log("Test Edicion: ", response);
        });
    }

    getEdicionesCurso = c =>{
        let input = require("../json/ediciones.json")
        let res = []
        for(let i=0; i<input.length;i++){
            if(c.includes(input[i].nombre))
                res.push(input[i])
        }
        return res;
    }

    getPrevias = c =>{
        let input = require("../json/cursos.json")
        let res = []
        for(let i=0; i<input.length;i++){
            if(c.includes(input[i].nombre))
                res.push(input[i])
        }
        return res;
    }

    getEdicion = c =>{
        let input = require("../json/ediciones.json")
        let res={"nombre": 404};
        for(let i=0; i<input.length;i++){
            if(input[i].nombre.toLowerCase()===c.toLowerCase())
                res = input[i]
        }
        return res;
    }

    getPrograma = c =>{
        let input = require("../json/programas.json")
        let res={"nombre": 404};
        for(let i=0; i<input.length;i++){
            if(input[i].nombre.toLowerCase()===c.toLowerCase())
                res = input[i]
        }
        return res;
    }

    getCurso = c =>{
        let input = require("../json/cursos.json")
        let res={"nombre": 404};
        for(let i=0; i<input.length;i++){
            if(input[i].nombre.toLowerCase()===c.toLowerCase())
                res = input[i]
        }
        return res;
    }


    getInicio = () =>{
        let input1 = require("../json/cursos.json")
        let input2 = require("../json/programas.json")
        let res = []
        for(let i=0; i<input1.length;i++){
            input1[i].tipo = "Curso"
            res.push(input1[i])
        }
        for(let i=0; i<input2.length;i++){
            input2[i].tipo = "Programa"
            res.push(input2[i])
        }
        res.sort((a,b) =>{
            var x = a.nombre.toLowerCase()
            var y = b.nombre.toLowerCase()
            if(x<y){ return -1}
            if(x>y){ return 1}
            return 0
        })
        return res;
    }

    getCategorias= () =>{
        let res = require('../json/categorias.json')
        return res;
    }

    getUsuario = (name) => {
        let input = require('../json/usuarios.json');
        let res = {"nickname": 404};
        for (let i = 0; i < input.length; i++) {
            if (input[i].nickname === name) {
                res = input[i];
            }
        }
        return res;
    }

    getUsuarios = () => {
        return require('../json/usuarios.json');
    }

    getEdicionesUsuario = (name) => {
        let input1 = require('../json/usuarios.json');
        let usuario = {"nickname": 404};
        let ediciones = [];
        let res = [];
        // Busco el usuario 'name'
        for (let i = 0; i < input1.length; i++) {
            if (input1[i].nickname === name) {
                usuario = JSON.parse(JSON.stringify(input1[i]));
            }
        }
        // Para cada edicion del usuario busco sus datos
        if (!(usuario.nickname === 404)) {
            ediciones = usuario.ediciones;
            ediciones.map(ed => {
                res.push(JSON.parse(JSON.stringify(this.getEdicion(ed.nombre))));
            })
            for (let j = 0; j < res.length; j++) {
                if (ediciones[j].hasOwnProperty('estado')) {
                    res[j].estado = ediciones[j].estado;
                }
            }
        }
        return res;
    }

    getProgramasUsuario = (name) => {
        let input1 = require('../json/usuarios.json');
        let usuario = {"nickname": 404};
        let res = [];
        // Busco el usuario 'name'
        for (let i = 0; i < input1.length; i++) {
            if (input1[i].nickname === name) {
                usuario = JSON.parse(JSON.stringify(input1[i]));
            }
        }
        // Para cada programa del usuario busco sus datos
        if (!(usuario.nickname === 404)) {
            if (usuario.hasOwnProperty('programas')) {
                usuario.programas.map(prog => {
                    res.push(JSON.parse(JSON.stringify(this.getPrograma(prog))));
                })
            }
        }
        return res;
    }

    getUsuariosEdicion = (nom_edicion) => {
        let usuarios = require('../json/usuarios.json');
        let res = [];
        for (let i = 0; i < usuarios.length; i++) {
            let ediciones = JSON.parse(JSON.stringify(usuarios[i].ediciones));
            for (let j = 0; j < ediciones.length; j++) {
                let element = JSON.parse(JSON.stringify(ediciones[j]));
                if(element.nombre === nom_edicion)
                {
                    let user = JSON.parse(JSON.stringify(usuarios[i]));
                    user.ediciones = JSON.parse(JSON.stringify(element));
                    res.push(user);
                }
            }            
        }
        return res;
    }

    getDocentesInstituto = (nom_instituto) => {
        let usuarios = require('../json/usuarios.json');
        let res = [];
        for (let i = 0; i < usuarios.length; i++) {
            let element = usuarios[i];
            if(element.hasOwnProperty('instituto'))
            {
                if(element.instituto === nom_instituto)
                {
                    res.push(JSON.parse(JSON.stringify(element)));
                }
            }
            
        }
        return res;
    }    

    getPrueba = () => {
        const response = axios.get('http://localhost:8080/edext-server-web/test')
        .then((response) => {
            console.log('response: ', response.data);
        });
    }
}

export default ApiRequest