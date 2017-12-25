import React, {Component} from 'react';
import Moment from 'react-moment';

class Footer extends Component {

    render() {
        const TODAY = new Date();
        return (
            <footer className="app-footer">
                <span><a href="http://www.auster.com.br">FRAMOS</a> &copy;<Moment format="YYYY">{TODAY}</Moment>
                </span>
                <span className="ml-auto">Powered by <a href="http://coreui.io">CoreUI</a></span>
            </footer>
        )
    }
}

export default Footer;
