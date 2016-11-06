package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.HomeFragmentViewModel;

public class HomeFragment extends Fragment {
    private HomeFragmentViewModel homeFragmentViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        fragmentHomeBinding.rvRecommended.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentHomeBinding.rvRecommended.setItemAnimator(new DefaultItemAnimator());
        homeFragmentViewModel = new HomeFragmentViewModel(getActivity(), fragmentHomeBinding);

        //RecyclerView rvRecommended = (RecyclerView) super.findViewById(R.layout.rvRecommended);

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        homeFragmentViewModel.generateFakeData();
    }
}