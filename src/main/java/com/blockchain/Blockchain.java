package com.blockchain;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Blockchain {
	static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private int difficulty = 1;
	private Transaction genesisTransaction;

	Blockchain() {
		Security.addProvider(new BouncyCastleProvider()); // Cryptographic API for Java e.g. ECDSA	
	}

	/**
	 * Hardcode first wallet, first transaction and first block
	 */
	void prepareGenesisTransaction(Wallet secondWallet, Blockchain blockchain) {
		Wallet genesisWallet = new Wallet();
		genesisTransaction = new Transaction(genesisWallet.publicKey, secondWallet.publicKey, 100f, null);
		genesisTransaction.generateSignature(genesisWallet.privateKey);
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value,
				genesisTransaction.transactionId));
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));

		Block genesisBlock = new Block("0");
		genesisBlock.addTransaction(genesisTransaction);
		genesisBlock.mineBlock(difficulty);
		blocks.add(genesisBlock);
		validateBlockchain(blockchain);
	}

	void makeTransfer(Wallet sender, Wallet recipient, float value, Blockchain blockchain) {
		Block block = new Block(blockchain.getHashFromLastBlock());
		printBalance(sender, "Sender");
		printBalance(recipient, "Recipient");
		block.addTransaction(sender.sendFunds(recipient.publicKey, value));
		block.mineBlock(difficulty);
		blocks.add(block);
		printBalance(sender, "Sender");
		printBalance(recipient, "Recipient");
		validateBlockchain(blockchain);
	}

	private void validateBlockchain(Blockchain blockchain) {
		System.out.print("\nValidating blockchain..");
		if (ChainValidator.check(blockchain.blocks, genesisTransaction, difficulty)) {
			System.out.print(" OK\n");
		}
	}

	Wallet createWallet() {
		return new Wallet();
	}

	void setMiningDifficulty(int value) {
		this.difficulty = value;
	}

	ArrayList<Block> getBlocks() {
		return blocks;
	}

	private void printBalance(Wallet wallet, String wallet_name) {
		Utils.log(wallet_name + " wallet balance = " + wallet.getBalance());
	}

	private String getHashFromLastBlock() {
		return blocks.get(blocks.size() - 1).hash;
	}
}