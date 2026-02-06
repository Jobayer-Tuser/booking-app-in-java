package org.booking.misc;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator {
    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validateEmailWithJavaMailPackage(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
            return true;
        } catch (AddressException exception) {
            return false;
        }
    }

    private boolean isDomainValid(String email) throws UnknownHostException {
        try {
            String domain = email.substring(email.indexOf("@") + 1);
            InetAddress inetAddress = InetAddress.getByName(domain);
            return true;
        } catch (UnknownHostException exception) {
            exception.getMessage();
            return false;
        }
    }

    public boolean isDomainAndEmailValid(String email) throws UnknownHostException {
        if (! isEmailValid(email)) {
            return false;
        }

        return isDomainValid(email);
    }
}
