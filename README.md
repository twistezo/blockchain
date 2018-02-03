## my-blockchain

### Description
My implementation of blockchain in Java

#### How it works?
1. Create 3 wallets: Genesis Wallet (as `GW`), Wallet A (as `A`) and Wallet B (as `B`)<br/>
We have to hardcode first transaction in `GW`<br/>
Create transaction (without block) and we have e.g. 100 unit on `GW`.<br/>
Current transactions data counter: `GW transaction: inputs = 0 , outputs = 1` -> `UTXOs = 1`
2. Send 100 unit from `GW` to `A`<br/>
Create new transaction (as `T`) and add to new block <br/>
Add 1 output transaction from UTXOs to input transactions of current `T`<br/>
Current transaction data counter: `T: inputs = 1, outputs = 0`<br/>
Add two `T` outputs -> `T(recipient, value, T.id)` and `T(sender, value, T.id)`<br/>
Current transaction data counter: `T: inputs = 1, outputs = 2`<br/>
Add these two outputs to UTXOs -> `UTXOs = 3`<br/>
Remove current `T` input from UTXOs -> `UTXOs = 2`

3. As far as we sending units (e.g. money) and creating transactions for every time we added a new block to blockchain and next transaction to UTXOs. Every next block in blockchain knows about previous.

#### What is UTXO in blockchain?
>A UTXO is an unspent transaction output. In an accepted transaction in a valid blockchain 
payment system (such as Bitcoin), only unspent outputs can be used as inputs to a transaction. 
When a transaction takes place, inputs are deleted and outputs are created as new UTXOs 
that may then be consumed in future transactions.

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
Creating genesis wallet and first transaction..
Mining block: 9343a963b3a65e687153396b1939d81774acc1523e0799afa54a06c990cc30f5

Test #1 Sending '40' from Wallet A to Wallet B
-----------------------------------------------
Sender wallet balance = 100.0
Recipient wallet balance = 0.0
Processing transaction..
Mining block: 531295eba463504fccf829f7347f3306a594644b04919bc4547efc7d83f392a9
Sender wallet balance = 60.0
Recipient wallet balance = 40.0

Validating blockchain..

Test #2 Sending '1000' from Wallet A to Wallet B
-----------------------------------------------
Sender wallet balance = 60.0
Recipient wallet balance = 40.0
Error: Not enough funds
Mining block: f867b858da73c160c039f5a31d0aaeda4da14c911848475b60c12c9bbee681dc
Sender wallet balance = 60.0
Recipient wallet balance = 40.0

Validating blockchain..

Test #3 Sending '20' from Wallet B to Wallet A
-----------------------------------------------
Sender wallet balance = 40.0
Recipient wallet balance = 60.0
Processing transaction..
Mining block: adcefad56af0ed6a810fde329c5be54b4b766b86e4813e57bd6eedfe1c641208
Sender wallet balance = 20.0
Recipient wallet balance = 80.0

Validating blockchain..

Test #4 Sending '30' from Wallet A to Wallet C
-----------------------------------------------
Sender wallet balance = 80.0
Recipient wallet balance = 0.0
Processing transaction..
Mining block: a9ae88c08579408bb1ec09bfd4a0fb5a7f938c9f524e1d0a9d7af9e581055682
Sender wallet balance = 50.0
Recipient wallet balance = 30.0

Validating blockchain..
```


