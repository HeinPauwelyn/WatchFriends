package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.content.Context;
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

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
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
        fragmentHomeBinding.rvRecommended.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentHomeBinding.rvRecommended.setItemAnimator(new DefaultItemAnimator());

        fragmentHomeBinding.rvPopular.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        fragmentHomeBinding.rvPopular.setItemAnimator(new DefaultItemAnimator());
        homeFragmentViewModel = new HomeFragmentViewModel(getActivity(), fragmentHomeBinding, this);

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        homeFragmentViewModel.generateFakeData();
        listener.collapseToolbar();
        listener.setTitle(getResources().getString(R.string.app_name));
        listener.getActionButton().setVisibility(View.GONE);

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

    Interfaces.headerChangedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interfaces.headerChangedListener) {
            listener = ((Interfaces.headerChangedListener) context);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}