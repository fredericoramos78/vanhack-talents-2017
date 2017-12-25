import React, {Component} from 'react';
import { Row, Col } from 'reactstrap';

import HotTopics from './HotTopics';
import LatestComments from "./LatestComments";
import UserQuestions from './UserQuestions';

class HomeTopicsList extends Component {

    componentDidMount() {
        if (this.props.isLoggedIn) {
            let resourceUrl = 'http://localhost:9000/api/topics/user/' + this.props.userId + '/latest.json';
            fetch(resourceUrl, { method: 'GET' })
                .then(function(response) {
                    console.log(response);
                });
        } else {
            let resourceUrl = 'http://localhost:9000/api/topics/search/latest.json';
            fetch(resourceUrl, { method: 'GET' })
                .then(function(response) {
                    console.log(response);
                });
        }
    }

    render() {
        if (this.props.isLoggedIn) {
            return (
                <Row>
                    <Col xs="12" sm={{size: 4, offset: 2}}>
                        <UserQuestions/>
                    </Col>
                    < Col xs="12" sm="4">
                        <HotTopics/>
                    </Col>
                </Row>
            );
        } else {
            return (
                <Row>
                    <Col xs="12" sm={{size: 4, offset: 2}}>
                        <HotTopics/>
                    </Col>
                    <Col xs="12" sm="4">
                        <LatestComments/>
                    </Col>
                </Row>
            );
        }
    }
}

export default HomeTopicsList;
