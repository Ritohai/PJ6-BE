package backendshop.controller;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.AgentsDTO;
import backendshop.model.dto.request.AgentRequest;
import backendshop.model.dto.response.AgentResponse;
import backendshop.model.entity.Agents;
import backendshop.service.impl.agent.AgentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;
    @PostMapping("/register")
    public ResponseEntity<String> create(@Valid @RequestBody AgentsDTO agentsDTO) throws CustomersException {
        return new ResponseEntity<>(agentService.create(agentsDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody AgentsDTO agentsDTO,@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(agentService.update(id,agentsDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Agents>> findAll() {
        return new ResponseEntity<>(agentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentsDTO> findById(@PathVariable Long id) throws CustomersException{
        return new ResponseEntity<>(agentService.findByAllId(id), HttpStatus.OK);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(agentService.deleteFlag(id), HttpStatus.OK);
    }

}
