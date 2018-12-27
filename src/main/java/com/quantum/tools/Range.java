package com.quantum.tools;

import java.math.BigDecimal;

public class Range {
    private BigDecimal start;

    public Range(BigDecimal start, BigDecimal end) {
        this.start = start;
        this.end = end;
    }

    private BigDecimal end;

    public BigDecimal getStart() {
        return start;
    }

    public void setStart(BigDecimal start) {
        this.start = start;
    }

    public BigDecimal getEnd() {
        return end;
    }

    public void setEnd(BigDecimal end) {
        this.end = end;
    }
}
