package backendshop.service.impl.agent;

import backendshop.exception.customer.CustomersException;
import backendshop.mapper.AgentsMapper;
import backendshop.model.dto.AgentsDTO;
import backendshop.model.entity.Agents;
import backendshop.model.entity.RoleUser;
import backendshop.repository.AgentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String create(AgentsDTO agentsDTO) throws CustomersException {
        if (agentRepository.existsByAgentCode(agentsDTO.getAgentCode())) {
            throw new CustomersException("Mã code agent đã tồn tại.");
        }
        if (agentRepository.existsByEmail(agentsDTO.getEmail())) {
            throw new CustomersException("Email đã tồn tại.");
        }
        Agents agents = AgentsMapper.INSTANCE.AgentsDTOToAgents(agentsDTO);
        agentRepository.save(agents);
        return "Đăng kí thành công.";
    }

    @Override
    public String update(Long id, AgentsDTO agentsDTO) throws CustomersException {
        if (findByAllId(id) != null) {
            Agents agents = AgentsMapper.INSTANCE.AgentsDTOToAgents(agentsDTO);
            if (agentRepository.existsByAgentCode(agentsDTO.getAgentCode())) {
                throw new CustomersException("Đã tồn tại mã code. Nhập mã khác.");
            }
            if (agentRepository.existsByEmail(agentsDTO.getEmail())) {
                throw new CustomersException("Đã tồn tại email. Nhập email khác.");
            }

            agents.setId(id);
            agentRepository.save(agents);
            return "Sửa thành công.";
        }
        throw new CustomersException("Không tìm thấy id.");
    }

    @Override
    public AgentsDTO findByAllId(Long id) throws CustomersException {
        Optional<Agents> optionalAgents = agentRepository.findById(id);
        if (optionalAgents.isPresent()) {
            return AgentsMapper.INSTANCE.AgentsToAgentDTO(optionalAgents.get());
        }
        throw new CustomersException("Không thấy id.");
    }

    @Override
    public List<Agents> findAll() {
        return agentRepository.findAll();
    }

    @Override
    public String deleteFlag(Long id) throws CustomersException {
        Optional<Agents> optionalAgents = agentRepository.findById(id);
        if (optionalAgents.isPresent() && !optionalAgents.get().isDeleteFlag()) {
            Agents agents = optionalAgents.get();
            agents.setDeleteFlag(!agents.isDeleteFlag());
            agentRepository.save(agents);
            return "Xóa thành công.";
        } else {
            throw new CustomersException("Không thấy id.");
        }
    }

    @Override
    public List<Agents> findAllBySearch(String search, Integer startId, Integer endId, String field, String sort, Integer page, Integer limit) {
        Sort sort1 = Sort.by(field);
        Page<Agents> agents = null;
        Pageable pageable = PageRequest.of(page, limit).withSort(sort1);
        if (startId != null && endId != null) {
            agents = agentRepository.searchByAgentCodeOrAccountNameOrChargeNameOrId(null, startId, endId, pageable);
        }else {
            agents = agentRepository.searchByAgentCodeOrAccountNameOrChargeNameOrId(search,null, null, PageRequest.of(page, limit).withSort(sort1));
        }

        List<Agents> agentsList = new ArrayList<>();
        if (agents != null && agents.hasContent()) {
            for (Agents agent : agents) {
                agentsList.add(agent);
            }
        }
        return agentsList;
    }


    //    File CSV
    @Override
    public void exportToCsv(HttpServletResponse response, List<Agents> data) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data_agent.csv");

        try (PrintWriter writer = response.getWriter();
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("id","create_date", "update_date", "agent_code","agent_name", "charge_name", "email", "bank_code","back_name","account_number","account_name","group_agent","role_user", "status"))) {
            for (Agents row : data) {
                csvPrinter.printRecord(row.getId(),row.getCreateDate(), row.getUpdateDate(), row.getAgentCode(), row.getAgentName(),row.getChargeName(), row.getEmail(), row.getBankCode(), row.getBankName(), row.getAccountNumber(), row.getAccountName(),row.getGroup(), row.getRoleUser(), row.isDeleteFlag());
            }
        }
    }
}
