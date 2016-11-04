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
    private static final String ARG_series = "nmct.jaspernielsmichielhein.watchfriends.series";
    private static final String ARG_season = "nmct.jaspernielsmichielhein.watchfriends.season";

    private SeasonFragmentViewModel seasonFragmentViewModel;

    private int seriesId = 0;
    private int seasonNumber = 0;
    private Season season = null;

    public SeasonFragment() {
        // Required empty public constructor
    }

    public static SeasonFragment newInstance(int seriesId, int seasonNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_series, seriesId);
        args.putInt(ARG_season, seasonNumber);

        SeasonFragment fragment = new SeasonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            seriesId = arguments.getInt(ARG_series);
            seasonNumber = arguments.getInt(ARG_season);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSeasonBinding fragmentSeasonBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_season, container, false);
        seasonFragmentViewModel = new SeasonFragmentViewModel(getActivity(), fragmentSeasonBinding, seriesId, seasonNumber);
        return fragmentSeasonBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seasonFragmentViewModel.loadSeason();
    }
}