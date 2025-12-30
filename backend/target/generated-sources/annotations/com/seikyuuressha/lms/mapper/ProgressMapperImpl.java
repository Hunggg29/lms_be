package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.ProgressResponse;
import com.seikyuuressha.lms.entity.Lesson;
import com.seikyuuressha.lms.entity.Progress;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T00:00:07+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProgressMapperImpl implements ProgressMapper {

    @Override
    public ProgressResponse toProgressResponse(Progress progress) {
        if ( progress == null ) {
            return null;
        }

        ProgressResponse.ProgressResponseBuilder progressResponse = ProgressResponse.builder();

        progressResponse.lessonId( progressLessonLessonId( progress ) );
        progressResponse.lessonTitle( progressLessonTitle( progress ) );
        progressResponse.lastWatchedAt( progress.getLastWatchedAt() );
        progressResponse.progressId( progress.getProgressId() );
        progressResponse.progressPercent( progress.getProgressPercent() );
        progressResponse.watchedSeconds( progress.getWatchedSeconds() );

        return progressResponse.build();
    }

    private UUID progressLessonLessonId(Progress progress) {
        Lesson lesson = progress.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getLessonId();
    }

    private String progressLessonTitle(Progress progress) {
        Lesson lesson = progress.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getTitle();
    }
}
