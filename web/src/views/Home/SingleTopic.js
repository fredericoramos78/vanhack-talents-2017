import React, {Component} from 'react';
import Moment from 'react-moment';
import { Link } from 'react-router-dom';

class SingleTopic extends Component {

    renderCommentCountOrLastCommentedAt() {
        const thisTopic = this.props.topicInfo;
        if (this.props.showCommentsCount) {
            return (
                <small className="text-muted float-right mt-1">
                    {thisTopic.commentsCount} <i className="fa fa-comments"></i>
                </small>
            );
        } else {
            return (
                <small className="text-muted float-right mt-1">
                    <Moment format="DD/MMM/YY [@] HH:mm">
                        {thisTopic.latestComment}
                    </Moment>
                </small>
            );
        }
    }

    render() {
        const thisTopic = this.props.topicInfo;
        return (
            <Link to={'/topic/'+thisTopic.id}>
                <div className="message">
                    <div className="py-3 pb-5 mr-3 float-left">
                        <div className="avatar">
                            <img src={"img/avatars/"+thisTopic.owner.avatarId+".jpg"}
                                 className="img-avatar"
                                 alt={thisTopic.owner.emailAddress}/>
                            <span className="avatar-status badge-success"></span>
                        </div>
                    </div>
                    <div>
                        <small className="text-muted">{thisTopic.owner.fullName}</small>
                        {this.renderCommentCountOrLastCommentedAt()}
                    </div>
                    <div className="text-truncate font-weight-bold">
                        {thisTopic.title}
                    </div>
                    <small className="text-muted">{thisTopic.question}</small>
                </div>
                <hr/>
            </Link>
        );
    }
}

export default SingleTopic;
