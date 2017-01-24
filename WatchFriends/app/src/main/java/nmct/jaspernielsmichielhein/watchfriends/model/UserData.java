package nmct.jaspernielsmichielhein.watchfriends.model;

import android.databinding.ObservableArrayList;


public class UserData extends User {

    private ObservableArrayList<Achievement> achievements;
    private ObservableArrayList<Follower> followers;
    private ObservableArrayList<Follower> follows;
    private ObservableArrayList<Series> watchlist;

    public ObservableArrayList<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(ObservableArrayList<Follower> followers) {
        this.followers = followers;
    }

    public ObservableArrayList<Follower> getFollows() {
        return follows;
    }

    public void setFollows(ObservableArrayList<Follower> follows) {
        this.follows = follows;
    }

    public ObservableArrayList<Series> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(ObservableArrayList<Series> watchlist) {
        this.watchlist = watchlist;
    }

    public ObservableArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(ObservableArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }
}
