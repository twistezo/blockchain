package com.blockchain;

import java.security.*;
import java.util.ArrayList;

class Transaction {
    String transactionId;
    PublicKey sender;
    PublicKey recipient;
    float value;
    ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    private float minimumTransactionValue = 0.1f;
    private int transactionCounter = 0;
    private byte[] signature;

    Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    boolean processTransaction() {
        Utils.log("Processing transaction..");
        if (verifiySignature() == false) {
            return false;
        }

        // Input is always an output of previously generated transaction
        for (TransactionInput input : inputs) {
            input.UTXO = Blockchain.UTXOs.get(input.transactionOutputId);
        }
        if (getInputsValue() < minimumTransactionValue) {
            return false;
        }

        // Substract current transaction value from all inputs 
        float leftOver = getInputsValue() - value;
        transactionId = calulateHash();

        // Generate outputs with properly sender and recipient values
        outputs.add(new TransactionOutput(this.recipient, value, transactionId));
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));

        // Add transaction outputs to Blockchain UTXOs
        for (TransactionOutput o : outputs) {
            Blockchain.UTXOs.put(o.id, o);
        }

        // Remove finished transactions from UTXOs
        for (TransactionInput i : inputs) {
            if (i.UTXO == null)
                continue;
            Blockchain.UTXOs.remove(i.UTXO.id);
        }
        return true;
    }

    /**
    * Encode String: "sender + recipient + value" and encode this with privateKey by ECDSA 
    */
    void generateSignature(PrivateKey privateKey) {
        String data = CryptoUtils.encodeWithBase64(sender) + CryptoUtils.encodeWithBase64(recipient)
                + Float.toString(value);
        signature = CryptoUtils.applyECDSASig(privateKey, data);
    }

    /**
     * String: "sender + recipient + value" verified by ECDSA and transaction signature
     */
    boolean verifiySignature() {
        String data = CryptoUtils.encodeWithBase64(sender) + CryptoUtils.encodeWithBase64(recipient)
                + Float.toString(value);
        return CryptoUtils.verifyECDSASig(sender, data, signature);
    }

    float getInputsValue() {
        float total = 0;
        for (TransactionInput i : inputs) {
            if (i.UTXO == null)
                continue;
            total += i.UTXO.value;
        }
        return total;
    }

    float getOutputsValue() {
        float total = 0;
        for (TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

    // String: "sender + recipient + value + transactionCounter" encoded by SHA256
    private String calulateHash() {
        transactionCounter++;
        return CryptoUtils.applySha256(CryptoUtils.encodeWithBase64(sender) + CryptoUtils.encodeWithBase64(recipient)
                + Float.toString(value) + transactionCounter);
    }
}