package com.bortnikov.artem.flikrshlikr.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bortnikov.artem.flikrshlikr.R;
import com.bortnikov.artem.flikrshlikr.view.feed.FeedFragment;
import com.bortnikov.artem.flikrshlikr.view.profile.ProfileFragment;
import com.bortnikov.artem.flikrshlikr.view.search.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SingleFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected Fragment createStartFragment() {
        return new FeedFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_feed: {
                placeFragment(FeedFragment.class.getName());
                break;
            }
            case R.id.nav_search: {
                placeFragment(SearchFragment.class.getName());
                break;
            }
            case R.id.nav_profile: {
                placeFragment(ProfileFragment.class.getName());
                break;
            }
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void placeFragment(String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = Fragment.instantiate(this, fragmentTag, null);
        transaction.setCustomAnimations(
                android.R.anim.fade_in, android.R.anim.fade_out,
                android.R.anim.fade_out, android.R.anim.fade_in);
        transaction.replace(R.id.container_fragments, fragment, fragmentTag);

        //transaction.addToBackStack(fragmentTag);

        transaction.commit();
    }
}
