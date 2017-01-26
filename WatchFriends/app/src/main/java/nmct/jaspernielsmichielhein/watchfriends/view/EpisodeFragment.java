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
    private static final String ARG_seriesId = "nmct.jaspernielsmichielhein.watchfriends.seriesId";

    private EpisodeFragmentViewModel episodeFragmentViewModel;

    private Episode episode = null;

    public EpisodeFragment() { }

    private int seriesId = 0;

    public static EpisodeFragment newInstance(Episode episode, int seriesId) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_episode, episode);
        args.putInt(ARG_seriesId, seriesId);

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
            seriesId = arguments.getInt(ARG_seriesId);
            episode.initExtraFields();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentEpisodeBinding fragmentEpisodeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode, container, false);
        episodeFragmentViewModel = new EpisodeFragmentViewModel(getActivity(), fragmentEpisodeBinding, episode, seriesId);
        return fragmentEpisodeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        episodeFragmentViewModel.loadEpisode();
    }
}