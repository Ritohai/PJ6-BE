package backendshop.mapper;


import backendshop.model.dto.ShopDTO;
import backendshop.model.entity.Shops;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    ShopDTO shopToShopDTO(Shops shop);

    Shops ShopDTOToShop(ShopDTO shopDTO);
}