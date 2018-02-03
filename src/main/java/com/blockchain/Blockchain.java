package com.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Blockchain {
	static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();
	private ArrayList<Block> blockchain = new ArrayList<Block>();
	private int difficulty = 3;
	private Transaction genesisTransaction;

	public static void main(String[] args) {
		Blockchain blockchainRunner = new Blockchain();
		blockchainRunner.run();
	}

	void run() {
		// Add a cryptographic API for Java e.g. ECDSA	
		Security.addProvider(new BouncyCastleProvider());
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		// Hardcode first wallet and first transaction
		println("Creating genesis wallet and first transaction...");
		Wallet genesisWallet = new Wallet();
		genesisTransaction = new Transaction(genesisWallet.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(genesisWallet.privateKey);
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value,
				genesisTransaction.transactionId));
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		println("Creating genesis wallet and first transaction... DONE");

		println("\nCreating and Mining Genesis block...");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);
		println("Creating and Mining Genesis block... DONE");

		println("\nTest #1...");
		Block block1 = new Block(genesis.hash);
		printBalance(walletA, "A");
		printBalance(walletB, "B");
		println("Sending '40' from WalletA to WalletB");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);
		printBalance(walletA, "A");
		printBalance(walletB, "B");

		println("\nTest #2...");
		Block block2 = new Block(block1.hash);
		printBalance(walletA, "A");
		printBalance(walletB, "B");
		println("Sending '1000' from WalletA to WalletB");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		addBlock(block2);
		printBalance(walletA, "A");
		printBalance(walletB, "B");

		println("\nTest #3...");
		Block block3 = new Block(block2.hash);
		printBalance(walletA, "A");
		printBalance(walletB, "B");
		println("Sending '20' from WalletB to WalletA");
		block3.addTransaction(walletB.sendFunds(walletA.publicKey, 20));
		addBlock(block3);
		printBalance(walletA, "A");
		printBalance(walletB, "B");

		ChainValidator.check(blockchain, genesisTransaction, difficulty);
	}

	private void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

	private void printBalance(Wallet wallet, String wallet_name) {
		println("Wallet " + wallet_name + " balance is: " + wallet.getBalance());
	}

	private void println(Object line) {
		System.out.println(line);
	}
}