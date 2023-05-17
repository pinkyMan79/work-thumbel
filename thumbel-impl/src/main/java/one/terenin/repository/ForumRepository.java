package one.terenin.repository;

import one.terenin.entity.ForumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// non-caching
@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {

    ForumEntity findForumEntityByTitle(String title, Pageable pageable);
    boolean existsByTitle(String title);

}

