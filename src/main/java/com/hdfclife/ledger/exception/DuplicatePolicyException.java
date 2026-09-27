package com.hdfclife.ledger.exception;
public class DuplicatePolicyException extends DeskException { public DuplicatePolicyException(String value) { super("Policy already exists: " + value); } }
