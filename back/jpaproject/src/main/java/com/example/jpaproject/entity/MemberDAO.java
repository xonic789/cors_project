package com.example.jpaproject.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;


@Entity
@Table(name="member")
public class MemberDAO {

    public MemberDAO() {

    }
    public MemberDAO (Long id, String email) {
       this.id = id;
       this.email = email;
    }
    @Id
    @Column(name = "id", nullable=false, columnDefinition = "long")
    private Long id;

    @Column(name = "email", nullable=false, length=256, columnDefinition = "nvarchar2")
    private String email;
}
