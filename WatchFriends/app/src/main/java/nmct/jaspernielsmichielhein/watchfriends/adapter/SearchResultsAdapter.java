package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.SearchResultBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiWatchFriendsHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.functions.Action1;

public class SearchResultsAdapter extends ArrayAdapter<Series> {
    private Interfaces.onSeriesSelectedListener mListener;

    private Context context;

    public SearchResultsAdapter(Context context, ListView listView) {
        super(context, R.layout.search_result);
        this.context = context;
        mListener = (Interfaces.onSeriesSelectedListener) context;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Series selectedSeries = getItem(position);
                ApiHelper.subscribe(ApiWatchFriendsHelper.getWatchFriendsServiceInstance().getSeries(selectedSeries.getId()), new Action1<Series>() {
                    @Override
                    public void call(Series returnedSeries) {
                        if (returnedSeries != null) {
                            mListener.onSeriesSelected(returnedSeries);
                        }
                    }
                });
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SearchResultBinding searchResultBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.search_result, parent, false);
        Series series = getItem(position);
        searchResultBinding.setSeries(series);

        ImageView imgSeriesPoster = (ImageView) searchResultBinding.getRoot().findViewById(R.id.imgSeriesPoster);
        Picasso.with(context).load(series.getFullPoster_path()).into(imgSeriesPoster);

        return searchResultBinding.getRoot();
    }
}