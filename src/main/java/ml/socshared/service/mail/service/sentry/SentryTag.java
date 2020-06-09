package ml.socshared.service.mail.service.sentry;

public enum SentryTag {
    SEND_MAIL("type", "send_mail"),
    SEND_MAIL_CONFIRM("type", "send_mail_confirm"),
    SEND_MAIL_RESET_PASSWORD("type",  "send_mail_reset_password");

    SentryTag(String t, String tag) {
        type = t;
        sentryTag = tag;
    }

    public String type() {
        return type;
    }
    public String value() {
        return sentryTag;
    }

    private final String sentryTag;
    private final String type;
    public static final String SERVICE_NAME = "MSS";

}
