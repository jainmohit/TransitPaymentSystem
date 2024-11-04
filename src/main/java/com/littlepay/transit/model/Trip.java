package com.littlepay.transit.model;

import java.time.LocalDateTime;

public class Trip {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Long duration;
    private final String fromStopId;
    private final String toStopId;
    private final Double chargeAmount;
    private final String companyId;
    private final String busId;
    private final String panId;
    private final TripStatus status;


    private Trip (TripBuilder tripBuilder) {
        this.startTime = tripBuilder.startTime;
        this.endTime = tripBuilder.endTime;
        this.duration = tripBuilder.duration;
        this.fromStopId = tripBuilder.fromStopId;
        this.toStopId = tripBuilder.toStopId;
        this.chargeAmount = tripBuilder.chargeAmount;
        this.companyId = tripBuilder.companyId;
        this.busId = tripBuilder.busId;
        this.panId = tripBuilder.panId;
        this.status = tripBuilder.status;
    }

    public String getBusId() {
        return busId;
    }

    public Double getChargeAmount() {
        return chargeAmount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public Long getDuration() {
        return duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getFromStopId() {
        return fromStopId;
    }

    public String getPanId() {
        return panId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public TripStatus getStatus() {
        return status;
    }

    public String getToStopId() {
        return toStopId;
    }

    public static class TripBuilder {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Long duration;
        private String fromStopId;
        private String toStopId;
        private Double chargeAmount;
        private String companyId;
        private String busId;
        private String panId;
        private TripStatus status;

        public TripBuilder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public TripBuilder setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public TripBuilder setDuration(Long duration) {
            this.duration = duration;
            return this;
        }

        public TripBuilder setFromStopId(String fromStopId) {
            this.fromStopId = fromStopId;
            return this;
        }

        public TripBuilder setToStopId(String toStopId) {
            this.toStopId = toStopId;
            return this;
        }

        public TripBuilder setChargeAmount(Double chargeAmount) {
            this.chargeAmount = chargeAmount;
            return this;
        }

        public TripBuilder setCompanyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        public TripBuilder setBusId(String busId) {
            this.busId = busId;
            return this;
        }

        public TripBuilder setPanId(String panId) {
            this.panId = panId;
            return this;
        }

        public TripBuilder setStatus(TripStatus status) {
            this.status = status;
            return this;
        }

        public Trip build() {
            return new Trip(this);
        }
    }
}
