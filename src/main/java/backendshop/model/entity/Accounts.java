package backendshop.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Accounts extends GeneralClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "pass_word")
    private String passWord;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_accounts",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agents agentId;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "shop_id")
    private Long shopId;
    @Column(name = "activated_flag")
    private Boolean activatedFlag;
}
