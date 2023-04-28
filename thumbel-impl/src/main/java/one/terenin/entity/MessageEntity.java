package one.terenin.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Getter
@Setter
@Entity
@Table(name = "t_msg")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity extends AbstractEntity{

    @Column(name = "content")
    private String content;

    @ManyToOne()
    @JoinColumn(name = "forum_id")
    private ForumEntity forumId;

    @Column(name = "sender_login")
    private String senderLogin;

}
