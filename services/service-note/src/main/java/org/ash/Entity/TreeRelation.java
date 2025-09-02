package org.ash.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tree_relation")
public class TreeRelation {

    @Id
    @Column(name = "ancestor")
    private String ancestor;

    @Id
    @Column(name = "descendant")
    private String descendant;

    @Column(name = "depth")
    private int depth;

    @Column(name = "user_account")
    private long userAccount;
}