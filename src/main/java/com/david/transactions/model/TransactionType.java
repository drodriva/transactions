package com.david.transactions.model;

public enum TransactionType {
	
	DEPOSIT(1), 
	WITHDRAWAL(2);

	private int code = 0;

	private TransactionType(int code) {

		this.code = code;
	}
	
    public int code() {
        return code;
    }

}
