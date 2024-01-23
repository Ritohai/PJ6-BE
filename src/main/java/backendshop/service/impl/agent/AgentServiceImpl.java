package backendshop.service.impl.agent;

import backendshop.exception.customer.CustomersException;
import backendshop.mapper.AgentsMapper;
import backendshop.model.dto.AgentsDTO;
import backendshop.model.entity.Agents;
import backendshop.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
