package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Contract;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SimilarSeriesAdapter extends ArrayAdapter<Series> {
    private Interfaces.onSeriesSelectedListener mListener;

    private Context context;

    public SimilarSeriesAdapter(Context context, ListView listView) {
        super(context, R.layout.row_series);
        this.context = context;
        mListener = (Interfaces.onSeriesSelectedListener) context;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowSeriesBinding rowSeriesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_series, parent, false);
        rowSeriesBinding.setSeries(getItem(position));

        Series series = getItem(position);
        Uri uri = Uri.parse(Contract.MOVIEDB_IMAGE_BASE_URL + series.getPoster_path());

        ImageView imgPoster = (ImageView) rowSeriesBinding.getRoot().findViewById(R.id.imgPoster);
        Picasso.with(context).load(uri).into(imgPoster);

        return rowSeriesBinding.getRoot();
    }
}
