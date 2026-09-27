package com.hdfclife.ledger.service;

import com.hdfclife.ledger.domain.*;
import com.hdfclife.ledger.dto.*;
import com.hdfclife.ledger.exception.*;
import com.hdfclife.ledger.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PolicyService {
    private final PolicyRepository policies; private final CustomerRepository customers; private final RiderRepository riders;
    public PolicyService(PolicyRepository policies, CustomerRepository customers, RiderRepository riders) { this.policies = policies; this.customers = customers; this.riders = riders; }
    @Transactional(readOnly = true) public List<PolicyResponse> all() { return policies.findAll(org.springframework.data.domain.Sort.by("policyNo")).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public PolicyResponse get(String no) { return toResponse(find(no)); }
    @Transactional(readOnly = true) public List<PolicyResponse> byStatus(String value) { return policies.findByStatusOrderByPolicyNoAsc(value).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public List<PolicyResponse> byType(String value) { return policies.findByProductTypeOrderByPolicyNoAsc(value).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public List<PolicyResponse> byCustomer(String value) { return policies.findByCustomer_FullNameOrderByPolicyNoAsc(value).stream().map(this::toResponse).toList(); }
    @Transactional(readOnly = true) public List<PolicyResponse> byPremium(int value) { return policies.findWithPremiumAtLeast(value).stream().map(this::toResponse).toList(); }
    @Transactional public PolicyResponse create(CreatePolicyRequest request) {
        if (policies.existsByPolicyNo(request.policyNo())) throw new DuplicatePolicyException(request.policyNo());
        Customer customer = customers.findByEmail(request.email()).orElseGet(() -> customers.save(new Customer(request.customer(), request.email())));
        return toResponse(policies.save(new Policy(request.policyNo(), customer, request.type(), request.basePremium(), request.status())));
    }
    @Transactional public void delete(String no) { policies.delete(find(no)); }
    public Policy find(String no) { return policies.findByPolicyNo(no).orElseThrow(() -> new PolicyNotFoundException(no)); }
    public PolicyResponse toResponse(Policy policy) { return new PolicyResponse(policy.getPolicyNo(), policy.getCustomer().getFullName(), policy.getCustomer().getEmail(), policy.getProductType(), policy.getBasePremium(), policy.getStatus(), policy.getRiders().stream().map(Rider::getCode).sorted().collect(Collectors.toList())); }
}
