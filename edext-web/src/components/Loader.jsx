import React, { Component } from 'react'
import PacmanLoader from 'react-spinners/PacmanLoader';
import './../css/Loader.css';

export default class Loader extends Component {
    render() {
        return (
            <div id="loader-animation">
                <div>
                    <PacmanLoader
                        sizeUnit={"px"}
                        size={25}
                        color={'#00E5FF'}
                        loading={true}
                    />
                </div>
                <div id="loader-text">
                    <h4>Loading...</h4>
                </div>
            </div>
        )
    }
}
