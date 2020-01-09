package it.unisa.scanapp.service;

import it.unisa.scanapp.domain.Customer;
import it.unisa.scanapp.domain.Product;
import it.unisa.scanapp.repository.ProductRepository;
import it.unisa.scanapp.service.dto.ProductsDTO;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecommendationService {

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final CustomerService customerService;
    private final ProductRepository productRepository;

    public RecommendationService(CustomerService customerService, ProductRepository productRepository) {
        this.customerService = customerService;
        this.productRepository = productRepository;
    }

    public Optional<Product> getRecommendedFrom(ProductsDTO productList){
        Optional<Customer> currentCustomer = customerService.getCustomerFromCurrentUser();

        if(!currentCustomer.isPresent()){
            return Optional.empty();
        }

        List<Product> inputProducts = getProductListFromIdList(productList.getProductsId());

        return computeRecommendations(currentCustomer.get(),inputProducts);
    }

    private List<Product> getProductListFromIdList(List<Long> productsId) {
        List<Product> result = new LinkedList<>();
        productsId.forEach(id -> {
            Optional<Product> currentProduct = productRepository.findById(id);
            currentProduct.ifPresent(result::add);
        });
        return result;
    }

    private Optional<Product> computeRecommendations(Customer customer, List<Product> inputProducts) {
        DataModel model = new GenericDataModel(getData(inputProducts));
        return null;
    }

    private FastByIDMap<PreferenceArray> getData(List<Product> inputProducts) {
        return null;
    }


}