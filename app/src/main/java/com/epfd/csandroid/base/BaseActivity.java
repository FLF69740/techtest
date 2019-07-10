package com.epfd.csandroid.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.epfd.csandroid.MainActivity;
import com.epfd.csandroid.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getFragmentLayout());
        ButterKnife.bind(this);
        configureToolbar();
        start(savedInstanceState);
    }

    public abstract int getFragmentLayout();
    public abstract void start(@Nullable Bundle savedInstanceState);
    public abstract Boolean isAChildActivity();

    /**
     *  UI
     */

    protected void configureToolbar(){
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (isAChildActivity() != null) {
            if (isAChildActivity()) {
                ActionBar actionBar = getSupportActionBar();
                assert actionBar != null;
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                this.configureDrawerLayout();
                this.configureNavigationView();
            }
        }
    }

    /**
     *  NAVIGATION DRAWER
     */

    // Handle back click to close menu
    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout != null) {
            if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                this.mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }
    }

    //Configure Drawer Layout
    private void configureDrawerLayout(){
        mDrawerLayout = findViewById(R.id.general_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //Configure NavigationView
    private void configureNavigationView(){
        NavigationView mNavigationView = findViewById(R.id.general_nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        View viewHeader = mNavigationView.getHeaderView(0);
     //   mNavUserName = viewHeader.findViewById(R.id.nav_userName);
     //   mNavUserPhoto = viewHeader.findViewById(R.id.nav_userPhoto);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /**
     *  UTILS
     */

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }

    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;

    //create http request Sign Out
    protected void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
        startActivity(new Intent(this, MainActivity.class));
    }

    //create http request Delete
    protected void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {
            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    //Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return aVoid -> {
            switch (origin){
                case SIGN_OUT_TASK:
                    finish();
                    break;
                case DELETE_USER_TASK:
                    finish();
                    break;
                default:
                    break;
            }
        };
    }

    /**
     *  ERROR HANDLER
     */

    protected OnFailureListener onFailureListener(){
        return e -> Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
    }



}
