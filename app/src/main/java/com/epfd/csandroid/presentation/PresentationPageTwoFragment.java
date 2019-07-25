package com.epfd.csandroid.presentation;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epfd.csandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresentationPageTwoFragment extends Fragment {

    public static PresentationPageTwoFragment newInstance(){
        return new PresentationPageTwoFragment();
    }

    public PresentationPageTwoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presentation_page_two, container, false);
    }

}
