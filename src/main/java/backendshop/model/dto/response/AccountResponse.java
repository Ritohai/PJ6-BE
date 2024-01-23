package backendshop.model.dto.response;

import backendshop.model.entity.Agents;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String loginId;
    private String passWord;
    private Set<String> roleId;
    private Agents agentId;
    private Long ownerId;
    private Long shopId;
    private Boolean activatedFlag;
}
