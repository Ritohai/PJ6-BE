package backendshop.controller;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.AgentsDTO;
import backendshop.model.entity.Agents;
import backendshop.service.impl.agent.AgentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentController {
    @Autowired
    private AgentService agentService;
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody AgentsDTO agentsDTO) throws CustomersException {
        return new ResponseEntity<>(agentService.create(agentsDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody AgentsDTO agentsDTO,@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(agentService.update(id,agentsDTO), HttpStatus.OK);
    }

    @GetMapping("/all")
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

    @GetMapping
    public ResponseEntity<List<Agents>> getAgent(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer startId,
            @RequestParam(required = false) Integer endId,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int limit
    ) {
        return new ResponseEntity<>(agentService.findAllBySearch(search,startId, endId,field,sort,page,limit), HttpStatus.OK);
    }

//    File CSV
    @GetMapping("/csv")
    public ResponseEntity<Void> exportCsv(HttpServletResponse response,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) Integer startId,
                                          @RequestParam(required = false) Integer endId,
                                          @RequestParam(defaultValue = "id") String field,
                                          @RequestParam(defaultValue = "asc") String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int limit
    ) throws IOException {
        List<Agents> data = agentService.findAllBySearch(search,startId,endId,field,sort,page,limit);
        agentService.exportToCsv(response, data);
        return ResponseEntity.ok().build();
    }

}
