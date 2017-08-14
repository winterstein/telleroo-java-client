# telleroo-java-client
A Java client for the Telleroo API banking system

Example usage:
```
Telleroo telleroo = new Telleroo("my-auth-token");
TAccount myAccount = telleroo.getAccounts().get(0);
system.out.println("Balance: £"+myAccount.getBalance()/100.0);
```   
  
