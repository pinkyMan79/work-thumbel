package one.terenin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import one.terenin.dto.util.Position;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_user")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractEntity{

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "photo")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte photo;

    @Column(name = "bio")
    private String biography;

    @OneToMany(mappedBy = "maintainer", fetch = FetchType.EAGER)
    private Set<FileEntity> files;

    @OneToMany
    @JoinColumn(name = "friends")
    private List<UserEntity> friends;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

}
