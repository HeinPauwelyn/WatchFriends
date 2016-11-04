package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.PosterSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SeriesAdapter extends ArrayAdapter<Series> {
    public Interfaces.onSeriesSelectedListener<Series> mListener;

    public SeriesAdapter(Context context, ListView listView) {
        super(context, R.layout.poster_series);
        mListener = (Interfaces.onSeriesSelectedListener<Series>) context;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Series selectedSeries = getItem(position);
                if (selectedSeries != null) {
                    mListener.onSeriesSelected(selectedSeries);
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PosterSeriesBinding posterSeriesBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.poster_series, parent, false);
        posterSeriesBinding.setSeries(getItem(position));
        return posterSeriesBinding.getRoot();
    }
}