package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeasonFragmentViewModel;

public class SeasonFragment extends Fragment {
    private static final String ARG_seriesName = "nmct.jaspernielsmichielhein.watchfriends.seriesName";
    private static final String ARG_seriesId = "nmct.jaspernielsmichielhein.watchfriends.seriesId";
    private static final String ARG_seasonNumber = "nmct.jaspernielsmichielhein.watchfriends.seasonNumber";

    private SeasonFragmentViewModel seasonFragmentViewModel;

    private Season season = null;

    private String seriesName = "";
    private int seriesId = 0;
    private int seasonNumber = 0;

    public SeasonFragment() { }

    public static SeasonFragment newInstance(String seriesName, int seriesId, int seasonNumber) {
        Bundle args = new Bundle();
        args.putString(ARG_seriesName, seriesName);
        args.putInt(ARG_seriesId, seriesId);
        args.putInt(ARG_seasonNumber, seasonNumber);

        SeasonFragment fragment = new SeasonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            seriesName = arguments.getString(ARG_seriesName);
            seriesId = arguments.getInt(ARG_seriesId);
            seasonNumber = arguments.getInt(ARG_seasonNumber);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSeasonBinding fragmentSeasonBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_season, container, false);
        seasonFragmentViewModel = new SeasonFragmentViewModel(getActivity(), fragmentSeasonBinding, seriesName, seriesId, seasonNumber);
        return fragmentSeasonBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seasonFragmentViewModel.loadSeason();
    }
}