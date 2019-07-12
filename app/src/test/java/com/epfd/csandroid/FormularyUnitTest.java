package com.epfd.csandroid;

import com.epfd.csandroid.formulary.BusinessFormulary;
import com.epfd.csandroid.models.Kid;
import com.epfd.csandroid.utils.Utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FormularyUnitTest {

    private Kid getAlexi(){
        return new Kid("Doe","alexi","CP", Utils.BOY);
    }

    private Kid getSophie(){
        return new Kid("dOe","SOPHIE","Cm1", Utils.GIRL);
    }

    private Kid getJohn(){
        return new Kid("snow","John","cm2", Utils.BOY);
    }

    private List<Kid> getKids(){
        List<Kid> kids = new ArrayList<>();
        kids.add(getAlexi());
        kids.add(getSophie());
        kids.add(getJohn());
        return kids;
    }

    @Test
    public void testIfKidNamesAreOkForDataBase(){
        List<Kid> kids = getKids();
        assertEquals("Alexi DOE,Sophie DOE,John SNOW", BusinessFormulary.getKidNameList(kids));
    }

    @Test
    public void testIfKidClassroomsAreOkForDataBase(){
        List<Kid> kids = getKids();
        assertEquals("CP,CM1,CM2", BusinessFormulary.getKidClassroomList(kids));
    }

    @Test
    public void testIfKidGenderAreOkForDataBase(){
        List<Kid> kids = getKids();
        assertEquals("BOY,GIRL,BOY", BusinessFormulary.getKidGenderList(kids));
    }

}
