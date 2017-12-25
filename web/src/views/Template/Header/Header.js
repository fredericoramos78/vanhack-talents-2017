import React, {Component} from 'react';
import {
    Nav,
    NavbarBrand,
} from 'reactstrap';
import HeaderDropdown from './HeaderDropdown';

class Header extends Component {

    constructor(props) {
        super(props);
    }

    sidebarToggle(e) {
        e.preventDefault();
        document.body.classList.toggle('sidebar-hidden');
    }

    sidebarMinimize(e) {
        e.preventDefault();
        document.body.classList.toggle('sidebar-minimized');
    }

    mobileSidebarToggle(e) {
        e.preventDefault();
        document.body.classList.toggle('sidebar-mobile-show');
    }

    render() {
        return (
            <header className="app-header navbar">
                <NavbarBrand href="/#home"></NavbarBrand>
                <h1>My ReactJS Forum</h1>
                <Nav className="ml-auto" navbar>
                    <HeaderDropdown onLogout={this.props.onLogout}/>
                </Nav>
            </header>
        );
    }
}

export default Header;
