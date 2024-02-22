package backendshop.service.impl.brand;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.BrandDTO;
import backendshop.model.entity.Brands;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface BrandService {

    List<Brands> findAllShow(String brandName, String url, Integer startId, Integer endId, Integer page, Integer limit) ;

    BrandDTO findById(Long id) throws CustomersException;

    BrandDTO addBrand(BrandDTO brandDTO) throws IOException;
    BrandDTO updateBrand(BrandDTO brandDTO) throws CustomersException;
    String delete(Long id)throws CustomersException;

    //    File CSV
    void exportToCsv(HttpServletResponse response, List<Brands> data) throws IOException;
}
