package xmlreader.pojo;

import java.util.List;

public class Services {

    private List<Service> services;

    public Services() {
    }

    public Services(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
