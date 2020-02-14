package it.unisa.scanapp.service.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecommendationSingleton {

    private Map<String, String> associations;

    public RecommendationSingleton() {
        associations = new ConcurrentHashMap<>();
    }


    public String getAssociatedBarcodeOf(String productBarcode){
        return associations.get(productBarcode);
    }

    public void setAssociationFor(String keyBarcode, String associatedBarcode){
        associations.putIfAbsent(keyBarcode, associatedBarcode);
    }
}
