package com.epfd.csandroid.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.epfd.csandroid.MainActivity;
import com.epfd.csandroid.R;
import com.epfd.csandroid.administrator.cakefridayedition.CakeFridayActivity;
import com.epfd.csandroid.administrator.classroomsedition.ClassroomsEditionActivity;
import com.epfd.csandroid.api.UserHelper;
import com.epfd.csandroid.formulary.FormularyActivity;
import com.epfd.csandroid.formulary.PrivacyPolicyActivity;
import com.epfd.csandroid.utils.Utils;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import butterknife.ButterKnife;

import static com.epfd.csandroid.utils.Utils.DEV;
import static com.epfd.csandroid.utils.Utils.EMPTY_PREFERENCES_LOG_CODE;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    protected Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;

    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;
    private static final int UPDATE_USER = 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //TEMP
        mPreferencesCode = getSharedPreferences(Utils.SHARED_INTERNAL_CODE, MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        this.setContentView(getFragmentLayout());
        ButterKnife.bind(this);
        configureToolbar();
        start(savedInstanceState);
    }

    //TEMP
    private SharedPreferences mPreferencesCode;

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
        NavigationView navigationView = findViewById(R.id.general_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View viewHeader = navigationView.getHeaderView(0);
        if (getCurrentUser() != null && getCurrentUser().getEmail() != null && !getCurrentUser().getEmail().equals(DEV)) {
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.genaral_drawer_admin_section).setVisible(false);
        }else {
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.general_drawer_delete).setVisible(false);
        }

        TextView navUserName = viewHeader.findViewById(R.id.nav_header_username);
        navUserName.setText(getCurrentUser().getDisplayName());
        TextView navUserMail = viewHeader.findViewById(R.id.nav_header_user_email);
        navUserMail.setText(getCurrentUser().getEmail());
        ImageView navUserPhoto = viewHeader.findViewById(R.id.nav_header_photo);
        if (getCurrentUser().getPhotoUrl() != null) Glide.with(this)
                .load(this.getCurrentUser().getPhotoUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(navUserPhoto);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cake_friday_edition :
                startActivity(new Intent(this, CakeFridayActivity.class));
                break;
            case R.id.classromms_edition :
                startActivity(new Intent(this, ClassroomsEditionActivity.class));
                break;
            case R.id.general_drawer_formulary :
                startActivity(new Intent(this, FormularyActivity.class));
                break;
            case R.id.general_drawer_deconnexion :
                signOutUserFromFirebase();
                finish();
                break;
            case R.id.general_drawer_delete :
                this.onClickDeleteButton();
                break;
            case R.id.general_drawer_policy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     *  UTILS
     */

    @Nullable
    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }

    //create http request Sign Out
    protected void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //TEMP
    //create http request Delete
    protected void deleteUserFromFirebase(){
        mPreferencesCode.edit().putString(Utils.BUNDLE_KEY_ACTIVE_USER, EMPTY_PREFERENCES_LOG_CODE).apply();
        if (this.getCurrentUser() != null) {
            UserHelper.deleteUser(getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener());
            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }



    protected void onClickDeleteButton() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, (dialogInterface, i) -> deleteUserFromFirebase())
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
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
                case UPDATE_USER:
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
