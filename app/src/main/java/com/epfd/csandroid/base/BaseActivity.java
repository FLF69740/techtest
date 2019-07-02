package com.epfd.csandroid.base;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;

import static com.epfd.csandroid.utils.Utils.INFORMATION_LOG;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(getFragmentLayout());
        ButterKnife.bind(this);
        configureToolbar();
        start();
    }

    public abstract int getFragmentLayout();
    public abstract void start();
    public abstract Boolean isAChildActivity();

    /**
     *  UI
     */

    protected void configureToolbar(){
        if (isAChildActivity() != null) {
            if (isAChildActivity()) {
                ActionBar actionBar = getSupportActionBar();
                assert actionBar != null;
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                Log.i(INFORMATION_LOG, INFORMATION_LOG);
            }
        }
    }

    /**
     *  UTILS
     */

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }



}
