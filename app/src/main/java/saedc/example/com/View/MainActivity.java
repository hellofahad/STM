package saedc.example.com.View;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import saedc.example.com.About.AboutActivity;
import saedc.example.com.Model.Pojo.LinechartPojo;
import saedc.example.com.Model.Repository.SpendingRepository;
import saedc.example.com.R;
import saedc.example.com.SettingsActivity;
import saedc.example.com.TaxCalculator.Taxcalculator;
import saedc.example.com.Tips.Tips;
import saedc.example.com.View.AddAndEditSpending.AddAndEditSpendingFragment;
import saedc.example.com.View.ChartList.chartlist;
import saedc.example.com.View.SpendingList.SpendingListFragment;
import saedc.example.com.View.SpendingList.SpendingListViewModel;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LifecycleOwner {

    FragmentManager fragmentManager;
    InputMethodManager imm;

    @Inject
    public SpendingRepository spendingRepository;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab1)
    FloatingActionButton fab1;

    @BindView(R.id.fab2)
    FloatingActionButton fab2;

    @BindView(R.id.fab3)
    FloatingActionButton fab3;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.menu_red)
    FloatingActionMenu menuRed;

    Intent intent;

    Boolean mColorsInverted;

    @BindView(R.id.collaps)
    CollapsingToolbarLayout collaps;
    SpendingListViewModel viewModel;
    double Percentage;
    final Calendar now = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM/yyyy");
    SharedPreferences SettingsDataBase;
    private LifecycleRegistry registry = new LifecycleRegistry(this);

    @Override
    public void onStart() {
        super.onStart();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

//        setTheme(R.style.settingTheme1);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SettingsDataBase = PreferenceManager.getDefaultSharedPreferences(this);


        viewModel = ViewModelProviders.of(this).get(SpendingListViewModel.class);


        Boolean IsColoreEnable = SettingsDataBase.getBoolean("color_preference", false);
        if (IsColoreEnable) {
            SubscribeThemeChanger();
        }


        fab1.setOnClickListener(clickListener);
        fab2.setOnClickListener(clickListener);
        fab3.setOnClickListener(clickListener);

        menuRed.setClosedOnTouchOutside(true);


        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        fragmentManager = getSupportFragmentManager();
        Boolean switchPref = SettingsDataBase.getBoolean("example_switch", false);
        String Disblyname = SettingsDataBase.getString("Settings_name", "hello");
        setSupportActionBar(toolbar);
        if (switchPref) {
            getSupportActionBar().setTitle(Disblyname);
        }

        menuRed.hideMenu(true);
        //Add this fragment just at start and dont add to backstack
        if (getFragmentBackStackCount() == 0) {
            showRootFragment(SpendingListFragment.newInstance());

            menuRed.showMenu(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void SubscribeThemeChanger() {
        viewModel.ChangecolorPercent.observe(this, new Observer<List<LinechartPojo>>() {
            @Override
            public void onChanged(@Nullable List<LinechartPojo> linechartPojos) {
                for (LinechartPojo data : linechartPojos) {
                    if (dateFormat.format(now.getTime()).equals(dateFormat.format(data.getSpendDate()))) {
                        Percentage += data.getPrice();
                    }
                }
                ChangeThemeBaseOnThisMonthTotal(Percentage, Integer.parseInt(SettingsDataBase.getString("edit_text_preference_1", "0")));
                Percentage = 0;
            }
        });

    }

    public void ChangeThemeBaseOnThisMonthTotal(double ThisMonthTotal, int salary) {
        double Percentag = ((ThisMonthTotal * 100.0) / salary);

        Percentag = Math.round(Percentag * 100.0) / 100.0;

        if (Percentag >= 20.0) {

            if (Percentag <= 40.0) {
                ChangrColore(R.color.colorPrimary, R.color.a);
            } else if (Percentag <= 60.0) {
                ChangrColore(R.color.a, R.color.c);
            } else if (Percentag <= 80.0) {
                ChangrColore(R.color.c, R.color.d);
            } else if (Percentag <= 100.0) {
                ChangrColore(R.color.d, R.color.e);
            }
        } else {
            ChangrColore(R.color.colorPrimary, R.color.colorPrimary);

        }


    }

    // float button menue
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab1:
                    Intent intent = new Intent(MainActivity.this, SavingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.fab2:
                    intent = new Intent(MainActivity.this, chartlist.class);
                    startActivity(intent);
                    break;
                case R.id.fab3:
                    showFragment(AddAndEditSpendingFragment.newInstance());
                    break;

            }
        }
    };

    public void ChangrColore(int colorStart, int colorEnd) {


        int colorFrom = getResources().getColor(colorStart);
        int colorTo = getResources().getColor(colorEnd);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(1000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                //                1            CDDC39
                //                2            FFEB3B
                //                3            FFC107
                //                4            FF9800
                //                5            FF5722

                collaps.setBackgroundColor((int) animator.getAnimatedValue());
                collaps.setContentScrimColor((int) animator.getAnimatedValue());
                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                getWindow().setStatusBarColor((int) animator.getAnimatedValue());

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                RelativeLayout navUsername = (RelativeLayout) headerView.findViewById(R.id.headerBackground);
                navUsername.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(getIntent());
                finish();
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showRootFragment(Fragment fragment) {
        appBarLayout.setExpanded(true);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container1, fragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        int backStackCount = getFragmentBackStackCount();
        //if backstack == 1 it means this is last fragment so show fab
        switch (backStackCount) {
            case 1:
                menuRed.showMenu(true);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }


    public void showFragment(Fragment nextFragment) {
        //be sure to not load same fragment
        if (isLastFragmentInBackstack(nextFragment)) {
            return;
        }

        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.fragment_container1, nextFragment)
                .addToBackStack(nextFragment.getClass().getName())
                .commit();


        menuRed.hideMenu(true);
        appBarLayout.setExpanded(false);
    }

    public boolean isLastFragmentInBackstack(Fragment fragment) {
        String currentFragmentName;
        String nextFragmentName = fragment.getClass().getName();

        //if count is 0 it means there isnt any fragment in backstack
        int count = getFragmentBackStackCount();
        if (count != 0) {
            currentFragmentName = getLastFragmentNameInBackStack();
            if (currentFragmentName.equals(nextFragmentName)) {
                return true;
            }
        }
        return false;
    }

    public String getLastFragmentNameInBackStack() {
        return fragmentManager.getBackStackEntryAt(getFragmentBackStackCount() - 1).getName();
    }

    public int getFragmentBackStackCount() {
        return fragmentManager.getBackStackEntryCount();
    }

    public void hideKeyboard(View v) {
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.currncy) {
            Intent intent = new Intent(this, Taxcalculator.class);
            startActivity(intent);
        } else if (id == R.id.tips) {
            Intent intent = new Intent(this, Tips.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return registry;
    }
}

