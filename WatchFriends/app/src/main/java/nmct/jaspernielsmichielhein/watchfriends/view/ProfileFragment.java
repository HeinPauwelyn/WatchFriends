package nmct.jaspernielsmichielhein.watchfriends.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentProfileBinding;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.ProfileFragmentViewModel;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SeasonFragmentViewModel;

public class ProfileFragment extends Fragment {
    private static final String ARG_series = "nmct.jaspernielsmichielhein.watchfriends.series";

    private ProfileFragmentViewModel profileFragmentViewModel;
    private FragmentProfileBinding fragmentProfileBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        fragmentProfileBinding.rvWatchlist.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentProfileBinding.rvWatchlist.setItemAnimator(new DefaultItemAnimator());
        profileFragmentViewModel = new ProfileFragmentViewModel(getActivity(), fragmentProfileBinding);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        profileFragmentViewModel.loadUser();
        profileFragmentViewModel.generateFakeDate();
    }
}
