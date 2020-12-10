package edu.michaelszeler.homebudget.HomeBudgetServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "security")
public class Security implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "authority", nullable = false)
    private String authority;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    public Security() {
    }

    public Security(Integer id, User user, String authority, Boolean enabled) {
        this.id = id;
        this.user = user;
        this.authority = authority;
        this.enabled = enabled;
    }
}
