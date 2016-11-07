package nmct.jaspernielsmichielhein.watchfriends.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
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

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentProfileBinding profileFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        profileFragmentViewModel = new ProfileFragmentViewModel(getActivity(), profileFragmentBinding);
        return profileFragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        profileFragmentViewModel.loadUser();
    }
}
