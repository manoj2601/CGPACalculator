package io.github.manoj2601.cgpacalculator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    Button addbtn;
    ArrayList<Course> courses = new ArrayList<Course>();
    ListView show;
    Spinner creditSpinner;
    Spinner gradeSpinner;

    EditText courseName;
    double currCGPA, currCredits;

    public void onSubmit(View v) {

        EditText currCG1 = (EditText) findViewById(R.id.currCG);
        EditText currCredit1 = (EditText) findViewById(R.id.compCredits);

        String currCG = currCG1.getText().toString();
        String currCredit = currCredit1.getText().toString();

        if(currCG.length()==0 && currCredit.length()==0)
        {
            currCG = "8.081";
            currCredit = "86";
        }

        currCGPA = Double.parseDouble(currCG);
        currCredits = Double.parseDouble(currCredit);
        int totalCredits = 0;
        int credits = 0;
        for(int i=0; i<courses.size(); i++) {
            Course c = courses.get(i);
            credits += c.getCredit();
            totalCredits += c.getCredit()*c.getGrade();
        }
        double finalSGPA = ((double)totalCredits)/credits;

        totalCredits += (int) Math.round(currCGPA*currCredits);
        credits += (int)currCredits;
        double finalCGPA = ((double)totalCredits)/credits;
        DecimalFormat df = new DecimalFormat("#.###");
        String ret = "Final SGPA: "+ df.format(finalSGPA) + "\nFinal CGPA: "+df.format(finalCGPA) ;
        Toast.makeText(MainActivity.this, ret, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        creditSpinner = findViewById(R.id.creditSpinner);
        gradeSpinner = findViewById(R.id.gradeSpinner);

        ArrayAdapter<CharSequence> creditAdapter = ArrayAdapter.createFromResource(this, R.array.credits, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grades, android.R.layout.simple_spinner_item);

        creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        creditSpinner.setAdapter(creditAdapter);
        gradeSpinner.setAdapter(gradeAdapter);

        courseName = (EditText) findViewById(R.id.courseName);
        show = (ListView) findViewById(R.id.myList);
        addbtn = (Button) findViewById(R.id.button);
        addbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String currCredit = creditSpinner.getSelectedItem().toString();
                String currGrade = gradeSpinner.getSelectedItem().toString();

                String cName;
                String temp = courseName.getText().toString();
                if(temp.compareTo("") == 0)
                    cName = "Course "+ String.valueOf(courses.size()+1);
                else cName = temp;

                if(currGrade.compareTo("Select Grade") == 0 || currCredit.compareTo("Credits")==0)
                {
                    Toast.makeText(MainActivity.this, "Please Select Course Grade and credits.", Toast.LENGTH_LONG).show();
                    return;
                }
                Course c = new Course(cName, currGrade, currCredit);
                courses.add(c);
                myListAdapter adapter = new myListAdapter(MainActivity.this, courses);
//                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, grades);
                show.setAdapter(adapter);
                courseName.getText().clear();
                creditSpinner.setSelection(0);
                gradeSpinner.setSelection(0);
            }
        });
        registerForContextMenu(show);
        //footer copyright hyperlink
        TextView textView =(TextView)findViewById(R.id.copyright);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "Created by: <a href='http://cse.iitd.ac.in/~cs5180411/'>Manoj</a>&#128522;";
        textView.setText(Html.fromHtml(text));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.edit:
                Course c = courses.get(info.position);
                courseName.setText(c.getCourseName());
                creditSpinner.setSelection(6 - c.getCredit());
                gradeSpinner.setSelection(11 - c.getGrade());

        }
        courses.remove(info.position);
        myListAdapter adapter = new myListAdapter(MainActivity.this, courses);
        show.setAdapter(adapter);
        return super.onContextItemSelected(item);
    }
}