package com.epfd.csandroid.presentation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.epfd.csandroid.R;
import java.util.ArrayList;
import java.util.List;

public class PresentationPageOneFragment extends Fragment {

    public static final String BUNDLE_LIST_STRING = "BUNDLE_LIST_STRING";

    public static PresentationPageOneFragment newInstance(ArrayList<String> arrayList){
        PresentationPageOneFragment page = new PresentationPageOneFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(BUNDLE_LIST_STRING, arrayList);
        page.setArguments(bundle);
        return page;
    }

    public PresentationPageOneFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presentation_page_one, container, false);

        TextView title = view.findViewById(R.id.presentation_textview_title);
        TextView lineOne = view.findViewById(R.id.presentation_textview_line_one);
        TextView lineTwo = view.findViewById(R.id.presentation_textview_line_two);
        TextView lineThree = view.findViewById(R.id.presentation_textview_line_three);
        TextView lineFour = view.findViewById(R.id.presentation_textview_line_four);
        TextView lineFive = view.findViewById(R.id.presentation_textview_line_five);
        TextView lineSix = view.findViewById(R.id.presentation_textview_line_six);
        TextView lineSeven = view.findViewById(R.id.presentation_textview_line_seven);
        TextView lineHeight = view.findViewById(R.id.presentation_textview_line_height);
        TextView lineNine = view.findViewById(R.id.presentation_textview_line_nine);
        TextView lineTen = view.findViewById(R.id.presentation_textview_line_ten);

        List<TextView> textViewList = new ArrayList<>();
        textViewList.add(title);
        textViewList.add(lineOne);
        textViewList.add(lineTwo);
        textViewList.add(lineThree);
        textViewList.add(lineFour);
        textViewList.add(lineFive);
        textViewList.add(lineSix);
        textViewList.add(lineSeven);
        textViewList.add(lineHeight);
        textViewList.add(lineNine);
        textViewList.add(lineTen);

        ArrayList<String> textStringList = getArguments().getStringArrayList(BUNDLE_LIST_STRING);

        for (int i = 0; i < textViewList.size(); i++) {
            if (i < textStringList.size()){
                textViewList.get(i).setText(textStringList.get(i));
            }else {
                textViewList.get(i).setVisibility(View.GONE);
            }
        }

        return view;
    }

}
