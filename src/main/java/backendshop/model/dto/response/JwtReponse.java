package backendshop.model.dto.response;

import backendshop.model.entity.Users;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtReponse {
    private Users users;
    private String token;
    private final String type = "Bearer";
    private Set<String> roles;
}
