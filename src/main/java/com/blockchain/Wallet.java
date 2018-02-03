package com.blockchain;

import java.security.spec.ECGenParameterSpec;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Wallet {
    PrivateKey privateKey;
    PublicKey publicKey;
    private HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    Wallet() {
        generatePrivateAndPublicKeys();
    }

    private void generatePrivateAndPublicKeys() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException("Wallet: generateKeyPair failed caused by: " + e);
        }
    }

    float getBalance() {
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            if (UTXO.isMine(publicKey)) {
                UTXOs.put(UTXO.id, UTXO);
                total += UTXO.value;
            }
        }
        return total;
    }

    Transaction sendFunds(PublicKey recipient, float value) {
        if (getBalance() < value) {
            System.out.println(" Error: Not enough funds");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
        float total = 0;
        for (Map.Entry<String, TransactionOutput> item : UTXOs.entrySet()) {
            TransactionOutput UTXO = item.getValue();
            total += UTXO.value;
            inputs.add(new TransactionInput(UTXO.id));
            if (total > value)
                break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient, value, inputs);
        newTransaction.generateSignature(privateKey);
        for (TransactionInput input : inputs) {
            UTXOs.remove(input.transactionOutputId);
        }
        return newTransaction;
    }
}
