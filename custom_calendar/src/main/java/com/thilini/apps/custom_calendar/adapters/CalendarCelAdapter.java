package com.thilini.apps.custom_calendar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.thilini.apps.custom_calendar.R;
import com.thilini.apps.custom_calendar.model.CustomCalCel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarCelAdapter extends RecyclerView.Adapter<CalendarCelAdapter.ViewHolder> {

    private static String[] colorArray = new String[]{"#FAA990","#8cff32","#F44701","#3CBEFC","#fdff32"};
    private Context mContext;
    private Spinner calendarDropdown;
    private ArrayList<CustomCalCel> cells;
    private RecyclerView calBody;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private int heightPixel;
    private int widthPixel;
    private int density ;

    public CalendarCelAdapter(ArrayList<CustomCalCel> cells, RecyclerView calBody, Spinner calDropdown, int height, int width, float density) {
        this.cells = cells;
        this.calBody = calBody;
        this.calendarDropdown = calDropdown;
        this.heightPixel = height ;
        this.widthPixel = width;
        this.density = (int)density ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout cellLayout;
        private TextView cellDate;
        private LinearLayout colorLinear;

        public ViewHolder(View itemView) {
            super(itemView);
            cellLayout = itemView.findViewById(R.id.calendar_cell_layout);
            cellDate = itemView.findViewById(R.id.cell_date);
            colorLinear = itemView.findViewById(R.id.color_linear);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_calendar_cell, parent, false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CustomCalCel cel = (CustomCalCel) this.cells.get(position);
        if (cel.isHeadCel()) {
            holder.cellDate.setText(cel.getText());
            //holder.cellDate.setTextColor(Color.parseColor("#000000"));
            holder.colorLinear.setOrientation(LinearLayout.HORIZONTAL);
            TextView tv = new TextView(mContext);
            //tv.setText("Mistaken Header");
            tv.setBackgroundColor(Color.parseColor(colorArray[0]));
            holder.colorLinear.addView(tv);
            //holder.colorLinear.setVisibility(View.INVISIBLE);
            if (cel.isHasRandomHeight()) {
                ViewGroup.LayoutParams lp = holder.cellLayout.getLayoutParams();
                lp.height = 100;
                holder.cellLayout.setLayoutParams(lp);
                holder.cellLayout.setClickable(false);
            }
        }else if (cel.isBodyCel()) {
            holder.colorLinear.setOrientation(LinearLayout.HORIZONTAL);
            holder.colorLinear.removeAllViews();
            holder.cellDate.setText(cel.getText());
            holder.cellDate.setTextColor(Color.parseColor(cel.getColorCode()));
            createCalendarEvents(holder,cel);
        }else if (cel.isFooterCel()) {
            holder.colorLinear.removeAllViews();
            holder.cellDate.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams lp = holder.cellLayout.getLayoutParams();
            lp.width = ((widthPixel)-300);
            holder.cellLayout.setLayoutParams(lp);
            holder.cellLayout.setClickable(false);
            holder.colorLinear.setOrientation(LinearLayout.VERTICAL);
            createVerticalCalendarEvents(holder,cel,(widthPixel-400));
        }
    }

    private void createVerticalCalendarEvents(final ViewHolder holder, CustomCalCel cell, final int width) {
        try {
            Date startDate = format.parse(cell.getDate().toString() + "");
            Date endDate = format.parse(cell.getDate().toString() + "");
            Calendar en = Calendar.getInstance();
            en.setTime(endDate);
            en.set(Calendar.HOUR,23);
            en.set(Calendar.MINUTE,59);
            en.set(Calendar.SECOND,59);
            en.set(Calendar.MILLISECOND,59);
            endDate = en.getTime();

            /*
            Query q = FirebaseFirestore.getInstance().collection("Users")
                    .document(Main.CURRENT_USER_KEY)
                    .collection("Tasks")
                    .whereGreaterThan("dueDate", startDate)
                    .whereLessThan("dueDate", endDate);
            q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> list = task.getResult().getDocuments();
                        if (list.size()>0) {
                            int i = 0;
                            for (DocumentSnapshot snapshot : list) {
                                com.smart.planner.POJOs.Task t = snapshot.toObject(com.smart.planner.POJOs.Task.class);
                                if(i <= 4) {
                                    TextView tv = new TextView(mContext);
                                    //tv.setWidth(width);
                                    tv.setText(t.getTaskName());
                                    tv.setBackgroundColor(Color.parseColor(colorArray[i]));

                                    holder.colorLinear.addView(tv);
                                    i++;
                                }
                            }
                        }
                    }
                }
            });
            */
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    private void createCalendarEvents(final ViewHolder holder, CustomCalCel cell) {
        try {
            Date startDate = format.parse(cell.getDate().toString() + "");
            Date endDate = format.parse(cell.getDate().toString() + "");
            Calendar en = Calendar.getInstance();
            en.setTime(endDate);
            en.set(Calendar.HOUR,23);
            en.set(Calendar.MINUTE,59);
            en.set(Calendar.SECOND,59);
            en.set(Calendar.MILLISECOND,59);
            endDate = en.getTime();
            /*
            Query q = FirebaseFirestore.getInstance().collection("Users")
                    .document(Main.CURRENT_USER_KEY)
                    .collection("Tasks")
                    .whereGreaterThan("dueDate", startDate)
                    .whereLessThan("dueDate", endDate);
            q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        List<DocumentSnapshot> list = task.getResult().getDocuments();
                        if (list.size()>0) {
                            int i = 0;
                            for (DocumentSnapshot snapshot : list) {
                                if(i <= 4) {
                                    TextView tv = new TextView(mContext);
                                    tv.setWidth(20);
                                    tv.setHeight(20);

                                    GradientDrawable gd = new GradientDrawable();
                                    gd.setShape(GradientDrawable.OVAL);
                                    gd.setColor(Color.parseColor(colorArray[i]));

                                    tv.setBackground(gd);
                                    holder.colorLinear.addView(tv);
                                    i++;
                                }
                            }
                        }
                    }
                }
            });
            *
             */
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
