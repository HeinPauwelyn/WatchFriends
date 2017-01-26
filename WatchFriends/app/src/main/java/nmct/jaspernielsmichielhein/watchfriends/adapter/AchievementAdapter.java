package nmct.jaspernielsmichielhein.watchfriends.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.AchievementBinding;
import nmct.jaspernielsmichielhein.watchfriends.databinding.PosterSeriesBinding;
import nmct.jaspernielsmichielhein.watchfriends.model.Achievement;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;

public class AchievementAdapter  extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private Context context;
    private ObservableArrayList<Achievement> achievements = null;

    public AchievementAdapter(Context context, ObservableArrayList<Achievement> achievements) {
        this.context = context;
        this.achievements = achievements;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AchievementBinding achievementBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.achievement, parent, false);
        AchievementAdapter.AchievementViewHolder achievementViewHolder = new AchievementAdapter.AchievementViewHolder(achievementBinding);
        return achievementViewHolder;
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        holder.getAchievementBinding().setAchievement(achievement);
        holder.getAchievementBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (achievements != null) {
            return achievements.size();
        }
        return 0;
    }

    public class AchievementViewHolder extends RecyclerView.ViewHolder {

        final AchievementBinding achievementBinding;

        public AchievementViewHolder(AchievementBinding achievementBinding) {
            super(achievementBinding.getRoot());
            this.achievementBinding = achievementBinding;
            //posterSeriesBinding.getRoot().setOnClickListener(this);
        }

        public AchievementBinding getAchievementBinding() {
            return achievementBinding;
        }
    }
}