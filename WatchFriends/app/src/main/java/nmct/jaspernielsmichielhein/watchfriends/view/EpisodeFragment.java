package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.EpisodeFragmentViewModel;

public class EpisodeFragment extends Fragment {
    private static final String ARG_episode = "nmct.jaspernielsmichielhein.watchfriends.episode";

    private EpisodeFragmentViewModel episodeFragmentViewModel;

    public EpisodeFragment() { }

    public static EpisodeFragment newInstance(Episode episode) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_episode, episode);

        EpisodeFragment fragment = new EpisodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /* todo gebruik onCreate voor argument-waarden
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int series = 0, season = 0, episode = 0;
        Bundle arguments = getArguments();
        if (arguments != null) {
            series = arguments.getInt(ARG_series);
            season = arguments.getInt(ARG_season);
            episode = arguments.getInt(ARG_episode);
        }
    }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Episode episode = null;
        Bundle arguments = getArguments();
        if (arguments != null) {
            episode = arguments.getParcelable(ARG_episode);
            episode.makeImage_uri();
            episode.makeShortcode();
        }
        FragmentEpisodeBinding fragmentEpisodeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode, container, false);
        episodeFragmentViewModel = new EpisodeFragmentViewModel(getActivity(), fragmentEpisodeBinding, episode);
        //episodeFragmentViewModel = new EpisodeFragmentViewModel(getActivity(), fragmentEpisodeBinding, 63174, 1, 10);
        return fragmentEpisodeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        episodeFragmentViewModel.loadEpisode();
    }
}