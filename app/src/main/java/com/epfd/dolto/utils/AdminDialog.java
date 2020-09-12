package com.epfd.dolto.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.epfd.dolto.R;

public class AdminDialog extends AppCompatDialogFragment {

    private String mAnswer = Utils.EMPTY;
    private ListenerAdminDialog mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.admin_event_dialog, null);

        EditText editTextCustomName = view.findViewById(R.id.editText_admin_dialog_random);

        builder.setView(view)
                .setTitle("CHOIX ADMINISTRATEUR")
                .setNeutralButton(R.string.RETOUR, (dialog, which) -> {})
                .setNegativeButton(R.string.SUPPRIMER, (dialog, which) -> mCallback.deleteAdminChoiceUsername(mAnswer))
                .setPositiveButton(R.string.AJOUTER, (dialog, which) -> mCallback.getAdminChoiceUsername(mAnswer));

        editTextCustomName.addTextChangedListener(new TextWatcher() {
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


        return builder.create();
    }

    // interface for button clicked
    public interface ListenerAdminDialog{
        void getAdminChoiceUsername(String name);
        void deleteAdminChoiceUsername(String name);
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
