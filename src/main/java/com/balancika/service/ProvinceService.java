package com.balancika.service;

import com.balancika.Exception.NotFoundException;
import com.balancika.Exception.ProvinceExistedException;
import com.balancika.Exception.ResourceNotFoundException;
import com.balancika.entity.Province;
import com.balancika.model.dto.ProvinceDTO;
import com.balancika.model.request.ProvinceCreateRequest;
import com.balancika.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
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
//                .orElseThrow(NotFoundException::new);
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public ProvinceDTO create(ProvinceCreateRequest payload) {
        if (provinceRepository.existsByName(payload.getName())){
            throw new ProvinceExistedException(payload.getName());
        }
        return new ProvinceDTO(provinceRepository.save(Province.builder()
                .name(payload.getName())
                .description(payload.getDescription())
                .build()));
    }

    @Transactional
    public ProvinceDTO update(Long id, ProvinceCreateRequest payload) {
        // check if the province not found
        Province province = provinceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(payload.getName()));
        // check duplicate name province
//        if (provinceRepository.existsByName(payload.getName())){
//            throw new ProvinceExistedException(payload.getName());
//        }
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

        Province province = provinceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        provinceRepository.delete(province);
    }


}
