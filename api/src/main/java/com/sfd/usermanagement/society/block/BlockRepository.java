package com.sfd.usermanagement.society.block;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author kuldeep
 */
public record BlockRepository(MongoTemplate mongoTemplate) {
    public Block create(Block block) {
        return mongoTemplate.save(block);
    }

    public Block findByNameAndSocietyId(String blockName, String societyId) {
        Criteria criteria = Criteria.where("name").is(blockName)
                .andOperator(Criteria.where("societyId").is(societyId));
        Query query = Query.query(criteria);
        return mongoTemplate.findOne(query, Block.class);
    }

    public Block findById(String blockId) {
        return mongoTemplate.findById(blockId, Block.class);
    }

    public Block findBySocietyIdAndBlockId(String blockId, String societyId) {
        Criteria criteria = Criteria
                .where("blockId").is(blockId)
                .andOperator(Criteria.where("societyId").is(societyId));
        return mongoTemplate.findOne(new Query(criteria), Block.class);
    }

    public List<Block> findBySocietyId(String societyId) {
        Criteria criteria = Criteria
                .where("societyId").is(societyId);
        return mongoTemplate.find(new Query(criteria), Block.class);
    }
}
