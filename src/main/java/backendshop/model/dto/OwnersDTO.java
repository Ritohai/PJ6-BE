package backendshop.model.dto;


import backendshop.model.entity.RoleUser;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OwnersDTO {
    private Long agentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String address;
    private String roleUser = RoleUser.OWNER.toString();
}
