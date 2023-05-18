package com.espresso.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SecureSSLContext {

    private SecureSSLContext() {
    }

    public static SSLContext getInstance(String protocol) throws NoSuchAlgorithmException, KeyManagementException {
        // Enforce the use of TLSv1.2 or higher
        if (protocol.equalsIgnoreCase("TLS") || protocol.equalsIgnoreCase("SSL")) {
            protocol = "TLSv1.2";
        }
        
        SSLContext sslContext = SSLContext.getInstance(protocol);
        sslContext.init(null, new TrustManager[] { new SecureTrustManager() }, null);
        return sslContext;
    }

    private static class SecureTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            // Perform certificate validation for the client here. Throw a CertificateException if the certificate is not trusted.
            // Example:
            // if (!isValidClientCertificate(certs[0])) {
            //    throw new CertificateException("Client certificate is not valid.");
            // }
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            // Perform certificate validation for the server here. Throw a CertificateException if the certificate is not trusted.
            // Example:
            // if (!isValidServerCertificate(certs[0])) {
            //    throw new CertificateException("Server certificate is not valid.");
            // }
        }

        public X509Certificate[] getAcceptedIssuers() {
            // Return an empty array to accept any issuer.
            return new X509Certificate[0];
        }
    }
}
