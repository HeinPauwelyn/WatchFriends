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

    private Episode episode = null;

    public EpisodeFragment() { }

    public static EpisodeFragment newInstance(Episode episode) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_episode, episode);

        EpisodeFragment fragment = new EpisodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            episode = arguments.getParcelable(ARG_episode);
            episode.makeImage_uri();
            episode.makeShortcode();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEpisodeBinding fragmentEpisodeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode, container, false);
        episodeFragmentViewModel = new EpisodeFragmentViewModel(getActivity(), fragmentEpisodeBinding, episode);
        return fragmentEpisodeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        episodeFragmentViewModel.loadEpisode();
    }
}