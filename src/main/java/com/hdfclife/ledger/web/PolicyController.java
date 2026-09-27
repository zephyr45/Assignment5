package com.hdfclife.ledger.web;

import com.hdfclife.ledger.dto.*; import com.hdfclife.ledger.service.*; import jakarta.validation.Valid; import io.swagger.v3.oas.annotations.*; import io.swagger.v3.oas.annotations.responses.ApiResponse; import io.swagger.v3.oas.annotations.tags.Tag; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.net.URI; import java.util.List;
@RestController @RequestMapping("/api/policies") @Tag(name = "Policies")
public class PolicyController {
    private final PolicyService service; private final ClaimService claims;
    public PolicyController(PolicyService service, ClaimService claims) { this.service = service; this.claims = claims; }
    @GetMapping @Operation(summary = "List policies") @ApiResponse(responseCode = "200") public List<PolicyResponse> list(@RequestParam(required = false) String status, @RequestParam(required = false) String type, @RequestParam(required = false) String customer) { if (status != null) return service.byStatus(status); if (type != null) return service.byType(type); if (customer != null) return service.byCustomer(customer); return service.all(); }
    @GetMapping("/search") @Operation(summary = "Search by minimum premium") public List<PolicyResponse> search(@RequestParam(required = false) Integer minPremium) { if (minPremium == null || minPremium < 0) throw new com.hdfclife.ledger.exception.InvalidRequestException("minPremium must be present and non-negative"); return service.byPremium(minPremium); }
    @GetMapping("/{policyNo}") @Operation(summary = "Get a policy") public PolicyResponse get(@PathVariable String policyNo) { return service.get(policyNo); }
    @GetMapping("/{policyNo}/claims") public List<ClaimResponse> claims(@PathVariable String policyNo) { return claims.byPolicy(policyNo); }
    @PostMapping @Operation(summary = "Create a policy") public ResponseEntity<PolicyResponse> create(@Valid @RequestBody CreatePolicyRequest request) { PolicyResponse response = service.create(request); return ResponseEntity.created(URI.create("/api/policies/" + response.policyNo())).body(response); }
    @DeleteMapping("/{policyNo}") public ResponseEntity<Void> delete(@PathVariable String policyNo) { service.delete(policyNo); return ResponseEntity.noContent().build(); }
}
