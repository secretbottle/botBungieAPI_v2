package net.bungie.model;

import org.hibernate.Hibernate;

public abstract class AbstractBaseEntity {

  protected Long id;

  protected AbstractBaseEntity() {
  }

  protected AbstractBaseEntity(long id) {
    this.id = id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public String toString() {
    return getClass().getSimpleName() + ":" + id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || !getClass().equals(Hibernate.getClass(o))) {
      return false;
    }
    AbstractBaseEntity that = (AbstractBaseEntity) o;
    return id != null && id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return (int) (long) id;
  }

}
