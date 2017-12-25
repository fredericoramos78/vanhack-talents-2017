import React, {Component} from 'react';
import { Button, Input, InputGroup, InputGroupAddon, Form, FormGroup, Tooltip } from 'reactstrap';

import { Route } from 'react-router-dom'


class SearchForTopic extends Component {

    constructor(props) {
        super(props);

        this.state = {
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
            <Form>
                <FormGroup>
                    <InputGroup id="searchForQuestionsArea" className="mb-3">
                        <Input id="searchForQuestionsInput" type="text" placeholder="what are you looking for?"/>
                        <InputGroupAddon>
                            <Route render={({ history }) => (
                                <a id="searchForQuestionsButton" href="javascript:void(0)" color="primary"
                                   onClick={() => { history.push('/search') }}>
                                <i className="fa fa-search"></i>
                                </a>
                            )} />
                        </InputGroupAddon>
                    </InputGroup>
                    <Tooltip placement="top" target="searchForQuestionsArea"
                             isOpen={this.state.tooltipOpen} toggle={this.toggleTooltip}>
                        Type some keywords and see if we already have some topics about it.
                    </Tooltip>
                </FormGroup>
            </Form>
        );
    }
}

export default SearchForTopic;
