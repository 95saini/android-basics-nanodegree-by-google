package com.example.arieldiax.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * @const QUESTIONS_NUMBER Number of the questions.
     */
    private static final int QUESTIONS_NUMBER = 5;

    /**
     * @var mQuestion1Response{1,3}CheckBox Response views for Question #1.
     */
    private CheckBox mQuestion1Response1CheckBox;
    private CheckBox mQuestion1Response2CheckBox;
    private CheckBox mQuestion1Response3CheckBox;

    /**
     * @var mQuestion2ResponseRadioGroup Response group for Question #2.
     */
    private RadioGroup mQuestion2ResponseRadioGroup;

    /**
     * @var mQuestion3ResponseEditText Response field for Question #3.
     */
    private EditText mQuestion3ResponseEditText;

    /**
     * @var mQuestion4ResponseRadioGroup Response group for Question #4.
     */
    private RadioGroup mQuestion4ResponseRadioGroup;

    /**
     * @var mQuestion5Response{1,3}CheckBox Response views for Question #5.
     */
    private CheckBox mQuestion5Response1CheckBox;
    private CheckBox mQuestion5Response2CheckBox;
    private CheckBox mQuestion5Response3CheckBox;

    /**
     * @var mMainToast Toast message for interaction buttons.
     */
    private Toast mMainToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        init();
    }

    /**
     * Initializes the user interface view bindings.
     */
    private void initUi() {
        mQuestion1Response1CheckBox = (CheckBox) findViewById(R.id.question_1_response_1_check_box);
        mQuestion1Response2CheckBox = (CheckBox) findViewById(R.id.question_1_response_2_check_box);
        mQuestion1Response3CheckBox = (CheckBox) findViewById(R.id.question_1_response_3_check_box);
        mQuestion2ResponseRadioGroup = (RadioGroup) findViewById(R.id.question_2_response_radio_group);
        mQuestion3ResponseEditText = (EditText) findViewById(R.id.question_3_response_edit_text);
        mQuestion4ResponseRadioGroup = (RadioGroup) findViewById(R.id.question_4_response_radio_group);
        mQuestion5Response1CheckBox = (CheckBox) findViewById(R.id.question_5_response_1_check_box);
        mQuestion5Response2CheckBox = (CheckBox) findViewById(R.id.question_5_response_2_check_box);
        mQuestion5Response3CheckBox = (CheckBox) findViewById(R.id.question_5_response_3_check_box);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mMainToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
    }

    /**
     * Submits the responses for all questions.
     *
     * @param view Instance of the View class.
     */
    public void submitQuestionResponses(View view) {
        int numberOfAnswers = 0;
        String question1Response = "";
        question1Response += getCheckBoxValue(mQuestion1Response1CheckBox);
        question1Response += getCheckBoxValue(mQuestion1Response2CheckBox);
        question1Response += getCheckBoxValue(mQuestion1Response3CheckBox);
        if (!question1Response.isEmpty() && question1Response.substring(1).equals(getString(R.string.answer_question_1))) {
            numberOfAnswers++;
        }
        String question2Response = getRadioGroupValue(mQuestion2ResponseRadioGroup);
        if (question2Response.equals(getString(R.string.answer_question_2))) {
            numberOfAnswers++;
        }
        String question3Response = getEditTextValue(mQuestion3ResponseEditText);
        if (question3Response.trim().equalsIgnoreCase(getString(R.string.answer_question_3))) {
            numberOfAnswers++;
        }
        String question4Response = getRadioGroupValue(mQuestion4ResponseRadioGroup);
        if (question4Response.equals(getString(R.string.answer_question_4))) {
            numberOfAnswers++;
        }
        String question5Response = "";
        question5Response += getCheckBoxValue(mQuestion5Response1CheckBox);
        question5Response += getCheckBoxValue(mQuestion5Response2CheckBox);
        question5Response += getCheckBoxValue(mQuestion5Response3CheckBox);
        if (!question5Response.isEmpty() && question5Response.substring(1).equals(getString(R.string.answer_question_5))) {
            numberOfAnswers++;
        }
        mMainToast.setText(getString(R.string.message_score, numberOfAnswers, QUESTIONS_NUMBER));
        mMainToast.show();
    }

    /**
     * Gets the check box value, if checked.
     *
     * @param checkBox Instance of the CheckBox class.
     * @return The check box value, if checked.
     */
    private String getCheckBoxValue(CheckBox checkBox) {
        String checkBoxValue = "";
        if (checkBox.isChecked()) {
            checkBoxValue = "," + checkBox.getText().toString();
        }
        return checkBoxValue;
    }

    /**
     * Gets the radio group value, if checked.
     *
     * @param radioGroup Instance of the RadioGroup class.
     * @return The radio group value, if checked.
     */
    private String getRadioGroupValue(RadioGroup radioGroup) {
        String radioGroupValue = "";
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId != -1) {
            radioGroupValue = ((RadioButton) findViewById(checkedRadioButtonId)).getText().toString();
        }
        return radioGroupValue;
    }

    /**
     * Gets the edit text value.
     *
     * @param editText Instance of the EditText class.
     * @return The edit text value.
     */
    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * Resets the responses for all questions back to empty.
     *
     * @param view Instance of the View class.
     */
    public void resetQuestionResponses(View view) {
        mQuestion1Response1CheckBox.setChecked(false);
        mQuestion1Response2CheckBox.setChecked(false);
        mQuestion1Response3CheckBox.setChecked(false);
        mQuestion2ResponseRadioGroup.clearCheck();
        mQuestion3ResponseEditText.setText("");
        mQuestion4ResponseRadioGroup.clearCheck();
        mQuestion5Response1CheckBox.setChecked(false);
        mQuestion5Response2CheckBox.setChecked(false);
        mQuestion5Response3CheckBox.setChecked(false);
        mMainToast.setText(R.string.message_question_responses_reset);
        mMainToast.show();
    }
}
