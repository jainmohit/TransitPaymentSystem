package com.littlepay.transit.model;

import java.time.LocalDateTime;

public class TapEvents {

    private final long id;
    private final LocalDateTime dateTime;
    private final TapType tapType;
    private final String stopId;
    private final String companyId;
    private final String busId;
    private final String panId;

    public String getBusId() {
        return busId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public long getId() {
        return id;
    }

    public String getPanId() {
        return panId;
    }

    public String getStopId() {
        return stopId;
    }

    public TapType getTapType() {
        return tapType;
    }

    private TapEvents(TapEventsBuilder builder) {
        this.id = builder.id;
        this.dateTime = builder.dateTime;
        this.tapType = builder.tapType;
        this.stopId = builder.stopId;
        this.companyId = builder.companyId;
        this.busId = builder.busId;
        this.panId = builder.panId;
    }

    public static class TapEventsBuilder {
        private long id;
        private LocalDateTime dateTime;
        private TapType tapType;
        private String stopId;
        private String companyId;
        private String busId;
        private String panId;

        public TapEventsBuilder id(long id) {
            this.id = id;
            return this;
        }

        public TapEventsBuilder dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public TapEventsBuilder tapType(TapType tapType) {
            this.tapType = tapType;
            return this;
        }

        public TapEventsBuilder stopId(String stopId) {
            this.stopId = stopId;
            return this;
        }

        public TapEventsBuilder companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        public TapEventsBuilder busId(String busId) {
            this.busId = busId;
            return this;
        }

        public TapEventsBuilder panId(String panId) {
            this.panId = panId;
            return this;
        }

        public TapEvents build() {
            return new TapEvents(this);
        }
    }
}
