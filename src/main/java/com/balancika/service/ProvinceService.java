package com.balancika.service;

import com.balancika.Exception.ProvinceExistedException;
import com.balancika.Exception.ResourceNotFoundException;
import com.balancika.entity.Province;
import com.balancika.model.dto.ProvinceDTO;
import com.balancika.model.request.ProvinceCreateRequest;
import com.balancika.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Transactional(readOnly = true)
    public Page<ProvinceDTO> getAll(Pageable pageable) {
        Page<Province> provinces = provinceRepository.findAll(pageable);
        return provinces.map(province -> ProvinceDTO.builder()
                .id(province.getId())
                .name(province.getName())
                .description(province.getDescription())
                .build());
    }

    @Transactional(readOnly = true)
    public ProvinceDTO getById(Long id) {
        return provinceRepository.findById(id)
                .map(ProvinceDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found ID: "+id));
    }

    @Transactional
    public ProvinceDTO create(ProvinceCreateRequest payload) {

        /*
        // if you want use ProvinceExistedException
        if (provinceRepository.existsByName(payload.getName())){
            throw new ProvinceExistedException(payload.getName());
        }
         */
        if (provinceRepository.existsByName(payload.getName())){
            throw new ResourceNotFoundException(String.format("Province name already exists: %s",payload.getName()));
        }
        return new ProvinceDTO(provinceRepository.save(Province.builder()
                .name(payload.getName())
                .description(payload.getDescription())
                .build()));
    }

    @Transactional
    public ProvinceDTO update(Long id, ProvinceCreateRequest payload) {
        // check if the province not found
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found Id: "+id));
        // check duplicate name province
        boolean duplicate = provinceRepository.existsByNameAndIdNot(payload.getName(),id);
        if (duplicate) {
            throw new DuplicateKeyException("Duplicate province name: "+ payload.getName());
        }
        province.setName(payload.getName());
        province.setDescription(payload.getDescription());

        return new ProvinceDTO(provinceRepository.save(province));

    }

    @Transactional
    public void delete(Long id) {
        /*
        // check NotFoundException Option 2
        Province province = provinceRepository.findById(id).orElseThrow(NotFoundException::new);
         */

        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found ID: "+id));
        provinceRepository.delete(province);
    }


}
