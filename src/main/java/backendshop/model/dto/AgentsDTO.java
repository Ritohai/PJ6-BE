package backendshop.model.dto;

import backendshop.model.entity.RoleUser;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AgentsDTO {
    private Long id;
    private String agentCode;
    private String agentName;
    private String chargeName;
    private String email;
    private String bankCode;
    private String bankName;
    private Long accountNumber;
    private String accountName;
    private String group;
    private String roleUser = RoleUser.AGENT.toString();


}
