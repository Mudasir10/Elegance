package com.nanodevs.elegance.Pojo;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class Measurements {

    // Show these Text View
    private String etLenght;
    private String etShoulder;
    private String etcolr;
    private String etchest;
    private String etstomachSize;
    private String etloosingchest;
    private String etloosingstomach;
    private String etDescription;


    private String etwrist;
    private String etthigh;
    private String etwaist;
    private String ethipSize;
    private String etpentlength;
    private String etpentbottom;
    private String etloosinghip;
    private String etarms;
    private String etSleeves;


    public void SetMeasurementsForWaistCoat(String etLenght, String etShoulder, String etcolr, String etchest, String etstomachSize, String etloosingchest, String etloosingstomach, String etDescription) {

        this.etLenght = etLenght;
        this.etShoulder = etShoulder;
        this.etcolr = etcolr;
        this.etchest = etchest;
        this.etstomachSize = etstomachSize;
        this.etloosingchest = etloosingchest;
        this.etloosingstomach = etloosingstomach;
        this.etDescription = etDescription;

    }


    public void SetMeasurementsForPant(String etthigh, String etwaist, String ethipSize, String etDescription, String etpentlength, String etpentbottom) {
        this.etthigh = etthigh;
        this.etwaist = etwaist;
        this.ethipSize = ethipSize;
        this.etDescription = etDescription;
        this.etpentlength = etpentlength;
        this.etpentbottom = etpentbottom;
    }





    public void  SetMeasurementsForThreePiece(String etLenght, String etSleeves, String etShoulder, String etcolr, String etchest, String etstomachSize, String ethipSize, String etwrist, String etpentlength, String etpentbottom, String etthigh, String etwaist, String etDescription,String etarms) {
        this.etLenght = etLenght;
        this.etSleeves = etSleeves;
        this.etShoulder = etShoulder;
        this.etcolr = etcolr;
        this.etchest = etchest;
        this.etstomachSize = etstomachSize;
        this.ethipSize = ethipSize;
        this.etwrist = etwrist;
        this.etpentlength = etpentlength;
        this.etpentbottom = etpentbottom;
        this.etthigh = etthigh;
        this.etwaist = etwaist;
        this.etarms=etarms;
        this.etDescription = etDescription;
    }







    public void SetMeasurementsForSaffariCoat(String etLenght, String etSleeves, String etShoulder, String etcolr, String etchest, String etstomachSize, String ethipSize, String etwrist, String etpentlength, String etpentbottom, String etthigh, String etwaist, String etDescription,String etarms) {

        this.etLenght = etLenght;
        this.etSleeves = etSleeves;
        this.etShoulder = etShoulder;
        this.etcolr = etcolr;
        this.etchest = etchest;
        this.etstomachSize = etstomachSize;
        this.ethipSize = ethipSize;
        this.etwrist = etwrist;
        this.etpentlength = etpentlength;
        this.etpentbottom = etpentbottom;
        this.etthigh = etthigh;
        this.etwaist = etwaist;
        this.etDescription = etDescription;
        this.etarms=etarms;
    }





    public void SetMeasurementsForSuit(String etLenght, String etSleeves, String etShoulder, String etcolr, String etchest, String etstomachSize, String etarms, String etwrist, String etloosingchest, String etloosingstomach, String etDescription, String ethipSize, String etloosinghip, String etpentlength, String etpentbottom) {

        this.etLenght = etLenght;
        this.etSleeves = etSleeves;
        this.etShoulder = etShoulder;
        this.etcolr = etcolr;
        this.etchest = etchest;
        this.etstomachSize = etstomachSize;
        this.etarms = etarms;
        this.etwrist = etwrist;
        this.etloosingchest = etloosingchest;
        this.etloosingstomach = etloosingstomach;
        this.etDescription = etDescription;
        this.ethipSize = ethipSize;
        this.etloosinghip = etloosinghip;
        this.etpentlength = etpentlength;
        this.etpentbottom = etpentbottom;
    }






    public void SetMeasurementsForShirt(String etLenght, String etSleeves, String etShoulder, String etcolr, String etchest, String etstomachSize, String etarms, String etwrist, String etloosingchest, String etloosingstomach, String etDescription) {

        this.etLenght = etLenght;
        this.etSleeves = etSleeves;
        this.etShoulder = etShoulder;
        this.etcolr = etcolr;
        this.etchest = etchest;
        this.etstomachSize = etstomachSize;
        this.etarms = etarms;
        this.etwrist = etwrist;
        this.etloosingchest = etloosingchest;
        this.etloosingstomach = etloosingstomach;
        this.etDescription = etDescription;

    }




    public Measurements() {

    }

    public void SetMeasurementsForKurta(String etLenght, String etSleeves, String etShoulder, String etcolr,
                                        String etchest, String etstomachSize, String ethipSize, String etarms, String etwrist, String etloosingchest, String etloosingstomach, String etloosinghip, String etpentlength, String etpentbottom, String etDescription) {
        this.etLenght = etLenght;
        this.etSleeves = etSleeves;
        this.etShoulder = etShoulder;
        this.etcolr = etcolr;
        this.etchest = etchest;
        this.etstomachSize = etstomachSize;
        this.ethipSize = ethipSize;
        this.etarms = etarms;
        this.etwrist = etwrist;
        this.etloosingchest = etloosingchest;
        this.etloosingstomach = etloosingstomach;
        this.etloosinghip = etloosinghip;
        this.etpentlength = etpentlength;
        this.etpentbottom = etpentbottom;
        this.etDescription = etDescription;
    }


    public Map<String,Object> KurtatoMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("length",etLenght);
        result.put("Sleeves",etSleeves);
        result.put("Shoulder",etShoulder);
        result.put("coler",etcolr);
        result.put("chest",etchest);
        result.put("stomachSize",etstomachSize);
        result.put("hipSize",ethipSize);
        result.put("armsSize",etarms);
        result.put("wristSize",etwrist);
        result.put("loosingChest",etloosingchest);
        result.put("loosingStomach",etloosingstomach);
        result.put("loosingHip",etloosinghip);
        result.put("pantLength",etpentlength);
        result.put("pantBottom",etpentbottom);
        result.put("des",etDescription);


        return result;
    }

    public Map<String,Object> ShirttoMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("length",etLenght);
        result.put("Sleeves",etSleeves);
        result.put("Shoulder",etShoulder);
        result.put("coler",etcolr);
        result.put("chest",etchest);
        result.put("stomachSize",etstomachSize);
        result.put("armsSize",etarms);
        result.put("wristSize",etwrist);
        result.put("loosingChest",etloosingchest);
        result.put("loosingStomach",etloosingstomach);
        result.put("des",etDescription);
        return result;
    }


    public Map<String,Object> SuitToMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("length",etLenght);
        result.put("Sleeves",etSleeves);
        result.put("Shoulder",etShoulder);
        result.put("coler",etcolr);
        result.put("chest",etchest);
        result.put("stomachSize",etstomachSize);
        result.put("armsSize",etarms);
        result.put("wristSize",etwrist);
        result.put("loosingChest",etloosingchest);
        result.put("loosingStomach",etloosingstomach);
        result.put("hipSize",ethipSize);
        result.put("loosingHip",etloosinghip);
        result.put("pantLength",etpentlength);
        result.put("pantBottom",etpentbottom);
        result.put("des",etDescription);

        return result;
    }





    public Map<String,Object> SaffariCoatToMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("length",etLenght);
        result.put("Sleeves",etSleeves);
        result.put("Shoulder",etShoulder);
        result.put("coler",etcolr);
        result.put("chest",etchest);
        result.put("stomachSize",etstomachSize);
        result.put("hipSize",ethipSize);
        result.put("wristSize",etwrist);
        result.put("pantLength",etpentlength);
        result.put("pantBottom",etpentbottom);
        result.put("waist",etwaist);
        result.put("thigh",etthigh);
        result.put("des",etDescription);
        result.put("arms",etarms);
        return result;
    }




    public Map<String,Object> ThreePiecetToMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("length",etLenght);
        result.put("Sleeves",etSleeves);
        result.put("Shoulder",etShoulder);
        result.put("coler",etcolr);
        result.put("chest",etchest);
        result.put("stomachSize",etstomachSize);
        result.put("hipSize",ethipSize);
        result.put("wristSize",etwrist);
        result.put("pantLength",etpentlength);
        result.put("pantBottom",etpentbottom);
        result.put("waist",etwaist);
        result.put("thigh",etthigh);
        result.put("des",etDescription);
        result.put("arms",etarms);
        return result;
    }


    public Map<String,Object> PanttToMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("pantLength",etpentlength);
        result.put("pantBottom",etpentbottom);
        result.put("waist",etwaist);
        result.put("thigh",etthigh);
        result.put("hipSize",ethipSize);
        result.put("des",etDescription);
        return result;
    }


    public Map<String,Object> WaistCoattToMap(){

        HashMap<String,Object> result=new HashMap<>();
        result.put("length",etLenght);
        result.put("shoulder",etShoulder);
        result.put("coler",etcolr);
        result.put("chest",etchest);
        result.put("stomachSize",etstomachSize);
        result.put("loosingChest",etloosingchest);
        result.put("loosingStomach",etloosingstomach);
        result.put("des",etDescription);
        return result;
    }









}
