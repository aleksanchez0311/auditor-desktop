package cu.lacumbre.auditor.service;

import cu.lacumbre.auditor.dto.ProductDTO;
import cu.lacumbre.auditor.mapper.ProductMapper;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.model.MeasureUnit;
import cu.lacumbre.auditor.model.Product;
import cu.lacumbre.auditor.model.TPVCategory;
import cu.lacumbre.auditor.repository.MeasureUnitRepository;
import cu.lacumbre.auditor.repository.ProductRepository;
import cu.lacumbre.auditor.repository.TPVCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final EntityService entityService;
    private final MeasureUnitRepository measureUnitRepository;
    private final TPVCategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, 
                         ProductMapper productMapper,
                         EntityService entityService,
                         MeasureUnitRepository measureUnitRepository,
                         TPVCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.entityService = entityService;
        this.measureUnitRepository = measureUnitRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Obtiene todos los productos activos de una entidad
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByEntity(Long entityId) {
        Entity entity = entityService.getEntityById(entityId);
        List<Product> products = productRepository.findByEntityAndActiveTrue(entity);
        return productMapper.toDTOList(products);
    }

    /**
     * Obtiene productos con paginación
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByEntityWithPagination(Long entityId, Pageable pageable) {
        Entity entity = entityService.getEntityById(entityId);
        Page<Product> products = productRepository.findByEntityAndActiveTrue(entity, pageable);
        return products.map(productMapper::toDTO);
    }

    /**
     * Busca un producto por ID
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findById(Long id) {
        return productRepository.findById(id)
                .filter(Product::getActive)
                .map(productMapper::toDTO);
    }

    /**
     * Busca un producto por código y entidad
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findByCodeAndEntity(String code, Long entityId) {
        Entity entity = entityService.getEntityById(entityId);
        return productRepository.findByCodeAndEntityAndActiveTrue(code, entity)
                .map(productMapper::toDTO);
    }

    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByNameContaining(String name, Long entityId) {
        Entity entity = entityService.getEntityById(entityId);
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndEntityAndActiveTrue(name, entity);
        return productMapper.toDTOList(products);
    }

    /**
     * Obtiene productos vendibles de una entidad
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findSellableByEntity(Long entityId) {
        Entity entity = entityService.getEntityById(entityId);
        List<Product> products = productRepository.findByEntityAndIsSellableTrueAndActiveTrue(entity);
        return productMapper.toDTOList(products);
    }

    /**
     * Obtiene productos con stock bajo
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findLowStockProducts(Long entityId) {
        Entity entity = entityService.getEntityById(entityId);
        List<Product> products = productRepository.findLowStockProducts(entity);
        return productMapper.toDTOList(products);
    }

    /**
     * Crea un nuevo producto
     */
    public ProductDTO create(ProductDTO productDTO) {
        Entity entity = entityService.getEntityById(productDTO.getEntityId());

        // Validar que el código no exista
        if (productDTO.getCode() != null && 
            productRepository.existsByCodeAndEntityAndActiveTrue(productDTO.getCode(), entity)) {
            throw new IllegalArgumentException("Ya existe un producto con el código: " + productDTO.getCode());
        }

        Product product = productMapper.toEntity(productDTO);
        product.setEntity(entity);
        product.setActive(true);

        // Establecer unidad de medida si se proporciona
        if (productDTO.getMeasureUnitId() != null) {
            MeasureUnit measureUnit = measureUnitRepository.findById(productDTO.getMeasureUnitId())
                    .orElseThrow(() -> new IllegalArgumentException("Unidad de medida no encontrada"));
            product.setMeasureUnit(measureUnit);
        }

        // Establecer categoría si se proporciona
        if (productDTO.getCategoryId() != null) {
            TPVCategory category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            product.setCategory(category);
        }

        // Establecer valores por defecto
        if (product.getStock() == null) {
            product.setStock(BigDecimal.ZERO);
        }
        if (product.getMinStock() == null) {
            product.setMinStock(BigDecimal.ZERO);
        }
        if (product.getIsBuildable() == null) {
            product.setIsBuildable(false);
        }
        if (product.getIsSellable() == null) {
            product.setIsSellable(true);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    /**
     * Actualiza un producto existente
     */
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .filter(Product::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        Entity entity = entityService.getEntityById(productDTO.getEntityId());

        // Validar que el código no exista en otro producto
        if (productDTO.getCode() != null && 
            productRepository.existsByCodeAndEntityAndIdNotAndActiveTrue(productDTO.getCode(), entity, id)) {
            throw new IllegalArgumentException("Ya existe otro producto con el código: " + productDTO.getCode());
        }

        productMapper.updateEntityFromDTO(productDTO, existingProduct);
        existingProduct.setEntity(entity);

        // Actualizar unidad de medida
        if (productDTO.getMeasureUnitId() != null) {
            MeasureUnit measureUnit = measureUnitRepository.findById(productDTO.getMeasureUnitId())
                    .orElseThrow(() -> new IllegalArgumentException("Unidad de medida no encontrada"));
            existingProduct.setMeasureUnit(measureUnit);
        } else {
            existingProduct.setMeasureUnit(null);
        }

        // Actualizar categoría
        if (productDTO.getCategoryId() != null) {
            TPVCategory category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
            existingProduct.setCategory(category);
        } else {
            existingProduct.setCategory(null);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Elimina un producto (soft delete)
     */
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .filter(Product::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        product.setActive(false);
        productRepository.save(product);
    }

    /**
     * Actualiza el stock de un producto
     */
    public ProductDTO updateStock(Long id, BigDecimal newStock) {
        Product product = productRepository.findById(id)
                .filter(Product::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        product.setStock(newStock);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Ajusta el stock de un producto (suma o resta)
     */
    public ProductDTO adjustStock(Long id, BigDecimal adjustment) {
        Product product = productRepository.findById(id)
                .filter(Product::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        BigDecimal currentStock = product.getStock() != null ? product.getStock() : BigDecimal.ZERO;
        BigDecimal newStock = currentStock.add(adjustment);
        
        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        product.setStock(newStock);
        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    /**
     * Obtiene un producto por ID (para uso interno)
     */
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .filter(Product::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }

    /**
     * Cuenta productos activos por entidad
     */
    @Transactional(readOnly = true)
    public long countByEntity(Long entityId) {
        Entity entity = entityService.getEntityById(entityId);
        return productRepository.countByEntityAndActiveTrue(entity);
    }
}