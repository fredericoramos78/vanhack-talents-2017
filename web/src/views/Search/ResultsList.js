import React, {Component} from 'react';
import {Card, CardBody, CardTitle, Button } from 'reactstrap';


class ResultsList extends Component {

    render() {
        return (
            <Card>
                <CardBody>
                    <CardTitle>Searching results</CardTitle>

                    <div className="message text-center">
                        <h4>Did not find what you were looking for?</h4>
                        <Button className="bg-danger" onClick={this.props.handleOpenNewTopic}>
                            Ask the community!
                        </Button>
                    </div>
                    <hr/>
                    <div>
                        <div className="message clearfix">
                            <div className="py-3 pb-5 mr-3 float-left">
                                <div className="avatar">
                                    <img src={'img/avatars/7.jpg'} className="img-avatar" alt="admin@bootstrapmaster.com"/>
                                    <span className="avatar-status badge-success"></span>
                                </div>
                            </div>
                            <div>
                                <small className="text-muted">Lukasz Holeczek</small>
                                <small className="text-muted float-right mt-1">
                                    1000 <i className="fa fa-comments"></i>
                                </small>
                            </div>
                            <div className="text-truncate font-weight-bold">Lorem ipsum dolor sit amet</div>
                            <small className="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                                eiusmod
                                tempor incididunt...
                            </small>
                        </div>
                        <hr/>
                        <div className="message clearfix">
                            <div className="py-3 pb-5 mr-3 float-left">
                                <div className="avatar">
                                    <img src={'img/avatars/7.jpg'} className="img-avatar" alt="admin@bootstrapmaster.com"/>
                                    <span className="avatar-status badge-success"></span>
                                </div>
                            </div>
                            <div>
                                <small className="text-muted">Lukasz Holeczek</small>
                                <small className="text-muted float-right mt-1">
                                    920 <i className="fa fa-comments"></i>
                                </small>
                            </div>
                            <div className="text-truncate font-weight-bold">Lorem ipsum dolor sit amet</div>
                            <small className="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                                eiusmod
                                tempor incididunt...
                            </small>
                        </div>
                        <hr/>
                        <div className="message clearfix">
                            <div className="py-3 pb-5 mr-3 float-left">
                                <div className="avatar">
                                    <img src={'img/avatars/7.jpg'} className="img-avatar" alt="admin@bootstrapmaster.com"/>
                                    <span className="avatar-status badge-success"></span>
                                </div>
                            </div>
                            <div>
                                <small className="text-muted">Lukasz Holeczek</small>
                                <small className="text-muted float-right mt-1">
                                    800 <i className="fa fa-comments"></i>
                                </small>
                            </div>
                            <div className="text-truncate font-weight-bold">Lorem ipsum dolor sit amet</div>
                            <small className="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
                                eiusmod
                                tempor incididunt...
                            </small>
                        </div>
                    </div>

                </CardBody>
            </Card>
        );
    }
}

export default ResultsList;
