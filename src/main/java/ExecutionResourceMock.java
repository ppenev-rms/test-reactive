import javax.ws.rs.core.Response;

/**
 * Created by user on 4/6/2015.
 */
public class ExecutionResourceMock {
    public Response executorResourceMock() {
        int activityId = 2;

        CoreManager manager = new CoreManager();
        manager.processContext();

        return Response.status(Response.Status.OK.getStatusCode()).entity(activityId).build();
    }
}
