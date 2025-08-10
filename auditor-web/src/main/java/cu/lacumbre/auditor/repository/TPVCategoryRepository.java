package cu.lacumbre.auditor.repository;

import cu.lacumbre.auditor.model.TPVCategory;
import cu.lacumbre.auditor.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de categorías TPV
 */
@Repository
public interface TPVCategoryRepository extends JpaRepository<TPVCategory, Long> {

    /**
     * Busca categorías activas por entidad
     */
    List<TPVCategory> findByEntityAndActiveTrueOrderBySortOrder(Entity entity);

    /**
     * Busca una categoría por nombre y entidad
     */
    Optional<TPVCategory> findByNameAndEntityAndActiveTrue(String name, Entity entity);

    /**
     * Verifica si existe una categoría con el nombre dado en la entidad
     */
    boolean existsByNameAndEntityAndActiveTrue(String name, Entity entity);

    /**
     * Verifica si existe una categoría con el nombre dado (excluyendo una categoría específica)
     */
    @Query("SELECT COUNT(c) > 0 FROM TPVCategory c WHERE c.name = :name AND c.entity = :entity AND c.id != :id AND c.active = true")
    boolean existsByNameAndEntityAndIdNotAndActiveTrue(@Param("name") String name, @Param("entity") Entity entity, @Param("id") Long id);

    /**
     * Cuenta categorías activas por entidad
     */
    long countByEntityAndActiveTrue(Entity entity);
}