package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.EpisodeFragmentViewModel;

public class EpisodeFragment extends Fragment {
    private EpisodeFragmentViewModel episodeFragmentViewModel;

    public EpisodeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEpisodeBinding fragmentEpisodeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode, container, false);
        episodeFragmentViewModel = new EpisodeFragmentViewModel(getActivity(), fragmentEpisodeBinding, 63174, 1, 10);
        return fragmentEpisodeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        episodeFragmentViewModel.loadEpisode();
    }
}