package com.epfd.dolto.presentation;

import android.content.Context;
import com.epfd.dolto.R;
import java.util.ArrayList;
import java.util.List;

public class BusinessPresentation {
    
    public static List<ArrayList<String>> createListing(Context context){
        List<ArrayList<String>> result = new ArrayList<>();

        ArrayList<String> arrayHistory = new ArrayList<>();
        arrayHistory.add(context.getString(R.string.presentation_historique_titre));
        arrayHistory.add(context.getString(R.string.presentation_historique_l1));
        arrayHistory.add(context.getString(R.string.presentation_historique_l2));
        arrayHistory.add(context.getString(R.string.presentation_historique_l3));
        arrayHistory.add(context.getString(R.string.presentation_historique_l4));
        arrayHistory.add(context.getString(R.string.presentation_historique_l5));
        arrayHistory.add(context.getString(R.string.presentation_historique_l6));

        ArrayList<String> arrayProjet = new ArrayList<>();
        arrayProjet.add(context.getString(R.string.presentation_projet_titre));
        arrayProjet.add(context.getString(R.string.presentation_projet_l1));
        arrayProjet.add(context.getString(R.string.presentation_projet_l2));
        arrayProjet.add(context.getString(R.string.presentation_projet_l3));
        arrayProjet.add(context.getString(R.string.presentation_projet_l4));
        arrayProjet.add(context.getString(R.string.presentation_projet_l5));
        arrayProjet.add(context.getString(R.string.presentation_projet_l6));
        arrayProjet.add(context.getString(R.string.presentation_projet_l7));
        arrayProjet.add(context.getString(R.string.presentation_projet_l8));
        arrayProjet.add(context.getString(R.string.presentation_projet_l9));

        ArrayList<String> arrayPastorale = new ArrayList<>();
        arrayPastorale.add(context.getString(R.string.presentation_pastorale_titre));
        arrayPastorale.add(context.getString(R.string.presentation_pastorale_l1));
        arrayPastorale.add(context.getString(R.string.presentation_pastorale_l2));
        arrayPastorale.add(context.getString(R.string.presentation_pastorale_l3));
        arrayPastorale.add(context.getString(R.string.presentation_pastorale_l4));
        arrayPastorale.add(context.getString(R.string.presentation_pastorale_l5));

        ArrayList<String> arrayAssociation = new ArrayList<>();
        arrayAssociation.add(context.getString(R.string.presentation_association_titre));
        arrayAssociation.add(context.getString(R.string.presentation_association_l1));
        arrayAssociation.add(context.getString(R.string.presentation_association_l2));
        arrayAssociation.add(context.getString(R.string.presentation_association_l3));
        arrayAssociation.add(context.getString(R.string.presentation_association_l4));
        arrayAssociation.add(context.getString(R.string.presentation_association_l5));
        arrayAssociation.add(context.getString(R.string.presentation_association_l6));
        arrayAssociation.add(context.getString(R.string.presentation_association_l7));
        arrayAssociation.add(context.getString(R.string.presentation_association_l8));

        ArrayList<String> arrayOgec = new ArrayList<>();
        arrayOgec.add(context.getString(R.string.presentation_ogec_titre));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l1));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l2));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l3));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l4));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l5));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l6));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l7));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l8));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l9));
        arrayOgec.add(context.getString(R.string.presentation_ogec_l10));

        ArrayList<String> arrayCode = new ArrayList<>();
        arrayCode.add(context.getString(R.string.presentation_reglement_titre));
        arrayCode.add(context.getString(R.string.presentation_reglement_l1));
        arrayCode.add(context.getString(R.string.presentation_reglement_l2));
        arrayCode.add(context.getString(R.string.presentation_reglement_l3));
        arrayCode.add(context.getString(R.string.presentation_reglement_l4));
        arrayCode.add(context.getString(R.string.presentation_reglement_l5));
        arrayCode.add(context.getString(R.string.presentation_reglement_l6));
        arrayCode.add(context.getString(R.string.presentation_reglement_l7));

        ArrayList<String> arraySchedule = new ArrayList<>();
        arraySchedule.add(context.getString(R.string.presentation_schedule_titre));
        arraySchedule.add(context.getString(R.string.presentation_schedule_l1));
        arraySchedule.add(context.getString(R.string.presentation_schedule_l2));
        arraySchedule.add(context.getString(R.string.presentation_schedule_l3));

        result.add(arrayHistory);
        result.add(arrayProjet);
        result.add(arrayPastorale);
        result.add(arrayAssociation);
        result.add(arrayOgec);
        result.add(arrayCode);
        result.add(arraySchedule);

        return result;
    }

}
