package net.bungie.repository;

import java.util.Optional;
import javax.annotation.security.PermitAll;
import net.bungie.model.User;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends CrudRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    long deleteById(@Param("id") long id);

    @Query("SELECT u FROM User u WHERE u.telegram_id=:telegramId")
    Optional<User> findByTelegram_id(@Param("telegramId") long telegramId);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.telegram_id=:telegramId")
    long deleteByTelegram_id(@Param("telegramId") long telegramId);
}
