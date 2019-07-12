package com.epfd.csandroid.formulary;

import androidx.annotation.Nullable;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.epfd.csandroid.utils.Utils;
import butterknife.BindView;
import butterknife.OnClick;

public class PasswordActivity extends BaseActivity {

    @BindView(R.id.password_email_text) TextView mEmailText;
    @BindView(R.id.password_name_text) TextView mNameText;
    @BindView(R.id.password_proposition) EditText mProposition;
    @BindView(R.id.password_wrong_proposition) TextView mAlertWrongPassword;

    private String mVerification;
    private SharedPreferences mPreferencesCode;

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_password;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        mNameText.setText(getIntent().getStringExtra(Utils.BUNDLE_USERNAME));
        mEmailText.setText(getIntent().getStringExtra(Utils.BUNDLE_USERMAIL));
        mVerification = getIntent().getStringExtra(Utils.BUNDLE_PASSWORD);
        mPreferencesCode = getSharedPreferences(Utils.SHARED_INTERNAL_CODE, MODE_PRIVATE);
    }

    @Override
    public Boolean isAChildActivity() {
        return null;
    }

    @OnClick(R.id.password_retour) public void passwordDeconnection(){
        this.signOutUserFromFirebase();
    }

    @OnClick(R.id.password_ok) public void passwordProposition(){
        if (mVerification.equals(mProposition.getText().toString().toUpperCase())){
            mPreferencesCode.edit().putString(Utils.BUNDLE_KEY_ACTIVE_USER, mVerification).apply();
            Intent intent = new Intent(this, FormularyActivity.class);
            intent.putExtra(Utils.BUNDLE_USERNAME, getIntent().getStringExtra(Utils.BUNDLE_USERNAME));
            intent.putExtra(Utils.BUNDLE_USERMAIL, getIntent().getStringExtra(Utils.BUNDLE_USERMAIL));
            startActivity(intent);
            finish();
        }else {
            mAlertWrongPassword.setVisibility(View.VISIBLE);
        }
    }
}
