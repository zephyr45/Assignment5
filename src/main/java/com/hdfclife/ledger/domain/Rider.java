package com.hdfclife.ledger.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "riders")
public class Rider {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true) private String code;
    @Column(nullable = false) private String name;
    @ManyToMany(mappedBy = "riders", fetch = FetchType.LAZY) private Set<Policy> policies = new HashSet<>();
    protected Rider() { }
    public String getCode() { return code; }
    public String getName() { return name; }
}
