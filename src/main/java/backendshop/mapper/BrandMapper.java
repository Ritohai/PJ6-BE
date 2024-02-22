package backendshop.mapper;


import backendshop.model.dto.BrandDTO;
import backendshop.model.entity.Brands;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
    BrandDTO brandToBrandDTO(Brands brands);
    Brands brandDTOToBrand(BrandDTO brandDTO);
}