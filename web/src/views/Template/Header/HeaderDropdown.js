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
            userId: -1,
            dropdownOpen: false,
            loginModalOpen: false,
            registerModalOpen: false
        };
        this.toggleDropdown = this.toggleDropdown.bind(this);
        this.toggleLoginModal = this.toggleLoginModal.bind(this);
        this.toggleRegisterModal = this.toggleRegisterModal.bind(this);
    }

    toggleRegisterModal() {
        this.setState({
            registerModalOpen: !this.state.registerModalOpen
        });
    }

    toggleLoginModal() {
        this.setState({
            loginModalOpen: !this.state.loginModalOpen
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
                <DropdownMenu right>
                    <DropdownItem>
                        <a href="javascript:void(0)" onClick={this.props.onLogout}>
                            <i className="fa fa-lock"></i> Logoff
                        </a>
                    </DropdownItem>
                </DropdownMenu>
            );
        } else {
            return (
                <div>
                    <DropdownMenu right>
                        <DropdownItem>
                            <a href="javascript:void(0)" onClick={this.toggleLoginModal}>
                                <i className="fa fa-unlock"></i> Login
                            </a>
                        </DropdownItem>
                        <DropdownItem>
                            <a href="javascript:void(0)" onClick={this.toggleRegisterModal}>
                                <i className="fa fa-user"></i> Register
                            </a>
                        </DropdownItem>
                    </DropdownMenu>
                    <Login isOpen={this.state.loginModalOpen}
                           isLoggedIn={this.state.isLoggedIn}
                           userId={this.state.userId}
                           handleCloseModal={this.toggleLoginModal}/>
                    <Register isOpen={this.state.registerModalOpen}
                              isLoggedIn={this.state.isLoggedIn}
                              userId={this.state.userId}
                              handleCloseModal={this.toggleRegisterModal}/>
                </div>
            );
        }
    }

    render() {
        const {attributes} = this.props;
        return (
            <Dropdown nav isOpen={this.state.dropdownOpen} toggle={this.toggleDropdown}>
                <DropdownToggle nav>
                    <div>
                        <i className="icon-user icons font-2xl d-block mt-4 header-user"></i>
                    </div>
                </DropdownToggle>
                    {this.dropAccnt()}
            </Dropdown>
        );
    }
}

export default HeaderDropdown;
