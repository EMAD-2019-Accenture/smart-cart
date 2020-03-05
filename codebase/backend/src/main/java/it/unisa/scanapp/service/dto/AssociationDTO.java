package it.unisa.scanapp.service.dto;

public class AssociationDTO {

    private String keyBarcode;
    private String associatedBarcode;

    public String getKeyBarcode() {
        return keyBarcode;
    }

    public void setKeyBarcode(String keyBarcode) {
        this.keyBarcode = keyBarcode;
    }

    public String getAssociatedBarcode() {
        return associatedBarcode;
    }

    public void setAssociatedBarcode(String associatedBarcode) {
        this.associatedBarcode = associatedBarcode;
    }


}
