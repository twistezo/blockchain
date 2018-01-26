package com.blockchain;

import java.util.Date;
import java.util.ArrayList;

class Block {
    String hash;
    String previousHash;
    String merkleRoot;
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    long timeStamp;
    int nonce;

    Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    String calculateHash() {
        String calculatedhash = StringUtils
                .applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
        return calculatedhash;
    }

    boolean addTransaction(Transaction transaction) {
        System.out.println(" Adding transaction to block...");
        if (transaction == null)
            return false;
        if (previousHash != "0") {
            if (transaction.processTransaction() != true) {
                System.out.println(" Warning: Transaction discarded");
                return false;
            }
        }
        System.out.println(" Adding transaction to block... DONE");
        transactions.add(transaction);
        return true;
    }

    void mineBlock(int difficulty) {
        System.out.println(" Mining block: " + hash);
        merkleRoot = StringUtils.getMerkleRoot(transactions);
        String target = StringUtils.getDificultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println(" Mining block... DONE");
    }
}