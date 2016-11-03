package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.databinding.ObservableList;

import java.util.ArrayList;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class HomeFragmentViewModel {

    private ArrayList<Series> recommerdByFriends = new ArrayList<>();
    private ArrayList<Series> newSeries = new ArrayList<>();
    private ArrayList<Series> watchList = new ArrayList<>();
    private ArrayList<Series> featured = new ArrayList<>();

    public HomeFragmentViewModel() {

        Series familie = new Series();
        familie.setName("Familie");
        familie.setPoster_path("http://static1.vtm.vmmacdn.be/sites/vtm.be/files/program/image/2016/08/familie-2016-vierkantlogo.jpg");
        recommerdByFriends.add(familie);

        Series thuis = new Series();
        thuis.setName("Thuis");
        thuis.setPoster_path("http://3.bp.blogspot.com/-TZQezJVPjDs/VmEz3-1TF1I/AAAAAAAACLc/AgkkWuTmNao/s1600/THUIS.jpg");
        recommerdByFriends.add(thuis);

        Series bevergem = new Series();
        bevergem.setName("Bevergem");
        bevergem.setPoster_path("https://s.s-bol.com/imgbase0/imagebase3/large/FC/8/6/4/6/9200000049886468.jpg");
        recommerdByFriends.add(bevergem);

        Series fcdeKampioenen = new Series();
        fcdeKampioenen.setName("F.C. De Kampioenen");
        fcdeKampioenen.setPoster_path("https://images.toychamp.be/highres/00740029/00740029_008.jpg");
        recommerdByFriends.add(fcdeKampioenen);

        Series eigenkweek = new Series();
        eigenkweek.setName("Eigen Kweek");
        eigenkweek.setPoster_path("https://www.standaardboekhandel.be/images/5413952189474.img?img=img/h42/h82/h00/h00/1674039.jpg");
        recommerdByFriends.add(eigenkweek);
    }

    public ArrayList<Series> getRecommerdByFriends() {
        return recommerdByFriends;
    }

    public void setRecommerdByFriends(ArrayList<Series> recommerdByFriends) {
        this.recommerdByFriends = recommerdByFriends;
    }

    public ArrayList<Series> getNewSeries() {
        return newSeries;
    }

    public void setNewSeries(ArrayList<Series> newSeries) {
        this.newSeries = newSeries;
    }

    public ArrayList<Series> getWatchList() {
        return watchList;
    }

    public void setWatchList(ArrayList<Series> watchList) {
        this.watchList = watchList;
    }

    public ArrayList<Series> getFeatured() {
        return featured;
    }

    public void setFeatured(ArrayList<Series> featured) {
        this.featured = featured;
    }
}
