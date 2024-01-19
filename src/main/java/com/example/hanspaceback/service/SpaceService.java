package com.example.hanspaceback.service;

import com.example.hanspaceback.domain.Department;
import com.example.hanspaceback.domain.Space;
import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.dto.response.SpaceResponse;
import com.example.hanspaceback.repository.DepartmentRepository;
import com.example.hanspaceback.repository.SpaceRepository;
import com.example.hanspaceback.repository.SpaceTimeExtraRepository;
import com.example.hanspaceback.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final DepartmentRepository departmentRepository;
    private final S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    public void create(SpaceRequest request, MultipartFile image) throws IOException {
        Department department = departmentRepository.findById(request.getDeptId()).orElseThrow();
        String imageName = null;

        if(!image.isEmpty()) {
            imageName = s3Uploader.upload(image,"images");
        }
        Space space = Space.builder()
                .name(request.getName())
                .headCount(request.getHeadCount())
                .availableStart(request.getAvailableStart())
                .availableEnd(request.getAvailableEnd())
                .detail(request.getDetail())
                .availability(request.isAvailability())
                .labelColor(request.getLabelColor())
                .department(department)
                .image(imageName)
                .build();
        spaceRepository.save(space);
    }
    public List<Space> findSpaceFetchJoin(){
        return spaceRepository.findSpaceFetchJoin();
    }
    public List<SpaceResponse> findByDeptId(Long deptId) {
        List<Space> spaces = spaceRepository.findByDepartment_DeptId(deptId);
        List<SpaceResponse> responses = new ArrayList<>();
        String url = "https://" + bucket + ".s3." + region + ".amazonaws.com/images/";

        for (Space space : spaces) {
            SpaceResponse response = new SpaceResponse();
            response.setSpaceId(space.getSpaceId());
            response.setName(space.getName());
            response.setHeadCount(space.getHeadCount());
            response.setAvailableStart(space.getAvailableStart());
            response.setAvailableEnd(space.getAvailableEnd());
            response.setDetail(space.getDetail());
            response.setAvailability(space.isAvailability());
            response.setLabelColor(space.getLabelColor());
            if(space.getImage() != null)
                response.setImage(url + space.getImage());
            responses.add(response);
        }
        return responses;
    }
    public Space update(Long id, SpaceRequest request, MultipartFile image) throws IOException {
        Space space = spaceRepository.findById(id).orElseThrow();
        String img = null;
        if (image != null && !image.isEmpty()) {
            img = s3Uploader.upload(image, "images");
        }
        space.update(request, img);
        spaceRepository.save(space);
        return space;
    }
    public void delete(Long id){
        spaceRepository.deleteById(id);
    }
}
