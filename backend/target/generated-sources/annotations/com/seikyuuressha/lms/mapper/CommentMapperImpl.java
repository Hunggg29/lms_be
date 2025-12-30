package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.CommentResponse;
import com.seikyuuressha.lms.entity.Comment;
import com.seikyuuressha.lms.entity.Lesson;
import java.util.ArrayList;
import java.util.List;
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
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse.CommentResponseBuilder commentResponse = CommentResponse.builder();

        commentResponse.lessonId( commentLessonLessonId( comment ) );
        commentResponse.user( userMapper.toUserResponse( comment.getUser() ) );
        commentResponse.parentCommentId( commentParentCommentCommentId( comment ) );
        commentResponse.commentId( comment.getCommentId() );
        commentResponse.content( comment.getContent() );
        commentResponse.createdAt( comment.getCreatedAt() );
        commentResponse.isActive( comment.getIsActive() );
        commentResponse.updatedAt( comment.getUpdatedAt() );

        return commentResponse.build();
    }

    @Override
    public List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentResponse> list = new ArrayList<CommentResponse>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( toCommentResponse( comment ) );
        }

        return list;
    }

    private UUID commentLessonLessonId(Comment comment) {
        Lesson lesson = comment.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getLessonId();
    }

    private UUID commentParentCommentCommentId(Comment comment) {
        Comment parentComment = comment.getParentComment();
        if ( parentComment == null ) {
            return null;
        }
        return parentComment.getCommentId();
    }
}
