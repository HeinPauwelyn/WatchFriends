package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.ImageListener;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesAdapter;
import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesListAdapter;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentProfileBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiMovieDbHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.ProfileFragmentViewModel;
import rx.functions.Action1;

public class ProfileFragment extends Fragment {
    private static final String ARG_series = "nmct.jaspernielsmichielhein.watchfriends.series";

    private ProfileFragmentViewModel profileFragmentViewModel;
    private FragmentProfileBinding fragmentProfileBinding;
    private Interfaces.headerChangedListener listener;
    private Context context;

    public ProfileFragment() {    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getBaseContext();

        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        profileFragmentViewModel = new ProfileFragmentViewModel(getActivity(), fragmentProfileBinding);

        fragmentProfileBinding.rvWatchlist.setAdapter(new SeriesAdapter(profileFragmentViewModel.getWatchlist(), context));
        fragmentProfileBinding.rvWatchlist.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false));
        fragmentProfileBinding.rvWatchlist.setItemAnimator(new DefaultItemAnimator());
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        profileFragmentViewModel.getUser();
        profileFragmentViewModel.generateFakeDate();

        if (listener != null) {
            listener.collapseToolbar();
            listener.getActionButton().setVisibility(View.GONE);
        }
    }
}
