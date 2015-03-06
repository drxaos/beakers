package beakers.utils.mail;

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
}
