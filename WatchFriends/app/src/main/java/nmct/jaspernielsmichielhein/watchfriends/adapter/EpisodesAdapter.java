package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodesAdapter extends ArrayAdapter<Episode> implements View.OnClickListener {
    private Interfaces.onEpisodeSelectedListener mListener;

    private Context context;

    private boolean watched = false;

    public EpisodesAdapter(Context context, ListView listView) {
        super(context, R.layout.row_episode);
        this.context = context;
        mListener = (Interfaces.onEpisodeSelectedListener) context;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Episode selectedEpisode = getItem(position);
                if (selectedEpisode != null) {
                    mListener.onEpisodeSelected(selectedEpisode);
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowEpisodeBinding rowEpisodeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_episode, parent, false);
        Episode episode = getItem(position);
        rowEpisodeBinding.setEpisode(episode);

        ImageButton imgViewed = (ImageButton) rowEpisodeBinding.getRoot().findViewById(R.id.imgViewed);
        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
        }
        imgViewed.setOnClickListener(this);

        return rowEpisodeBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        ImageButton imgViewed = (ImageButton) v;
        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
            Snackbar.make(v, "Marked episode as not watched", Snackbar.LENGTH_LONG).show();
        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
            Snackbar.make(v, "Marked episode as watched", Snackbar.LENGTH_LONG).show();
        }
        watched = !watched;
    }
}