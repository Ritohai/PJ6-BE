package backendshop.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Prefectures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pref_name")
    private String prefecturesName;
    // quận
    @Column(name = "district")
    private String district;
    // phường
    @Column(name = "wards")
    private String wards;
    @Column(name = "zip_code_1")
    private int zipcode1;
    @Column(name = "zip_code_2")
    private int zipCode2;

}
