package com.seikyuuressha.lms.mapper;

import com.seikyuuressha.lms.dto.response.AnswerResponse;
import com.seikyuuressha.lms.dto.response.QuestionResponse;
import com.seikyuuressha.lms.dto.response.QuizAnswerResponse;
import com.seikyuuressha.lms.dto.response.QuizAttemptResponse;
import com.seikyuuressha.lms.dto.response.QuizResponse;
import com.seikyuuressha.lms.entity.Answer;
import com.seikyuuressha.lms.entity.Course;
import com.seikyuuressha.lms.entity.Lesson;
import com.seikyuuressha.lms.entity.Module;
import com.seikyuuressha.lms.entity.Question;
import com.seikyuuressha.lms.entity.Quiz;
import com.seikyuuressha.lms.entity.QuizAnswer;
import com.seikyuuressha.lms.entity.QuizAttempt;
import com.seikyuuressha.lms.entity.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-31T00:00:07+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class QuizMapperImpl implements QuizMapper {

    @Override
    public QuizResponse toQuizResponse(Quiz quiz) {
        if ( quiz == null ) {
            return null;
        }

        QuizResponse.QuizResponseBuilder quizResponse = QuizResponse.builder();

        quizResponse.courseId( quizCourseCourseId( quiz ) );
        quizResponse.moduleId( quizModuleModuleId( quiz ) );
        quizResponse.lessonId( quizLessonLessonId( quiz ) );
        quizResponse.questions( toQuestionResponseList( quiz.getQuestions() ) );
        quizResponse.description( quiz.getDescription() );
        quizResponse.isPublished( quiz.getIsPublished() );
        quizResponse.maxAttempts( quiz.getMaxAttempts() );
        quizResponse.orderIndex( quiz.getOrderIndex() );
        quizResponse.passingScore( quiz.getPassingScore() );
        quizResponse.quizId( quiz.getQuizId() );
        quizResponse.timeLimit( quiz.getTimeLimit() );
        quizResponse.title( quiz.getTitle() );

        return quizResponse.build();
    }

    @Override
    public QuestionResponse toQuestionResponse(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResponse.QuestionResponseBuilder questionResponse = QuestionResponse.builder();

        questionResponse.quizId( questionQuizQuizId( question ) );
        questionResponse.answers( toAnswerResponseList( question.getAnswers() ) );
        questionResponse.questionType( question.getType() );
        questionResponse.explanation( question.getExplanation() );
        questionResponse.orderIndex( question.getOrderIndex() );
        questionResponse.points( question.getPoints() );
        questionResponse.questionId( question.getQuestionId() );
        questionResponse.questionText( question.getQuestionText() );

        return questionResponse.build();
    }

    @Override
    public AnswerResponse toAnswerResponse(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerResponse.AnswerResponseBuilder answerResponse = AnswerResponse.builder();

        answerResponse.questionId( answerQuestionQuestionId( answer ) );
        answerResponse.answerId( answer.getAnswerId() );
        answerResponse.answerText( answer.getAnswerText() );
        answerResponse.isCorrect( answer.getIsCorrect() );
        answerResponse.orderIndex( answer.getOrderIndex() );

        return answerResponse.build();
    }

    @Override
    public QuizAttemptResponse toQuizAttemptResponse(QuizAttempt attempt) {
        if ( attempt == null ) {
            return null;
        }

        QuizAttemptResponse.QuizAttemptResponseBuilder quizAttemptResponse = QuizAttemptResponse.builder();

        quizAttemptResponse.userId( attemptUserUserId( attempt ) );
        quizAttemptResponse.quiz( toQuizResponse( attempt.getQuiz() ) );
        quizAttemptResponse.startTime( attempt.getStartedAt() );
        quizAttemptResponse.endTime( attempt.getSubmittedAt() );
        quizAttemptResponse.attemptId( attempt.getAttemptId() );
        quizAttemptResponse.attemptNumber( attempt.getAttemptNumber() );
        quizAttemptResponse.maxScore( attempt.getMaxScore() );
        quizAttemptResponse.passed( attempt.getPassed() );
        quizAttemptResponse.percentage( attempt.getPercentage() );
        quizAttemptResponse.status( attempt.getStatus() );
        quizAttemptResponse.totalScore( attempt.getTotalScore() );

        return quizAttemptResponse.build();
    }

    @Override
    public QuizAnswerResponse toQuizAnswerResponse(QuizAnswer quizAnswer) {
        if ( quizAnswer == null ) {
            return null;
        }

        QuizAnswerResponse.QuizAnswerResponseBuilder quizAnswerResponse = QuizAnswerResponse.builder();

        quizAnswerResponse.answerId( quizAnswer.getQuizAnswerId() );
        quizAnswerResponse.attemptId( quizAnswerAttemptAttemptId( quizAnswer ) );
        quizAnswerResponse.questionId( quizAnswerQuestionQuestionId( quizAnswer ) );
        quizAnswerResponse.selectedAnswerId( getFirstSelectedAnswerId( quizAnswer ) );
        quizAnswerResponse.userAnswer( quizAnswer.getTextAnswer() );
        quizAnswerResponse.pointsAwarded( quizAnswer.getPointsEarned() );
        quizAnswerResponse.isCorrect( quizAnswer.getIsCorrect() );

        return quizAnswerResponse.build();
    }

    @Override
    public List<QuestionResponse> toQuestionResponseList(List<Question> questions) {
        if ( questions == null ) {
            return null;
        }

        List<QuestionResponse> list = new ArrayList<QuestionResponse>( questions.size() );
        for ( Question question : questions ) {
            list.add( toQuestionResponse( question ) );
        }

        return list;
    }

    @Override
    public List<AnswerResponse> toAnswerResponseList(List<Answer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<AnswerResponse> list = new ArrayList<AnswerResponse>( answers.size() );
        for ( Answer answer : answers ) {
            list.add( toAnswerResponse( answer ) );
        }

        return list;
    }

    @Override
    public List<QuizAnswerResponse> toQuizAnswerResponseList(List<QuizAnswer> answers) {
        if ( answers == null ) {
            return null;
        }

        List<QuizAnswerResponse> list = new ArrayList<QuizAnswerResponse>( answers.size() );
        for ( QuizAnswer quizAnswer : answers ) {
            list.add( toQuizAnswerResponse( quizAnswer ) );
        }

        return list;
    }

    private UUID quizCourseCourseId(Quiz quiz) {
        Course course = quiz.getCourse();
        if ( course == null ) {
            return null;
        }
        return course.getCourseId();
    }

    private UUID quizModuleModuleId(Quiz quiz) {
        Module module = quiz.getModule();
        if ( module == null ) {
            return null;
        }
        return module.getModuleId();
    }

    private UUID quizLessonLessonId(Quiz quiz) {
        Lesson lesson = quiz.getLesson();
        if ( lesson == null ) {
            return null;
        }
        return lesson.getLessonId();
    }

    private UUID questionQuizQuizId(Question question) {
        Quiz quiz = question.getQuiz();
        if ( quiz == null ) {
            return null;
        }
        return quiz.getQuizId();
    }

    private UUID answerQuestionQuestionId(Answer answer) {
        Question question = answer.getQuestion();
        if ( question == null ) {
            return null;
        }
        return question.getQuestionId();
    }

    private UUID attemptUserUserId(QuizAttempt quizAttempt) {
        Users user = quizAttempt.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUserId();
    }

    private UUID quizAnswerAttemptAttemptId(QuizAnswer quizAnswer) {
        QuizAttempt attempt = quizAnswer.getAttempt();
        if ( attempt == null ) {
            return null;
        }
        return attempt.getAttemptId();
    }

    private UUID quizAnswerQuestionQuestionId(QuizAnswer quizAnswer) {
        Question question = quizAnswer.getQuestion();
        if ( question == null ) {
            return null;
        }
        return question.getQuestionId();
    }
}
