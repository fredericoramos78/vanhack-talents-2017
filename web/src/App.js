import React, { Component }  from 'react';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import {Container} from 'reactstrap';

// Styles
import 'font-awesome/css/font-awesome.min.css';
import 'simple-line-icons/css/simple-line-icons.css';

// Import Main styles for this application & temp fix for reactstrap
import '../scss/style.scss'
import '../scss/core/_dropdown-menu-right.scss';
// Import Billcheckout & other specific styles
import '../scss/billcheckout.scss';
import '../node_modules/react-big-calendar/lib/css/react-big-calendar.css';

// App basic structure
import Header from './views/Template/Header/';
import Footer from './views/Template/Footer/';

// Layout components
import Home from './views/Home/';
import SearchResults from './views/Search/';
import RulesList from './views/Rules/';
// Other views
import Page404 from './views/Pages/Page404/';
import Page500 from './views/Pages/Page500/';

// Initializing calendar
import BigCalendar from 'react-big-calendar';
import moment from 'moment';

BigCalendar.setLocalizer(
    BigCalendar.momentLocalizer(moment)
);



class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false
        };
    }


    handleAuthentication() {
        this.setState({isLoggedIn: true});
    }

    handleLogout() {
        this.setState({isLoggedIn: false});
    }

    render() {
        return (
            <Router>
                <div className="app">
                    <Header onLogout={(e) => this.handleLogout(e)}/>
                    <div className="app-body">
                        <main className="main">
                            <Container fluid>
                                <Switch>
                                    <Route exact path="/home" component={Home}/>
                                    <Route exact path="/search" component={SearchResults}/>
                                    <Route exact path="/rules" component={RulesList}/>
                                    // error pages
                                    <Route exact path="/404" name="Page 404" component={Page404}/>
                                    <Route exact path="/500" name="Page 500" component={Page500}/>
                                    <Redirect to="/home"/>
                                </Switch>
                            </Container>
                        </main>
                    </div>
                    <Footer/>
                </div>
            </Router>
        );
    }
}

export default App;
