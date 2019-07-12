package com.epfd.csandroid.formulary;

import com.epfd.csandroid.models.Kid;

import java.util.List;

public class BusinessFormulary {

    public static String getKidNameList(List<Kid> kids){
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (Kid kid : kids){
            result.append(prefix);
            prefix = ",";
            result.append(kid.getPrenom().substring(0,1).toUpperCase()).append(kid.getPrenom().substring(1).toLowerCase()).append(" ").append(kid.getNom().toUpperCase());
        }
        return result.toString();
    }

    public static String getKidClassroomList(List<Kid> kids){
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (Kid kid : kids){
            result.append(prefix);
            prefix = ",";
            result.append(kid.getClasse().toUpperCase());
        }
        return result.toString();
    }

    public static String getKidGenderList(List<Kid> kids){
        StringBuilder result = new StringBuilder();
        String prefix = "";
        for (Kid kid : kids){
            result.append(prefix);
            prefix = ",";
            result.append(kid.getGenre());
        }
        return result.toString();
    }



}
