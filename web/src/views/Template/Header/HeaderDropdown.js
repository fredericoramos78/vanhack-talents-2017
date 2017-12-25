import React, { Component } from 'react';
import {
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Dropdown
} from 'reactstrap';

import Login from '../../Login';
import Register from '../../Login/Register';

class HeaderDropdown extends Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoggedIn: false,
            dropdownOpen: false,
            loginModalOpen: false,
            registerModalOpen: false
        };
        this.toggleDropdown = this.toggleDropdown.bind(this);
        this.toggleLoginModal = this.toggleLoginModal.bind(this);
        this.toggleRegisterModal = this.toggleRegisterModal.bind(this);
    }

    toggleLoginModal() {
        this.setState({
            loginModalOpen: !this.state.loginModalOpen
        });
    }

    toggleRegisterModal() {
        this.setState({
            registerModalOpen: !this.state.registerModalOpen
        });
    }

    toggleDropdown() {
        this.setState({
            dropdownOpen: !this.state.dropdownOpen
        });
    }

    dropAccnt() {
        if (this.state.isLoggedIn) {
            return (
                <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggleDropdown}>
                    <DropdownToggle nav>
                        <div>
                            <i className="icon-user icons font-2xl d-block mt-4 header-user"></i>
                        </div>
                    </DropdownToggle>
                    <DropdownMenu right>
                        <DropdownItem><i className="fa fa-wrench"></i> Preferências</DropdownItem>
                        <DropdownItem>
                            <a href="javascript:void(0)" onClick={this.props.onLogout}>
                                <i className="fa fa-lock"></i> Sair
                            </a>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>
            );
        } else {
            return (
                <div>
                    <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggleDropdown}>
                        <DropdownToggle nav>
                            <div>
                                <i className="icon-user icons font-2xl d-block mt-4 header-user"></i>
                            </div>
                        </DropdownToggle>
                        <DropdownMenu right>
                            <DropdownItem>
                                <a href="javascript:void(0)" onClick={this.toggleLoginModal}>
                                    <i className="fa fa-unlock"></i> Login
                                </a>
                            </DropdownItem>
                            <DropdownItem>
                                <a href="javascript:void(0)" onClick={this.toggleRegisterModal}>
                                    <i className="fa fa-user"></i> Registrar
                                </a>
                            </DropdownItem>
                        </DropdownMenu>
                    </Dropdown>
                    <Login isOpen={this.state.loginModalOpen}
                           handleOnLogin={this.toggleLoginModal}
                           handleModalDismiss={this.toggleLoginModal}/>
                    <Register isOpen={this.state.registerModalOpen}
                              handleOnRegister={this.toggleRegisterModal}
                              handleModalDismiss={this.toggleRegisterModal}/>
                </div>
            );
        }
    }

    render() {
        const {attributes} = this.props;
        return (
            this.dropAccnt()
        );
    }
}

export default HeaderDropdown;
