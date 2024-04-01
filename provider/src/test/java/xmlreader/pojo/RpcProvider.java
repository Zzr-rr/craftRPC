package xmlreader.pojo;

public class RpcProvider {
    private String packagePath;
    private String host;
    private int port;
    private String registration;
    private String registrationAddress;


    public RpcProvider() {
    }

    public RpcProvider(String packagePath, String host, int port, String registration, String registrationAddress) {
        this.packagePath = packagePath;
        this.host = host;
        this.port = port;
        this.registration = registration;
        this.registrationAddress = registrationAddress;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }
}
