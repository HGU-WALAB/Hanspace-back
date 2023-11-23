package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.SpaceRequest;
import com.example.hanspaceback.s3.S3Uploader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Space extends BaseEntity{
    @Id
    @Column(name = "spaceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaceId;
    private String name;
    private int headCount;
    private String availableStart;
    private String availableEnd;
    private String detail;
    private boolean availability;
    private String labelColor;
    private String image;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

//    @Transient
//    private S3Uploader s3Uploader;
    public void update(SpaceRequest request, String image) throws IOException {
        this.name = request.getName();
        this.headCount = request.getHeadCount();
        this.availableStart = request.getAvailableStart();
        this.availableEnd = request.getAvailableEnd();
        this.detail = request.getDetail();
        this.availability = request.isAvailability();
//        // 이미지가 존재하면 S3에 업로드
//        if (image != null && !image.isEmpty()) {
//            String imageUrl = s3Uploader.upload(image, "images");
//            this.image = imageUrl;
//        }
        this.image = image;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserve> reserve = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "space",  cascade = CascadeType.ALL)
    private SpaceTimeExtra spaceTimeExtra;
}
