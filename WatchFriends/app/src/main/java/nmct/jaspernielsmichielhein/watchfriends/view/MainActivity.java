package nmct.jaspernielsmichielhein.watchfriends.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import nmct.jaspernielsmichielhein.watchfriends.R;
import nmct.jaspernielsmichielhein.watchfriends.api.MovieDBService;
import nmct.jaspernielsmichielhein.watchfriends.helper.ApiHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.AuthHelper;
import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;
import nmct.jaspernielsmichielhein.watchfriends.model.Episode;
import nmct.jaspernielsmichielhein.watchfriends.model.Series;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener,
        Interfaces.onHeaderChanged,
        Interfaces.onSeriesSelectedListener,
        Interfaces.onSeasonSelectedListener,
        Interfaces.onEpisodeSelectedListener {

    private ImageView headerImage;
    private FloatingActionButton actionButton;
    private CollapsingToolbarLayout toolbarLayout;
    private AppBarLayout appBarLayout;
    private MovieDBService movieDBService;

    public void setTitle(String title){
        toolbarLayout.setTitle(title);
    }

    public ImageView getHeaderImage(){
        return headerImage;
    }

    public FloatingActionButton getActionButton(){
        return actionButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerImage = (ImageView) findViewById(R.id.header_image);
        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        movieDBService = ApiHelper.getMoviedbServiceInstance();
        //collapseToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AuthHelper.isUserLoggedIn(this)) {
            navigate(HomeFragment.newInstance(), "homeFragment", false);
        } else {
            showLoginActivity();
        }
    }

    private void showLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, MainActivity.class)));
        searchView.setIconifiedByDefault(false);

        return true;
    }

    //https://androidhub.intel.com/en/posts/nglauber/Android_Search.html
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Searching by: "+ query, Toast.LENGTH_SHORT).show();

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String uri = intent.getDataString();
            Toast.makeText(this, "Suggestion: "+ uri, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //todo

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_watching:
                break;
            case R.id.nav_watchlist:
                ApiHelper.subscribe(movieDBService.getSeries(63174),
                        new Action1<Series>() {
                            @Override
                            public void call(Series series) {
                                navigate(SeriesFragment.newInstance(series), "seasonFragment", true);
                            }
                        });
                break;
            case R.id.nav_watched:
                break;

            case R.id.nav_settings:
                //navigate(new SettingsFragment());
                break;
            case R.id.nav_logout:
                AuthHelper.logUserOff(this);
                showLoginActivity();
                break;
            case R.id.nav_upgrade:
                break;

            case R.id.nav_help:
                break;
            case R.id.nav_about:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void collapseToolbar(){
        appBarLayout.setExpanded(false, true);
        actionButton.setVisibility(View.GONE);
        //todo ook disablen
    }


    private void navigate(Fragment fragment, String tag, boolean collapsing){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_frame, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
        if(collapsing){
            appBarLayout.setExpanded(true, true);
            actionButton.setVisibility(View.VISIBLE);
            //todo ook enablen
        }
        else{
            collapseToolbar();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //search button press
        navigate(SearchFragment.newInstance(query), "searchFragment", false);
        return false;
    }

    //todo search back button -> navigate(HomeFragment)

    @Override
    public boolean onQueryTextChange(String newText) {
        //search query changed
        return false;
    }

    @Override
    public void onSetTitle(String title) {
        setTitle(title);
    }

    @Override
    public void onSetImage(Uri uri) {
        Picasso.with(this).load(uri).into(getHeaderImage());
    }

    @Override
    public FloatingActionButton onGetActionButton() {
        return getActionButton();
    }

    @Override
    public void onSeriesSelected(Series series) {
        navigate(SeriesFragment.newInstance(series), "seriesFragment", true);
    }

    @Override
    public void onSeasonSelected(String seriesName, int seriesId, int seasonNumber) {
        navigate(SeasonFragment.newInstance(seriesName, seriesId, seasonNumber), "seasonFragment", true);
    }

    @Override
    public void onEpisodeSelected(Episode episode) {
        navigate(EpisodeFragment.newInstance(episode), "episodeFragment", true);
    }
}