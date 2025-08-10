package cu.lacumbre.auditor.controller;

import cu.lacumbre.auditor.dto.ProductDTO;
import cu.lacumbre.auditor.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para la gestión de productos
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Productos", description = "API para la gestión de productos")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener productos por entidad")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<List<ProductDTO>> getProductsByEntity(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId) {
        List<ProductDTO> products = productService.findByEntity(entityId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener productos por entidad con paginación")
    @ApiResponse(responseCode = "200", description = "Página de productos obtenida exitosamente")
    @GetMapping("/entity/{entityId}/paginated")
    public ResponseEntity<Page<ProductDTO>> getProductsByEntityPaginated(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            Pageable pageable) {
        Page<ProductDTO> products = productService.findByEntityWithPagination(entityId, pageable);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID del producto") @PathVariable Long id) {
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener producto por código y entidad")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/entity/{entityId}/code/{code}")
    public ResponseEntity<ProductDTO> getProductByCodeAndEntity(
            @Parameter(description = "Código del producto") @PathVariable String code,
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId) {
        return productService.findByCodeAndEntity(code, entityId)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente")
    @GetMapping("/entity/{entityId}/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId,
            @Parameter(description = "Nombre a buscar") @RequestParam String name) {
        List<ProductDTO> products = productService.findByNameContaining(name, entityId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener productos vendibles por entidad")
    @ApiResponse(responseCode = "200", description = "Lista de productos vendibles obtenida exitosamente")
    @GetMapping("/entity/{entityId}/sellable")
    public ResponseEntity<List<ProductDTO>> getSellableProductsByEntity(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId) {
        List<ProductDTO> products = productService.findSellableByEntity(entityId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Obtener productos con stock bajo")
    @ApiResponse(responseCode = "200", description = "Lista de productos con stock bajo obtenida exitosamente")
    @GetMapping("/entity/{entityId}/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId) {
        List<ProductDTO> products = productService.findLowStockProducts(entityId);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Crear nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Ya existe un producto con el mismo código")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.create(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @Operation(summary = "Actualizar producto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "409", description = "Ya existe otro producto con el mismo código")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ID del producto") @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.update(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID del producto") @PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar stock de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @Parameter(description = "ID del producto") @PathVariable Long id,
            @Parameter(description = "Nuevo stock") @RequestParam BigDecimal stock) {
        try {
            ProductDTO updatedProduct = productService.updateStock(id, stock);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Ajustar stock de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock ajustado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Ajuste inválido (stock negativo)"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/stock/adjust")
    public ResponseEntity<ProductDTO> adjustStock(
            @Parameter(description = "ID del producto") @PathVariable Long id,
            @Parameter(description = "Ajuste de stock (positivo o negativo)") @RequestParam BigDecimal adjustment) {
        try {
            ProductDTO updatedProduct = productService.adjustStock(id, adjustment);
            return ResponseEntity.ok(updatedProduct);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("no encontrado")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @Operation(summary = "Contar productos por entidad")
    @ApiResponse(responseCode = "200", description = "Conteo realizado exitosamente")
    @GetMapping("/entity/{entityId}/count")
    public ResponseEntity<Long> countProductsByEntity(
            @Parameter(description = "ID de la entidad") @PathVariable Long entityId) {
        long count = productService.countByEntity(entityId);
        return ResponseEntity.ok(count);
    }
}