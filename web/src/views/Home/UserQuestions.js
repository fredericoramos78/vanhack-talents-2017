import React, {Component} from 'react';
import {Card, CardBody, CardTitle} from 'reactstrap';

import AjaxInterface from "../Utils/AjaxInterface";


class UserQuestions extends Component {

    componentDidMount() {
        var resourceUrl = '/api/topics/user/'+this.props.userId+'/latest.json';
        AjaxInterface.get(resourceUrl)
            .then(function(response) {
                console.log(response);
            });
    }

    render() {
        return (
            <Card>
                <CardBody>
                    <CardTitle>Your questions</CardTitle>

                    <div className="message">
                        <div className="py-3 pb-5 mr-3 float-left">
                            <div className="avatar">
                                <img src={'img/avatars/7.jpg'} className="img-avatar" alt="admin@bootstrapmaster.com"/>
                                <span className="avatar-status badge-success"></span>
                            </div>
                        </div>
                        <div>
                            <small className="text-muted">&nbsp;</small>
                        </div>
                        <div className="text-truncate">
                            <span className="font-weight-bold">Lorem ipsum dolor sit amet</span>
                            <small className="text-muted float-right mt-1">
                                920 <i className="fa fa-comments"></i>
                            </small>
                        </div>
                        <small className="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                            eiusmod
                            tempor incididunt...
                        </small>
                    </div>
                    <hr/>
                    <div className="message">
                        <div className="py-3 pb-5 mr-3 float-left">
                            <div className="avatar">
                                <img src={'img/avatars/7.jpg'} className="img-avatar" alt="admin@bootstrapmaster.com"/>
                                <span className="avatar-status badge-success"></span>
                            </div>
                        </div>
                        <div>
                            <small className="text-muted">&nbsp;</small>
                        </div>
                        <div className="text-truncate">
                            <span className="font-weight-bold">Lorem ipsum dolor sit amet</span>
                            <small className="text-muted float-right mt-1">
                                920 <i className="fa fa-comments"></i>
                            </small>
                        </div>
                        <small className="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                            eiusmod
                            tempor incididunt...
                        </small>
                    </div>
                    <hr/>
                    <div className="message">
                        <div className="py-3 pb-5 mr-3 float-left">
                            <div className="avatar">
                                <img src={'img/avatars/7.jpg'} className="img-avatar" alt="admin@bootstrapmaster.com"/>
                                <span className="avatar-status badge-success"></span>
                            </div>
                        </div>
                        <div>
                            <small className="text-muted">&nbsp;</small>
                        </div>
                        <div className="text-truncate">
                            <span className="font-weight-bold">Lorem ipsum dolor sit amet</span>
                            <small className="text-muted float-right mt-1">
                                920 <i className="fa fa-comments"></i>
                            </small>
                        </div>
                        <small className="text-muted">
                            <strong>21-dec @10:21 [Fulano da Silva]</strong>:
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eius
                        </small>
                    </div>

                </CardBody>
            </Card>
        );
    }
}

export default UserQuestions;
