package backendshop.repository;

import backendshop.model.entity.Prefectures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefecturesRepository extends JpaRepository<Prefectures, Long> {
}
