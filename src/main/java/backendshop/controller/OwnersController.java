package backendshop.controller;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.OwnersDTO;
import backendshop.model.entity.Owners;
import backendshop.service.impl.owner.OwnerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnersController {
    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody OwnersDTO ownersDTO) throws CustomersException {
        return new ResponseEntity<>(ownerService.create(ownersDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Owners>> findAll() {
        return new ResponseEntity<>(ownerService.findAll(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Owners>> findAllBySearch(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer startId,
            @RequestParam(required = false) Integer endId,
            @RequestParam(defaultValue = "id") String field,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int limit
    ) {
        return new ResponseEntity<>(ownerService.findAllBySearch(search, startId, endId, field, sort, page, limit), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnersDTO> findById(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(ownerService.findByAllId(id), HttpStatus.OK);
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlag(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(ownerService.deleteFlag(id), HttpStatus.OK);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,@RequestBody OwnersDTO ownersDTO) throws CustomersException {
        return new ResponseEntity<>(ownerService.update(id, ownersDTO), HttpStatus.OK);
    }

    @GetMapping("/csvOwner")
    public ResponseEntity<Void> exportCsv(HttpServletResponse response,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false) Integer startId,
                                          @RequestParam(required = false) Integer endId,
                                          @RequestParam(defaultValue = "id") String field,
                                          @RequestParam(defaultValue = "asc") String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "3") int limit) throws IOException {
        List<Owners> data = ownerService.findAllBySearch(search,startId,endId,field,sort,page,limit);
        ownerService.exportToCSVOwner(response, data);
        return ResponseEntity.ok().build();
    }

}
