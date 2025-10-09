package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import br.com.murilocb123.portflow.dto.AssetDTO;
import br.com.murilocb123.portflow.dto.AssetFilterDTO;
import br.com.murilocb123.portflow.mapper.AssetMapper;
import br.com.murilocb123.portflow.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @PostMapping
    public AssetDTO create(@RequestBody AssetDTO dto) {
        AssetEntity entity = AssetMapper.toEntity(dto);
        AssetEntity saved = assetService.create(entity);
        return AssetMapper.toDTO(saved);
    }

    @GetMapping("/{id}")
    public AssetDTO getById(@PathVariable UUID id) {
        return AssetMapper.toDTO(assetService.getById(id));
    }

    @PutMapping("/{id}")
    public AssetDTO update(@PathVariable UUID id, @RequestBody AssetDTO dto) {
        AssetEntity entity = AssetMapper.toEntity(dto);
        AssetEntity updated = assetService.update(id, entity);
        return AssetMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        assetService.delete(id);
    }

    @GetMapping
    public Page<AssetDTO> list(Pageable pageable, AssetFilterDTO filter) {
        Page<AssetEntity> page = assetService.list(pageable, filter);
        List<AssetDTO> dtos = page.getContent().stream().map(AssetMapper::toDTO).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @GetMapping("/all")
    public List<AssetDTO> listAll() {
        return assetService.listAll().stream().map(AssetMapper::toDTO).toList();
    }

    @GetMapping("/available")
    public List<AssetDTO> listAllByAssetsAvailable() {
        return assetService.listAllByAssetsAvailable().stream().map(AssetMapper::toDTO).toList();
    }
}
