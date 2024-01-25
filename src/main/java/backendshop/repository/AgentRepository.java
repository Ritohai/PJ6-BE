package backendshop.repository;

import backendshop.model.entity.Agents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agents, Long> {
    boolean existsByAgentCode(String agentCode);
    boolean existsByEmail(String email);

    @Query(value = "SELECT agent FROM Agents agent WHERE LOWER(agent.agentCode) like %:search%   OR LOWER(agent.agentName) LIKE %:search% OR LOWER(agent.chargeName) LIKE %:search% OR agent.id between :startId AND :endId" )
//    Page<Agents> searchByAgentCodeOrAccountNameOrGroupOrId(@Param("search") String search, @Param("startId") Integer startId,
//                                                           @Param("endId") Integer endId,  Pageable pageable);

    Page<Agents> searchByAgentCodeOrAccountNameOrChargeNameOrId( String search,  Integer startId,  Integer endId, Pageable pageable);


}
