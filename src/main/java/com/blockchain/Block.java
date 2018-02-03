package com.blockchain;

import java.util.Date;
import java.util.ArrayList;

class Block {
    String hash;
    String previousHash;
    String merkleRoot;
    ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    long timeStamp;
    int nonce; // miners starting iterate point

    Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    /**
     * String: "previousHash + timeStamp + nonce + merkeRoot" encoded by SHA256
     */
    String calculateHash() {
        String calculatedhash = CryptoUtils
                .applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
        return calculatedhash;
    }

    boolean addTransaction(Transaction transaction) {
        if (transaction == null)
            return false;
        if (previousHash != "0") {
            if (transaction.processTransaction() != true) {
                Utils.log("Warning: Transaction discarded");
                return false;
            }
        }
        transactions.add(transaction);
        return true;
    }

    void mineBlock(int difficulty) {
        Utils.log("Mining block: " + hash);
        merkleRoot = CryptoUtils.getMerkleRoot(transactions);
        String target = CryptoUtils.getDificultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
    }
}