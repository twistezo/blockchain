## my-blockchain

### Description
My blockchain implementation in Java

### Tools
Java

### Requirements
maven

### Features
* blocks
* wallets
* transactions
* mining system
* digital signatures

### Build/Run
```
mvn clean compile assembly:single exec:java
```

### Actual version
```
*** My Blockchain ***
Creating genesis wallet and first transaction...
Creating genesis wallet and first transaction... DONE

Creating and Mining Genesis block...
 Adding transaction to block...
 Adding transaction to block... DONE
 Mining block: aac9277705d37faf43e35d8dec7ff7aba6756e988ec4eb8fed9739fc5a84ad4d
 Mining block... DONE
Creating and Mining Genesis block... DONE

Test #1...
Wallet A balance is: 100.0
Wallet B balance is: 0.0
Sending '40' from WalletA to WalletB
 Adding transaction to block...
 Processing transaction...
 Processing transaction... DONE
 Adding transaction to block... DONE
 Mining block: ea230fae881499318071c232818b5908725cfbbcfc9c29a6a377ac670ecfea07
 Mining block... DONE
Wallet A balance is: 60.0
Wallet B balance is: 40.0

Test #2...
Wallet A balance is: 60.0
Wallet B balance is: 40.0
Sending '1000' from WalletA to WalletB
 Error: Not enough funds
 Adding transaction to block...
 Mining block: ba33660efde135f1e2544c8ec86882674ded718abb58a8f81d9d23c2a3b8c712
 Mining block... DONE
Wallet A balance is: 60.0
Wallet B balance is: 40.0

Test #3...
Wallet A balance is: 60.0
Wallet B balance is: 40.0
Sending '20' from WalletB to WalletA
 Adding transaction to block...
 Processing transaction...
 Processing transaction... DONE
 Adding transaction to block... DONE
 Mining block: 95978333a7b46a9b5e3509de09108a1f8fd74645b60b3864e4f3706334ad1787
 Mining block... DONE
Wallet A balance is: 80.0
Wallet B balance is: 20.0

Validate blockchain...
Validate blockchain... DONE
```


