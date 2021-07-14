package io.github.emvnuel.zedeliverychallenge.repository;

import io.github.emvnuel.zedeliverychallenge.model.Partner;


import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PartnerRepository {

    private final MongoTemplate mongoTemplate;

    public PartnerRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Partner save(Partner partner) {
        return mongoTemplate.save(partner);
    }

    public Optional<Partner> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Partner.class));
    }

    public Optional<Partner> findNearestPartnerWithinCoveraregeArea(Double lat, Double lon) {
        Point location = new Point(lat, lon);
        return Optional.ofNullable(
                mongoTemplate.findOne(new Query(Criteria.where("address").nearSphere(location)
                        .and("coverageArea").intersects(new GeoJsonPoint(location))), Partner.class));
    }
}
