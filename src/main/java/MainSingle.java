import com.sforce.soap.enterprise.*;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.EmailTemplate;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class MainSingle
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
//             doSendEmail();
			sendTemplatedEmail();


        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }

    }



//    public static void doSendEmail() {
//        try {
//
//            SingleEmailMessage message = new SingleEmailMessage();
//
//            message.setBccSender(true);
//            message.setEmailPriority(EmailPriority.High);
//            message.setReplyTo("person1@salesforce.com");
//            message.setSaveAsActivity(false);
//            message.setSubject("Tem Hi Raj This is how you use the " + "sendEmail method.");
//
//            message.setTemplateId("00X58000000fz0c");
//            message.setTreatBodiesAsTemplate(false);
//
//            GetUserInfoResult guir = connection.getUserInfo();
//            message.setTargetObjectId(guir.getUserId());
//            message.setUseSignature(true);
//
//            SingleEmailMessage[] messages = { message };
//            SendEmailResult[] results = connection.sendEmail(messages);
//            if (results[0].isSuccess()) {
//                System.out.println("The email was sent successfully.");
//            } else {
//                System.out.println("The email failed to send: "
//                        + results[0].getErrors()[0].getMessage());
//            }
//        } catch (ConnectionException ce) {
//            ce.printStackTrace();
//        }
//    }

	//  -------------------------------------------------------------------------
	//  HELPER method: sendTemplatedEmail
	//  -------------------------------------------------------------------------
	public static void sendTemplatedEmail() throws ConnectionException
	{

		String name = "00358000015xGRK";

//		GetUserInfoResult guir = connection.getUserInfo();

		SingleEmailMessage email = new SingleEmailMessage();

		QueryResult queryResults = connection.query("SELECT Id, Name FROM Contact where Email = 'kumaran.thava@gmail.com'");
		if (queryResults.getSize() > 0) {
			// cast the SObject to a strongly-typed Account
			Contact a = (Contact)queryResults.getRecords()[0];
			System.out.println("Account Id: " + a.getId() + " - Name: "+a.getName());

			email.setTargetObjectId(a.getId());
			email.setTemplateId("00X58000000fz0iEAA");
			email.setSaveAsActivity(false);

			SingleEmailMessage[] messages = { email };
			SendEmailResult[] results = connection.sendEmail(messages);
			if (results[0].isSuccess()) {
				System.out.println("The email was sent successfully.");
			} else {
				System.out.println("The email failed to send: "
						+ results[0].getErrors()[0].getMessage());
			}
		} else {
			System.out.println("Unable to find the account");
		}

//		email.setTemplateId("00X58000000fz0a");


//		QueryResult queryResults = connection.query("select Id, Subject, HtmlValue, Body from EmailTemplate where Id ='00X58000000fz0iEAA'");
//		if (queryResults.getSize() > 0) {
//			for (int i=0;i<queryResults.getRecords().length;i++) {
//				// cast the SObject to a strongly-typed Account
//				EmailTemplate emailTemplate = (EmailTemplate)queryResults.getRecords()[i];
//				String plainBody = emailTemplate.getBody();
//				plainBody = plainBody.replace("{!CustomField}", "raj kumaran UK");
//				email.setPlainTextBody(plainBody);
//			}
//		}



	}

}