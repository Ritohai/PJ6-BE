package backendshop.controller;

import backendshop.exception.customer.CustomersException;
import backendshop.model.dto.ShopDTO;
import backendshop.service.impl.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping()
    public ResponseEntity<List<ShopDTO>> getAllShop(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "id") String filed,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int limit
    ) throws CustomersException {
        return new ResponseEntity<>(shopService.findAllShow(search, filed, sort, page, limit), HttpStatus.OK);
    }

    // lấy thông tin shop theo id
    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getById(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(shopService.findById(id), HttpStatus.OK);
    }

    // Thêm mới Shop
    @PostMapping("/addShop")
    public ResponseEntity<ShopDTO> addShop(@ModelAttribute ShopDTO shopDTO) throws IOException {
        return new ResponseEntity<>(shopService.addShop(shopDTO), HttpStatus.CREATED);
    }

    //Chỉnh sửa thông tin shop theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ShopDTO> updateShop(@ModelAttribute ShopDTO shopDTO) throws CustomersException {
        return new ResponseEntity<>(shopService.updateShop(shopDTO), HttpStatus.OK);
    }

    // Xóa Shop theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Long id) throws CustomersException {
        return new ResponseEntity<>(shopService.delete(id), HttpStatus.OK);
    }
}
