import React, {Component} from 'react';
import Moment from 'react-moment';

class SingleComment extends Component {

    render() {
        return (
            <div>
                <div className="message">
                    <div className="py-3 pb-5 mr-3 float-left">
                        <div className="avatar">
                            <img src={"/img/avatars/"+this.props.avatarId+".jpg"}
                                 className="img-avatar"
                                 alt={this.props.emailAddress}/>
                            <span className="avatar-status badge-success"></span>
                        </div>
                    </div>
                    <div>
                        <small className="text-muted">{this.props.fullName}</small>
                        <small className="text-muted float-right mt-1">
                            <Moment format="DD/MMM/YY [@] HH:mm">
                                {this.props.commentTimestamp}
                            </Moment>
                        </small>
                    </div>
                    <div>
                        <small className="text-muted">{this.props.content}</small>
                    </div>
                </div>
                <hr/>
            </div>
        );
    }
}

export default SingleComment;
