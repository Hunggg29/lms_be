package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.CoInstructorResponse;
import com.seikyuuressha.lms.dto.response.CourseResponse;
import com.seikyuuressha.lms.entity.Categories;
import com.seikyuuressha.lms.entity.Course;
import com.seikyuuressha.lms.entity.CourseInstructor;
import com.seikyuuressha.lms.entity.Users;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T00:05:15+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CourseResponse toCourseResponse(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse.CourseResponseBuilder courseResponse = CourseResponse.builder();

        courseResponse.categoryName( courseCategoryName( course ) );
        courseResponse.instructor( userMapper.toInstructorResponse( course.getInstructor() ) );
        courseResponse.isPublished( course.isPublished() );
        courseResponse.courseId( course.getCourseId() );
        courseResponse.createdAt( course.getCreatedAt() );
        courseResponse.description( course.getDescription() );
        courseResponse.level( course.getLevel() );
        courseResponse.price( course.getPrice() );
        courseResponse.slug( course.getSlug() );
        courseResponse.thumbnailUrl( course.getThumbnailUrl() );
        courseResponse.title( course.getTitle() );
        courseResponse.updatedAt( course.getUpdatedAt() );

        return courseResponse.build();
    }

    @Override
    public CourseResponse toCourseResponseWithoutModules(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponse.CourseResponseBuilder courseResponse = CourseResponse.builder();

        courseResponse.categoryName( courseCategoryName( course ) );
        courseResponse.instructor( userMapper.toInstructorResponse( course.getInstructor() ) );
        courseResponse.isPublished( course.isPublished() );
        courseResponse.courseId( course.getCourseId() );
        courseResponse.createdAt( course.getCreatedAt() );
        courseResponse.description( course.getDescription() );
        courseResponse.level( course.getLevel() );
        courseResponse.price( course.getPrice() );
        courseResponse.slug( course.getSlug() );
        courseResponse.thumbnailUrl( course.getThumbnailUrl() );
        courseResponse.title( course.getTitle() );
        courseResponse.updatedAt( course.getUpdatedAt() );

        return courseResponse.build();
    }

    @Override
    public CoInstructorResponse toCoInstructorResponse(CourseInstructor courseInstructor) {
        if ( courseInstructor == null ) {
            return null;
        }

        CoInstructorResponse.CoInstructorResponseBuilder coInstructorResponse = CoInstructorResponse.builder();

        coInstructorResponse.userId( courseInstructorUserUserId( courseInstructor ) );
        coInstructorResponse.fullName( courseInstructorUserFullName( courseInstructor ) );
        coInstructorResponse.email( courseInstructorUserEmail( courseInstructor ) );
        coInstructorResponse.avatarUrl( courseInstructorUserAvatarUrl( courseInstructor ) );
        if ( courseInstructor.getUserRole() != null ) {
            coInstructorResponse.role( courseInstructor.getUserRole().name() );
        }
        coInstructorResponse.addedAt( courseInstructor.getAddedAt() );

        return coInstructorResponse.build();
    }

    private String courseCategoryName(Course course) {
        Categories category = course.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getName();
    }

    private UUID courseInstructorUserUserId(CourseInstructor courseInstructor) {
        Users user = courseInstructor.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUserId();
    }

    private String courseInstructorUserFullName(CourseInstructor courseInstructor) {
        Users user = courseInstructor.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getFullName();
    }

    private String courseInstructorUserEmail(CourseInstructor courseInstructor) {
        Users user = courseInstructor.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getEmail();
    }

    private String courseInstructorUserAvatarUrl(CourseInstructor courseInstructor) {
        Users user = courseInstructor.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getAvatarUrl();
    }
}
