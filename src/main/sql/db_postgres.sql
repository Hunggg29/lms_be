-- Database conversion from SQL Server to PostgreSQL
-- Run this script to initialize the database in PostgreSQL

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- 1. Roles Table
-- ============================================
CREATE TABLE Roles (
    roleId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    roleName VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO Roles (roleName) VALUES ('STUDENT'), ('INSTRUCTOR'), ('ADMIN');

-- ============================================
-- 2. Users Table
-- ============================================
CREATE TABLE Users (
    userId UUID PRIMARY KEY,
    fullName VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatarUrl VARCHAR(255),
    bio VARCHAR(500),
    roleId UUID NOT NULL REFERENCES Roles(roleId) ON UPDATE CASCADE ON DELETE CASCADE,
    createdAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    isActive BOOLEAN DEFAULT TRUE,
    failedLoginAttempts INT NOT NULL DEFAULT 0,
    lockUntil TIMESTAMPTZ
);

-- ============================================
-- 3. Categories Table
-- ============================================
CREATE TABLE Categories (
    categoryId UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- ============================================
-- 4. Courses Table
-- ============================================
CREATE TABLE Courses (
    courseId UUID PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    thumbnailUrl VARCHAR(255),
    level VARCHAR(50), 
    price DECIMAL(10,2) DEFAULT 0,
    categoryId UUID REFERENCES Categories(categoryId) ON UPDATE CASCADE ON DELETE CASCADE,
    instructorId UUID REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,  
    createdAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    isPublished BOOLEAN DEFAULT FALSE
);

-- ============================================
-- 4.1 CourseInstructors Table
-- ============================================
CREATE TABLE CourseInstructors (
    courseId UUID NOT NULL,
    userId UUID NOT NULL,
    userRole VARCHAR(50) NOT NULL DEFAULT 'CO_INSTRUCTOR',
    addedAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    PRIMARY KEY (courseId, userId),
    CONSTRAINT FK_CourseInstructors_Course FOREIGN KEY (courseId) REFERENCES Courses(courseId) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_CourseInstructors_User FOREIGN KEY (userId) REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT CK_CourseInstructors_Role CHECK (userRole IN ('OWNER', 'CO_INSTRUCTOR'))
);

-- ============================================
-- 5. Modules Table
-- ============================================
CREATE TABLE Modules (
    moduleId UUID PRIMARY KEY,
    courseId UUID REFERENCES Courses(courseId) ON UPDATE CASCADE ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    sort_order INT NOT NULL DEFAULT 1
);

-- ============================================
-- 6. Lessons Table
-- ============================================
CREATE TABLE Lessons (
    lessonId UUID PRIMARY KEY,
    moduleId UUID REFERENCES Modules(moduleId) ON UPDATE CASCADE ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    durationSeconds INT,
    sort_order INT NOT NULL DEFAULT 1
);

-- ============================================
-- 6.1. Videos Table
-- ============================================
CREATE TABLE Videos (
    videoId UUID PRIMARY KEY,
    lessonId UUID NOT NULL REFERENCES Lessons(lessonId) ON UPDATE CASCADE ON DELETE CASCADE,
    s3Key VARCHAR(500) NOT NULL,
    s3Bucket VARCHAR(100) NOT NULL,
    originalFilename VARCHAR(255),
    fileSize BIGINT,
    mimeType VARCHAR(100),
    durationSeconds INT,
    processingStatus VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    uploadedAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 7. LessonResources Table
-- ============================================
CREATE TABLE LessonResources (
    resourceId UUID PRIMARY KEY,
    fileName VARCHAR(255),
    lessonId UUID REFERENCES Lessons(lessonId) ON UPDATE CASCADE ON DELETE CASCADE,
    resourceUrl VARCHAR(1000),
    resourceType VARCHAR(50),
    s3Key VARCHAR(1000), 
    fileSize BIGINT, 
    createdAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 8. Enrollments Table
-- ============================================
CREATE TABLE Enrollments (
    enrollmentId UUID PRIMARY KEY,
    userId UUID REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    courseId UUID REFERENCES Courses(courseId) ON UPDATE CASCADE ON DELETE CASCADE,
    enrolledAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    progressPercent FLOAT DEFAULT 0,
    UNIQUE (userId, courseId)
);

-- ============================================
-- 9. Progress Table
-- ============================================
CREATE TABLE Progress (
    progressId UUID PRIMARY KEY,
    userId UUID REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    lessonId UUID REFERENCES Lessons(lessonId) ON UPDATE CASCADE ON DELETE CASCADE,
    watchedSeconds INT DEFAULT 0,
    progressPercent FLOAT DEFAULT 0,
    lastWatchedAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (userId, lessonId)
);

-- ============================================
-- 10. Reviews Table
-- ============================================
CREATE TABLE Reviews (
    reviewId UUID PRIMARY KEY,
    userId UUID REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    courseId UUID REFERENCES Courses(courseId) ON UPDATE CASCADE ON DELETE CASCADE,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(500),
    createdAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMPTZ NULL,
    isActive BOOLEAN DEFAULT TRUE
);

-- ============================================
-- 11. Comments Table
-- ============================================
CREATE TABLE Comments (
    commentId UUID PRIMARY KEY,
    lessonId UUID REFERENCES Lessons(lessonId) ON UPDATE CASCADE ON DELETE CASCADE,
    userId UUID REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    content VARCHAR(500) NOT NULL,
    createdAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMPTZ NULL,
    parentId UUID NULL REFERENCES Comments(commentId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    isActive BOOLEAN DEFAULT TRUE
);

-- ============================================
-- 12. Payments Table
-- ============================================
CREATE TABLE Payments (
    paymentId UUID PRIMARY KEY,
    userId UUID REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    courseId UUID REFERENCES Courses(courseId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    enrollmentId UUID REFERENCES Enrollments(enrollmentId) ON UPDATE CASCADE ON DELETE CASCADE,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'VND',
    paymentMethod VARCHAR(50), 
    paymentStatus VARCHAR(50) DEFAULT 'PENDING',
    transactionId VARCHAR(100) UNIQUE,
    vnpayOrderInfo VARCHAR(500),
    vnpayResponseCode VARCHAR(50),
    createdAt TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    paidAt TIMESTAMPTZ NULL
);

-- ============================================
-- 13. Quizzes Table
-- ============================================
CREATE TABLE Quizzes (
    quizId UUID PRIMARY KEY,
    courseId UUID NOT NULL REFERENCES Courses(courseId) ON UPDATE CASCADE ON DELETE CASCADE,
    moduleId UUID NULL REFERENCES Modules(moduleId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    lessonId UUID NULL REFERENCES Lessons(lessonId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    passingScore INT NOT NULL DEFAULT 70,
    timeLimit INT NOT NULL DEFAULT 0,
    maxAttempts INT NOT NULL DEFAULT 0,
    isPublished BOOLEAN NOT NULL DEFAULT FALSE,
    orderIndex INT NOT NULL DEFAULT 0,
    version BIGINT NOT NULL DEFAULT 0, 
    createdAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMPTZ NULL
);

-- ============================================
-- 14. Questions Table
-- ============================================
CREATE TABLE Questions (
    questionId UUID PRIMARY KEY,
    quizId UUID NOT NULL REFERENCES Quizzes(quizId) ON UPDATE CASCADE ON DELETE CASCADE,
    questionText TEXT NOT NULL,
    question_type VARCHAR(50) NOT NULL DEFAULT 'MULTIPLE_CHOICE',
    points INT NOT NULL DEFAULT 1,
    orderIndex INT NOT NULL DEFAULT 0,
    explanation TEXT,
    version BIGINT NOT NULL DEFAULT 0, 
    CONSTRAINT CK_Questions_Type CHECK (question_type IN ('MULTIPLE_CHOICE', 'MULTIPLE_SELECT', 'TRUE_FALSE', 'SHORT_ANSWER'))
);

-- ============================================
-- 15. Answers Table
-- ============================================
CREATE TABLE Answers (
    answerId UUID PRIMARY KEY,
    questionId UUID REFERENCES Questions(questionId) ON UPDATE CASCADE ON DELETE CASCADE,
    answerText TEXT NOT NULL,
    isCorrect BOOLEAN DEFAULT FALSE,
    orderIndex INT NOT NULL DEFAULT 0,
    version BIGINT NOT NULL DEFAULT 0 
);

-- ============================================
-- 16. QuizAttempts Table
-- ============================================
CREATE TABLE QuizAttempts (
    attemptId UUID NOT NULL PRIMARY KEY,
    quizId UUID NOT NULL REFERENCES Quizzes(quizId) ON UPDATE CASCADE ON DELETE CASCADE,
    userId UUID NOT NULL REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    attemptNumber INT NOT NULL,
    startedAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    submittedAt TIMESTAMPTZ NULL,
    totalScore INT NOT NULL DEFAULT 0,
    maxScore INT NOT NULL,
    percentage FLOAT NULL,
    attempt_status VARCHAR(50) NOT NULL DEFAULT 'IN_PROGRESS',
    passed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT CK_QuizAttempts_Status CHECK (attempt_status IN ('IN_PROGRESS', 'SUBMITTED', 'GRADED', 'EXPIRED'))
);

-- ============================================
-- 17. QuizAnswers Table
-- ============================================
CREATE TABLE QuizAnswers (
    quizAnswerId UUID NOT NULL PRIMARY KEY,
    attemptId UUID NOT NULL REFERENCES QuizAttempts(attemptId) ON UPDATE CASCADE ON DELETE CASCADE,
    questionId UUID NOT NULL REFERENCES Questions(questionId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    textAnswer TEXT NULL,
    isCorrect BOOLEAN NOT NULL DEFAULT FALSE,
    pointsEarned INT NOT NULL DEFAULT 0,
    answeredAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 18. QuizAnswerSelections Table
-- ============================================
CREATE TABLE QuizAnswerSelections (
    quizAnswerId UUID NOT NULL REFERENCES QuizAnswers(quizAnswerId) ON UPDATE CASCADE ON DELETE CASCADE,
    answerId UUID NOT NULL REFERENCES Answers(answerId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    PRIMARY KEY (quizAnswerId, answerId)
);

-- ============================================
-- 19. Certificates Table
-- ============================================
CREATE TABLE Certificates (
    certificateId UUID PRIMARY KEY,
    userId UUID NOT NULL REFERENCES Users(userId) ON UPDATE NO ACTION ON DELETE NO ACTION,
    courseId UUID NOT NULL REFERENCES Courses(courseId) ON UPDATE CASCADE ON DELETE CASCADE,
    certificateCode VARCHAR(100) NOT NULL UNIQUE,
    pdfUrl VARCHAR(255) NOT NULL,
    issuedAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finalScore FLOAT NULL,
    completionNote TEXT,
    isValid BOOLEAN NOT NULL DEFAULT TRUE,
    revokedAt TIMESTAMPTZ NULL,
    revokedReason VARCHAR(500),
    UNIQUE (userId, courseId)
);

CREATE TABLE PasswordResetTokens (
    tokenId UUID NOT NULL PRIMARY KEY,
    userId UUID NOT NULL REFERENCES Users(userId) ON UPDATE CASCADE ON DELETE CASCADE,
    resetCode VARCHAR(6) NOT NULL,
    createdAt TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiresAt TIMESTAMPTZ NOT NULL,
    isUsed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE InvalidatedTokens (
	tokenId VARCHAR(36) NOT NULL PRIMARY KEY,
	expiryTime TIMESTAMPTZ NOT NULL,
	invalidatedAt TIMESTAMPTZ
);

-- ============================================
-- Indexes
-- ============================================
CREATE INDEX IX_Users_Email ON Users(email);
CREATE INDEX IX_Courses_Slug ON Courses(slug);
CREATE INDEX IX_Modules_CourseId ON Modules(courseId);
CREATE INDEX IX_Lessons_ModuleId ON Lessons(moduleId);
CREATE INDEX IX_Progress_UserLesson ON Progress(userId, lessonId);
CREATE INDEX IX_Enrollments_UserCourse ON Enrollments(userId, courseId);
CREATE INDEX IX_Quizzes_CourseId ON Quizzes(courseId);
CREATE INDEX IX_Quizzes_ModuleId ON Quizzes(moduleId);
CREATE INDEX IX_Quizzes_LessonId ON Quizzes(lessonId);
CREATE INDEX IX_QuizAttempts_UserQuiz ON QuizAttempts(userId, quizId);
CREATE INDEX IX_QuizAnswers_Attempt ON QuizAnswers(attemptId);
CREATE INDEX IX_Payments_Enrollment ON Payments(enrollmentId);
CREATE INDEX IX_Certificates_Code ON Certificates(certificateCode);
CREATE INDEX IX_CourseInstructors_UserId ON CourseInstructors(userId);
CREATE INDEX idx_invalidated_tokens_expiry ON InvalidatedTokens(expiryTime);

-- ============================================
-- Views
-- ============================================
CREATE OR REPLACE VIEW vw_CourseStats AS
SELECT 
    c.courseId,
    c.title,
    COUNT(DISTINCT e.enrollmentId) AS totalEnrollments,
    AVG(r.rating) AS avgRating
FROM Courses c
LEFT JOIN Enrollments e ON c.courseId = e.courseId
LEFT JOIN Reviews r ON c.courseId = r.courseId AND r.isActive = TRUE
GROUP BY c.courseId, c.title;

-- ============================================
-- Stored Procedures (Commented out as strict Stored Procedures are less common in Postgres apps, 
-- and logic is often moved to app layer or Functions. The original Procedure was: UpdateCourseProgress)
-- ============================================
/*
CREATE OR REPLACE PROCEDURE UpdateCourseProgress(p_userId UUID, p_courseId UUID)
LANGUAGE plpgsql
AS $$
DECLARE
    totalLessons INT;
    completedLessons INT;
BEGIN
    SELECT COUNT(*) INTO totalLessons FROM Lessons l
    JOIN Modules m ON l.moduleId = m.moduleId
    WHERE m.courseId = p_courseId;

    SELECT COUNT(*) INTO completedLessons FROM Progress p
    JOIN Lessons l ON p.lessonId = l.lessonId
    JOIN Modules m ON l.moduleId = m.moduleId
    WHERE m.courseId = p_courseId 
        AND p.progressPercent >= 90
        AND p.userId = p_userId;

    UPDATE Enrollments
    SET progressPercent = (100.0 * completedLessons / NULLIF(totalLessons, 0))
    WHERE userId = p_userId AND courseId = p_courseId;
END;
$$;
*/
