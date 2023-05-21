package one.terenin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_file")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity extends AbstractEntity{

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_location")
    private String fileLocation;

    //load content lazy
    @Column(name = "data")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] data;

    @ManyToOne()
    @JoinColumn(name = "maintainer")
    private UserEntity maintainer;

}
