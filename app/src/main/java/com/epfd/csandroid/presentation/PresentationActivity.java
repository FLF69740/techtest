package com.epfd.csandroid.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.epfd.csandroid.R;
import com.epfd.csandroid.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PresentationActivity extends BaseActivity {

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_presentation;
    }

    @Override
    public Boolean isAChildActivity() {
        return true;
    }

    @Override
    public void start(@Nullable Bundle savedInstanceState) {
        List<ArrayList<String>> myList = createListString();
        ViewPager viewPager = findViewById(R.id.presentation_viewpager);
        viewPager.setAdapter(new PresentationAdapter(getSupportFragmentManager(),2, myList));
    }

    private List<ArrayList<String>> createListString() {
        List<ArrayList<String>> result = new ArrayList<>();

        ArrayList<String> arrayHistory = new ArrayList<>();
        arrayHistory.add(getString(R.string.presentation_historique_titre));
        arrayHistory.add(getString(R.string.presentation_historique_l1));
        arrayHistory.add(getString(R.string.presentation_historique_l2));
        arrayHistory.add(getString(R.string.presentation_historique_l3));
        arrayHistory.add(getString(R.string.presentation_historique_l4));
        arrayHistory.add(getString(R.string.presentation_historique_l5));
        arrayHistory.add(getString(R.string.presentation_historique_l6));

        ArrayList<String> arrayProjet = new ArrayList<>();
        arrayProjet.add(getString(R.string.presentation_projet_titre));
        arrayProjet.add(getString(R.string.presentation_projet_l1));
        arrayProjet.add(getString(R.string.presentation_projet_l2));
        arrayProjet.add(getString(R.string.presentation_projet_l3));
        arrayProjet.add(getString(R.string.presentation_projet_l4));
        arrayProjet.add(getString(R.string.presentation_projet_l5));
        arrayProjet.add(getString(R.string.presentation_projet_l6));
        arrayProjet.add(getString(R.string.presentation_projet_l7));
        arrayProjet.add(getString(R.string.presentation_projet_l8));

        ArrayList<String> arrayPastorale = new ArrayList<>();
        arrayPastorale.add(getString(R.string.presentation_pastorale_titre));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l1));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l2));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l3));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l4));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l5));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l6));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l7));
        arrayPastorale.add(getString(R.string.presentation_pastorale_l8));

        ArrayList<String> arrayAssociation = new ArrayList<>();
        arrayAssociation.add(getString(R.string.presentation_association_titre));
        arrayAssociation.add(getString(R.string.presentation_association_l1));
        arrayAssociation.add(getString(R.string.presentation_association_l2));
        arrayAssociation.add(getString(R.string.presentation_association_l3));
        arrayAssociation.add(getString(R.string.presentation_association_l4));
        arrayAssociation.add(getString(R.string.presentation_association_l5));
        arrayAssociation.add(getString(R.string.presentation_association_l6));
        arrayAssociation.add(getString(R.string.presentation_association_l7));
        arrayAssociation.add(getString(R.string.presentation_association_l8));
        arrayAssociation.add(getString(R.string.presentation_association_l9));

        ArrayList<String> arrayOgec = new ArrayList<>();
        arrayOgec.add(getString(R.string.presentation_ogec_titre));
        arrayOgec.add(getString(R.string.presentation_ogec_l1));
        arrayOgec.add(getString(R.string.presentation_ogec_l2));
        arrayOgec.add(getString(R.string.presentation_ogec_l3));
        arrayOgec.add(getString(R.string.presentation_ogec_l4));
        arrayOgec.add(getString(R.string.presentation_ogec_l5));
        arrayOgec.add(getString(R.string.presentation_ogec_l6));
        arrayOgec.add(getString(R.string.presentation_ogec_l7));
        arrayOgec.add(getString(R.string.presentation_ogec_l8));
        arrayOgec.add(getString(R.string.presentation_ogec_l9));
        arrayOgec.add(getString(R.string.presentation_ogec_l10));

        ArrayList<String> arrayCode = new ArrayList<>();
        arrayCode.add(getString(R.string.presentation_reglement_titre));
        arrayCode.add(getString(R.string.presentation_reglement_l1));
        arrayCode.add(getString(R.string.presentation_reglement_l2));
        arrayCode.add(getString(R.string.presentation_reglement_l3));
        arrayCode.add(getString(R.string.presentation_reglement_l4));
        arrayCode.add(getString(R.string.presentation_reglement_l5));
        arrayCode.add(getString(R.string.presentation_reglement_l6));
        arrayCode.add(getString(R.string.presentation_reglement_l7));

        result.add(arrayHistory);
        result.add(arrayProjet);
        result.add(arrayPastorale);
        result.add(arrayAssociation);
        result.add(arrayOgec);
        result.add(arrayCode);

        return result;
    }


}
