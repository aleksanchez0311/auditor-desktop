package cu.lacumbre.auditor.mapper;

import cu.lacumbre.auditor.dto.ProductDTO;
import cu.lacumbre.auditor.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper para convertir entre Product y ProductDTO
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    /**
     * Convierte un producto a DTO
     */
    @Mapping(source = "measureUnit.id", target = "measureUnitId")
    @Mapping(source = "measureUnit.name", target = "measureUnitName")
    @Mapping(source = "measureUnit.abbreviation", target = "measureUnitAbbreviation")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "entity.id", target = "entityId")
    @Mapping(source = "entity.name", target = "entityName")
    ProductDTO toDTO(Product product);

    /**
     * Convierte una lista de productos a DTOs
     */
    List<ProductDTO> toDTOList(List<Product> products);

    /**
     * Convierte un DTO a producto (sin relaciones)
     */
    @Mapping(target = "measureUnit", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "entity", ignore = true)
    Product toEntity(ProductDTO productDTO);

    /**
     * Actualiza un producto existente con los datos del DTO
     */
    @Mapping(target = "measureUnit", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(ProductDTO productDTO, @MappingTarget Product product);
}