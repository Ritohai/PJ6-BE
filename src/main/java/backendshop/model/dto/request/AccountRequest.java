package backendshop.model.dto.request;

import backendshop.model.entity.Agents;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

    private String loginId;
    private String passWord;
    private Set<String> roleId;
    private Agents agentId;
    private Long ownerId;
    private Long shopId;

}
