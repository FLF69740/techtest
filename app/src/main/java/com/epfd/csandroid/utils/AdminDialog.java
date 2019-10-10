package com.epfd.csandroid.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.epfd.csandroid.R;

public class AdminDialog extends AppCompatDialogFragment {

    private RadioButton mRadioButtonAdmin;
    private RadioButton mRadioButtonCustom;
    private EditText mEditTextCustomName;
    private String mAnswer = "ADMIN";
    private ListenerAdminDialog mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.admin_event_dialog, null);

        mRadioButtonAdmin = view.findViewById(R.id.radio_admin_dialog_choice_admin);
        mRadioButtonCustom = view.findViewById(R.id.radio_admin_dialog_choice_random);
        mEditTextCustomName = view.findViewById(R.id.editText_admin_dialog_random);

        builder.setView(view)
                .setTitle("CHOIX ADMINISTRATEUR")
                .setNegativeButton(R.string.RETOUR, (dialog, which) -> {})
                .setPositiveButton(R.string.AJOUTER, (dialog, which) -> mCallback.getAdminChoiceUsername(mAnswer));
        
        this.createRadioGroup();


        return builder.create();
    }

    private void createRadioGroup() {
        mRadioButtonAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mAnswer = "ADMIN";
                mEditTextCustomName.setText("");
                mRadioButtonAdmin.setChecked(true);
                mRadioButtonCustom.setChecked(false);
            }
        });

        mRadioButtonCustom.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mAnswer = Utils.EMPTY;
                mEditTextCustomName.setText("");
                mRadioButtonCustom.setChecked(true);
                mRadioButtonAdmin.setChecked(false);
            }
        });

        mEditTextCustomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    mAnswer = Utils.EMPTY;
                }else {
                    mAnswer = s.toString();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // interface for button clicked
    public interface ListenerAdminDialog{
        void getAdminChoiceUsername(String name);
    }

    //callback for button clicked
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (ListenerAdminDialog) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ListenerAdminDialog");
        }
    }
}
