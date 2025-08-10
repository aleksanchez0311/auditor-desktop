package cu.lacumbre.auditor.repository;

import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de productos
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca productos activos por entidad
     */
    List<Product> findByEntityAndActiveTrue(Entity entity);

    /**
     * Busca productos activos por entidad con paginación
     */
    Page<Product> findByEntityAndActiveTrue(Entity entity, Pageable pageable);

    /**
     * Busca un producto por código y entidad
     */
    Optional<Product> findByCodeAndEntityAndActiveTrue(String code, Entity entity);

    /**
     * Busca productos por nombre (búsqueda parcial, insensible a mayúsculas)
     */
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.entity = :entity AND p.active = true")
    List<Product> findByNameContainingIgnoreCaseAndEntityAndActiveTrue(@Param("name") String name, @Param("entity") Entity entity);

    /**
     * Busca productos vendibles por entidad
     */
    List<Product> findByEntityAndIsSellableTrueAndActiveTrue(Entity entity);

    /**
     * Busca productos con stock bajo (menor al mínimo)
     */
    @Query("SELECT p FROM Product p WHERE p.entity = :entity AND p.stock < p.minStock AND p.active = true")
    List<Product> findLowStockProducts(@Param("entity") Entity entity);

    /**
     * Busca productos por categoría
     */
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.entity = :entity AND p.active = true")
    List<Product> findByCategoryAndEntity(@Param("categoryId") Long categoryId, @Param("entity") Entity entity);

    /**
     * Verifica si existe un producto con el código dado (excluyendo un producto específico)
     */
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.code = :code AND p.entity = :entity AND p.id != :id AND p.active = true")
    boolean existsByCodeAndEntityAndIdNotAndActiveTrue(@Param("code") String code, @Param("entity") Entity entity, @Param("id") Long id);

    /**
     * Verifica si existe un producto con el código dado
     */
    boolean existsByCodeAndEntityAndActiveTrue(String code, Entity entity);

    /**
     * Busca productos con precio en un rango específico
     */
    @Query("SELECT p FROM Product p WHERE p.entity = :entity AND p.salePrice BETWEEN :minPrice AND :maxPrice AND p.active = true")
    List<Product> findByPriceRange(@Param("entity") Entity entity, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Cuenta productos activos por entidad
     */
    long countByEntityAndActiveTrue(Entity entity);
}