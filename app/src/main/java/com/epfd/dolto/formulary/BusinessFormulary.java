package com.epfd.dolto.formulary;

import com.epfd.dolto.models.Kid;

import java.util.Arrays;
import java.util.List;

public class BusinessFormulary {

    //Transform List of Kid Objects to String name with ',' sperator
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

    //Transform List of Kid Objects to String classroom with ',' sperator
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

    //Transform List of Kid Objects to String gender with ',' sperator
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

    //Transform String with ',' sperator to List of String
    public static List<String> getStringListToKids(String myList){
        return Arrays.asList(myList.split(","));
    }

    //Extract List of String name with to List of String FullName
    public static String getStringNameFromKid(String myKidFullName){
        List<String> tempList = Arrays.asList(myKidFullName.split(" "));
        StringBuilder subPart = new StringBuilder();
        String prefix = "";
        for (String part : tempList){
            subPart.append(prefix);
            if (!part.equals(tempList.get(0))) {
                prefix = " ";
                subPart.append(part);
            }
        }
        return subPart.toString();
    }

    //Extract List of String forname with to List of String FullName
    public static String getStringFornameFromKid(String myKidFullName){
        List<String> tempList = Arrays.asList(myKidFullName.split(" "));
        return tempList.get(0);
    }



}
