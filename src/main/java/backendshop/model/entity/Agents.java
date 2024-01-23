package backendshop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Agents extends GeneralClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    @Column(name = "parent_agent_id")
//    private Long parentId;
    @Column(name = "agent_code")
    private String agentCode;
    @Column(name = "agent_name")
    private String agentName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String passWord;
    @Column(name = "bank_code")
    private String bankCode;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "account_number")
    private Long accountNumber;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "group_agent")
    private String group;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id", nullable = false)
//    private Role roles;
    private RoleUser roleUser ;

}
