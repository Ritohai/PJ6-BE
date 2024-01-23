package backendshop.repository;

import backendshop.model.entity.Agents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agents, Long> {
    boolean existsByAgentCode(String agentCode);
    boolean existsByEmail(String email);


}
