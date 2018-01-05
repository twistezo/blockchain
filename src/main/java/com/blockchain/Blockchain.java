package com.blockchain;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

class Blockchain {

	public static ArrayList<Block> blockChain = new ArrayList<Block>();
	public static int difficulty = 5; // for now Litecoin's difficulty is around 440

	public static void main(String[] args) {
		System.out.println("*** My Blockchain ***");

		blockChain.add(new Block("I am first block", "0"));
		System.out.println("\nTrying to mine block 1...");
		blockChain.get(0).mineBlock(difficulty);

		blockChain.add(new Block("I am second block", blockChain.get(blockChain.size() - 1).hash));
		System.out.println("\nTrying to mine block 2...");
		blockChain.get(1).mineBlock(difficulty);

		blockChain.add(new Block("I am third block", blockChain.get(blockChain.size() - 1).hash));
		System.out.println("\nTrying to mine block 3...");
		blockChain.get(2).mineBlock(difficulty);

		System.out.println("\nBlockchain is valid: " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
		System.out.println("\nBlockchain: ");
		System.out.println(blockchainJson);
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		for (int i = 1; i < blockChain.size(); i++) {
			currentBlock = blockChain.get(i);
			previousBlock = blockChain.get(i - 1);
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Warning: Current block hash and calculated hash for this block are not equal.");
				return false;
			}
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Warning: Current block previous hash and previous block hash are not equal.");
				return false;
			}
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("Warning: This block has not been mined.");
			}
		}
		return true;
	}
}
