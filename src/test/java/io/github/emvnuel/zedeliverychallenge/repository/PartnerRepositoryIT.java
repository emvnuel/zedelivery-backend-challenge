package io.github.emvnuel.zedeliverychallenge.repository;

import io.github.emvnuel.zedeliverychallenge.model.Partner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DataMongoTest
@ExtendWith(SpringExtension.class)
@Slf4j
class PartnerRepositoryIT {

    @Autowired
    private PartnerRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() throws Exception {


        List<Point> coordinates = Arrays.asList(
            new Point(-43.36556, -22.99669),
            new Point(-43.36539, -23.01928),
            new Point(-43.26583, -23.01802),
            new Point(-43.25724, -23.00649),
            new Point(-43.23355, -23.00127),
            new Point(-43.2381, -22.99716),
            new Point(-43.23866, -22.99649),
            new Point(-43.24063, -22.99756),
            new Point(-43.24634, -22.99736),
            new Point(-43.24677, -22.99606),
            new Point(-43.24067, -22.99381),
            new Point(-43.24886, -22.99121),
            new Point(-43.25617,-22.99456),
            new Point(-43.25625,-22.99203),
            new Point(-43.25346,-22.99065),
            new Point(-43.29599,-22.98283),
            new Point(-43.3262,-22.96481),
            new Point(-43.33427,-22.96402),
            new Point(-43.33616,-22.96829),
            new Point(-43.342,-22.98157),
            new Point(-43.34817,-22.97967),
            new Point(-43.35142,-22.98062),
            new Point(-43.3573,-22.98084),
            new Point(-43.36522,-22.98032),
            new Point(-43.36696,-22.98422),
            new Point(-43.36717,-22.98855),
            new Point(-43.36636,-22.99351),
            new Point(-43.36556,-22.99669)
        );

        repository.save(new Partner("Adega Osasco",
                "Ze da Ambev",
                "02.453.716/000170",
                new GeoJsonMultiPolygon(Arrays.asList(new GeoJsonPolygon(coordinates))),
                new GeoJsonPoint(-43.297337, -23.013538)));
    }

    @Test
    public void shouldReturnNearestRestaurantWithinCoverageArea() {
        Optional<Partner> partner = repository.findNearestPartnerWithinCoverageArea(-43.297335, -23.013532);
        assertTrue(partner.isPresent());
    }

    @Test
    public void shouldNotReturnNearestRestaurantIfOutsideCoverageArea() {
        Optional<Partner> partner = repository.findNearestPartnerWithinCoverageArea(43.297335, 23.013532);
        assertTrue(partner.isEmpty());
    }
}