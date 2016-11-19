package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.ImageListener;


import nmct.jaspernielsmichielhein.watchfriends.adapter.SeriesListAdapter;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiMovieDbHelper;
import nmct.jaspernielsmichielhein.watchfriends.model.MediaItem;
import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentHomeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.HomeFragmentViewModel;
import rx.functions.Action1;

public class HomeFragment extends Fragment implements HomeFragmentViewModel.ISeriesAddedListener {

    private HomeFragmentViewModel homeFragmentViewModel;
    private FragmentHomeBinding fragmentHomeBinding;
    private Interfaces.headerChangedListener listener;
    private Interfaces.onSeriesSelectedListener lstSeries;
    private Context context;

    public HomeFragment() { }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getBaseContext();
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };*/

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeFragmentViewModel = new HomeFragmentViewModel(getActivity(), fragmentHomeBinding, this);

        fragmentHomeBinding.rvLists.setAdapter(new SeriesListAdapter(homeFragmentViewModel.getSeriesLists(), context, lstSeries));
        fragmentHomeBinding.rvLists.setLayoutManager(new LinearLayoutManager(context));
        fragmentHomeBinding.rvLists.setItemAnimator(new DefaultItemAnimator());

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        homeFragmentViewModel.getData();

        if (listener != null) {
            listener.collapseToolbar();
            listener.setTitle(getResources().getString(R.string.app_name));
            listener.getActionButton().setVisibility(View.GONE);
        }
    }

    @Override
    public void updateLists(ObservableArrayList<SeriesList> seriesLists) {

        fragmentHomeBinding.rvLists.setAdapter(new SeriesListAdapter(seriesLists, context, lstSeries));
    }

    @Override
    public void updateCarousel(final ObservableArrayList<Series> series) {
        if (series != null && fragmentHomeBinding != null) {
            fragmentHomeBinding.cvCarousel.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(final int position, ImageView imageView) {

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Integer id = Integer.valueOf(v.getTag().toString());

                            ApiHelper.subscribe(ApiMovieDbHelper.getMoviedbServiceInstance().getSeries(id), new Action1<Series>() {
                                @Override
                                public void call(Series series) {
                                    ((MainActivity)getActivity()).onSeriesSelected(series);
                                }
                            });
                        }
                    });

                    imageView.setTag(series.get(position).getId());
                    Picasso.with(imageView.getContext()).load(series.get(position).getFullBackdrop_path()).into(imageView);
                }
            });
            fragmentHomeBinding.cvCarousel.setPageCount(series.size());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interfaces.headerChangedListener && context instanceof Interfaces.onSeriesSelectedListener) {
            listener = (Interfaces.headerChangedListener) context;
            lstSeries = (Interfaces.onSeriesSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Interfaces.headerChangedListener && activity instanceof Interfaces.onSeriesSelectedListener) {
            listener = (Interfaces.headerChangedListener) activity;
            lstSeries = (Interfaces.onSeriesSelectedListener) context;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}