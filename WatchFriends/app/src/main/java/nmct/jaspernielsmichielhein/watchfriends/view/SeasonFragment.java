package nmct.jaspernielsmichielhein.watchfriends.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeasonFragmentViewModel;

public class SeasonFragment extends Fragment {
    private SeasonFragmentViewModel seasonFragmentViewModel;

    public SeasonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSeasonBinding fragmentSeasonBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_season, container, false);
        seasonFragmentViewModel = new SeasonFragmentViewModel(getActivity(), fragmentSeasonBinding, 63174, 1);
        return fragmentSeasonBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seasonFragmentViewModel.loadSeason();
    }
}