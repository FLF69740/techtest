package com.epfd.csandroid.presentation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epfd.csandroid.R;

public class PresentationPageThreeFragment extends Fragment {

    public static PresentationPageThreeFragment newInstance(){
        return new PresentationPageThreeFragment();
    }

    public PresentationPageThreeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presentation_page_three, container, false);
    }

}
