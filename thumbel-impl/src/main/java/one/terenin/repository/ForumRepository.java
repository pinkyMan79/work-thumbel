package one.terenin.repository;

import one.terenin.entity.ForumEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// non-caching
@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {

    ForumEntity findForumEntityByTitle(String title);
    boolean existsByTitle(String title);

}

