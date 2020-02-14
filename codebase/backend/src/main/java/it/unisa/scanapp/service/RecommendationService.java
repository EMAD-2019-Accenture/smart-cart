package it.unisa.scanapp.service;

import it.unisa.scanapp.domain.Customer;
import it.unisa.scanapp.domain.Product;
import it.unisa.scanapp.repository.ProductRepository;
import it.unisa.scanapp.service.dto.AssociationDTO;
import it.unisa.scanapp.service.dto.ProductsDTO;
import it.unisa.scanapp.service.util.RecommendationSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecommendationService {

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final CustomerService customerService;
    private final ProductRepository productRepository;

    @Autowired
    private RecommendationSingleton dictionary;

    public RecommendationService(CustomerService customerService, ProductRepository productRepository) {
        this.customerService = customerService;
        this.productRepository = productRepository;
    }

    public Optional<Product> getRecommendedFrom(ProductsDTO productList){
        Optional<Customer> currentCustomer = customerService.getCustomerFromCurrentUser();


        if(!currentCustomer.isPresent()){
            log.warn("Customer not present in recommendation request!");
            return Optional.empty();
        }

        List<Product> inputProducts = getProductListFromIdList(productList.getProductsId());
        log.info("Input products {}",inputProducts);

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
        String keyBarcode = inputProducts.get(0).getBarcode();
        return productRepository.findByBarcodeWithEagerRelationships(dictionary.getAssociatedBarcodeOf(keyBarcode));
    }


    public void createAssociation(AssociationDTO associationDTO) {
        dictionary.setAssociationFor(associationDTO.getKeyBarcode(),associationDTO.getAssociatedBarcode());
    }
}
