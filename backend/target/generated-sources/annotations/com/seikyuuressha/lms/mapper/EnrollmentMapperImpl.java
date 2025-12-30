package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.EnrollmentResponse;
import com.seikyuuressha.lms.entity.Enrollment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T00:05:15+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class EnrollmentMapperImpl implements EnrollmentMapper {

    @Override
    public EnrollmentResponse toEnrollmentResponse(Enrollment enrollment) {
        if ( enrollment == null ) {
            return null;
        }

        EnrollmentResponse.EnrollmentResponseBuilder enrollmentResponse = EnrollmentResponse.builder();

        enrollmentResponse.enrolledAt( enrollment.getEnrolledAt() );
        enrollmentResponse.enrollmentId( enrollment.getEnrollmentId() );
        enrollmentResponse.progressPercent( enrollment.getProgressPercent() );

        return enrollmentResponse.build();
    }
}
