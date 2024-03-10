package com.sfd.usermanagement.society.block;

import com.sfd.usermanagement.common.InvalidDataException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

/**
 * @author kuldeep
 */
public record BlockService(BlockRepository repository) {
    public Block create(Block block) {
        checkIfAlreadyExists(block.getBlockName(), block.getSocietyId());
        return repository.create(block);
    }

    private void checkIfAlreadyExists(String blockName, String societyId) {
        Block block = repository.findByNameAndSocietyId(blockName, societyId);
        if(Objects.nonNull(block)) {
            throw new InvalidDataException("Block is already exists in selected society.", HttpStatus.IM_USED.value());
        }
    }

    public Object findById(String blockId) {
        return repository().findById(blockId);
    }


    public Block findBySocietyIdAndBlockId(String blockId, String societyId) {
        return repository.findBySocietyIdAndBlockId(blockId, societyId);
    }

    public List<Block> findBySocietyId(String societyId) {
        return repository.findBySocietyId(societyId);
    }
}
