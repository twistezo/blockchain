package com.blockchain;

public class Main {

    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Wallet walletA = blockchain.createWallet();
        Wallet walletB = blockchain.createWallet();
        Wallet walletC = blockchain.createWallet();

        Utils.log("\nCreating genesis wallet and first transaction..");
        blockchain.prepareGenesisTransaction(walletA);
        blockchain.setMiningDifficulty(4);

        Utils.log("\nTest #1 Sending '40' from Wallet A to Wallet B");
        Utils.logDottedLine();
        blockchain.makeTransfer(walletA, walletB, 40f, blockchain);

        Utils.log("\nTest #2 Sending '1000' from Wallet A to Wallet B");
        Utils.logDottedLine();
        blockchain.makeTransfer(walletA, walletB, 1000f, blockchain);

        Utils.log("\nTest #3 Sending '20' from Wallet B to Wallet A");
        Utils.logDottedLine();
        blockchain.makeTransfer(walletB, walletA, 20f, blockchain);

        Utils.log("\nTest #4 Sending '30' from Wallet A to Wallet C");
        Utils.logDottedLine();
        blockchain.makeTransfer(walletA, walletC, 30f, blockchain);
    }
}