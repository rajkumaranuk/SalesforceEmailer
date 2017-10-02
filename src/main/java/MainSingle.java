import com.sforce.soap.enterprise.*;
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

		GetUserInfoResult guir = connection.getUserInfo();

		SingleEmailMessage email = new SingleEmailMessage();
		email.setTargetObjectId(guir.getUserId());
		email.setTemplateId("00X58000000fz0a");
		email.setSaveAsActivity(false);

		SingleEmailMessage[] messages = { email };
		SendEmailResult[] results = connection.sendEmail(messages);
		if (results[0].isSuccess()) {
			System.out.println("The email was sent successfully.");
		} else {
			System.out.println("The email failed to send: "
					+ results[0].getErrors()[0].getMessage());
		}

	}

}