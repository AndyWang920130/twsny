package com.tswny.init.webservice.dto;

public class AirlineData {
    private String company;
    private String AirlineCode;
    private String StartDrome;
    private String ArriveDrome;
    private String StartTime;
    private String ArriveTime;
    private String Mode;
    private String AirlineStop;
    private String Week;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAirlineCode() {
        return AirlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        AirlineCode = airlineCode;
    }

    public String getStartDrome() {
        return StartDrome;
    }

    public void setStartDrome(String startDrome) {
        StartDrome = startDrome;
    }

    public String getArriveDrome() {
        return ArriveDrome;
    }

    public void setArriveDrome(String arriveDrome) {
        ArriveDrome = arriveDrome;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getArriveTime() {
        return ArriveTime;
    }

    public void setArriveTime(String arriveTime) {
        ArriveTime = arriveTime;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getAirlineStop() {
        return AirlineStop;
    }

    public void setAirlineStop(String airlineStop) {
        AirlineStop = airlineStop;
    }

    public String getWeek() {
        return Week;
    }

    public void setWeek(String week) {
        Week = week;
    }
}
