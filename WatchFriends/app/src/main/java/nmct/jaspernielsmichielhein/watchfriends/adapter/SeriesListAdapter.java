package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowSeriesListBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.SeriesList;

public class SeriesListAdapter extends RecyclerView.Adapter<SeriesListAdapter.SeriesListViewHolder> {

    private Context context = null;
    private ObservableArrayList<SeriesList> seriesLists;

    public SeriesListAdapter(ObservableArrayList<SeriesList> seriesLists, Context context) {
        if(context != null) {
            this.context = context;
        }
        this.seriesLists = seriesLists;
    }

    @Override
    public SeriesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RowSeriesListBinding rowSeriesListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.row_series_list, parent, false);
        SeriesListAdapter.SeriesListViewHolder seriesViewHolder = new SeriesListAdapter.SeriesListViewHolder(rowSeriesListBinding);
        return seriesViewHolder;
    }

    @Override
    public void onBindViewHolder(SeriesListViewHolder holder, int position) {
        SeriesList seriesObject = seriesLists.get(position);
        holder.getRowSeriesListBinding().setSeriesList(seriesObject);
        holder.getRowSeriesListBinding().executePendingBindings();

        holder.getRowSeriesListBinding().rvListItems.setAdapter(new SeriesAdapter(context, seriesLists.get(position).getSeries()));
        holder.getRowSeriesListBinding().rvListItems.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        holder.getRowSeriesListBinding().rvListItems.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public int getItemCount() {
        if (seriesLists != null) {
            return seriesLists.size();
        }

        return 0;
    }

    public class SeriesListViewHolder extends RecyclerView.ViewHolder {

        public final RowSeriesListBinding rowSeriesListBinding;

        public SeriesListViewHolder(RowSeriesListBinding rowSeriesListBinding) {
            super(rowSeriesListBinding.getRoot());
            this.rowSeriesListBinding = rowSeriesListBinding;


            //rowSeriesListBinding.rvListItems.setAdapter(new SeriesAdapter());
        }

        public RowSeriesListBinding getRowSeriesListBinding() {
            return rowSeriesListBinding;
        }
    }
}
