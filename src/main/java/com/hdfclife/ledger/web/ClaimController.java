package com.hdfclife.ledger.web;
import com.hdfclife.ledger.dto.*; import com.hdfclife.ledger.service.ClaimService; import jakarta.validation.Valid; import io.swagger.v3.oas.annotations.*; import io.swagger.v3.oas.annotations.tags.Tag; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.net.URI;
@RestController @RequestMapping("/api/claims") @Tag(name = "Claims")
public class ClaimController {
    private final ClaimService service; public ClaimController(ClaimService service) { this.service = service; }
    @PostMapping @Operation(summary = "Create a claim") public ResponseEntity<ClaimResponse> create(@Valid @RequestBody CreateClaimRequest request) { ClaimResponse response = service.create(request); return ResponseEntity.created(URI.create("/api/claims/" + response.claimNo())).body(response); }
}
