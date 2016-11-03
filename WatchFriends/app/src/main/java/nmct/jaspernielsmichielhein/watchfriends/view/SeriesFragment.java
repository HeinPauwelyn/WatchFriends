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
    private SeriesFragmentViewModel seriesFragmentViewModel;

    public SeriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSeriesBinding fragmentSeriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false);
        seriesFragmentViewModel = new SeriesFragmentViewModel(getActivity(), fragmentSeriesBinding, 63174);
        return fragmentSeriesBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        seriesFragmentViewModel.loadSeries();
    }
}