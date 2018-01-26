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
    private float minimumTransaction = 0.1f;
    private int transactionCounter = 0;
    private byte[] signature;

    Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    void generateSignature(PrivateKey privateKey) {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(recipient)
                + Float.toString(value);
        signature = StringUtils.applyECDSASig(privateKey, data);
    }

    boolean processTransaction() {
        System.out.println(" Processing transaction...");
        if (verifiySignature() == false) {
            return false;
        }
        for (TransactionInput i : inputs) {
            i.UTXO = Blockchain.UTXOs.get(i.transactionOutputId);
        }
        if (getInputsValue() < minimumTransaction) {
            return false;
        }

        float leftOver = getInputsValue() - value;
        transactionId = calulateHash();
        outputs.add(new TransactionOutput(this.recipient, value, transactionId));
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));

        for (TransactionOutput o : outputs) {
            Blockchain.UTXOs.put(o.id, o);
        }
        for (TransactionInput i : inputs) {
            if (i.UTXO == null)
                continue;
            Blockchain.UTXOs.remove(i.UTXO.id);
        }
        System.out.println(" Processing transaction... DONE");
        return true;
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

    boolean verifiySignature() {
        String data = StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(recipient)
                + Float.toString(value);
        return StringUtils.verifyECDSASig(sender, data, signature);
    }

    float getOutputsValue() {
        float total = 0;
        for (TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

    private String calulateHash() {
        transactionCounter++;
        return StringUtils.applySha256(StringUtils.getStringFromKey(sender) + StringUtils.getStringFromKey(recipient)
                + Float.toString(value) + transactionCounter);
    }
}