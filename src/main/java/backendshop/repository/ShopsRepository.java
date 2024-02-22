package backendshop.repository;

import backendshop.model.entity.Shops;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopsRepository extends JpaRepository<Shops, Long> {
    @Query(value = "SELECT s FROM Shops s WHERE LOWER(s.ownerName) LIKE %:search% " +
            "OR LOWER(s.companyName) LIKE %:search% " + "OR LOWER(s.ownerPhone) LIKE %:search%"
    )
    Page<Shops> findAllBySearch(@Param("search") String search, Pageable pageable);
}
