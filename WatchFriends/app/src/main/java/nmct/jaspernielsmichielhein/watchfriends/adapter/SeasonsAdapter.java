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
import android.widget.ListView;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.RowSeasonBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Season;

public class SeasonsAdapter extends ArrayAdapter<Season> implements View.OnClickListener {
    private Interfaces.onSeasonSelectedListener mListener;

    private Context context;

    private boolean watched = false;

    public SeasonsAdapter(Context context, ListView listView) {
        super(context, R.layout.row_episode);
        this.context = context;
        mListener = (Interfaces.onSeasonSelectedListener) context;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Season selectedSeason = getItem(position);
                if (selectedSeason != null) {
                    mListener.onSeasonSelected(selectedSeason);
                }
            }
        });
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RowSeasonBinding rowSeasonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_season, parent, false);
        Season season = getItem(position);
        //season.initExtraFields();
        rowSeasonBinding.setSeason(season);

        ImageButton imgViewed = (ImageButton) rowSeasonBinding.getRoot().findViewById(R.id.imgViewed);
        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
        }
        imgViewed.setOnClickListener(this);

        return rowSeasonBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        ImageButton imgViewed = (ImageButton) v;
        if (watched) {
            imgViewed.setImageResource(R.drawable.ic_visibility_white_24dp);
            Snackbar.make(v, "Marked Season as not watched", Snackbar.LENGTH_LONG).show();
        } else {
            imgViewed.setImageResource(R.drawable.ic_visibility_off_white_24dp);
            Snackbar.make(v, "Marked Season as watched", Snackbar.LENGTH_LONG).show();
        }
        watched = !watched;
    }
}