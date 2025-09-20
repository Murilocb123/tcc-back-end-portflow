package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.PortfolioEntity;
import br.com.murilocb123.portflow.dto.BrokerDTO;
import br.com.murilocb123.portflow.dto.PortfolioDTO;
import br.com.murilocb123.portflow.mapper.BrokerMapper;
import br.com.murilocb123.portflow.mapper.PortfolioMapper;
import br.com.murilocb123.portflow.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping
    public PortfolioDTO create(@RequestBody PortfolioDTO dto) {
        PortfolioEntity entity = PortfolioMapper.toEntity(dto);
        PortfolioEntity saved = portfolioService.create(entity);
        return PortfolioMapper.toDTO(saved);
    }

    @GetMapping("/{id}")
    public PortfolioDTO getById(@PathVariable UUID id) {
        return PortfolioMapper.toDTO(portfolioService.getById(id));
    }

    @PutMapping("/{id}")
    public PortfolioDTO update(@PathVariable UUID id, @RequestBody PortfolioDTO dto) {
        PortfolioEntity entity = PortfolioMapper.toEntity(dto);
        PortfolioEntity updated = portfolioService.update(id, entity);
        return PortfolioMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        portfolioService.delete(id);
    }

    @GetMapping
    public Page<PortfolioDTO> list(Pageable pageable) {
        Page<PortfolioEntity> page = portfolioService.list(pageable);
        List<PortfolioDTO> dtos = page.getContent().stream().map(PortfolioMapper::toDTO).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @GetMapping("/all")
    public List<PortfolioDTO> listAll() {
        return portfolioService.listAll().stream().map(PortfolioMapper::toDTO).toList();
    }
}
