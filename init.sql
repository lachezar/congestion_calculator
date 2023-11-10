-- TODO: replace with migration scripts (Flyway)

CREATE TABLE public.filter_rule
(
    id bigserial NOT NULL,
    definition jsonb NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO filter_rule (definition) VALUES ('{"type":"VehicleTypeFilterRule","vehicleType":"Emergency"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"VehicleTypeFilterRule","vehicleType":"Bus"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"VehicleTypeFilterRule","vehicleType":"Diplomat"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"VehicleTypeFilterRule","vehicleType":"Motorcycle"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"VehicleTypeFilterRule","vehicleType":"Military"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"VehicleTypeFilterRule","vehicleType":"Foreign"}');

INSERT INTO filter_rule (definition) VALUES ('{"type":"WeekdayFilterRule","dow":"SATURDAY"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"WeekdayFilterRule","dow":"SUNDAY"}');

INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2012-12-31T23:00:00Z","endInclusive":"2013-01-01T23:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-01-04T23:00:00Z","endInclusive":"2013-01-06T23:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-03-27T22:00:00Z","endInclusive":"2013-04-01T22:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-04-29T22:00:00Z","endInclusive":"2013-05-01T22:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-05-07T22:00:00Z","endInclusive":"2013-05-09T22:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-06-04T22:00:00Z","endInclusive":"2013-06-06T22:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-06-20T22:00:00Z","endInclusive":"2013-06-21T22:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-06-30T22:00:00Z","endInclusive":"2013-07-31T22:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-10-31T23:00:00Z","endInclusive":"2013-11-01T23:00:00Z"}');
INSERT INTO filter_rule (definition) VALUES ('{"type":"DateRangeFilterRule","startInclusive":"2013-12-23T23:00:00Z","endInclusive":"2013-12-26T23:00:00Z"}');

CREATE TABLE public.charge_rule
(
    id bigserial NOT NULL,
    definition jsonb NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"06:00:00","end":"06:29:59.999999999","price":8}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"06:30:00","end":"06:59:59.999999999","price":13}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"07:00:00","end":"07:59:59.999999999","price":18}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"08:00:00","end":"08:29:59.999999999","price":13}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"08:30:00","end":"14:59:59.999999999","price":8}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"15:00:00","end":"15:29:59.999999999","price":13}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"15:30:00","end":"16:59:59.999999999","price":18}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"17:00:00","end":"17:59:59.999999999","price":13}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"18:00:00","end":"18:29:59.999999999","price":8}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"18:30:00","end":"23:59:59.999999999","price":0}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"TimeWindowChargeRule","start":"08:00:00","end":"05:59:59.999999999","price":0}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"MaxPerHourChargeRule","maxPrice":60}');
INSERT INTO charge_rule (definition) VALUES ('{"type":"MaxPerDayChargeRule","maxPrice":60}');
