package beakers.mail.utils;

/**
 * Unwrapped mail content
 */
public class MailMessageContent {
    String from
    String to
    String plainText
    String html
    String subject

    Map<String, List<String>> headers
    byte[] messageData


    @Override
    public String toString() {
        return "From: '" + from + '\'' + ", To: '" + to + '\'' + ", Subject: '" + subject + '\'';
    }
}
