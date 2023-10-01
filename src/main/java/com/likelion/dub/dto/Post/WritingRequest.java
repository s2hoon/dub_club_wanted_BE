package com.likelion.dub.dto.Post;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WritingRequest {

    private String title;
    private String content;

    @Nullable
    private MultipartFile image;


}
