package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.InstructorResponse;
import com.seikyuuressha.lms.dto.response.UserResponse;
import com.seikyuuressha.lms.entity.Roles;
import com.seikyuuressha.lms.entity.Users;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T00:00:08+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toUserResponse(Users user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.roleName( userRoleRoleName( user ) );
        userResponse.isActive( user.isActive() );
        userResponse.avatarUrl( user.getAvatarUrl() );
        userResponse.bio( user.getBio() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.email( user.getEmail() );
        userResponse.fullName( user.getFullName() );
        userResponse.userId( user.getUserId() );

        return userResponse.build();
    }

    @Override
    public InstructorResponse toInstructorResponse(Users user) {
        if ( user == null ) {
            return null;
        }

        InstructorResponse.InstructorResponseBuilder instructorResponse = InstructorResponse.builder();

        instructorResponse.avatarUrl( user.getAvatarUrl() );
        instructorResponse.bio( user.getBio() );
        instructorResponse.email( user.getEmail() );
        instructorResponse.fullName( user.getFullName() );
        instructorResponse.userId( user.getUserId() );

        return instructorResponse.build();
    }

    private String userRoleRoleName(Users users) {
        Roles role = users.getRole();
        if ( role == null ) {
            return null;
        }
        return role.getRoleName();
    }
}
