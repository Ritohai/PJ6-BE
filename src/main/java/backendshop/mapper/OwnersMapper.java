package backendshop.mapper;

import backendshop.model.dto.OwnersDTO;
import backendshop.model.entity.Agents;
import backendshop.model.entity.Owners;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "string")
public interface OwnersMapper {
    OwnersMapper INSTANCE = Mappers.getMapper(OwnersMapper.class);

    @Mapping(source = "agentId.id", target = "agentId")
    OwnersDTO OwnersToOwnersDTO(Owners owners);

    @Mapping(source = "agentId", target = "agentId.id")
    Owners OwnersDTOToOwners(OwnersDTO ownersDTO);

    default Long map(Agents agent) {
        return agent != null ? agent.getId() : null;
    }
}
