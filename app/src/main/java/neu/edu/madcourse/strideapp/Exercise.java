package neu.edu.madcourse.strideapp;

public class Exercise {

    // It is strange that these are all string and leads to complications in the stop method of MapsActivity.
    // However, the adapter requires them to be strings for display settext
    // Maybe I can concatenate and convert to string here (ie, add MPH) instead, although still would return stirng. or in the adapter?
    String  activity, date, time, speed, distance, TotalDistance;
/*
    public String getId() {
        return id;
    }
*/

    public String getActivity() {
        return activity;
    }

    public String getDate() {
        return date;
    }

    public String getDistance() {
        return distance;
    }

    public String getTotalDistance() {
        return TotalDistance;
    }

    public String getTime() {
        return time;
    }


    public String getSpeed() {
        return speed;
    }
}
