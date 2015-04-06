/**
 * Created by user on 4/6/2015.
 */
import javax.ws.rs.core.Response;

/**
 * Created by user on 2/5/2015.
 */
public class Main {
    public static void main(String[] args){
        ExecutionResourceMock executionResourceMock = new ExecutionResourceMock();
        Response response = executionResourceMock.executorResourceMock();
        System.out.println("Response received: " + response.getEntity());
    }
}
