package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.databinding.FragmentSearchBinding;
import nmct.jaspernielsmichielhein.watchfriends.viewmodel.SearchFragmentViewModel;

public class SearchFragment extends Fragment {
    private static final String ARG_QUERY = "nmct.jaspernielsmichielhein.watchfriends.searchquery";

    private SearchFragmentViewModel searchFragmentViewModel;

    private String query = "";

    public SearchFragment() { }

    public static SearchFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            query = getArguments().getString(ARG_QUERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        searchFragmentViewModel = new SearchFragmentViewModel(getActivity(), fragmentSearchBinding, query);
        return fragmentSearchBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        searchFragmentViewModel.search();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}