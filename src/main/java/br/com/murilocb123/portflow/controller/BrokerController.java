package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.BrokerEntity;
import br.com.murilocb123.portflow.dto.BrokerDTO;
import br.com.murilocb123.portflow.dto.BrokerFilterDTO;
import br.com.murilocb123.portflow.mapper.BrokerMapper;
import br.com.murilocb123.portflow.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/broker")
@RequiredArgsConstructor
public class BrokerController {
    private final BrokerService brokerService;

    @PostMapping
    public BrokerDTO create(@RequestBody BrokerDTO dto) {
        BrokerEntity entity = BrokerMapper.toEntity(dto);
        BrokerEntity saved = brokerService.create(entity);
        return BrokerMapper.toDTO(saved);
    }

    @GetMapping("/{id}")
    public BrokerDTO getById(@PathVariable UUID id) {
        return BrokerMapper.toDTO(brokerService.getById(id));
    }

    @PutMapping("/{id}")
    public BrokerDTO update(@PathVariable UUID id, @RequestBody BrokerDTO dto) {
        BrokerEntity entity = BrokerMapper.toEntity(dto);
        BrokerEntity updated = brokerService.update(id, entity);
        return BrokerMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        brokerService.delete(id);
    }

    @GetMapping
    public Page<BrokerDTO> list(Pageable pageable, BrokerFilterDTO filter) {
        Page<BrokerEntity> page = brokerService.list(pageable, filter);
        List<BrokerDTO> dtos = page.getContent().stream().map(BrokerMapper::toDTO).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @GetMapping("/all")
    public List<BrokerDTO> listAll() {
        return brokerService.listAll().stream().map(BrokerMapper::toDTO).toList();
    }

    @GetMapping("/available/asset/{assetId}")
    public List<BrokerDTO> listAllByBrokersAvailable(@PathVariable UUID assetId) {
        return brokerService.listAllByBrokersAvailable(assetId).stream().map(BrokerMapper::toDTO).toList();
    }
}
