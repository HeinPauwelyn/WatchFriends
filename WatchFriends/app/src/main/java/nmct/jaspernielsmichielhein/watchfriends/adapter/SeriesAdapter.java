package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.PosterSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {
    public Interfaces.onSeriesSelectedListener mListener;

    private Context context;
    private ObservableArrayList<Series> series = null;

    public SeriesAdapter(ObservableArrayList<Series> series, Context context) {
        this.context = context;
        this.series = series;
        mListener = (Interfaces.onSeriesSelectedListener) context;
    }

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PosterSeriesBinding posterSeriesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.poster_series, parent, false);
        SeriesAdapter.SeriesViewHolder seriesViewHolder = new SeriesAdapter.SeriesViewHolder(posterSeriesBinding);
        return seriesViewHolder;
    }

    @Override
    public void onBindViewHolder(SeriesViewHolder holder, int position) {
        Series seriesObject = series.get(position);
        holder.getPosterSeriesBinding().setSeries(seriesObject);
        holder.getPosterSeriesBinding().executePendingBindings();

        Picasso.with(context).load(seriesObject.getFullPoster_path()).into(holder.imgSeriesPoster);
        //holder.teName.setText(seriesObject.getName());
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final PosterSeriesBinding posterSeriesBinding;

        public final ImageView imgSeriesPoster;

        public SeriesViewHolder(PosterSeriesBinding posterSeriesBinding) {
            super(posterSeriesBinding.getRoot());
            this.posterSeriesBinding = posterSeriesBinding;
            posterSeriesBinding.getRoot().setOnClickListener(this);

            imgSeriesPoster = (ImageView) posterSeriesBinding.getRoot().findViewById(R.id.imgSeriesPoster);
        }

        public PosterSeriesBinding getPosterSeriesBinding() {
            return posterSeriesBinding;
        }

        @Override
        public void onClick(View v) {
            Series selectedSeries = series.get(getAdapterPosition());
        }
    }
}