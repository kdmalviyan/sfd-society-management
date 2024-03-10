package com.sfd.usermanagement.society;

import com.sfd.usermanagement.common.InvalidDataException;
import com.sfd.usermanagement.society.block.Block;
import com.sfd.usermanagement.society.block.BlockService;
import com.sfd.usermanagement.society.street.Street;
import com.sfd.usermanagement.society.street.StreetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author kuldeep
 */

@Service
@RequiredArgsConstructor
@Slf4j
public record SocietyService(SocietyRepository repository,
                             BlockService blockService,
                             StreetService streetService) {
    public Society create(Society society) {
        return repository.save(society);
    }

    public Society findById(String societyId) {
        return repository.findById(societyId);
    }

    public Block addBlock(Block block, String societyId) {
        if(Objects.nonNull(repository.findById(societyId))) {
            block.setSocietyId(societyId);
            return blockService.create(block);
        } else {
            throw new InvalidDataException("Society does not exists. Please check with Admin.", HttpStatus.NOT_FOUND.value());
        }
    }

    public Street addStreet(Street street, String societyId, String blockId) {
        if(Objects.nonNull(blockId)) {
            if(Objects.nonNull(blockService.findById(blockId))
                    && Objects.nonNull(repository.findById(societyId))) {
                street.setSocietyId(societyId);
                street.setBlockId(blockId);
                return streetService.create(street);
            }
        } else {
            if(Objects.nonNull(repository.findById(societyId))) {
                street.setSocietyId(societyId);
                street.setBlockId(blockId);
                return streetService.create(street);
            }
        }
        throw new InvalidDataException("You have provided, invalid street or block", HttpStatus.BAD_REQUEST.value());
    }

    public Block findBlockBySocietyIdAndBlockId(String blockId, String societyId) {
        return blockService.findBySocietyIdAndBlockId(blockId, societyId);
    }

    public List<Block> findBlockBySocietyId(String societyId) {
        return blockService.findBySocietyId(societyId);
    }

    public List<Street> findStreetBySocietyId(String societyId) {
        return streetService.findBySocietyId(societyId);
    }

    public List<Street> findStreetBySocietyIdAndBlockId(String societyId, String blockId) {
        return streetService.findBySocietyIdAndBlockId(societyId, blockId);
    }
}
