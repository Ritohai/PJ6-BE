package backendshop.model.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentResponse {
    private Long id;
    private String agentCode;
    private String agentName;
    private String email;
    private String bankCode;
    private String bankName;
    private Long accountNumber;
    private String accountName;
    private String group;
    private String roles;
}
