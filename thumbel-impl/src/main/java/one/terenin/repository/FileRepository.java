package one.terenin.repository;

import one.terenin.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// non-caching
@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

    FileEntity findFileEntityByFileName(String fileName);

   /* @Query("select friends.files from UserEntity.friends friends" +
            " join fetch UserEntity u where u.id = :uuid")
    List<FileEntity> findAllFriendsFilesByUserEntityId(UUID uuid);*/

}
