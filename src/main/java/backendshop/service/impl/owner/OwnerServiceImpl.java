package backendshop.service.impl.owner;

import backendshop.exception.customer.CustomersException;
import backendshop.mapper.OwnersMapper;
import backendshop.model.dto.OwnersDTO;
import backendshop.model.entity.Agents;
import backendshop.model.entity.Owners;
import backendshop.repository.AgentRepository;
import backendshop.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService{
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Override
    public String create(OwnersDTO ownersDTO) throws CustomersException {
        if (ownerRepository.existsByEmail(ownersDTO.getEmail())) {
            throw  new CustomersException("Email đã tồn tại.");
        }
        if (ownerRepository.existsByPhone(ownersDTO.getPhone())) {
            throw new CustomersException("Số điện thoại đã ồn tại.");
        }
        Agents agents = agentRepository.findById(ownersDTO.getAgentId()).get();
        if (agents.isDeleteFlag()) {
            throw new CustomersException("Đại lý không tồn tại.");
        }
        Owners owners = OwnersMapper.INSTANCE.OwnersDTOToOwners(ownersDTO);
        ownerRepository.save(owners);
        return "Đăng kí thành công.";
    }

    @Override
    public String update(Long id, OwnersDTO ownersDTO) throws CustomersException {
        if (findByAllId(id) != null) {
            Owners owners = OwnersMapper.INSTANCE.OwnersDTOToOwners(ownersDTO);
            owners.setId(id);
            ownerRepository.save(owners);
            return "Sửa thành công.";
        }
        throw new CustomersException("Không tìm thấy id.");
    }

    @Override
    public OwnersDTO findByAllId(Long id) throws CustomersException {
        Optional<Owners> ownersOptional = ownerRepository.findById(id);
        if (ownersOptional.isPresent()) {
            return OwnersMapper.INSTANCE.OwnersToOwnersDTO(ownersOptional.get());
        }
        throw new CustomersException("Không tìm thấy id.");
    }

    @Override
    public List<Owners> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public String deleteFlag(Long id) throws CustomersException {
        Optional<Owners> optionalOwners = ownerRepository.findById(id);
        if (optionalOwners.isPresent() && !optionalOwners.get().isDeleteFlag()) {
            Owners owners = optionalOwners.get();
            owners.setDeleteFlag(!owners.isDeleteFlag());
            ownerRepository.save(owners);
            return "Xóa thành công.";
        } else {
            throw new CustomersException("Không thấy id.");
        }
    }
}
