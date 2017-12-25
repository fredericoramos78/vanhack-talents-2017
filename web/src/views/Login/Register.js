import React, {Component} from 'react';
import { Row, Col, Button, Input, InputGroup, InputGroupAddon, Modal, ModalBody } from 'reactstrap';

class Register extends Component {

    render() {
        return (
            <div>
                <Modal isOpen={this.props.isOpen} className={this.props.className}>
                    <ModalBody>
                        <Row className="justify-content-center">
                            <Col md="10">
                                <h1>Registration</h1>
                                <br/>
                                <InputGroup className="mb-3">
                                    <InputGroupAddon><i className="icon-user"></i></InputGroupAddon>
                                    <Input type="text" placeholder="me@company.com"/>
                                </InputGroup>
                                <InputGroup className="mb-4">
                                    <InputGroupAddon><i className="icon-lock"></i></InputGroupAddon>
                                    <Input type="password" placeholder="password"/>
                                </InputGroup>
                                <InputGroup className="mb-4">
                                    <InputGroupAddon><i className="icon-lock"></i></InputGroupAddon>
                                    <Input type="password" placeholder="retype your password"/>
                                </InputGroup>
                                <Row>
                                    <Col xs={{size: 6, offset: 6}} className="text-right">
                                        <a href="javascript:void(0)" onClick={() => this.props.handleModalDismiss()}>fechar</a>
                                        &nbsp;
                                        <Button color="primary" className="px-4"
                                                onClick={() => this.props.handleOnRegister()}>Login
                                        </Button>
                                    </Col>
                                </Row>
                            </Col>
                        </Row>
                    </ModalBody>
                </Modal>
            </div>
        );
    }
}

export default Register;
