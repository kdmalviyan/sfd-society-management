package com.sfd.usermanagement.society;

import com.sfd.usermanagement.common.InvalidDataException;
import com.sfd.usermanagement.society.block.Block;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @author kuldeep
 */
public record SocietyRepository(MongoTemplate mongoTemplate) {
    public Society save(Society society) {
        checkIfAlreadyExists(society.getName(), society.getAddress());
        return mongoTemplate.save(society);
    }

    private void checkIfAlreadyExists(String name, Address address) {
        Criteria criteria = Criteria
                .where("name").is(name)
                .andOperator(Criteria.where("address.line1").is(address.getLine1()))
                .andOperator(Criteria.where("address.line2").is(address.getLine2()))
                .andOperator(Criteria.where("address.city").is(address.getCity()))
                .andOperator(Criteria.where("address.state").is(address.getState()))
                .andOperator(Criteria.where("address.country").is(address.getCountry()))
                .andOperator(Criteria.where("address.zipcode").is(address.getZipcode()));
        Query query = Query.query(criteria);
        if(Objects.nonNull(mongoTemplate.findOne(query, Society.class))) {
            throw new InvalidDataException("Society is already registered in our system." +
                    " Reach out to our Admin.", HttpStatus.IM_USED.value());
        }
    }

    public Society findById(String societyId) {
        return mongoTemplate.findById(societyId, Society.class);
    }
}
