package br.com.app.template;

import android.annotation.SuppressLint;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

/**
 * Created by artur on 05/08/15.
 */
@EActivity
public class BaseActivity extends AppCompatActivity {

    private int menuRes;

    private DrawerLayout drawerLayout;

    public void init(Toolbar toolbar, DrawerLayout drawerLayout, int menuRes) {

        setDepedencies(menuRes, drawerLayout);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ViewCompat.setElevation(toolbar, 8);
            ViewCompat.setTranslationZ(toolbar, 8);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * AndroidAnnotations handle de "requires api level 11" warning
     * @see BaseActivity_#onOptionsItemSelected
     */
    @SuppressLint("InlinedApi")
    @OptionsItem(android.R.id.home)
    public void onHomeToolbarClick() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void setDepedencies(int menuRes, DrawerLayout drawerLayout) {
        this.menuRes = menuRes;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuRes, menu);
        return true;
    }


}
