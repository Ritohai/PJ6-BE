package backendshop.service.impl.shop;

import backendshop.exception.customer.CustomersException;
import backendshop.mapper.ShopMapper;
import backendshop.model.dto.ShopDTO;
import backendshop.model.entity.Shops;
import backendshop.repository.ShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopsRepository shopRepository;

    @Override
    public List<ShopDTO> findAllShow(String search, String filed, String sort, Integer page, Integer limit) throws CustomersException {
        Sort sort1 = Sort.by(filed);
        Page<Shops> shopPage = shopRepository.findAllBySearch(search, PageRequest.of(page, limit).withSort(sort1));
        List<ShopDTO> shopDTOList = new ArrayList<>();
        for (Shops shop : shopPage) {
            if (!shop.isDeleteFlag()) {
                ShopDTO shopDTO = ShopMapper.INSTANCE.shopToShopDTO(shop);
                shopDTOList.add(shopDTO);
            }
        }
        return shopDTOList;
    }

    @Override
    public ShopDTO findById(Long id) throws CustomersException {
        Optional<Shops> s = shopRepository.findById(id);
        if (s.isPresent() && s.get().isDeleteFlag()) {
            return ShopMapper.INSTANCE.shopToShopDTO(s.get());
        }
        throw new CustomersException("Không tìm thấy shop");
    }


    @Override
    public ShopDTO addShop(ShopDTO shopDTO) {
        try {
            Shops shop = ShopMapper.INSTANCE.ShopDTOToShop(shopDTO);
            Shops saveShop = shopRepository.save(shop);
            return ShopMapper.INSTANCE.shopToShopDTO(saveShop);
        } catch (Exception e) {
            throw new RuntimeException("" + e.getMessage());
        }
    }

    @Override
    public ShopDTO updateShop(ShopDTO shopDTO) throws CustomersException {
        try {
            Long shopId = shopDTO.getId();
            if (shopId == null) {
                throw new CustomersException("ID Shop không được rỗng");
            }
            Optional<Shops> existingShopOptional = shopRepository.findById(shopId);
            if (existingShopOptional.isPresent()) {
                Shops existingShop = existingShopOptional.get();
                // cập nhật các thuộc tính
                existingShop.setOwnerId(shopDTO.getOwnerId());
                existingShop.setCompanyName(shopDTO.getCompanyName());
                existingShop.setShopName(shopDTO.getShopName());
                existingShop.setShopPhone(shopDTO.getShopPhone());
                existingShop.setOwnerName(shopDTO.getOwnerName());
                existingShop.setOwnerPhone(shopDTO.getOwnerPhone());
                existingShop.setPrefecturesId(shopDTO.getPrefecturesId());
                existingShop.setBrand(shopDTO.getBrand());
                existingShop.setPlans(shopDTO.getPlans());
                existingShop.setZipCode(shopDTO.getZipCode());
                existingShop.setStoreStatus(shopDTO.getStoreStatus());
                existingShop.setDeleteFlag(shopDTO.isDeleteFlag());
                Shops updateShop = shopRepository.save(existingShop);
                return ShopMapper.INSTANCE.shopToShopDTO(updateShop);
            } else {
                throw new CustomersException("Shop không được tìm thấy");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi cập nhật " + e.getMessage(), e);
        }
    }

    @Override
    public String delete(Long id) throws CustomersException {
        Optional<Shops> shop = shopRepository.findById(id);
        if (shop.isPresent() && shop.get().isDeleteFlag()) {
            Shops shop1 = shop.get();
            shop1.setDeleteFlag(false);
            shopRepository.save(shop1);
            return "Xóa thành công !";
        }
        throw new CustomersException("Shop Không tồn tại");
    }
}
