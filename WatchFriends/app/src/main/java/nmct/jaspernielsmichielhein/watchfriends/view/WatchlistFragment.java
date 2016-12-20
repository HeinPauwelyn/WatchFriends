package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.WatchlistFragmentViewModel;

public class WatchlistFragment extends Fragment {

    private WatchlistFragmentViewModel watchlistFragmentViewModel;

    private ObservableArrayList<Episode> episodes;

    public WatchlistFragment() {
        // Required empty public constructor
    }

    public static WatchlistFragment newInstance() {
        WatchlistFragment fragment = new WatchlistFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        watchlistFragmentViewModel = new WatchlistFragmentViewModel(getActivity(), episodes);
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        watchlistFragmentViewModel.loadEpisodes();
    }
}
