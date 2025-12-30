package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.ReviewResponse;
import com.seikyuuressha.lms.entity.Course;
import com.seikyuuressha.lms.entity.Review;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T00:00:07+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReviewResponse toReviewResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewResponse.ReviewResponseBuilder reviewResponse = ReviewResponse.builder();

        reviewResponse.courseId( reviewCourseCourseId( review ) );
        reviewResponse.user( userMapper.toUserResponse( review.getUser() ) );
        reviewResponse.comment( review.getComment() );
        reviewResponse.createdAt( review.getCreatedAt() );
        reviewResponse.isActive( review.getIsActive() );
        reviewResponse.rating( review.getRating() );
        reviewResponse.reviewId( review.getReviewId() );
        reviewResponse.updatedAt( review.getUpdatedAt() );

        return reviewResponse.build();
    }

    private UUID reviewCourseCourseId(Review review) {
        Course course = review.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getCourseId();
    }
}
