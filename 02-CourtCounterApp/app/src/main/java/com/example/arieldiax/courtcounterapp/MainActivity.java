package com.example.arieldiax.courtcounterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /**
     * @var mTeamAScore Score counter for Team A.
     */
    private int mTeamAScore;

    /**
     * @var mTeamAScoreTextView Score view for Team A.
     */
    private TextView mTeamAScoreTextView;

    /**
     * @var mTeamBScore Score counter for Team B.
     */
    private int mTeamBScore;

    /**
     * @var mTeamBScoreTextView Score view for Team B.
     */
    private TextView mTeamBScoreTextView;

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
        mTeamAScoreTextView = (TextView) findViewById(R.id.team_a_score_text_view);
        mTeamBScoreTextView = (TextView) findViewById(R.id.team_b_score_text_view);
    }

    /**
     * Initializes the back end logic bindings.
     */
    private void init() {
        mTeamAScore = 0;
        mTeamBScore = 0;
        mMainToast = Toast.makeText(getApplicationContext(), R.string.message_team_scores_reset, Toast.LENGTH_SHORT);
    }

    /**
     * Increases the score for Team A by 6 points.
     *
     * @param view Instance of the View class.
     */
    public void addSixForTeamA(View view) {
        updateScoreForTeamA(6);
    }

    /**
     * Increases the score for Team A by 3 points.
     *
     * @param view Instance of the View class.
     */
    public void addThreeForTeamA(View view) {
        updateScoreForTeamA(3);
    }

    /**
     * Increases the score for Team A by 2 points.
     *
     * @param view Instance of the View class.
     */
    public void addTwoForTeamA(View view) {
        updateScoreForTeamA(2);
    }

    /**
     * Increases the score for Team A by 1 points.
     *
     * @param view Instance of the View class.
     */
    public void addOneForTeamA(View view) {
        updateScoreForTeamA(1);
    }

    /**
     * Updates both the logical and presenter counter for Team A.
     *
     * @param score Amount of points to be added.
     */
    private void updateScoreForTeamA(int score) {
        mTeamAScore += score;
        displayScoreForTeamA(mTeamAScore);
    }

    /**
     * Displays the given score for Team A.
     *
     * @param score Amount of points to be presented.
     */
    private void displayScoreForTeamA(int score) {
        mTeamAScoreTextView.setText(String.valueOf(score));
    }

    /**
     * Increases the score for Team B by 6 points.
     *
     * @param view Instance of the View class.
     */
    public void addSixForTeamB(View view) {
        updateScoreForTeamB(6);
    }

    /**
     * Increases the score for Team B by 3 points.
     *
     * @param view Instance of the View class.
     */
    public void addThreeForTeamB(View view) {
        updateScoreForTeamB(3);
    }

    /**
     * Increases the score for Team B by 2 points.
     *
     * @param view Instance of the View class.
     */
    public void addTwoForTeamB(View view) {
        updateScoreForTeamB(2);
    }

    /**
     * Increases the score for Team B by 1 points.
     *
     * @param view Instance of the View class.
     */
    public void addOneForTeamB(View view) {
        updateScoreForTeamB(1);
    }

    /**
     * Updates both the logical and presenter counter for Team B.
     *
     * @param score Amount of points to be added.
     */
    private void updateScoreForTeamB(int score) {
        mTeamBScore += score;
        displayScoreForTeamB(mTeamBScore);
    }

    /**
     * Displays the given score for Team B.
     *
     * @param score Amount of points to be presented.
     */
    private void displayScoreForTeamB(int score) {
        mTeamBScoreTextView.setText(String.valueOf(score));
    }

    /**
     * Resets the score for both teams back to 0.
     *
     * @param view Instance of the View class.
     */
    public void resetTeamScores(View view) {
        mTeamAScore = 0;
        mTeamBScore = 0;
        displayScoreForTeamA(mTeamAScore);
        displayScoreForTeamB(mTeamBScore);
        mMainToast.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("team_a_score", mTeamAScore);
        outState.putInt("team_b_score", mTeamBScore);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTeamAScore = savedInstanceState.getInt("team_a_score");
        mTeamBScore = savedInstanceState.getInt("team_b_score");
        displayScoreForTeamA(mTeamAScore);
        displayScoreForTeamB(mTeamBScore);
    }
}
