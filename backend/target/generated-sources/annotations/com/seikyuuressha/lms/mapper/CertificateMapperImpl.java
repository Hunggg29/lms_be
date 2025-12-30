package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.CertificateResponse;
import com.seikyuuressha.lms.entity.Certificate;
import com.seikyuuressha.lms.entity.Course;
import com.seikyuuressha.lms.entity.Users;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T00:00:07+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public CertificateResponse toCertificateResponse(Certificate certificate) {
        if ( certificate == null ) {
            return null;
        }

        CertificateResponse.CertificateResponseBuilder certificateResponse = CertificateResponse.builder();

        certificateResponse.userId( certificateUserUserId( certificate ) );
        certificateResponse.courseId( certificateCourseCourseId( certificate ) );
        certificateResponse.revocationReason( certificate.getRevokedReason() );
        certificateResponse.certificateCode( certificate.getCertificateCode() );
        certificateResponse.certificateId( certificate.getCertificateId() );
        certificateResponse.finalScore( certificate.getFinalScore() );
        certificateResponse.isValid( certificate.getIsValid() );
        certificateResponse.issuedAt( certificate.getIssuedAt() );
        certificateResponse.pdfUrl( certificate.getPdfUrl() );
        certificateResponse.revokedAt( certificate.getRevokedAt() );

        return certificateResponse.build();
    }

    private UUID certificateUserUserId(Certificate certificate) {
        Users user = certificate.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUserId();
    }

    private UUID certificateCourseCourseId(Certificate certificate) {
        Course course = certificate.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getCourseId();
    }
}
