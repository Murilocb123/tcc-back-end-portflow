package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.AssetHistoryEntity;
import br.com.murilocb123.portflow.domain.identifier.AssetPriceId;
import br.com.murilocb123.portflow.dto.AssetHistoryDTO;
import br.com.murilocb123.portflow.mapper.AssetHistoryMapper;
import br.com.murilocb123.portflow.service.AssetHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asset-history")
@RequiredArgsConstructor
public class AssetHistoryController {
    private final AssetHistoryService assetHistoryService;

    @PostMapping
    public AssetHistoryDTO create(@RequestBody AssetHistoryDTO dto) {
        AssetHistoryEntity entity = AssetHistoryMapper.toEntity(dto);
        AssetHistoryEntity saved = assetHistoryService.create(entity);
        return AssetHistoryMapper.toDTO(saved);
    }

    @GetMapping("/{assetId}/{date}")
    public AssetHistoryDTO getById(@PathVariable UUID assetId, @PathVariable String date) {
        return AssetHistoryMapper.toDTO(assetHistoryService.getById(assetId, date));
    }

    @PutMapping("/{assetId}/{date}")
    public AssetHistoryDTO update(@PathVariable UUID assetId, @PathVariable String date, @RequestBody AssetHistoryDTO dto) {
        AssetHistoryEntity entity = AssetHistoryMapper.toEntity(dto);
        AssetHistoryEntity updated = assetHistoryService.update(assetId, date, entity);
        return AssetHistoryMapper.toDTO(updated);
    }

    @DeleteMapping("/{assetId}/{date}")
    public void delete(@PathVariable UUID assetId, @PathVariable String date) {
        assetHistoryService.delete(assetId, date);
    }

    @GetMapping
    public Page<AssetHistoryDTO> list(Pageable pageable) {
        Page<AssetHistoryEntity> page = assetHistoryService.list(pageable);
        List<AssetHistoryDTO> dtos = page.getContent().stream().map(AssetHistoryMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }
}
