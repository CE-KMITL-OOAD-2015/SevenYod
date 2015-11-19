//package mchat;
//
//import junit.framework.TestCase;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.FileSystemXmlApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//
///**
// * Created by LIURLNs on 11/19/2015.
// */
//public class EmailServiceTest extends TestCase {
//    @SuppressWarnings("resource")
//    public static void main(String args[]) {
//        ApplicationContext context = new FileSystemXmlApplicationContext("mailapp.xml");
//        EmailService mailer = (EmailService) context.getBean("mailService");
//        String toAddr = "liurln38@gmail.com";
//        String subject = "This email sent by Test";
//        String body = "This email sent by Test";
//        mailer.sendMail(toAddr, subject, body);
//    }
//}