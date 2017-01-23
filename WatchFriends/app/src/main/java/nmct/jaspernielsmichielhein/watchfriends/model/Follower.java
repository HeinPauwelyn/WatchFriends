package nmct.jaspernielsmichielhein.watchfriends.model;

import java.util.Date;


public class Follower {
    private String userId;
    private String followingId;
    private Date since;

    public Follower(String userId, String followingId, Date since) {
        this.userId = userId;
        this.followingId = followingId;
        this.since = since;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }
}
