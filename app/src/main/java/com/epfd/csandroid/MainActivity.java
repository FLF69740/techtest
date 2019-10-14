package com.epfd.csandroid;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimatedVectorDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.epfd.csandroid.api.PhotoCodeHelper;
import com.epfd.csandroid.api.UserHelper;
import com.epfd.csandroid.api.VersionCodeHelper;
import com.epfd.csandroid.firstpage.FirstPageActivity;
import com.epfd.csandroid.api.PasswordHelper;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.formulary.ContactActivity;
import com.epfd.csandroid.formulary.FormularyActivity;
import com.epfd.csandroid.formulary.PasswordActivity;
import com.epfd.csandroid.models.User;
import com.epfd.csandroid.models.VersionCode;
import com.epfd.csandroid.presentation.PresentationActivity;
import com.epfd.csandroid.utils.BitmapStorage;
import com.epfd.csandroid.utils.FireBaseStorageUtils;
import com.epfd.csandroid.utils.Utils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.OnClick;

import static com.epfd.csandroid.utils.Utils.BUNDLE_PASSWORD;
import static com.epfd.csandroid.utils.Utils.BUNDLE_USERMAIL;
import static com.epfd.csandroid.utils.Utils.BUNDLE_USERNAME;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_activity_coordinator_layout) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.main_vertical_guide) Guideline mGuidelineVertical;
    @BindView(R.id.main_horizontal_guide) Guideline mGuideLineHorizontal;
    @BindView(R.id.main_vertical_left_guide) Guideline mGuidelineLeftVertical;
    @BindView(R.id.main_connexion_btn) Button mMainConnexionBtn;
    @BindView(R.id.main_presentation_btn) Button mMainPresentationBtn;
    @BindView(R.id.main_contact_btn) Button mMainContactBtn;
    @BindView(R.id.main_progressBar) ProgressBar mProgressBar;

    private PropertyValuesHolder mAlphaValues = PropertyValuesHolder.ofInt("ALPHA", 0, 100);
    private PropertyValuesHolder mWaitValues = PropertyValuesHolder.ofInt("WAIT", 3000, 1);
    private PropertyValuesHolder mPurcentGuideLineValues, mPurcentBottomGuideLineValues, mPurcentLeftGuideLineValues;
    private int mAnimationAlpha = 0;

    private String mInternalCodeRegistration;

    public static final String MAIN_EXTRA_CLASSROOMLIST = "MAIN_EXTRA_CLASSROOMLIST";
    private static final String DIMENSION = "DIMENSION";
    private static final String BOTTOM_DIMENSION = "BOTTOM_DIMENSION";
    private static final String LEFT_DIMENSION = "LEFT_DIMENSION";
    private static final int RC_SIGN_IN = 123;

    @Override
    public int getFragmentLayout() {return R.layout.activity_main;}

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        int i = 0;
        VersionCodeHelper.getVersionCode().addOnSuccessListener(documentSnapshot -> {
            VersionCode versionCode = documentSnapshot.toObject(VersionCode.class);
            if (!BuildConfig.VERSION_NAME.equals(versionCode.getVersionNumber()) && versionCode.isVersionPublished()){
                Toast.makeText(getApplicationContext(), "PAS LA BONNE VERSION", Toast.LENGTH_SHORT).show();
            }else {
                mProgressBar.setVisibility(View.INVISIBLE);
                mInternalCodeRegistration = getSharedPreferences(Utils.SHARED_INTERNAL_CODE, MODE_PRIVATE).getString(Utils.BUNDLE_KEY_ACTIVE_USER, Utils.EMPTY_PREFERENCES_LOG_CODE);
                ImageView imageView = findViewById(R.id.logo_animation);
                imageView.setBackgroundResource(R.drawable.avd_anim_epfd);
                AnimatedVectorDrawable animationDrawable = (AnimatedVectorDrawable) imageView.getBackground();
                animationDrawable.start();
                playAnimation();
            }
        });



    }

    @Override
    public Boolean isAChildActivity() {return null;}

    /**
     *  ANIMATION
     */

    // launch animation at the start of MainActivity
    private void playAnimation() {
        this.configureGuideLines();
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        animators.add(waitVectorAnimation());
        animators.add(reduceLogoAnimation());
        animators.add(showBtn());
        animatorSet.playSequentially(animators);
        animatorSet.start();
    }

    // verify orientation in order to get correct guidelines values
    private void configureGuideLines(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mPurcentGuideLineValues = PropertyValuesHolder.ofInt(DIMENSION, 100, 50);
            mPurcentBottomGuideLineValues = PropertyValuesHolder.ofInt(BOTTOM_DIMENSION, 100, 40);
            mPurcentLeftGuideLineValues = PropertyValuesHolder.ofInt(LEFT_DIMENSION, 10, 30);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mPurcentGuideLineValues = PropertyValuesHolder.ofInt(DIMENSION, 100, 55);
            mPurcentBottomGuideLineValues = PropertyValuesHolder.ofInt(BOTTOM_DIMENSION, 100, 40);
            mPurcentLeftGuideLineValues = PropertyValuesHolder.ofInt(LEFT_DIMENSION, 10, 15);
        }
    }


    // wait during animationDrawable
    private Animator waitVectorAnimation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mWaitValues);
        animator.setDuration(2000);
        animator.addUpdateListener(animation -> {});
        return animator;
    }

    // reduce logo
    private Animator reduceLogoAnimation(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mPurcentGuideLineValues, mPurcentLeftGuideLineValues, mPurcentBottomGuideLineValues);
        animator.setDuration(700);
        animator.addUpdateListener(animation -> {
            int rightValue = (int) animation.getAnimatedValue(DIMENSION);
            int leftValue = (int) animation.getAnimatedValue(LEFT_DIMENSION);
            int bottomValue = (int) animation.getAnimatedValue(BOTTOM_DIMENSION);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mGuidelineVertical.getLayoutParams();
            ConstraintLayout.LayoutParams paramsL = (ConstraintLayout.LayoutParams) mGuidelineLeftVertical.getLayoutParams();
            ConstraintLayout.LayoutParams paramsH = (ConstraintLayout.LayoutParams) mGuideLineHorizontal.getLayoutParams();
            paramsL.guidePercent = (float)leftValue /100;
            params.guidePercent = (float)rightValue /100;
            paramsH.guidePercent = (float)bottomValue /100;
            mGuidelineLeftVertical.setLayoutParams(paramsL);
            mGuidelineVertical.setLayoutParams(params);
            mGuideLineHorizontal.setLayoutParams(paramsH);
        });
        return animator;
    }

    //show gradiently connexion button
    private Animator showBtn(){
        ValueAnimator animator = new ValueAnimator();
        animator.setValues(mAlphaValues);
        animator.setDuration(1500);
        animator.addUpdateListener(animation -> {
            mAnimationAlpha = (int) animation.getAnimatedValue();
            mMainConnexionBtn.setAlpha((float)mAnimationAlpha /100);
            mMainPresentationBtn.setAlpha((float)mAnimationAlpha /100);
            mMainContactBtn.setAlpha((float)mAnimationAlpha /100);
        });
        return animator;
    }

    /**
     *  BUTTON ACTION
     */

    @OnClick(R.id.main_contact_btn) public void onClickContact(){
        startActivity(new Intent(this, ContactActivity.class));
    }

    @OnClick(R.id.main_presentation_btn) public void onClickPresentation(){
        startActivity(new Intent(this, PresentationActivity.class));
    }

    @OnClick(R.id.main_connexion_btn) public void onClickLogin(){
        if (this.isCurrentUserLogged()){
            codeVerification();
        }else {
            this.startSignIn();
        }
    }

    /**
     *  CONNEXION AUTH
     */

    private List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), new AuthUI.IdpConfig.GoogleBuilder().build());

    //Launch sign in Activity
    private void startSignIn(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        .setLogo(R.drawable.logo)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                this.mProgressBar.setVisibility(View.VISIBLE);

                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> Log.i(Utils.INFORMATION_LOG, "Code : " + task.getResult().getToken()));

                this.codeVerification();
            } else { // ERRORS
                if (response == null) {
                    showSnackBar(this.mCoordinatorLayout, getString(R.string.error_authentication_canceled));
                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackBar(this.mCoordinatorLayout, getString(R.string.error_no_internet));
                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackBar(this.mCoordinatorLayout, getString(R.string.error_unknown_error));
                }
            }
        }
    }

    //APEL Code && photo Code  verification : 1/ phot verification -> OK : 2/ Formulary verification -> OK : 3/ User database verification
    private void codeVerification(){
        if (this.getCurrentUser() != null){

            //VERIFICATION OF PHOTOS GALLERY
            PhotoCodeHelper.getPhotoCode().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String galleryBackEnd = documentSnapshot.getString("serial");
                    List<String> controlList = Arrays.asList(galleryBackEnd.split(BitmapStorage.PHOTO_SEPARATOR));
                    String myGallery = BitmapStorage.getPhotoMemoryCode(getApplicationContext());
                    boolean imageMissing = false;
                    for (String imageName : controlList) {
                        if (!myGallery.contains(imageName))
                            imageMissing = true;
                    }
                    if (imageMissing){
                        FireBaseStorageUtils fireBaseStorageUtils = new FireBaseStorageUtils();
                        fireBaseStorageUtils.initialisePhotoGallery(getApplicationContext());
                    }

                    PasswordHelper.getCode().addOnSuccessListener(documentSnapshot2 -> {
                        String backEndCode = documentSnapshot2.getString(Utils.NAME_DATA_CODE);
                        if (backEndCode != null && !mInternalCodeRegistration.toUpperCase().equals(backEndCode.toUpperCase())){
                            Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                            intent.putExtra(BUNDLE_USERNAME, getCurrentUser().getDisplayName());
                            intent.putExtra(BUNDLE_USERMAIL, getCurrentUser().getEmail());
                            intent.putExtra(BUNDLE_PASSWORD, backEndCode);
                            startActivity(intent);
                        } else {
                            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot1 -> {
                                if (documentSnapshot1.toObject(User.class) != null) {
                                    User user = documentSnapshot1.toObject(User.class);
                                    Intent intent = new Intent(getApplicationContext(), FirstPageActivity.class);
                                    intent.putExtra(MAIN_EXTRA_CLASSROOMLIST, user.getStringClasseNameList());
                                    startActivity(intent);

                                } else {
                                    startActivity(new Intent(getApplicationContext(), FormularyActivity.class));
                                }
                            });
                        }
                        finish();
                    });

                }
            });
        }
    }


    /**
     *  UI
     */

    //Show Snack Bar with a message
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }


}
