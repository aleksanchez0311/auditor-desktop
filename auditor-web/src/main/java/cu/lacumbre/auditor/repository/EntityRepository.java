package cu.lacumbre.auditor.repository;

import cu.lacumbre.auditor.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de entidades comerciales
 */
@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {

    /**
     * Busca entidades activas
     */
    List<Entity> findByActiveTrue();

    /**
     * Busca una entidad por código
     */
    Optional<Entity> findByCodeAndActiveTrue(String code);

    /**
     * Busca entidades por nombre (búsqueda parcial, insensible a mayúsculas)
     */
    @Query("SELECT e FROM Entity e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) AND e.active = true")
    List<Entity> findByNameContainingIgnoreCaseAndActiveTrue(@Param("name") String name);

    /**
     * Verifica si existe una entidad con el código dado (excluyendo una entidad específica)
     */
    @Query("SELECT COUNT(e) > 0 FROM Entity e WHERE e.code = :code AND e.id != :id AND e.active = true")
    boolean existsByCodeAndIdNotAndActiveTrue(@Param("code") String code, @Param("id") Long id);

    /**
     * Verifica si existe una entidad con el código dado
     */
    boolean existsByCodeAndActiveTrue(String code);
}