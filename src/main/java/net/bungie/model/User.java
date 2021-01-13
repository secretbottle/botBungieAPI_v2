package net.bungie.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User extends AbstractBaseEntity {
  
  private String name;
  private String telegram_id;
  private String membership_id;
  private String nickname;

  protected User() {
  }

  protected User(Integer id, String name) {
    super(id);
    this.name = name;
  }

}
