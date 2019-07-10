package com.epfd.csandroid.formulary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.OnClick;

public class FormularyActivity extends BaseActivity {

    @BindView(R.id.formulary_information) TextView mTextViewInformation;



    @Override
    public int getFragmentLayout() {
        return R.layout.activity_formulary;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        if (this.getCurrentUser() != null){
            String info = "name : " + getCurrentUser().getDisplayName() + "\nmail : " + getCurrentUser().getEmail();
            mTextViewInformation.setText(info);
        }
    }

    @Override
    public Boolean isAChildActivity() {
        return false;
    }







    // 4 - Adding requests to button listeners

    @OnClick(R.id.formulary_deconnexion) public void onClickSignOutButton() { this.signOutUserFromFirebase(); }

    @OnClick(R.id.formulary_delete) public void onClickDeleteButton() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage(R.string.popup_message_confirmation_delete_account)
                .setPositiveButton(R.string.popup_message_choice_yes, (dialogInterface, i) -> deleteUserFromFirebase())
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }








}
