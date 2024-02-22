package backendshop.service.impl.shop;


import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.ShopDTO;

import java.io.IOException;
import java.util.List;

public interface ShopService  {
    List<ShopDTO> findAllShow(String search, String filed, String sort, Integer page, Integer limit) throws CustomersException;

    ShopDTO findById(Long id) throws CustomersException;

    ShopDTO addShop(ShopDTO shopDTO) throws IOException;
    ShopDTO updateShop(ShopDTO shopDTO) throws CustomersException;
    String delete(Long id)throws CustomersException;
}