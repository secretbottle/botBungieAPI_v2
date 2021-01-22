package net.bungie.model;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "telegram_id", name = "user_telegram_id")})
public class User extends AbstractBaseEntity {

  @Column(name = "telegram_id",nullable = false, unique = true)
  @NotBlank
  @Size(max = 100)
  private String telegram_id;

  @Column(name = "membership_id", nullable = false)
  @NotBlank
  @Size(max = 100)
  private String membership_id;

  @Column(name = "nickname", nullable = false)
  @NotBlank
  @Size(max = 100)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "user_platforms", joinColumns = @JoinColumn(name = "user_id"),
      uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "platform"}, name = "user_platforms_idx")})
  @Column(name = "platform")
  @ElementCollection(fetch = FetchType.EAGER)
  @BatchSize(size = 200)
  private Set<Platform> platforms;
}
