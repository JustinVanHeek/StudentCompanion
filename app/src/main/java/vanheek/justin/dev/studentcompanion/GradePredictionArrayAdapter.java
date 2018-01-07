package vanheek.justin.dev.studentcompanion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vanheek.justin.dev.studentcompanion.objects.Assignment;
import vanheek.justin.dev.studentcompanion.objects.Course;
import vanheek.justin.dev.studentcompanion.objects.Exam;

/**
 * Created by justi on 2017-12-29.
 */

public class GradePredictionArrayAdapter extends ArrayAdapter<Course> {

    private final Context context;
    private final Course[] values;

    public GradePredictionArrayAdapter(Context context, Course[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.grade_pred_row, parent, false);

        Course course = values[position];

        //Set name
        ((TextView)rowView.findViewById(R.id.gradeCourse)).setText(course.getName());

        //Do calculations
        double earned = 0;
        double lost = 0;
        double currentPoints = 0;
        double totalPoints = 0;
        for(Assignment a : course.getAssignments()) {
            earned = earned + a.getGrade()*a.getPercentOfGrade()/100;
            lost = lost + (100-a.getGrade())*a.getPercentOfGrade()/100;
            currentPoints = currentPoints + a.getGrade()*a.getPercentOfGrade()/100;
            totalPoints = totalPoints + a.getPercentOfGrade();
        }
        for(Exam e : course.getExams()) {
            earned = earned + e.getGrade()*e.getPercentOfGrade()/100;
            lost = lost + (100-e.getGrade())*e.getPercentOfGrade()/100;
            currentPoints = currentPoints + e.getGrade()*e.getPercentOfGrade()/100;
            totalPoints = totalPoints + e.getPercentOfGrade();
        }
        final double predictedGrade = currentPoints/totalPoints*100;

        //Display results in textual form
        ((TextView)rowView.findViewById(R.id.gradePredictionText)).setText("Predicted Grade: " + predictedGrade + "%");
        ((TextView)rowView.findViewById(R.id.gradeEarnedText)).setText("Minimum Possible Grade: " + earned + "%");
        ((TextView)rowView.findViewById(R.id.gradeLostText)).setText("Maximum Possible Grade " + (100-lost) + "%" );

        //Display results in visual form
        final double finalLost = lost;
        final double finalEarned = earned;
        rowView.findViewById(R.id.gradeBar).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rowView.findViewById(R.id.gradeBar).getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int barWidth = rowView.findViewById(R.id.gradeBar).getWidth();

                Log.d("GradePred","W"+barWidth);

                //Earned
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams((int) (barWidth* finalEarned /100+0.5),50);
                rowView.findViewById(R.id.gradeEarned).setLayoutParams(params);
                //Lost
                ConstraintLayout.LayoutParams params2 = new ConstraintLayout.LayoutParams((int) (barWidth* finalLost /100+0.5),50);
                rowView.findViewById(R.id.gradeLost).setLayoutParams(params2);
                //Predicted Grade
                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(3,80);
                lp.setMargins((int)(barWidth*predictedGrade/100+0.5),10,0,0);
                rowView.findViewById(R.id.gradePrediction).setLayoutParams(lp);

                ConstraintSet set = new ConstraintSet();
                ConstraintLayout lay = (ConstraintLayout)rowView.findViewById(R.id.conLay);
                set.clone(lay);
                set.connect(R.id.gradeBar,ConstraintSet.TOP,R.id.conLay,ConstraintSet.TOP,15);
                set.connect(R.id.gradeEarned,ConstraintSet.LEFT,R.id.gradeBar,ConstraintSet.LEFT,0);
                set.connect(R.id.gradeEarned,ConstraintSet.TOP,R.id.gradeBar,ConstraintSet.TOP,0);
                set.connect(R.id.gradeLost,ConstraintSet.RIGHT,R.id.gradeBar,ConstraintSet.RIGHT,0);
                set.connect(R.id.gradeLost,ConstraintSet.TOP,R.id.gradeBar,ConstraintSet.TOP,0);
                set.connect(R.id.gradePrediction,ConstraintSet.LEFT,R.id.gradeBar,ConstraintSet.LEFT,(int)(barWidth*predictedGrade/100+0.5));
                set.applyTo(lay);
            }
        });



        return rowView;
    }

}
