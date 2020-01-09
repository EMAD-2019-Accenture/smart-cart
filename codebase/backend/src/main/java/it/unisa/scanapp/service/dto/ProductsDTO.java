package it.unisa.scanapp.service.dto;

import java.util.List;

public class ProductsDTO {

    private List<Long> productsId;


    public List<Long> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<Long> productsId) {
        this.productsId = productsId;
    }
}
