package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.PortfolioAssetEntity;
import br.com.murilocb123.portflow.dto.PortfolioAssetDTO;
import br.com.murilocb123.portflow.dto.StrategyDTO;
import br.com.murilocb123.portflow.mapper.PortfolioAssetMapper;
import br.com.murilocb123.portflow.service.PortfolioAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/portfolio-assets")
@RequiredArgsConstructor
public class PortfolioAssetController {
    private final PortfolioAssetService portfolioAssetService;

    @PostMapping
    public void create(@RequestBody PortfolioAssetDTO dto) {
        PortfolioAssetEntity entity = PortfolioAssetMapper.toEntity(dto);
        portfolioAssetService.persistPortfolio(entity);
    }

    @GetMapping("/{id}")
    public PortfolioAssetDTO getById(@PathVariable UUID id) {
        return PortfolioAssetMapper.toDTO(portfolioAssetService.getById(id));
    }

    @PutMapping("/{id}")
    public PortfolioAssetDTO update(@PathVariable UUID id, @RequestBody PortfolioAssetDTO dto) {
        PortfolioAssetEntity entity = PortfolioAssetMapper.toEntity(dto);
        PortfolioAssetEntity updated = portfolioAssetService.update(id, entity);
        return PortfolioAssetMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        portfolioAssetService.delete(id);
    }

    @GetMapping
    public Page<PortfolioAssetDTO> list(Pageable pageable) {
        Page<PortfolioAssetEntity> page = portfolioAssetService.list(pageable);
        List<PortfolioAssetDTO> dtos = page.getContent().stream().map(PortfolioAssetMapper::toDTO).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    @GetMapping("/strategies")
    public Page<StrategyDTO> listStrategies(Pageable pageable) {
        return portfolioAssetService.listWithCurrentPrice(pageable);
    }
}
