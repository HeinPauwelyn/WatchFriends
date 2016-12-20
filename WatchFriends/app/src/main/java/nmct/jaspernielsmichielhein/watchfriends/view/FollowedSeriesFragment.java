package nmct.jaspernielsmichielhein.watchfriends.view;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentFollowedSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.FollowedSeriesFragmentViewModel;

public class FollowedSeriesFragment extends Fragment {

    private FragmentFollowedSeriesBinding fragmentFollowedSeriesBinding;
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
        FollowedSeriesFragmentViewModel = new FollowedSeriesFragmentViewModel(context, fragmentFollowedSeriesBinding, this);

        return inflater.inflate(R.layout.fragment_followed_series, container, false);
    }

}
