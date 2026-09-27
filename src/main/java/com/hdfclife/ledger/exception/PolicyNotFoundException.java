package com.hdfclife.ledger.exception;
public class PolicyNotFoundException extends DeskException { public PolicyNotFoundException(String value) { super("Policy not found: " + value); } }
