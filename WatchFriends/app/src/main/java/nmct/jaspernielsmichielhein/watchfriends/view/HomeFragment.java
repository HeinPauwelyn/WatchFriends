package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.HomeFragmentViewModel;

public class HomeFragment extends Fragment {

    private HomeFragmentViewModel homeFragmentViewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeFragmentViewModel = new HomeFragmentViewModel(fragmentHomeBinding, 63174, 2, 2);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
