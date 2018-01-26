package com.blockchain;

class TransactionInput {
    String transactionOutputId;
    TransactionOutput UTXO;

    TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}