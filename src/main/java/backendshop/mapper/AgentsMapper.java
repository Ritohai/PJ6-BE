package backendshop.mapper;

import backendshop.model.dto.AgentsDTO;
import backendshop.model.entity.Agents;
import backendshop.model.entity.RoleUser;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "string")
public interface AgentsMapper {
    AgentsMapper INSTANCE = Mappers.getMapper(AgentsMapper.class);

    AgentsDTO AgentsToAgentDTO(Agents agents);
    Agents AgentsDTOToAgents(AgentsDTO agentsDTO);


}
