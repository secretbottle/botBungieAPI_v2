package net.bungie.model;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class User extends AbstractBaseEntity {
  
  protected String name;

  protected User() {
  }

  protected User(Integer id, String name) {
    super(id);
    this.name = name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return super.toString() + '(' + name + ')';
  }
}
