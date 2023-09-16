package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.DeptMember;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.DeptMemberRequest;
import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .detail(request.getDetail())
                .lableColor(request.getLableColor())
                .department(department)
                .image(request.getImage())
                .unusableDate(request.getUnusableDate())
                .build();
        spaceRepository.save(space);
    }

    public List<Space> findSpaceFetchJoin(){
        return spaceRepository.findSpaceFetchJoin();
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
