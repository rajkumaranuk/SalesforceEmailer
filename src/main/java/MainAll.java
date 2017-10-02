import com.sforce.soap.enterprise.*;
import com.sforce.soap.enterprise.Error;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class MainAll
{

    static final String USERNAME = "raj.kumaranuk@gmail.com";
    static final String PASSWORD = "London2015BE0wxfqcCb52QTLm1jnBTU3G";
    static EnterpriseConnection connection;

    public static void main(String[] args) {

        ConnectorConfig config = new ConnectorConfig();
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        //config.setTraceMessage(true);

        try {

            connection = Connector.newConnection(config);

            // display some current settings
            System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
            System.out.println("Service EndPoint: "+config.getServiceEndpoint());
            System.out.println("Username: "+config.getUsername());
            System.out.println("SessionId: "+config.getSessionId());

            // run the different examples
            //            queryContacts();
            //            createAccounts();
            //            updateAccounts();
            //            deleteAccounts();
            doSendEmail();


        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }

    }

    // queries and displays the 5 newest contacts
    private static void queryContacts() {

        System.out.println("Querying for the 5 newest Contacts...");

        try {

            // query for the 5 newest contacts
            QueryResult queryResults = connection.query("SELECT Id, FirstName, LastName, Account.Name " +
                    "FROM Contact WHERE AccountId != NULL ORDER BY CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0) {
                for (int i=0;i<queryResults.getRecords().length;i++) {
                    // cast the SObject to a strongly-typed Contact
                    Contact c = (Contact)queryResults.getRecords()[i];
                    System.out.println("Id: " + c.getId() + " - Name: "+c.getFirstName()+" "+
                            c.getLastName()+" - Account: "+c.getAccount().getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // create 5 test Accounts
    private static void createAccounts() {

        System.out.println("Creating 5 new test Accounts...");
        Account[] records = new Account[5];

        try {

            // create 5 test accounts
            for (int i=0;i<5;i++) {
                Account a = new Account();
                a.setName("My Test Account "+i);
                records[i] = a;
            }

            // create the records in Salesforce.com
            SaveResult[] saveResults = connection.create(records);

            // check the returned results for any errors
            for (int i=0; i< saveResults.length; i++) {
                if (saveResults[i].isSuccess()) {
                    System.out.println(i+". Successfully created record - Id: " + saveResults[i].getId());
                } else {
                    Error[] errors = saveResults[i].getErrors();
                    for (int j=0; j< errors.length; j++) {
                        System.out.println("ERROR creating record: " + errors[j].getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // updates the 5 newly created Accounts
    private static void updateAccounts() {

        System.out.println("Update the 5 new test Accounts...");
        Account[] records = new Account[5];

        try {

            QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
                    "CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0) {
                for (int i=0;i<queryResults.getRecords().length;i++) {
                    // cast the SObject to a strongly-typed Account
                    Account a = (Account)queryResults.getRecords()[i];
                    System.out.println("Updating Id: " + a.getId() + " - Name: "+a.getName());
                    // modify the name of the Account
                    a.setName(a.getName()+" -- UPDATED");
                    records[i] = a;
                }
            }

            // update the records in Salesforce.com
            SaveResult[] saveResults = connection.update(records);

            // check the returned results for any errors
            for (int i=0; i< saveResults.length; i++) {
                if (saveResults[i].isSuccess()) {
                    System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
                } else {
                    Error[] errors = saveResults[i].getErrors();
                    for (int j=0; j< errors.length; j++) {
                        System.out.println("ERROR updating record: " + errors[j].getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void doSendEmail() {
        try {
            //            EmailFileAttachment efa = new EmailFileAttachment();
            //            byte[] fileBody = new byte[1000000];
            //            efa.setBody(fileBody);
            //            efa.setFileName("attachment");
            MassEmailMessage message = new MassEmailMessage();
            //            message.setBccAddresses(new String[] {
            //                    "someone@salesforce.com"
            //            });
            //            message.setCcAddresses(new String[] {
            //                    "person1@salesforce.com", "person2@salesforce.com"
            //            });
            message.setBccSender(true);
            message.setEmailPriority(EmailPriority.High);
            message.setReplyTo("person1@salesforce.com");
            message.setSaveAsActivity(false);
            //message.setSubject("Tem Hi Raj This is how you use the " + "sendEmail method.");

            message.setTemplateId("00X58000000fz0c");
            //            message.setTreatBodiesAsTemplate(true);

            // We can also just use an id for an implicit to address
            GetUserInfoResult guir = connection.getUserInfo();
            message.setTargetObjectIds(new String[]{"00358000015xGRK"});
            //            message.setTargetObjectIds(new String[]{guir.getUserId()});
            message.setUseSignature(true);
            //message.setPlainTextBody("This is the humongous body of the message.");
            //            EmailFileAttachment[] efas = { efa };
            //            message.setFileAttachments(efas);
            //            message.setToAddresses(new String[] { "person3@salesforce.com" });
            MassEmailMessage[] messages = { message };
            SendEmailResult[] results = connection.sendEmail(messages);
            if (results[0].isSuccess()) {
                System.out.println("The email was sent successfully.");
            } else {
                System.out.println("The email failed to send: "
                        + results[0].getErrors()[0].getMessage());
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
    }

    // delete the 5 newly created Account
    private static void deleteAccounts() {

        System.out.println("Deleting the 5 new test Accounts...");
        String[] ids = new String[5];

        try {

            QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
                    "CreatedDate DESC LIMIT 5");
            if (queryResults.getSize() > 0) {
                for (int i=0;i<queryResults.getRecords().length;i++) {
                    // cast the SObject to a strongly-typed Account
                    Account a = (Account)queryResults.getRecords()[i];
                    // add the Account Id to the array to be deleted
                    ids[i] = a.getId();
                    System.out.println("Deleting Id: " + a.getId() + " - Name: "+a.getName());
                }
            }

            // delete the records in Salesforce.com by passing an array of Ids
            DeleteResult[] deleteResults = connection.delete(ids);

            // check the results for any errors
            for (int i=0; i< deleteResults.length; i++) {
                if (deleteResults[i].isSuccess()) {
                    System.out.println(i+". Successfully deleted record - Id: " + deleteResults[i].getId());
                } else {
                    Error[] errors = deleteResults[i].getErrors();
                    for (int j=0; j< errors.length; j++) {
                        System.out.println("ERROR deleting record: " + errors[j].getMessage());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}