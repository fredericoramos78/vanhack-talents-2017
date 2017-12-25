import React, {Component} from 'react';
import { Row, Col } from 'reactstrap';

import SearchForTopic from '../Home/SearchForTopic';
import ResultsList from './ResultsList';

import NewTopic from '../Topic/NewTopic';

class SearchResults extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoggedIn: true,
            tooltipOpen: false,
            newTopicModalOpen: false
        };
        this.toggleTooltip = this.toggleTooltip.bind(this);
        this.toggleNewTopicModal = this.toggleNewTopicModal.bind(this);
    }

    toggleTooltip() {
        this.setState({
            tooltipOpen: !this.state.tooltipOpen
        });
    }

    toggleNewTopicModal() {
        this.setState({
            newTopicModalOpen: !this.state.newTopicModalOpen
        });
    }

    render() {
        return (
            <div>
                <div className="animated fadeIn">
                    <Row className="search-and-ask-toolbar">
                        <Col xs="12" sm={{ size: 8, offset: 2 }}>
                            <SearchForTopic/>
                        </Col>
                    </Row>
                    <Row className="search-and-ask-toolbar">
                        <Col xs="12" sm={{ size: 8, offset: 2 }}>
                            <ResultsList handleOpenNewTopic={this.toggleNewTopicModal}/>
                        </Col>
                    </Row>
                </div>
                <NewTopic isOpen={this.state.newTopicModalOpen}
                          handleOnNewTopic={this.toggleNewTopicModal}
                          handleModalDismiss={this.toggleNewTopicModal}/>
            </div>
        );
    }
}

export default SearchResults;
