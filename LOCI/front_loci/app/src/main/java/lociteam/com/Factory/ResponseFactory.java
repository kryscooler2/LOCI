package lociteam.com.Factory;

import lociteam.com.Model.ResponseToRequest;
import lociteam.com.Model.Station;

public class ResponseFactory {

    public ResponseToRequest create(){
        return new Station();
    }
}