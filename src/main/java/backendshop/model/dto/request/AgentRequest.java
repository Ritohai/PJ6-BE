package backendshop.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentRequest {
    @NotEmpty(message = "Không để trống.")
    private String agentCode;
    @NotEmpty(message = "Không để trống.")
    private String agentName;
    @NotEmpty(message = "Không để trống.")
    private String email;
    @NotEmpty(message = "Không để trống.")
    private String passWord;
    @NotEmpty(message = "Không để trống.")
    private String bankCode;
    @NotEmpty(message = "Không để trống.")
    private String bankName;
    private Long accountNumber;
    @NotEmpty(message = "Không để trống.")
    private String accountName;
    @NotEmpty(message = "Không để trống.")
    private String group;
    private String roles;


}
