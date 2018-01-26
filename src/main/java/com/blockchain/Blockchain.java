package com.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/** 
 * What is UTX in blockchain?
 * 
 * A UTXO is an unspent transaction output. In an accepted transaction in a valid blockchain 
 * payment system (such as Bitcoin), only unspent outputs can be used as inputs to a transaction. 
 * When a transaction takes place, inputs are deleted and outputs are created as new UTXOs 
 * that may then be consumed in future transactions.
 */

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
		Security.addProvider(new BouncyCastleProvider());
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		// hardcoded first wallet and transaction
		System.out.println("Creating genesis wallet and first transaction...");
		Wallet genesisWallet = new Wallet();
		genesisTransaction = new Transaction(genesisWallet.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(genesisWallet.privateKey);
		genesisTransaction.transactionId = "0";
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value,
				genesisTransaction.transactionId));
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		System.out.println("Creating genesis wallet and first transaction... DONE");

		System.out.println("\nCreating and Mining Genesis block...");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);
		System.out.println("Creating and Mining Genesis block... DONE");

		System.out.println("\nTest #1...");
		Block block1 = new Block(genesis.hash);
		printBalance(walletA, "A");
		printBalance(walletB, "B");
		System.out.println("Sending '40' from WalletA to WalletB");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);
		printBalance(walletA, "A");
		printBalance(walletB, "B");

		System.out.println("\nTest #2...");
		Block block2 = new Block(block1.hash);
		printBalance(walletA, "A");
		printBalance(walletB, "B");
		System.out.println("Sending '1000' from WalletA to WalletB");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		addBlock(block2);
		printBalance(walletA, "A");
		printBalance(walletB, "B");

		System.out.println("\nTest #3...");
		Block block3 = new Block(block2.hash);
		printBalance(walletA, "A");
		printBalance(walletB, "B");
		System.out.println("Sending '20' from WalletB to WalletA");
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
		System.out.println("Wallet " + wallet_name + " balance is: " + wallet.getBalance());
	}
}