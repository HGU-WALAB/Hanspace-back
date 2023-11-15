package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.dto.response.SpaceResponse;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.SpaceRepository;
import com.example.hanspaceback.repository.SpaceTimeExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final DepartmentRepository departmentRepository;
    public void create(SpaceRequest request){
        Department department = departmentRepository.findById(request.getDeptId()).orElseThrow();
        Space space = Space.builder()
                .name(request.getName())
                .headCount(request.getHeadCount())
                .availableStart(request.getAvailableStart())
                .availableEnd(request.getAvailableEnd())
                .detail(request.getDetail())
                .availability(request.isAvailability())
                .department(department)
                .image(request.getImage())
                .build();
        spaceRepository.save(space);
    }
    public List<Space> findSpaceFetchJoin(){
        return spaceRepository.findSpaceFetchJoin();
    }
    public List<SpaceResponse> findByDeptId(Long deptId) {
        List<Space> spaces = spaceRepository.findByDepartment_DeptId(deptId);
        List<SpaceResponse> responses = new ArrayList<>();

        for (Space space : spaces) {
            SpaceResponse response = new SpaceResponse();
            response.setSpaceId(space.getSpaceId());
            response.setName(space.getName());
            response.setHeadCount(space.getHeadCount());
            response.setAvailableStart(space.getAvailableStart());
            response.setAvailableEnd(space.getAvailableEnd());
            response.setDetail(space.getDetail());
            response.setAvailability(space.isAvailability());
            response.setImage(space.getImage());
            responses.add(response);
        }
        return responses;
    }
    public Space update(Long id, SpaceRequest request){
        Space space = spaceRepository.findById(id).orElseThrow();
        space.update(request);
        spaceRepository.save(space);
        return space;
    }
    public void delete(Long id){
        spaceRepository.deleteById(id);
    }
}
