import './Home.css';

import React, {Component} from 'react';
import { Row, Col } from 'reactstrap';

import SearchForTopic from './SearchForTopic';
import HomeTopicsList from './HomeTopicsList';

class Home extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoggedIn: false,
            tooltipOpen: false,
        };
        this.toggleTooltip = this.toggleTooltip.bind(this);
    }

    toggleTooltip() {
        this.setState({
            tooltipOpen: !this.state.tooltipOpen
        });
    }

    render() {
        return (
            <div className="animated fadeIn">
                    <Row className="search-and-ask-toolbar">
                        <Col xs="12" sm={{ size: 8, offset: 2 }}>
                            <SearchForTopic/>
                        </Col>
                    </Row>
                    <HomeTopicsList isLoggedIn={this.state.isLoggedIn}/>
                </div>
        );
    }
}

export default Home;
