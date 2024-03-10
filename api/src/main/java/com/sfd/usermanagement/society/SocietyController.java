package com.sfd.usermanagement.society;

import com.sfd.usermanagement.society.block.Block;
import com.sfd.usermanagement.society.street.Street;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kuldeep
 */

@RestController
@RequestMapping("society")
public record SocietyController(SocietyService service) {
    @PostMapping
    public ResponseEntity<Society> create(@RequestBody Society society) {
        return ResponseEntity.ok(service.create(society));
    }

    @GetMapping("{societyId}")
    public ResponseEntity<Society> getById(@PathVariable("societyId") String societyId) {
        return ResponseEntity.ok(service.findById(societyId));
    }

    @PostMapping("{societyId}/block")
    public ResponseEntity<Block> addBlock(@RequestBody Block block, @PathVariable("societyId") String societyId) {
        return ResponseEntity.ok(service.addBlock(block, societyId));
    }

    @GetMapping("{societyId}/block/{blockId}")
    public ResponseEntity<Block> findBlockById(@PathVariable("societyId") String societyId,
                                               @PathVariable("blockId") String blockId) {
        return ResponseEntity.ok(service.findBlockBySocietyIdAndBlockId(blockId, societyId));
    }

    @GetMapping("{societyId}/block/")
    public ResponseEntity<List<Block>> findBlockByIdSocietyId(@PathVariable("societyId") String societyId) {
        return ResponseEntity.ok(service.findBlockBySocietyId(societyId));
    }

    @PostMapping("{societyId}/street")
    public ResponseEntity<Street> addStreet(@RequestBody Street street,
                                            @PathVariable("societyId") String societyId,
                                            @RequestParam(value = "blockId", required = false) String blockId) {
        return ResponseEntity.ok(service.addStreet(street, societyId, blockId));
    }

    @GetMapping("{societyId}/street")
    public ResponseEntity<List<Street>> findStreetBySocietyId(@PathVariable("societyId") String societyId) {
        return ResponseEntity.ok(service.findStreetBySocietyId(societyId));
    }

    @GetMapping("{societyId}/street/block/{blockId}")
    public ResponseEntity<List<Street>> findStreetBySocietyIdAndBlockId(@PathVariable("societyId") String societyId,
                                                                        @PathVariable("blockId") String blockId) {
        return ResponseEntity.ok(service.findStreetBySocietyIdAndBlockId(societyId, blockId));
    }
}
