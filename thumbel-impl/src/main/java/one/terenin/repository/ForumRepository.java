package one.terenin.repository;

import one.terenin.dto.special.ForumUserInfo;
import one.terenin.entity.ForumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// non-caching
@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {

    @Query("from ForumEntity forum ")
    List<ForumEntity> findForumEntityByTitle(String title, Pageable pageable);
    boolean existsByTitle(String title);

    @Query("select count(tm) from ForumEntity forum\n" +
            "    inner join MessageEntity tm on forum.id = tm.forum.id WHERE forum.id = ? and tm.senderLogin = ?\n" +
            "        and (select count(msg) as message from MessageEntity msg).message > 5 group by tm.senderLogin, forum.title")
    Integer specialQuery(UUID forumId, String senderLogin);

    // мне надо сделать запрос с подзапросом который будет выводить вот это (форум + логин + сколько сообщений этот пользователь отправил на этот форум)

}

