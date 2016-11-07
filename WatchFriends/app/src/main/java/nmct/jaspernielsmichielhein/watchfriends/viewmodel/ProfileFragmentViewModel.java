package nmct.jaspernielsmichielhein.watchfriends.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentProfileBinding;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProfileFragmentViewModel extends BaseObservable {

    private final Interfaces.onHeaderChanged mListener;
    private Context context;
    private FragmentProfileBinding fragmentProfileBinding;

    private int userId;

    public ProfileFragmentViewModel(Context context, FragmentProfileBinding fragmentProfileBinding) {
        this.context = context;
        this.fragmentProfileBinding = fragmentProfileBinding;

        this.userId = userId;

        if (context instanceof Interfaces.onHeaderChanged) {
            mListener = (Interfaces.onHeaderChanged) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement onHeaderChanged");
        }
    }

    public void loadUser() {
        final ProfileFragmentViewModel that = this;
        fragmentProfileBinding.setViewmodel(that);
        setHeader();
    }

    private void setHeader() {
        mListener.onSetTitle("UserName");
        //mListener.onSetImage();
        final FloatingActionButton fab = mListener.onGetActionButton();
        fab.setVisibility(View.INVISIBLE);
    }
}