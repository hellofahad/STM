package saedc.example.com.About;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import saedc.example.com.R;

/**
 * Created by woow on 3/17/18.
 */
public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    LinearLayout ratel, changelogl, licensesl, googleplusl, githubl, twitterl, appsl, bugsl, donatel;
    String version;
    TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);

        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionText = (TextView) findViewById(R.id.app_version);
        versionText.setText(version);

        ratel = (LinearLayout) findViewById(R.id.rate);
        changelogl = (LinearLayout) findViewById(R.id.changelog);
        licensesl = (LinearLayout) findViewById(R.id.licenses);
        googleplusl = (LinearLayout) findViewById(R.id.googleplus);
        githubl = (LinearLayout) findViewById(R.id.github);
        twitterl = (LinearLayout) findViewById(R.id.twitter);
        appsl = (LinearLayout) findViewById(R.id.apps);
        bugsl = (LinearLayout) findViewById(R.id.bugs);
        donatel = (LinearLayout) findViewById(R.id.donate);

        ratel.setOnClickListener(this);
        changelogl.setOnClickListener(this);
        licensesl.setOnClickListener(this);
        googleplusl.setOnClickListener(this);
        githubl.setOnClickListener(this);
        twitterl.setOnClickListener(this);
        appsl.setOnClickListener(this);
        bugsl.setOnClickListener(this);
        donatel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                break;

            case R.id.changelog:
                new Changelog(this).show();
                break;

            case R.id.licenses:

                break;

            case R.id.googleplus:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com")));
                break;

            case R.id.github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com")));
                break;

            case R.id.twitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com")));
                break;

            case R.id.apps:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com")));
                break;

            case R.id.bugs:

                break;

            case R.id.donate:

                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_developer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.devclick:
                startActivity(new Intent(getApplicationContext(), DevelopersActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}