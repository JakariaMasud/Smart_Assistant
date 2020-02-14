package com.example.smartassistant.Model;

public class TimeBasedEvent {
    private long id;
    private String title;
    private String type;
    private String period;
    private long selectedTime;

    public TimeBasedEvent() {
    }

    public TimeBasedEvent(final long id, final String title, final String type, final String period, final long selectedTime) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.period = period;
        this.selectedTime = selectedTime;
    }

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }

    public long getSelectedTime() {
        return this.selectedTime;
    }

    public void setSelectedTime(final long selectedTime) {
        this.selectedTime = selectedTime;
    }
}


