package com.makuners.android.androidquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static android.text.TextUtils.isEmpty;
import static com.makuners.android.androidquiz.R.id.welcome_line;
import static com.makuners.android.androidquiz.R.string.user_name;

public class MainActivity extends AppCompatActivity {
    int[] scoreTable = new int[6];
    int globalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the Intent that started this activity and extract the string
        Intent i = getIntent();
        String userName = i.getStringExtra(IntroActivity.USER_NAME);
        //Set the name as welcome line text
        Resources res = getResources();
        String defaultUserName = res.getString(R.string.default_user_name);
        //Check if the user has left the field empty and, in case, replace name
        //with a generic placeholder
        if (!isEmpty(userName)) {
            userName = String.format(res.getString(user_name), userName);
        } else {
            userName = String.format(res.getString(user_name), defaultUserName);
        }
        //Set the welcome headline
        TextView welcomeLineTextView = (TextView) findViewById(welcome_line);
        welcomeLineTextView.setText(userName);

    }
    public void question1RadioButtons(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.question_1_answer_2:
                if (checked)
                   scoreTable[0] = 1;
                    break;
            default:
                scoreTable[0] = 0;
                break;
        }

    }

    public void question2TextField(){
        TextView question2AnswerView = (TextView) findViewById(R.id.question_2_answer);
        String question2Answer = question2AnswerView.getText().toString();
        if(question2Answer.equalsIgnoreCase("java")){
            scoreTable[1] = 1;
        }else{
            scoreTable[1] = 0;
        }
    }

    public void question3CheckBoxes() {

        //Set an array with the answers, true when correct (checked or unchecked)
        //false when wrong
        boolean[] checkBoxesScore = new boolean[4];

        //Check the status of checkbox1 (unchecked is correct)
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.question_3_answer_1);
        checkBoxesScore[0] = !checkBox1.isChecked();

        //Check the status of checkbox2 (checked is correct)
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.question_3_answer_2);
        checkBoxesScore[1] = checkBox2.isChecked();

        //Check the status of checkbox3 (unchecked is correct)
        final CheckBox checkBox3 = (CheckBox) findViewById(R.id.question_3_answer_3);
        checkBoxesScore[2] = !checkBox3.isChecked();

        //Check the status of checkbox4 (checked is correct)
        final CheckBox checkBox4 = (CheckBox) findViewById(R.id.question_3_answer_4);
        checkBoxesScore[3] = checkBox4.isChecked();

        //Check if all the answers have value true, if not exit
        //and set the score for this question to 0
        for(int i = 0; i < checkBoxesScore.length; i++){
            if(checkBoxesScore[i]){
                scoreTable[2] = 1;
            }else{
                scoreTable[2] = 0;
                return;
            }
        }

    }

    public void question4RadioButtons(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.question_4_answer_3:
                if (checked)
                    scoreTable[3] = 1;
                break;
            default:
                scoreTable[3] = 0;
                break;
        }

    }

    public void question5RadioButtons(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.question_5_answer_2:
                if (checked)
                    scoreTable[4] = 1;
                break;
            default:
                scoreTable[4] = 0;
                break;
        }

    }

    public void question6RadioButtons(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.question_6_answer_3:
                if (checked)
                    scoreTable[5] = 1;
                break;
            default:
                scoreTable[5] = 0;
                break;
        }

    }

    public void calculateFinalScore(View view){

        //Initialize the final score view, kept hidden until calculate button is pressed
        TextView finalScoreDisplay = (TextView) findViewById(R.id.final_score);
        finalScoreDisplay.setVisibility(View.VISIBLE);

        //Calculate the score for question 2
        question2TextField();

        //Calculate the score for question 3
        question3CheckBoxes();

        //Reset the globalScore variable to avoid unreal sums of points
        globalScore = 0;

        for(int i = 0; i<scoreTable.length; i++){
            if(scoreTable[i] == 1){
                globalScore++;
            }
        }
        displayFinalScore(finalScoreDisplay);

    }

    public void displayFinalScore(TextView view){
        view.setText(String.valueOf(globalScore));

        for(int i = 0; i < scoreTable.length; i++){
            //Dynamically generate a reference to each question wrapper
            int viewId = this.getResources().getIdentifier("question_"+(i+1)+"_wrapper", "id", getPackageName());
            LinearLayout wrapper = (LinearLayout) findViewById(viewId);
            //If the question has been answered correctly set a green background
            if(scoreTable[i] == 1){
                wrapper.setBackgroundColor(getResources().getColor(R.color.rightAnswerBackground));
            //Wrong answer, set the background color to red
            }else{
                wrapper.setBackgroundColor(getResources().getColor(R.color.wrongAnswerBackground));
            }
        }

        //Jump to the bottom of the view to focus the just-shown
        //score indicator
        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        String resultMessage;
        if(globalScore <= 3){
            resultMessage = String.format(getString(R.string.zero_to_three_correct_answers), String.valueOf(globalScore));
        }else if(globalScore == 4 || globalScore == 5){
            resultMessage = String.format(getString(R.string.four_or_five_correct_answers), String.valueOf(globalScore));
        }else{
            resultMessage = String.format(getString(R.string.all_correct_answers), String.valueOf(globalScore));
        }
        Toast toast = Toast.makeText(this, resultMessage, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

    }

}
