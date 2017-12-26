import React, {Component} from 'react';
import {Card, CardBody, CardTitle, Row, Col, Button } from 'reactstrap';
import { Link } from 'react-router-dom';

import AjaxInterface from '../Utils/AjaxInterface';

import SingleComment from './SingleComment';

class TopicHistory extends Component {


    constructor(props) {
        super(props);
        this.state = {
            topicInfo: undefined,
            comments: []
        };
    }

    componentWillMount() {
        const resourceUrl = 'api/topics/' + this.props.match.params.topicId + '/info.json';
        const self = this;
        AjaxInterface.get(resourceUrl)
            .then(function(response) {
                self.setState({
                    topicInfo: response.data,
                });
            });
    }

    renderComments() {
        if (this.state.comments.length === 0) {
            return (<h4>No comment yet!</h4>);
        } else {
            return this.state.comments.map((eachComment) =>
                <SingleComment avatarId={eachComment.user.avatarId}
                               emailAddress={eachComment.user.emailAddress}
                               fullName={eachComment.user.fullName}
                               commentTimestamp={eachComment.createdAt}
                               content={eachComment.content}>
                </SingleComment>
            );
        }
    }

    renderCommentButton() {
        if (this.props.isLoggedIn) {
            return (<Button className="bg-primary float-right">Comment it!</Button>);
        }
        return;
    }

    render() {
        if (this.state.topicInfo === undefined) {
            return(<h4>Topic #{this.props.match.params.topicId} does not exist.</h4>);
        } else {
            return (
                <div className="animated fadeIn">
                    <Row className="search-and-ask-toolbar">
                        <Col xs="12" sm={{ size: 8, offset: 2 }}>
                            <Card>
                                <CardBody>
                                    <CardTitle>{this.state.topicInfo.title}
                                        <Link to="/home">
                                            <Button className="bg-secondary float-right last-inline">Home</Button>
                                        </Link>
                                        {this.renderCommentButton()}
                                    </CardTitle>
                                    <div className="clearfix">
                                        <div className="py-3 pb-5 mr-3 float-left">
                                            <div className="avatar">
                                                <img src={"/img/avatars/"+this.state.topicInfo.owner.avatarId+".jpg"}
                                                     className="img-avatar"
                                                     alt={this.state.topicInfo.owner.emailAddress}/>
                                                <span className="avatar-status badge-success"></span>
                                            </div>
                                        </div>
                                        <div>
                                            <small className="text-muted">
                                                <strong>{this.state.topicInfo.owner.fullName}</strong>
                                            </small>
                                            <br/>
                                            <small className="text-muted">{this.state.topicInfo.question}</small>
                                        </div>
                                    </div>
                                    <hr/>
                                    {this.renderComments()}
                                    <div>
                                        <Link to="/home">
                                            <Button className="bg-secondary float-right">Home</Button>
                                        </Link>
                                    </div>
                                </CardBody>
                            </Card>
                        </Col>
                    </Row>
                </div>
            );
        }
    }
}

export default TopicHistory;
