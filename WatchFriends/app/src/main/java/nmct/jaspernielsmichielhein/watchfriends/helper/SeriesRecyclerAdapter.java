package nmct.jaspernielsmichielhein.watchfriends.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SeriesRecyclerAdapter extends RecyclerView.Adapter<SeriesRecyclerAdapter.SeriesViewHolder> {

    @Override
    public SeriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SeriesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SeriesViewHolder extends RecyclerView.ViewHolder {

        public SeriesViewHolder(View itemView) {
            super(itemView);
        }
    }
}
