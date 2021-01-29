package io.github.manoj2601.cgpacalculator;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class myListAdapter extends ArrayAdapter<Course> {

    public myListAdapter(@NonNull Context context, ArrayList<Course> arrayList) {
        super(context, 0, arrayList);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if(currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_layout, parent, false);
        }

        Course currCourse = getItem(position);
        TextView title = currentItemView.findViewById(R.id.title);
        title.setText(currCourse.getCourseName());

        TextView subtitle = currentItemView.findViewById(R.id.subtitle);
        subtitle.setText("Credit: "+currCourse.getCredit()+" Grade: "+currCourse.getGrade());

        return currentItemView;

    }
}

