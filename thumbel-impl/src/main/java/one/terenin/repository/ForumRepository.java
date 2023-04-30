package one.terenin.repository;

import one.terenin.entity.ForumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {
}
