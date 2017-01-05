package com.example.arieldiax.reportcard;

public class ReportCard {

    /**
     * @var GRADE_LETTER_* Letters of the grade.
     */
    private static final char GRADE_LETTER_A = 'A';
    private static final char GRADE_LETTER_B = 'B';
    private static final char GRADE_LETTER_C = 'C';
    private static final char GRADE_LETTER_D = 'D';
    private static final char GRADE_LETTER_F = 'F';

    /**
     * @var mTeacherName Name of the teacher.
     */
    private String mTeacherName;

    /**
     * @var mSubjectTitle Title of the subject.
     */
    private String mSubjectTitle;

    /**
     * @var mStudentName Name of the student.
     */
    private String mStudentName;

    /**
     * @var mStudentGrade Grade of the student.
     */
    private int mStudentGrade;

    /**
     * Creates a new ReportCard object.
     *
     * @param teacherName  Name of the teacher.
     * @param subjectTitle Title of the subject.
     * @param studentName  Name of the student.
     * @param studentGrade Grade of the student.
     */
    public ReportCard(String teacherName, String subjectTitle, String studentName, int studentGrade) {
        mTeacherName = teacherName;
        mSubjectTitle = subjectTitle;
        mStudentName = studentName;
        mStudentGrade = (studentGrade > 0) ? studentGrade : 0;
    }

    @Override
    public String toString() {
        return "{" +
                "\"teacher_name\":\"" + mTeacherName + "\"," +
                "\"subject_title\":\"" + mSubjectTitle + "\"," +
                "\"student_name\":\"" + mStudentName + "\"," +
                "\"student_grade\":" + mStudentGrade + "," +
                "\"grade_letter\":\"" + getGradeLetter() + "\"" +
                "}";
    }

    /**
     * Gets the name of the teacher.
     *
     * @return The name of the teacher.
     */
    public String getTeacherName() {
        return mTeacherName;
    }

    /**
     * Gets the title of the subject.
     *
     * @return The title of the subject.
     */
    public String getSubjectTitle() {
        return mSubjectTitle;
    }

    /**
     * Gets the name of the student.
     *
     * @return The name of the student.
     */
    public String getStudentName() {
        return mStudentName;
    }

    /**
     * Gets the grade of the student.
     *
     * @return The grade of the student.
     */
    public int getStudentGrade() {
        return mStudentGrade;
    }

    /**
     * Gets the letter of the grade.
     *
     * @return The letter of the grade.
     */
    public char getGradeLetter() {
        char gradeLetter = Character.MIN_VALUE;
        if (mStudentGrade >= 90) {
            gradeLetter = GRADE_LETTER_A;
        } else if (mStudentGrade >= 80) {
            gradeLetter = GRADE_LETTER_B;
        } else if (mStudentGrade >= 70) {
            gradeLetter = GRADE_LETTER_C;
        } else if (mStudentGrade >= 60) {
            gradeLetter = GRADE_LETTER_D;
        } else {
            gradeLetter = GRADE_LETTER_F;
        }
        return gradeLetter;
    }
}
