package backendshop.controller;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.BrandDTO;
import backendshop.model.entity.Brands;
import backendshop.service.impl.brand.BrandService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping
    public ResponseEntity<List<Brands>> getAllBrand(
            @RequestParam(required = false) Integer startId,
            @RequestParam(required = false) Integer endId,
            @RequestParam(name = "brandName", defaultValue = "", required = false) String brandName,
            @RequestParam(name = "url", defaultValue = "",required = false) String url,
//            @RequestParam(defaultValue = "id") String field,
//            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int limit

    ) throws CustomersException {
        return new ResponseEntity<>(brandService.findAllShow(brandName,url,startId, endId , page, limit), HttpStatus.OK);

    }

    // lấy thông tin brands theo id
    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getById(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(brandService.findById(id), HttpStatus.OK);
    }

    // thêm brand mới
    @PostMapping("/addBrand")
    public ResponseEntity<BrandDTO> addBrand(@Valid @ModelAttribute BrandDTO brandDTO) throws IOException {
        return new ResponseEntity<>(brandService.addBrand(brandDTO), HttpStatus.CREATED);
    }

    // Chỉnh sửa thông tin brand theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@Valid @ModelAttribute BrandDTO brandDTO) throws CustomersException {
        return new ResponseEntity<>(brandService.updateBrand(brandDTO), HttpStatus.OK);
    }

    // xóa brand theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(brandService.delete(id), HttpStatus.OK);
    }

    //    File CSV
    @GetMapping("/csv")
    public ResponseEntity<Void> exportCsv(HttpServletResponse response,
                                          @RequestParam(required = false) Integer startId,
                                          @RequestParam(required = false) Integer endId,
                                          @RequestParam(name = "brandName", defaultValue = "", required = false) String brandName,
                                          @RequestParam(name = "url", defaultValue = "",required = false) String url,
//            @RequestParam(defaultValue = "id") String field,
//            @RequestParam(defaultValue = "asc") String sort,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "2") int limit) throws IOException {
        List<Brands> data = brandService.findAllShow(brandName, url, startId, endId, page, limit);
        brandService.exportToCsv(response, data);
        return ResponseEntity.ok().build();
    }
}
