package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.LessonResponse;
import com.seikyuuressha.lms.entity.Lesson;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T00:05:15+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class LessonMapperImpl implements LessonMapper {

    @Override
    public LessonResponse toLessonResponse(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }

        LessonResponse.LessonResponseBuilder lessonResponse = LessonResponse.builder();

        lessonResponse.order( lesson.getSortOrder() );
        lessonResponse.content( lesson.getContent() );
        lessonResponse.durationSeconds( lesson.getDurationSeconds() );
        lessonResponse.lessonId( lesson.getLessonId() );
        lessonResponse.title( lesson.getTitle() );

        return lessonResponse.build();
    }

    @Override
    public LessonResponse toLessonResponseSimple(Lesson lesson) {
        if ( lesson == null ) {
            return null;
        }

        LessonResponse.LessonResponseBuilder lessonResponse = LessonResponse.builder();

        lessonResponse.order( lesson.getSortOrder() );
        lessonResponse.content( lesson.getContent() );
        lessonResponse.durationSeconds( lesson.getDurationSeconds() );
        lessonResponse.lessonId( lesson.getLessonId() );
        lessonResponse.title( lesson.getTitle() );

        lessonResponse.userProgress( 0.0 );

        return lessonResponse.build();
    }
}
