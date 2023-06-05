package one.terenin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_forum")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ForumEntity extends AbstractEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "forum")
    //@JoinColumn(name = "messages")
    private Set<MessageEntity> messages;

}
