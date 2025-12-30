package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.PaymentResponse;
import com.seikyuuressha.lms.entity.Course;
import com.seikyuuressha.lms.entity.Enrollment;
import com.seikyuuressha.lms.entity.Payment;
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
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toPaymentResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse.PaymentResponseBuilder paymentResponse = PaymentResponse.builder();

        paymentResponse.userId( paymentUserUserId( payment ) );
        paymentResponse.courseId( paymentCourseCourseId( payment ) );
        paymentResponse.enrollmentId( paymentEnrollmentEnrollmentId( payment ) );
        paymentResponse.paymentProvider( payment.getPaymentMethod() );
        paymentResponse.amount( payment.getAmount() );
        paymentResponse.createdAt( payment.getCreatedAt() );
        paymentResponse.paidAt( payment.getPaidAt() );
        paymentResponse.paymentId( payment.getPaymentId() );
        paymentResponse.paymentStatus( payment.getPaymentStatus() );
        paymentResponse.transactionId( payment.getTransactionId() );
        paymentResponse.vnpayResponseCode( payment.getVnpayResponseCode() );

        return paymentResponse.build();
    }

    private UUID paymentUserUserId(Payment payment) {
        Users user = payment.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUserId();
    }

    private UUID paymentCourseCourseId(Payment payment) {
        Course course = payment.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getCourseId();
    }

    private UUID paymentEnrollmentEnrollmentId(Payment payment) {
        Enrollment enrollment = payment.getEnrollment();
        if ( enrollment == null ) {
            return null;
        }
        return enrollment.getEnrollmentId();
    }
}
