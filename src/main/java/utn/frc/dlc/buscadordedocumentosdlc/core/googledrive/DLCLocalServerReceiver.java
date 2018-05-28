package utn.frc.dlc.buscadordedocumentosdlc.core.googledrive;


import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.util.Throwables;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 * OAuth 2.0 verification code receiver that runs a Jetty server on a free port, waiting for a
 * redirect with the verification code.
 * <p>
 * <p>
 * Implementation is thread-safe.
 * </p>
 *
 * @author Yaniv Inbar
 * @since 1.11
 */
public final class DLCLocalServerReceiver implements VerificationCodeReceiver {

    private static final String LOCALHOST = "localhost";

    private static final String CALLBACK_PATH = "/Callback";

    private Server server;

    String code;

    String error;

    final Semaphore waitUnlessSignaled = new Semaphore(0 /* initially zero permit */);

    private int port;

    private final String host;

    private final String callbackPath;

    private String successLandingPageUrl;

    private String failureLandingPageUrl;

    public DLCLocalServerReceiver() {
        this(LOCALHOST, -1, CALLBACK_PATH, null, null);
    }

    DLCLocalServerReceiver(String host, int port,
                           String successLandingPageUrl, String failureLandingPageUrl) {
        this(host, port, CALLBACK_PATH, successLandingPageUrl, failureLandingPageUrl);
    }

    DLCLocalServerReceiver(String host, int port, String callbackPath,
                           String successLandingPageUrl, String failureLandingPageUrl) {
        this.host = host;
        this.port = port;
        this.callbackPath = callbackPath;
        this.successLandingPageUrl = successLandingPageUrl;
        this.failureLandingPageUrl = failureLandingPageUrl;
    }

    @Override
    public String getRedirectUri() throws IOException {
        server = new Server(port != -1 ? port : 0);
        Connector connector = server.getConnectors()[0];
        connector.setHost(host);
        server.addHandler(new CallbackHandler());
        try {
            server.start();
            port = connector.getLocalPort();
        } catch (Exception e) {
            Throwables.propagateIfPossible(e);
            throw new IOException(e);
        }
        return "http://" + host + ":" + port + callbackPath;
    }

    @Override
    public String waitForCode() throws IOException {
        waitUnlessSignaled.acquireUninterruptibly();
        if (error != null) {
            throw new IOException("User authorization failed (" + error + ")");
        }
        return code;
    }

    @Override
    public void stop() throws IOException {
        waitUnlessSignaled.release();
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                Throwables.propagateIfPossible(e);
                throw new IOException(e);
            }
            server = null;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getCallbackPath() {
        return callbackPath;
    }

    public static final class Builder {

        private String host = LOCALHOST;

        private int port = -1;

        private String successLandingPageUrl;
        private String failureLandingPageUrl;

        private String callbackPath = CALLBACK_PATH;

        public DLCLocalServerReceiver build() {
            return new DLCLocalServerReceiver(host, port, callbackPath,
                    successLandingPageUrl, failureLandingPageUrl);
        }

        public String getHost() {
            return host;
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public int getPort() {
            return port;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public String getCallbackPath() {
            return callbackPath;
        }

        public Builder setCallbackPath(String callbackPath) {
            this.callbackPath = callbackPath;
            return this;
        }

        public Builder setLandingPages(String successLandingPageUrl, String failureLandingPageUrl) {
            this.successLandingPageUrl = successLandingPageUrl;
            this.failureLandingPageUrl = failureLandingPageUrl;
            return this;
        }
    }

    class CallbackHandler extends AbstractHandler {

        @Override
        public void handle(
                String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
                throws IOException {
            if (!CALLBACK_PATH.equals(target)) {
                return;
            }

            try {
                ((Request) request).setHandled(true);
                error = request.getParameter("error");
                code = request.getParameter("code");

                if (error == null && successLandingPageUrl != null) {
                    response.sendRedirect(successLandingPageUrl);
                } else if (error != null && failureLandingPageUrl != null) {
                    response.sendRedirect(failureLandingPageUrl);
                } else {
                    writeLandingHtml(response);
                }
                response.flushBuffer();
            } finally {
                waitUnlessSignaled.release();
            }
        }

        private void writeLandingHtml(HttpServletResponse response) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");

            PrintWriter doc = response.getWriter();
            doc.println("<html>");
            doc.println("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
            doc.println("<body>");
            doc.println("Received verification code. You may now close this window.");
            doc.println("</body>");
            doc.println("</html>");
            doc.flush();
        }
    }
}

