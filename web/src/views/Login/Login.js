import React, {Component} from 'react';
import { Alert, Row, Col, Button, Input, InputGroup, InputGroupAddon, Modal, ModalBody } from 'reactstrap';
import AjaxInterface from "../Utils/AjaxInterface";

class Login extends Component {

    constructor(props) {
        super(props);

        this.state = {
            emailAddress: '',
            password: '',
            errorMessage: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    formIsValid() {
        return this.state.emailAddress.length > 6 &&
               this.state.password.length > 4;
    }

    handleInputChange(event) {
        const value = event.target.value;
        const name = event.target.name;
        this.setState({ [name]: value });
    }

    onLoginClick() {
        const resourceUrl = 'api/auth/login.json';
        const self = this;
        const postForm = { 'emailAddress': this.state.emailAddress,
                           'password': this.state.password };
        // remove any previous error message
        this.setState({ errorMessage: '' });
        AjaxInterface.post(resourceUrl, postForm)
            .then(function(response) {
                if (response.status === 200) {
                    self.setState({ isLoggedIn: true,
                                    userId: response.data.id });
                    self.handleCloseModal();
                }
            })
            .catch(function(error) {
                let errorMessage = error;
                if (error.response) {
                    errorMessage = error.response.data.cause;
                }
                console.log(errorMessage);
                self.setState({errorMessage: errorMessage});
            });
    }

    render() {
        return (
            <div>
                <Modal isOpen={this.props.isOpen} className={this.props.className}>
                    <ModalBody>
                        <Row className="justify-content-center">
                            <Col md="10">
                                <h1>Login</h1>
                                <br/>
                                <InputGroup className="mb-3">
                                    <InputGroupAddon><i className="fa fa-envelope"></i></InputGroupAddon>
                                    <Input name="emailAddress" type="text" placeholder="me@company.com"
                                           value={this.state.emailAddress} onChange={this.handleInputChange}/>
                                </InputGroup>
                                <InputGroup className="mb-4">
                                    <InputGroupAddon><i className="fa fa-lock"></i></InputGroupAddon>
                                    <Input name="password" type="password" placeholder="password"
                                           value={this.state.password} onChange={this.handleInputChange}/>
                                </InputGroup>
                                <Row>
                                    <Col xs="12">
                                        <Alert color="danger" isOpen={this.state.errorMessage !== ''}>
                                            {this.state.errorMessage}
                                        </Alert>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col xs="6">
                                        <Button color="link" className="px-0">Forgot password?</Button>
                                    </Col>
                                    <Col xs="6" className="text-right">
                                        <a href="javascript:void(0)" onClick={() => this.props.handleCloseModal()}>close</a>
                                        &nbsp;
                                        <Button color="primary" className="px-4"
                                                disabled={this.formIsValid() === false}
                                                onClick={() => this.onLoginClick()}>Login
                                        </Button>
                                    </Col>
                                </Row>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs="12" className="text-right">
                                <p className="text-muted version-number">
                                    <small>v1.0.0_20171221</small>
                                </p>
                            </Col>
                        </Row>
                    </ModalBody>
                </Modal>
            </div>
        );
    }
}

export default Login;
