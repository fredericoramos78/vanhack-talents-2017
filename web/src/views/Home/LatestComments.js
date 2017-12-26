import React, {Component} from 'react';
import {Card, CardBody, CardTitle} from 'reactstrap';

import SingleTopic from './SingleTopic';

import AjaxInterface from "../Utils/AjaxInterface";


class LatestComments extends Component {

    constructor(props) {
        super(props);
        this.state = {
            lastCommentedTopics: [],
        };
    }

    componentWillMount() {
        const resourceUrl = 'api/topics/search/latest.json';
        const self = this;
        AjaxInterface.get(resourceUrl)
            .then(function(response) {
                self.setState({
                    lastCommentedTopics: response.data || []
                });
            });
    }

    renderEachItem() {
        if (this.state.lastCommentedTopics === undefined || this.state.lastCommentedTopics.length === 0) {
            return (<h5>There are no topics open yet!</h5>);
        } else {
            return this.state.lastCommentedTopics.map((eachTopic) =>
                <SingleTopic key={eachTopic.id} topicInfo={eachTopic} showCommentsCount={false}/>
            );
        }
    }

    render() {
        return (
            <Card>
                <CardBody>
                    <CardTitle>Latest commented topics</CardTitle>
                    {this.renderEachItem()}
                </CardBody>
            </Card>
        );
    }
}

export default LatestComments;
