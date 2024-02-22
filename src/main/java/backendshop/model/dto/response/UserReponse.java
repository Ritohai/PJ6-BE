package backendshop.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserReponse {
    private Long id;
    private String email;
    private String username;
    private String password;
    private Boolean status;
    private String roles;
}
