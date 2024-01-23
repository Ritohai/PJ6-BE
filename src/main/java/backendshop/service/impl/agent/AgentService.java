package backendshop.service.impl.agent;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.AgentsDTO;
import backendshop.model.dto.request.AgentRequest;
import backendshop.model.dto.response.AgentResponse;
import backendshop.model.entity.Agents;

import java.util.List;

public interface AgentService {
    String create(AgentsDTO agentsDTO) throws CustomersException;
    String update(Long id,AgentsDTO agentsDTO) throws CustomersException;

    AgentsDTO findByAllId(Long id) throws CustomersException;

    List<Agents> findAll();
    String deleteFlag(Long id) throws CustomersException;
}
