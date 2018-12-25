package com.quantum.tools;

public class Range {
    private double start;

    public Range(double start, double end) {
        this.start = start;
        this.end = end;
    }

    private double end;

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
