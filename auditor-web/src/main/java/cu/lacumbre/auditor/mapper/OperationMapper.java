package cu.lacumbre.auditor.mapper;

import cu.lacumbre.auditor.dto.OperationDTO;
import cu.lacumbre.auditor.dto.OperationDetailDTO;
import cu.lacumbre.auditor.model.Operation;
import cu.lacumbre.auditor.model.OperationDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * Mapper para convertir entre Operation y OperationDTO
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OperationMapper {

    /**
     * Convierte una operación a DTO
     */
    @Mapping(source = "entity.id", target = "entityId")
    @Mapping(source = "entity.name", target = "entityName")
    OperationDTO toDTO(Operation operation);

    /**
     * Convierte una lista de operaciones a DTOs
     */
    List<OperationDTO> toDTOList(List<Operation> operations);

    /**
     * Convierte un DTO a operación (sin relaciones)
     */
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "details", ignore = true)
    Operation toEntity(OperationDTO operationDTO);

    /**
     * Actualiza una operación existente con los datos del DTO
     */
    @Mapping(target = "entity", ignore = true)
    @Mapping(target = "details", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(OperationDTO operationDTO, @MappingTarget Operation operation);

    /**
     * Convierte un detalle de operación a DTO
     */
    @Mapping(source = "operation.id", target = "operationId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.code", target = "productCode")
    OperationDetailDTO toDetailDTO(OperationDetail operationDetail);

    /**
     * Convierte una lista de detalles de operación a DTOs
     */
    List<OperationDetailDTO> toDetailDTOList(List<OperationDetail> operationDetails);

    /**
     * Convierte un DTO a detalle de operación (sin relaciones)
     */
    @Mapping(target = "operation", ignore = true)
    @Mapping(target = "product", ignore = true)
    OperationDetail toDetailEntity(OperationDetailDTO operationDetailDTO);

    /**
     * Actualiza un detalle de operación existente con los datos del DTO
     */
    @Mapping(target = "operation", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateDetailEntityFromDTO(OperationDetailDTO operationDetailDTO, @MappingTarget OperationDetail operationDetail);
}