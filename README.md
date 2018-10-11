# virtualbank

## Assumptions
- The user has already logged in and authenticated
- Wallet endpoints such as show all and delete are not required for security reasons (we wouldn't want a user to see all other users wallet id's or be able to delete them)
- Similarly, transaction endpoints such as show all, delete, and update are not provided
- The user and wallet are associated by a one-to-one relationship, and the user id is a globally unique id
- Reversal transactions should be saved as "new" transactions, with the amount being the negated of the original

Note: Please see the considerations below for improvements and considerations

## Endpoints

### Wallets

#### GET /wallets/{id}
- returns the wallet with the provided id or an exception if it does not exist
- sample usage: curl -v localhost:8080/wallets/ff808081666128ab0166612a199b0002

#### POST /wallets
- creates a new wallet and returns it
- sample usage:  curl -X POST localhost:8080/wallets

#### GET /wallets/{id}/balance
- returns the balance of the wallet matching the given id or an exception if the wallet does not exist
- sample usage:  curl -X GET localhost:8080/wallets/ff808081666128ab0166612a199b0002/balance

#### PUT /wallets/{id}/deposit
- deposits an amount into the wallet with the given id or an exception if a) the wallet does not exist or b) the amount is negative
- sample usage:  curl -X PUT localhost:8080/wallets/ff808081666128ab0166612a199b0002/deposit?amount=20

#### PUT /wallets/{id}/withdraw
- withdraws an amount from the wallet with the given id or an exception if a) the wallet does not exist or b) the amount is negative
- sample usage:  curl -X PUT localhost:8080/wallets/ff808081666128ab0166612a199b0002/withdraw?amount=20

#### GET /wallets/{id}/transactions
- returns the last N transactions on the wallet matching the given id or an exception if a) the wallet does not exist b) N is less than or equal to zero
- sample usage:  curl -X GET localhost:8080/wallets/ff808081666128ab0166612a199b0002/transactions?limit=10

### Transactions

#### GET /transactions/{id}
- returns the transaction with the provided id or an exception if it does not exist
- sample usage: curl -v localhost:8080/transactions/ff808081666128ab0166612a199b0003

#### POST /transactions/{id}/reverse
- creates a reversal transaction, negating the amount on the transaction with the provided id or throws an exception if the transaction a) does not exist or b) has already been reversed
- sample usage:  curl -X POST localhost:8080/transactions/ff808081666128ab0166612a199b0003/reverse

## Considerations and Improvements
- Add testing for thread-safety and add instance level locks on the wallet class to ensure no more than one thread can make changes to the account 
- Use Springs @Transactional notation to ensure transactions satisfy ACID properties in the event of an application failure or crash
- Add testing at the web layer
- Add support for making transfers between accounts (this was not originally part of the systems requirements doc)
