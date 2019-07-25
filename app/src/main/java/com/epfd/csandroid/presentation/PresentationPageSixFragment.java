package com.epfd.csandroid.presentation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.epfd.csandroid.R;

public class PresentationPageSixFragment extends Fragment {

    public static PresentationPageSixFragment newInstance(){
        return new PresentationPageSixFragment();
    }

    public PresentationPageSixFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presentation_page_six, container, false);
    }

}
