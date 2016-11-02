package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import java.util.ArrayList;

import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class HomeFragmentViewModel {

    private ArrayList<Series> recommerdFriends;
    private ArrayList<Series> newSersies;
    private ArrayList<Series> watchList;

    public ArrayList<Series> getRecommerdFriends() {
        return recommerdFriends;
    }

    public void setRecommerdFriends(ArrayList<Series> recommerdFriends) {
        this.recommerdFriends = recommerdFriends;
    }

    public ArrayList<Series> getNewSersies() {
        return newSersies;
    }

    public void setNewSersies(ArrayList<Series> newSersies) {
        this.newSersies = newSersies;
    }

    public ArrayList<Series> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<Series> watchList) {
        this.watchList = watchList;
    }
}
