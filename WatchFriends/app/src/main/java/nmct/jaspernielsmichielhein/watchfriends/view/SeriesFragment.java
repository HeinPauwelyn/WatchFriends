package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeriesFragmentViewModel;

public class SeriesFragment extends Fragment {
    private static final String ARG_series = "nmct.jaspernielsmichielhein.watchfriends.series";

    private SeriesFragmentViewModel seriesFragmentViewModel;

    public SeriesFragment() {
        // Required empty public constructor
    }

    public static SeriesFragment newInstance(int seriesId) {
        Bundle args = new Bundle();
        args.putInt(ARG_series, seriesId);

        SeriesFragment fragment = new SeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int series = 0;
        Bundle arguments = getArguments();
        if (arguments != null) {
            series = arguments.getInt(ARG_series);
        }
        FragmentSeriesBinding fragmentSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false);
        seriesFragmentViewModel = new SeriesFragmentViewModel(getActivity(), fragmentSeriesBinding, series);
        return fragmentSeriesBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seriesFragmentViewModel.loadSeries();
    }
}