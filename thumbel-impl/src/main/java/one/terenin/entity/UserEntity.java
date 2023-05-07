package one.terenin.entity;

import liquibase.pro.packaged.S;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import one.terenin.dto.util.Position;
import one.terenin.entity.common.Role;
import one.terenin.entity.common.State;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(name = "login", columnNames = "login"),
        @UniqueConstraint(name = "hashPassword", columnNames = "password")
    })
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractEntity{

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String hashPassword;

    @Column(name = "photo")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public boolean isAdmin(){
        return role.equals(Role.ADMIN);
    }
    public boolean isUser(){
        return role.equals(Role.USER);
    }
    public boolean isGuest(){
        return role.equals(Role.GUEST);
    }
    public boolean isActive(){
        return state.equals(State.NOT_BANNED);
    }
    public boolean isBanned(){
        return state.equals(State.BANNED);
    }

}
