package nmct.jaspernielsmichielhein.watchfriends.view;


import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesListAdapter;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentFollowedSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.FollowedSeriesFragmentViewModel;

public class FollowedSeriesFragment extends Fragment implements FollowedSeriesFragmentViewModel.ISeriesAddedListener {

    private FragmentFollowedSeriesBinding fragmentFollowedSeriesBinding;
    private FollowedSeriesFragmentViewModel followedSeriesFragmentViewModel;
    private Context context;

    public FollowedSeriesFragment() {
    }

    public static FollowedSeriesFragment newInstance() {
        return new FollowedSeriesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getBaseContext();

        fragmentFollowedSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_followed_series, container, false);
        followedSeriesFragmentViewModel = new FollowedSeriesFragmentViewModel(getActivity(), fragmentFollowedSeriesBinding, this);

        fragmentFollowedSeriesBinding.rvLists.setAdapter(new SeriesListAdapter(followedSeriesFragmentViewModel.getSeriesList(), context));
        fragmentFollowedSeriesBinding.rvLists.setLayoutManager(new LinearLayoutManager(context));
        fragmentFollowedSeriesBinding.rvLists.setItemAnimator(new DefaultItemAnimator());

        return fragmentFollowedSeriesBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        followedSeriesFragmentViewModel.getData();
    }

    @Override
    public void updateLists(ObservableArrayList<SeriesList> seriesLists) {
        fragmentFollowedSeriesBinding.rvLists.setAdapter(new SeriesListAdapter(seriesLists, context));
    }
}
