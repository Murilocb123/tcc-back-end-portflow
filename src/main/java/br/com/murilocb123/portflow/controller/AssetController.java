package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.AssetEntity;
import br.com.murilocb123.portflow.dto.AssetDTO;
import br.com.murilocb123.portflow.service.AssetService;
import br.com.murilocb123.portflow.mapper.AssetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Page<AssetDTO> list(Pageable pageable) {
        Page<AssetEntity> page = assetService.list(pageable);
        List<AssetDTO> dtos = page.getContent().stream().map(AssetMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }
}
