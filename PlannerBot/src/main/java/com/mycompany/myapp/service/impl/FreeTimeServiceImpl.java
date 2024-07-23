package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FreeTime;
import com.mycompany.myapp.repository.FreeTimeRepository;
import com.mycompany.myapp.service.FreeTimeService;
import com.mycompany.myapp.service.dto.FreeTimeDTO;
import com.mycompany.myapp.service.mapper.FreeTimeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.FreeTime}.
 */
@Service
@Transactional
public class FreeTimeServiceImpl implements FreeTimeService {

    private static final Logger log = LoggerFactory.getLogger(FreeTimeServiceImpl.class);

    private final FreeTimeRepository freeTimeRepository;

    private final FreeTimeMapper freeTimeMapper;

    public FreeTimeServiceImpl(FreeTimeRepository freeTimeRepository, FreeTimeMapper freeTimeMapper) {
        this.freeTimeRepository = freeTimeRepository;
        this.freeTimeMapper = freeTimeMapper;
    }

    @Override
    public FreeTimeDTO save(FreeTimeDTO freeTimeDTO) {
        log.debug("Request to save FreeTime : {}", freeTimeDTO);
        FreeTime freeTime = freeTimeMapper.toEntity(freeTimeDTO);
        freeTime = freeTimeRepository.save(freeTime);
        return freeTimeMapper.toDto(freeTime);
    }

    @Override
    public FreeTimeDTO update(FreeTimeDTO freeTimeDTO) {
        log.debug("Request to update FreeTime : {}", freeTimeDTO);
        FreeTime freeTime = freeTimeMapper.toEntity(freeTimeDTO);
        freeTime = freeTimeRepository.save(freeTime);
        return freeTimeMapper.toDto(freeTime);
    }

    @Override
    public Optional<FreeTimeDTO> partialUpdate(FreeTimeDTO freeTimeDTO) {
        log.debug("Request to partially update FreeTime : {}", freeTimeDTO);

        return freeTimeRepository
            .findById(freeTimeDTO.getId())
            .map(existingFreeTime -> {
                freeTimeMapper.partialUpdate(existingFreeTime, freeTimeDTO);

                return existingFreeTime;
            })
            .map(freeTimeRepository::save)
            .map(freeTimeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FreeTimeDTO> findOne(Long id) {
        log.debug("Request to get FreeTime : {}", id);
        return freeTimeRepository.findById(id).map(freeTimeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FreeTime : {}", id);
        freeTimeRepository.deleteById(id);
    }
}
