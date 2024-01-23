package backendshop.service.impl.owner;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.OwnersDTO;
import backendshop.model.entity.Owners;

import java.util.List;

public interface OwnerService {
    String create(OwnersDTO ownersDTO) throws CustomersException;
    String update(Long id,OwnersDTO ownersDTO) throws CustomersException;

    OwnersDTO findByAllId(Long id) throws CustomersException;

    List<Owners> findAll();
    String deleteFlag(Long id) throws CustomersException;
}
