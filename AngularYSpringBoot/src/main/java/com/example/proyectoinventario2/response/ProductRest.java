package com.example.proyectoinventario2.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductRest {

    private ArrayList<HashMap<String,String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String date) {
        HashMap<String,String> meta = new HashMap<String,String>();
        meta.put("Tipo",type);
        meta.put("Codigo",code);
        meta.put("Date",date);

        metadata.add(meta);
    }
}
