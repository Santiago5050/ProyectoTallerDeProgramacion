import React, { Component } from 'react';
import '../css/ModalReproducirVideo.css';

class ModalReproducirVideo extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="modal reproductorModal" id="reproductorVideoModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div className="modal-dialog" role="document">
					<div className="modal-content">
                        <div className="modal-header">
							<h5 className="modal-title" id="exampleModalLabel">Video</h5>
							<button type="button" className="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
                        <div className="modal-body">
                            <iframe width="560" height="315" src={ this.props.video } frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default ModalReproducirVideo;