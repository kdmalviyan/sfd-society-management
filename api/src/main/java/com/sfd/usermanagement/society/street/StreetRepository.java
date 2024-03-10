package com.sfd.usermanagement.society.street;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author kuldeep
 */
public record StreetRepository(MongoTemplate mongoTemplate) {
    public Street create(Street street) {
        return mongoTemplate.save(street);
    }

    public Street findBySocietyIdAndBlockIdAndStreetNum(String societyId,
                                                        String blockId,
                                                        String streetNum) {
        Criteria criteria = Criteria.where("societyId").is(societyId)
                .andOperator(Criteria.where("blockId").is(blockId))
                .andOperator(Criteria.where("streetNum").is(streetNum));
        Query query= new Query(criteria);
        return mongoTemplate.findOne(query, Street.class);
    }

    public List<Street> findBySocietyId(String societyId) {
        Criteria criteria = Criteria.where("societyId").is(societyId);
        return mongoTemplate.find(new Query(criteria), Street.class);
    }

    public List<Street> findBySocietyIdAndBlockId(String societyId, String blockId) {
        Criteria criteria = Criteria.where("societyId").is(societyId)
                .andOperator(Criteria.where("blockId").is(blockId));
        return mongoTemplate.find(new Query(criteria), Street.class);
    }
}
