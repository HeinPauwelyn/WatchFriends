package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeriesFragmentViewModel;

public class SeriesFragment extends Fragment {
    private static final String ARG_series = "nmct.jaspernielsmichielhein.watchfriends.series";

    private SeriesFragmentViewModel seriesFragmentViewModel;

    private int seriesId = 0;
    private Series series = null;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            seriesId = arguments.getInt(ARG_series);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSeriesBinding fragmentSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false);
        seriesFragmentViewModel = new SeriesFragmentViewModel(getActivity(), fragmentSeriesBinding, seriesId);
        return fragmentSeriesBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seriesFragmentViewModel.loadSeries();
    }
}