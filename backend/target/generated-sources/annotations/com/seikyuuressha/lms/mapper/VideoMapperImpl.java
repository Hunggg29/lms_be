package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.VideoResponse;
import com.seikyuuressha.lms.entity.Lesson;
import com.seikyuuressha.lms.entity.Video;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T00:05:14+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class VideoMapperImpl implements VideoMapper {

    @Override
    public VideoResponse toVideoResponse(Video video) {
        if ( video == null ) {
            return null;
        }

        VideoResponse.VideoResponseBuilder videoResponse = VideoResponse.builder();

        videoResponse.lessonId( videoLessonLessonId( video ) );
        videoResponse.durationSeconds( video.getDurationSeconds() );
        videoResponse.fileSize( video.getFileSize() );
        videoResponse.mimeType( video.getMimeType() );
        videoResponse.originalFilename( video.getOriginalFilename() );
        videoResponse.uploadedAt( video.getUploadedAt() );
        videoResponse.videoId( video.getVideoId() );

        videoResponse.processingStatus( video.getProcessingStatus().name() );

        return videoResponse.build();
    }

    private UUID videoLessonLessonId(Video video) {
        Lesson lesson = video.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getLessonId();
    }
}
