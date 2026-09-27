package com.hdfclife.ledger.service;

import com.hdfclife.ledger.domain.*; import com.hdfclife.ledger.dto.*; import com.hdfclife.ledger.exception.*; import com.hdfclife.ledger.repo.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service
public class ClaimService {
    private final ClaimRepository claims; private final PolicyService policyService;
    public ClaimService(ClaimRepository claims, PolicyService policyService) { this.claims = claims; this.policyService = policyService; }
    @Transactional public ClaimResponse create(CreateClaimRequest request) { Policy policy = policyService.find(request.policyNo()); String no = String.format("CLM-%02d", claims.count() + 1); return toResponse(claims.save(new Claim(no, policy, request.claimAmount(), request.urgency(), "SUBMITTED"))); }
    @Transactional(readOnly = true) public List<ClaimResponse> byPolicy(String policyNo) { policyService.find(policyNo); return claims.findByPolicy_PolicyNoOrderByClaimNoAsc(policyNo).stream().map(this::toResponse).toList(); }
    private ClaimResponse toResponse(Claim claim) { return new ClaimResponse(claim.getClaimNo(), claim.getPolicy().getPolicyNo(), claim.getAmount(), claim.getUrgency(), claim.getStatus()); }
}
