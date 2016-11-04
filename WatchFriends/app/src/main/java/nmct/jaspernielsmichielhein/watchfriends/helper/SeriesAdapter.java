package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.SeriesPosterBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SeriesAdapter extends ArrayAdapter<Series> {

    public SeriesAdapter(Context ctx) {

        super(ctx, R.layout.series_poster);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SeriesPosterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.series_poster, parent, false);
        binding.setSeries(getItem(position));
        return binding.getRoot();
    }
}
