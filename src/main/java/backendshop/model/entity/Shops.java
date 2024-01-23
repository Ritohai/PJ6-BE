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
public class Shops extends GeneralClass{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owners ownerId;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "shop_phone")
    private String shopPhone;
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "owner_phone")
    private String ownerPhone;
    // tỉnh
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "prefectures_id",nullable = false)
    private Prefectures prefecturesId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shop_brand",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id"))
    private Set<Brands> brand;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shop_plans",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id"))
    private Set<Plans> plans;
    @Column(name = "zip_code")
    private Integer zipCode;
    @Column(name = "store_status")
    private Boolean storeStatus;
}
