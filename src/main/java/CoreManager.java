import rx.Observable;
import rx.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by user on 4/6/2015.
 */
public class CoreManager {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void processContext() {
        List<TestSuite> suites = getTestSuites();
        Observable<ExecutionResult> results = submitSuitesForExecution(suites);
        results.subscribe(new Observer<ExecutionResult>() {
            private List<ExecutionResult> results = new LinkedList<>();

            @Override
            public void onCompleted() {
                System.out.println("Finished!");
                System.out.println("Reporting: " + produceReporting(results));
                executor.shutdown();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onNext(ExecutionResult executionResult) {
                System.out.println("Received results for suite with id=" + executionResult.getTestSuiteId());
                System.out.println("It has passed: " + executionResult.isHasPassed());
                System.out.println("--------------------");
                results.add(executionResult);
            }
        });

    }

    private List<TestSuite> getTestSuites() {
        List<TestSuite> suites = new LinkedList<>();
        suites.add(new TestSuite().setId(1));
        suites.add(new TestSuite().setId(2));
        suites.add(new TestSuite().setId(3));
        return suites;
    }

    private Observable<ExecutionResult> submitSuitesForExecution(List<TestSuite> suites) {
        return Observable.create( observer -> executor.execute(() -> {
            try {
                for (TestSuite suite : suites) {
                    ExecutionResult result = new ExecutionResult();
                    result.setTestSuiteId(suite.getId());
                    result.setHasPassed(suite.getId() % 2 == 0);
                    Thread.sleep(1000);
                    observer.onNext(result);
                }

                observer.onCompleted();
            } catch (Exception ex) {
                observer.onError(ex);
            }
        }) );
    }

    private String produceReporting(List<ExecutionResult> results) {
        System.out.println("Reports are being produced!!!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            return "Failure";
        }
        return "Success";
    }
}
