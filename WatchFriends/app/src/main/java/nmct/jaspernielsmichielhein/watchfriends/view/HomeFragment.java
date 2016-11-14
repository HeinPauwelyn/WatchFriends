package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.ImageListener;


import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesListAdapter;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.HomeFragmentViewModel;

public class HomeFragment extends Fragment implements HomeFragmentViewModel.ISeriesAddedToCarouselListener {

    private HomeFragmentViewModel homeFragmentViewModel;
    private FragmentHomeBinding fragmentHomeBinding;

    public HomeFragment() { }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeFragmentViewModel = new HomeFragmentViewModel(getActivity(), fragmentHomeBinding, this);

        fragmentHomeBinding.rvLists.setAdapter(new SeriesListAdapter(homeFragmentViewModel.getSeriesLists(), getActivity().getBaseContext()));
        fragmentHomeBinding.rvLists.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        fragmentHomeBinding.rvLists.setItemAnimator(new DefaultItemAnimator());

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        homeFragmentViewModel.getData();
    }

    @Override
    public void updateCarousel(final ObservableArrayList<MediaItem> mediaItems) {
        if (mediaItems != null && fragmentHomeBinding != null) {
            fragmentHomeBinding.cvCarousel.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {

                    Picasso.with(imageView.getContext()).load(mediaItems.get(position).getFullPoster_path()).into(imageView);
                }
            });
            fragmentHomeBinding.cvCarousel.setPageCount(mediaItems.size());
        }
    }
}