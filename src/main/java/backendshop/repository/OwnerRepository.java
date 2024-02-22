package backendshop.repository;

import backendshop.model.entity.Owners;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owners, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query(value = "SELECT owner FROM Owners owner WHERE LOWER(owner.firstName) LIKE %:search% OR LOWER(owner.lastName) LIKE %:search% OR LOWER(owner.email) LIKE %:search% OR LOWER(owner.phone) LIKE %:search% OR owner.id between :startId and :endId")
    Page<Owners> searchByFirstNameOrLastNameOrEmailOrPhoneOrId(@Param("search") String search, @Param("startId") Integer startId, @Param("endId") Integer endId, Pageable pageable);

}
