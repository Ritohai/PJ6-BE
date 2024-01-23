package backendshop.model.dto;

import backendshop.model.entity.Agents;
import jakarta.persistence.Column;
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
    private String phone ;
    private String company;
    private String city;
    private int zipCode1;
    private int zipCode2;
    private String address;

}
