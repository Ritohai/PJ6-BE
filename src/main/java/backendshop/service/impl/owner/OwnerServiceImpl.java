package backendshop.service.impl.owner;

import backendshop.exception.customer.CustomersException;
import backendshop.mapper.OwnersMapper;
import backendshop.model.dto.OwnersDTO;
import backendshop.model.entity.Agents;
import backendshop.model.entity.Owners;
import backendshop.repository.AgentRepository;
import backendshop.repository.OwnerRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Override
    public String create(OwnersDTO ownersDTO) throws CustomersException {
        if (ownerRepository.existsByEmail(ownersDTO.getEmail())) {
            throw new CustomersException("Email đã tồn tại.");
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

    @Override
    public List<Owners> findAllBySearch(String search, Integer startId, Integer endId, String field, String sort, Integer page, Integer limit) {
        Sort sort1 = Sort.by(field);
        Page<Owners> owners;
        Pageable pageable = PageRequest.of(page, limit).withSort(sort1);
        if (startId != null && endId != null) {
            owners = ownerRepository.searchByFirstNameOrLastNameOrEmailOrPhoneOrId(null, startId, endId, pageable);
        } else {
            owners = ownerRepository.searchByFirstNameOrLastNameOrEmailOrPhoneOrId(search, null, null, pageable);
        }

        List<Owners> list = new ArrayList<>();
        if (owners != null && owners.hasContent()) {
            for (Owners o : owners) {
                list.add(o);
            }
        }
        return list;
    }

    @Override
    public void exportToCSVOwner(HttpServletResponse response, List<Owners> owners) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data_owner.csv");
        try (
                PrintWriter writer = response.getWriter();
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id", "agent_id", "first_name", "last_name", "email", "phone", "company", "address", "role_user", "created_date", "update_date", " status"))) {
            for (Owners o: owners) {
                csvPrinter.printRecord(o.getId(),o.getAgentId(),o.getFirstName(),o.getLastName(),o.getEmail(),o.getPhone(),o.getCompany(),o.getAddress(),o.getRoleUser(), o.getCreateDate(), o.getUpdateDate(), o.isDeleteFlag());
            }
        }


    }
}
