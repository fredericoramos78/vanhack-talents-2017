import React, {Component} from 'react';
import {Card, CardBody, CardTitle} from 'reactstrap';

import SingleTopic from './SingleTopic';


import AjaxInterface from "../Utils/AjaxInterface";



class HotTopics extends Component {

    constructor(props) {
        super(props);
        this.state = {
            hotTopics: [],
        };
    }

    componentWillMount() {
        const resourceUrl = 'api/topics/search/hot.json';
        const self = this;
        AjaxInterface.get(resourceUrl)
            .then(function(response) {
                self.setState({
                    hotTopics: response.data || []
                });
            });
    }

    renderEachItem() {
        if (this.state.hotTopics === undefined || this.state.hotTopics.length === 0) {
            return (<h5>There are no topics open yet!</h5>);
        } else {
            return this.state.hotTopics.map((eachTopic) =>
                <SingleTopic key={eachTopic.id} topicInfo={eachTopic} showCommentsCount={true}/>
            );
        }
    }

    render() {
        return (
            <Card>
                <CardBody>
                    <CardTitle>Hot topics</CardTitle>
                    {this.renderEachItem()}
                </CardBody>
            </Card>
        );
    }
}

export default HotTopics;
