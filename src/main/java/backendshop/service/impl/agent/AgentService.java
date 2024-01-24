package backendshop.service.impl.agent;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.AgentsDTO;
import backendshop.model.dto.request.AgentRequest;
import backendshop.model.dto.response.AgentResponse;
import backendshop.model.entity.Agents;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AgentService {
    String create(AgentsDTO agentsDTO) throws CustomersException;
    String update(Long id,AgentsDTO agentsDTO) throws CustomersException;

    AgentsDTO findByAllId(Long id) throws CustomersException;

    List<Agents> findAll();
    String deleteFlag(Long id) throws CustomersException;

    List<AgentsDTO> findAllBySearch(String search,Integer startId, Integer endId,String field, String sort, Integer page, Integer limit );

//    File CSV
    void exportToCsv(HttpServletResponse response, List<Agents> data) throws IOException;

}
