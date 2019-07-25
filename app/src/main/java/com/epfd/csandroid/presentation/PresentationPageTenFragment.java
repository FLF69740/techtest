package com.epfd.csandroid.presentation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.epfd.csandroid.R;

public class PresentationPageTenFragment extends Fragment {

    public static PresentationPageTenFragment newInstance(){
        return new PresentationPageTenFragment();
    }

    public PresentationPageTenFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presentation_page_ten, container, false);
    }

}
