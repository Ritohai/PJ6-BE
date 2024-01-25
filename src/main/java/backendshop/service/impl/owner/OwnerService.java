package backendshop.service.impl.owner;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.OwnersDTO;
import backendshop.model.entity.Owners;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public interface OwnerService {
    String create(OwnersDTO ownersDTO) throws CustomersException;
    String update(Long id,OwnersDTO ownersDTO) throws CustomersException;

    OwnersDTO findByAllId(Long id) throws CustomersException;

    List<Owners> findAll();
    String deleteFlag(Long id) throws CustomersException;

    List<Owners> findAllBySearch(String search, Integer startId, Integer endId, String field, String sort, Integer page, Integer limit);

//    Export file CSV
    void exportToCSVOwner(HttpServletResponse response, List<Owners> owners) throws IOException;

}
