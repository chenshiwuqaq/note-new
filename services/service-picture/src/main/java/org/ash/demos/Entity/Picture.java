package org.ash.demos.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "note_picture")
@Data
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "path", length = 255)
    private String path;

    @Column(name = "url", length = 500)
    private String url;

    private Long size;

    @Column(name = "create_datetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    private Long account;

    @Column(name = "update_datetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDatetime;

    @Column(name = "level", length = 255)
    private String level;

    // 无参构造函数
    public Picture() {
    }
}
