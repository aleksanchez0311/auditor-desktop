package cu.lacumbre.auditor.repository;

import cu.lacumbre.auditor.model.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de unidades de medida
 */
@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Long> {

    /**
     * Busca unidades de medida activas
     */
    List<MeasureUnit> findByActiveTrue();

    /**
     * Busca una unidad de medida por nombre
     */
    Optional<MeasureUnit> findByNameAndActiveTrue(String name);

    /**
     * Busca una unidad de medida por abreviación
     */
    Optional<MeasureUnit> findByAbbreviationAndActiveTrue(String abbreviation);

    /**
     * Verifica si existe una unidad de medida con el nombre dado
     */
    boolean existsByNameAndActiveTrue(String name);

    /**
     * Verifica si existe una unidad de medida con la abreviación dada
     */
    boolean existsByAbbreviationAndActiveTrue(String abbreviation);
}