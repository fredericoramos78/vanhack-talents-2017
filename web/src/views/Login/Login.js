import React, {Component} from 'react';
import { Row, Col, Button, Input, InputGroup, InputGroupAddon, Modal, ModalBody } from 'reactstrap';

class Login extends Component {

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
                                    <InputGroupAddon><i className="icon-user"></i></InputGroupAddon>
                                    <Input type="text" placeholder="me@company.com"/>
                                </InputGroup>
                                <InputGroup className="mb-4">
                                    <InputGroupAddon><i className="icon-lock"></i></InputGroupAddon>
                                    <Input type="password" placeholder="password"/>
                                </InputGroup>
                                <Row>
                                    <Col xs="6">
                                        <Button color="link" className="px-0">Forgot password?</Button>
                                    </Col>
                                    <Col xs="6" className="text-right">
                                        <a href="javascript:void(0)" onClick={() => this.props.handleModalDismiss()}>fechar</a>
                                        &nbsp;
                                        <Button color="primary" className="px-4"
                                                onClick={() => this.props.handleOnLogin()}>Login
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
