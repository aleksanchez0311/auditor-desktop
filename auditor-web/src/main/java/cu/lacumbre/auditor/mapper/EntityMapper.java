package cu.lacumbre.auditor.mapper;

import cu.lacumbre.auditor.dto.EntityDTO;
import cu.lacumbre.auditor.model.Entity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper para convertir entre Entity y EntityDTO
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityMapper {

    /**
     * Convierte una entidad a DTO
     */
    EntityDTO toDTO(Entity entity);

    /**
     * Convierte una lista de entidades a DTOs
     */
    List<EntityDTO> toDTOList(List<Entity> entities);

    /**
     * Convierte un DTO a entidad
     */
    Entity toEntity(EntityDTO entityDTO);

    /**
     * Actualiza una entidad existente con los datos del DTO
     */
    void updateEntityFromDTO(EntityDTO entityDTO, @MappingTarget Entity entity);
}