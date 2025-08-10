package cu.lacumbre.auditor.service;

import cu.lacumbre.auditor.dto.EntityDTO;
import cu.lacumbre.auditor.mapper.EntityMapper;
import cu.lacumbre.auditor.model.Entity;
import cu.lacumbre.auditor.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de entidades comerciales
 */
@Service
@Transactional
public class EntityService {

    private final EntityRepository entityRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public EntityService(EntityRepository entityRepository, EntityMapper entityMapper) {
        this.entityRepository = entityRepository;
        this.entityMapper = entityMapper;
    }

    /**
     * Obtiene todas las entidades activas
     */
    @Transactional(readOnly = true)
    public List<EntityDTO> findAllActive() {
        List<Entity> entities = entityRepository.findByActiveTrue();
        return entityMapper.toDTOList(entities);
    }

    /**
     * Busca una entidad por ID
     */
    @Transactional(readOnly = true)
    public Optional<EntityDTO> findById(Long id) {
        return entityRepository.findById(id)
                .filter(Entity::getActive)
                .map(entityMapper::toDTO);
    }

    /**
     * Busca una entidad por código
     */
    @Transactional(readOnly = true)
    public Optional<EntityDTO> findByCode(String code) {
        return entityRepository.findByCodeAndActiveTrue(code)
                .map(entityMapper::toDTO);
    }

    /**
     * Busca entidades por nombre (búsqueda parcial)
     */
    @Transactional(readOnly = true)
    public List<EntityDTO> findByNameContaining(String name) {
        List<Entity> entities = entityRepository.findByNameContainingIgnoreCaseAndActiveTrue(name);
        return entityMapper.toDTOList(entities);
    }

    /**
     * Crea una nueva entidad
     */
    public EntityDTO create(EntityDTO entityDTO) {
        // Validar que el código no exista
        if (entityDTO.getCode() != null && entityRepository.existsByCodeAndActiveTrue(entityDTO.getCode())) {
            throw new IllegalArgumentException("Ya existe una entidad con el código: " + entityDTO.getCode());
        }

        Entity entity = entityMapper.toEntity(entityDTO);
        entity.setActive(true);
        Entity savedEntity = entityRepository.save(entity);
        return entityMapper.toDTO(savedEntity);
    }

    /**
     * Actualiza una entidad existente
     */
    public EntityDTO update(Long id, EntityDTO entityDTO) {
        Entity existingEntity = entityRepository.findById(id)
                .filter(Entity::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Entidad no encontrada con ID: " + id));

        // Validar que el código no exista en otra entidad
        if (entityDTO.getCode() != null && 
            entityRepository.existsByCodeAndIdNotAndActiveTrue(entityDTO.getCode(), id)) {
            throw new IllegalArgumentException("Ya existe otra entidad con el código: " + entityDTO.getCode());
        }

        entityMapper.updateEntityFromDTO(entityDTO, existingEntity);
        Entity updatedEntity = entityRepository.save(existingEntity);
        return entityMapper.toDTO(updatedEntity);
    }

    /**
     * Elimina una entidad (soft delete)
     */
    public void delete(Long id) {
        Entity entity = entityRepository.findById(id)
                .filter(Entity::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Entidad no encontrada con ID: " + id));

        entity.setActive(false);
        entityRepository.save(entity);
    }

    /**
     * Verifica si existe una entidad con el código dado
     */
    @Transactional(readOnly = true)
    public boolean existsByCode(String code) {
        return entityRepository.existsByCodeAndActiveTrue(code);
    }

    /**
     * Obtiene una entidad por ID (para uso interno)
     */
    @Transactional(readOnly = true)
    public Entity getEntityById(Long id) {
        return entityRepository.findById(id)
                .filter(Entity::getActive)
                .orElseThrow(() -> new IllegalArgumentException("Entidad no encontrada con ID: " + id));
    }
}