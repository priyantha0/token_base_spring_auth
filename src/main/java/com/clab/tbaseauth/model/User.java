package com.clab.tbaseauth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User implements Serializable {
  @Serial private static final long serialVersionUID = 8103990967401860339L;

  @Id private String id;
  private String name;
  private String email;
  private String password;
}
