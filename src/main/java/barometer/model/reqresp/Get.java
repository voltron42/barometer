package barometer.model.reqresp;

import java.util.List;

public class Get implements Request {

    private String url;

    private List<Param> params;

    private List<Header> headers;
}
