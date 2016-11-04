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
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodesAdapter extends ArrayAdapter<Episode> {
    private Interfaces.onItemSelectedListener<Episode> mListener;

    public EpisodesAdapter(Context context, ListView listView) {
        super(context, R.layout.row_episode);
        mListener = (Interfaces.onItemSelectedListener<Episode>) context;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Episode selectedEpisode = getItem(position);
                if (selectedEpisode != null) {
                    mListener.onSelected(selectedEpisode);
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowEpisodeBinding rowEpisodeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_episode, parent, false);
        rowEpisodeBinding.setEpisode(getItem(position));
        return rowEpisodeBinding.getRoot();
    }
}