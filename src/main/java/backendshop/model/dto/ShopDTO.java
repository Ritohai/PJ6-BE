package backendshop.model.dto;

import backendshop.model.entity.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ShopDTO {
    private Long id;
    private Owners ownerId;
    private String companyName;
    private String shopName;
    private String shopPhone;
    private String ownerName;
    private String ownerPhone;
    private Prefectures prefecturesId;
    private Set<Brands> brand;
    private Set<Plans> plans;
    private Integer zipCode;
    private Boolean storeStatus;
    private Date createDate;
    private Date updateDate;
    private boolean deleteFlag = false;
    private String roleUser = RoleUser.SHOP.toString();
}