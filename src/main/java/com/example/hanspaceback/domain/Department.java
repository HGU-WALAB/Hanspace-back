package com.example.hanspaceback.domain;

import com.example.hanspaceback.dto.request.DepartmentRequest;
import com.example.hanspaceback.s3.S3Uploader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Department extends BaseEntity{
    // NonNull이 필요할까?
    @Id
    @Column(name = "deptId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;
    private String siteName;
    private String deptName;
    private String logoImage;
    private boolean userAccept;
    private int maxReserveCount;
    private String link;
    private String extraInfo;
    private String deptImage;

//    @Transient
//    private S3Uploader s3Uploader;

    public void update(DepartmentRequest request, String deptImage, String logoImage) throws IOException {
        this.siteName = request.getSiteName();
        this.deptName = request.getDeptName();
        this.userAccept = request.isUserAccept();
        this.maxReserveCount = request.getMaxRserveCount();
        this.link = request.getLink();
        this.extraInfo = request.getExtraInfo();
        this.deptImage = deptImage;
        this.logoImage = logoImage;
//        if (logoImage != null && !logoImage.isEmpty()) {
//            String imageUrl = s3Uploader.upload(logoImage, "logoImage");
//            this.logoImage = imageUrl;
//        }
//        if (deptImage != null && !deptImage.isEmpty()) {
//            String imageUrl = s3Uploader.upload(deptImage, "deptImage");
//            this.deptImage = imageUrl;
//        }
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DeptMember> deptMember = new ArrayList<>();


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Space> space = new ArrayList<>();
}
