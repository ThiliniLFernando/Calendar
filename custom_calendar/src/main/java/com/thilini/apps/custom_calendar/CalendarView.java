package com.thilini.apps.custom_calendar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thilini.apps.custom_calendar.adapters.CalendarCelAdapter;
import com.thilini.apps.custom_calendar.model.CustomCalCel;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarView extends LinearLayout {

    private TextView year, month, forward, backward;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private LocalDate currentCalDate;
    private ArrayList<CustomCalCel> cells;
    private CalendarCelAdapter calendarViewAdapter;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_month, this);
        initComponents(view);
    }

    private void initComponents(View view) {
        currentCalDate = LocalDate.now();
        year = view.findViewById(R.id.year);
        month = view.findViewById(R.id.month);
        forward = view.findViewById(R.id.forward);
        backward = view.findViewById(R.id.backward);
        recyclerView = view.findViewById(R.id.custom_calendar);

        DisplayMetrics metrics = new DisplayMetrics();
        //((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        cells = new ArrayList<>();

        calendarViewAdapter = new CalendarCelAdapter(cells, recyclerView, null, metrics.heightPixels, metrics.widthPixels, metrics.density);
        recyclerView.setAdapter(calendarViewAdapter);
        layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration div1 = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        div1.setDrawable(new ColorDrawable(Color.parseColor("#808080")));
        recyclerView.addItemDecoration(div1);

        DividerItemDecoration div2 = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        div2.setDrawable(new ColorDrawable(Color.parseColor("#808080")));
        recyclerView.addItemDecoration(div2);

        recyclerView.setLayoutManager(layoutManager);
        displayParticularMonth();

        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCalDate != null) {
                    layoutManager = new GridLayoutManager(getContext(), 7);
                    currentCalDate = currentCalDate.minusMonths(1);
                    recyclerView.setLayoutManager(layoutManager);
                    displayParticularMonth();
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCalDate != null) {
                    layoutManager = new GridLayoutManager(getContext(), 7);
                    currentCalDate = currentCalDate.plusMonths(1);
                    recyclerView.setLayoutManager(layoutManager);
                    displayParticularMonth();
                }
            }
        });
    }

    private void displayParticularMonth() {
        cells.clear();
        ArrayList<LocalDate> dates = new ArrayList();
        year.setText(currentCalDate.getYear() + "");
        month.setText(currentCalDate.getMonth().name());

        cells.add(new CustomCalCel("Mon").setIsAHeadCel(true).setHasRandomHeight(true));
        cells.add(new CustomCalCel("Tue").setIsAHeadCel(true).setHasRandomHeight(true));
        cells.add(new CustomCalCel("Wed").setIsAHeadCel(true).setHasRandomHeight(true));
        cells.add(new CustomCalCel("Thu").setIsAHeadCel(true).setHasRandomHeight(true));
        cells.add(new CustomCalCel("Fri").setIsAHeadCel(true).setHasRandomHeight(true));
        cells.add(new CustomCalCel("Sat").setIsAHeadCel(true).setHasRandomHeight(true));
        cells.add(new CustomCalCel("Sun").setIsAHeadCel(true).setHasRandomHeight(true));

        LocalDate start = currentCalDate;
        start = start.withDayOfMonth(1);

        int emptyDatesBefore = start.getDayOfWeek().getValue() - 1; // tuesday --> 1
        LocalDate end = start;
        end = start.plusMonths(1);

        int emptyDatesAfter = 0; // monday --> 6
        String color1 = "#000000";
        String color2= "#586D71";

        if (emptyDatesBefore > 0) {
            for (int i = emptyDatesBefore; i > 0; i--) {
                LocalDate lastMonthDate = start.minusDays(i);
                cells.add(new CustomCalCel(lastMonthDate.getDayOfMonth() + "").setDate(lastMonthDate).setIsABodyCel(true).setColorCode("#808080"));
            }
        }

        while (start.isBefore(end)) {
            if (start.isEqual(LocalDate.now())) {
                cells.add(new CustomCalCel(start.getDayOfMonth() + "").setDate(start).setIsABodyCel(true).setColorCode(color2));
            } else {
                cells.add(new CustomCalCel(start.getDayOfMonth() + "").setDate(start).setIsABodyCel(true).setColorCode(color1));
            }
            start = start.plusDays(1);
        }

        if (cells.size() < 49) {
            emptyDatesAfter = 49 - cells.size();
        }

        if (emptyDatesAfter > 0) {
            for (int i = 0; i < emptyDatesAfter; i++) {
                LocalDate nextMonthDate = end.plusDays(i);
                cells.add(new CustomCalCel(nextMonthDate.getDayOfMonth() + "").setDate(nextMonthDate).setIsABodyCel(true).setColorCode("#808080"));
            }
        }
        calendarViewAdapter.notifyDataSetChanged();
    }
}
