package com.sfd.usermanagement.society.street;

import com.sfd.usermanagement.common.InvalidDataException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

/**
 * @author kuldeep
 */
public record StreetService(StreetRepository repository) {
    public Street create(Street street) {
        checkIfStreetAlreadyExists(street);
        return repository.create(street);
    }

    private void checkIfStreetAlreadyExists(Street street) {
        if(Objects.nonNull(repository.findBySocietyIdAndBlockIdAndStreetNum(street.getSocietyId(),
                street.getBlockId(), street.getStreetNum()))) {
            throw new InvalidDataException("Street is already exists in given block/society", HttpStatus.IM_USED.value());
        }
    }

    public List<Street> findBySocietyId(String societyId) {
        return repository.findBySocietyId(societyId);
    }

    public List<Street> findBySocietyIdAndBlockId(String societyId, String blockId) {
        return repository.findBySocietyIdAndBlockId(societyId, blockId);
    }
}
