package com.hdfclife.ledger.exception;
public class ClaimNotFoundException extends DeskException { public ClaimNotFoundException(String value) { super("Claim not found: " + value); } }
