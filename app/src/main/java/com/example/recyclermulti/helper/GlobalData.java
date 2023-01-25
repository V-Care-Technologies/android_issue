package com.example.recyclermulti.helper;

import com.example.recyclermulti.models.res.QuestionsDataItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static LatLng EndLocation = new LatLng(0, 0);

    public static QuestionsDataItem questionsDataItem;
    public static boolean isSignature = false;

    public static final String LANGUAGE_SELECTED="Default_EN";
    public static final String PHOTO_PATH="photo_path";
    public static final String VIDEO_PATH="VIDEO_path";


    public static final String SERVER_URL = "https://resel.co.in/dat/";
}
