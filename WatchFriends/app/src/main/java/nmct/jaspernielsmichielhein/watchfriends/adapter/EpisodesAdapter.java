package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowEpisodeBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;

public class EpisodesAdapter extends ArrayAdapter<Episode> implements AdapterView.OnItemClickListener {
    public EpisodesAdapter(Context context) {
        super(context, R.layout.row_episode);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowEpisodeBinding rowEpisodeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_episode, parent, false);
        rowEpisodeBinding.setEpisode(getItem(position));
        return rowEpisodeBinding.getRoot();
    }

    /* todo click op item -> episode ophalen -> navigeren naar fragment via listener op mainacitivity */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}