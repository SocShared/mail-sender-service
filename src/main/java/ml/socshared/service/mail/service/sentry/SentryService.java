package ml.socshared.service.mail.service.sentry;

import io.sentry.Sentry;
import io.sentry.event.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SentryService {

    public SentryService() {
        Sentry.init();
    }

    public void logException(Throwable exc) {
        Sentry.capture(exc);
        Sentry.clearContext();
    }

    public void logException(Throwable exc, User affectedUser) {
        logException(exc, affectedUser, null);
    }

    public void logException(Throwable exc, Breadcrumb breadcrumb) {
        logException(exc, null, breadcrumb);
    }

    public void logException(Throwable exc, User affectedUser, Breadcrumb breadcrumb) {
        if (affectedUser != null) {
            Sentry.getContext().setUser(affectedUser);
        }

        if (breadcrumb != null) {
            Sentry.getContext().recordBreadcrumb(breadcrumb);
        }

        logException(exc);
    }

    public void logException(Throwable exc, Map<String, String> tags, Map<String, Object> extras) {
        logException(exc, null, null, tags, extras);
    }

    public void logException(Throwable exc, User affectedUser, Breadcrumb breadcrumb,
                             Map<String, String> tags, Map<String, Object> extras) {

        addTagsAndExtrasToSentryContext(tags, extras);

        logException(exc, affectedUser, breadcrumb);
    }

    public void logMessage(String message) {
        Sentry.capture(message);
        Sentry.clearContext();
    }

    public void logMessage(String message, User affectedUser) {
        logMessage(message, affectedUser, null);
    }

    public void logMessage(String message, Breadcrumb breadcrumb) {
        logMessage(message, null, breadcrumb);
    }

    public void logMessage(String message, User affectedUser, Breadcrumb breadcrumb) {
        Sentry.getContext().setUser(affectedUser);
        Sentry.getContext().recordBreadcrumb(breadcrumb);

        logMessage(message);
    }

    public void logMessage(String message, Map<String, String> tags, Map<String, Object> extras) {
        logMessage(message, null, null, tags, extras);
    }

    public void logMessage(String message, User affectedUser, Breadcrumb breadcrumb,
                             Map<String, String> tags, Map<String, Object> extras) {

        addTagsAndExtrasToSentryContext(tags, extras);

        logMessage(message, affectedUser, breadcrumb);
    }

    public void logEvent(Event event) {
        Sentry.capture(event);
    }

    private void addTagsAndExtrasToSentryContext(Map<String, String> tags, Map<String, Object> extras) {
        if (tags != null) {
            for (Map.Entry<String, String> tag : tags.entrySet()) {
                Sentry.getContext().addTag(tag.getKey(), tag.getValue());
            }
        }

        if (extras != null) {
            for (Map.Entry<String, Object> extra : extras.entrySet()) {
                Sentry.getContext().addExtra(extra.getKey(), extra.getValue());
            }
        }
    }
}
