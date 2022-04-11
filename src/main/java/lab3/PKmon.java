package lab3;

import java.util.function.Supplier;


public class PKmon {
  public static void main(String[] args) {
    testWithBuffer("BufferLock", BufferLock::new);
    testWithBuffer("BufferSemaphore", BufferSemaphore::new);
  }

  private static void testWithBuffer(String testLabel, Supplier<Buffer> bufferSupplier) {
    var tests = new ProducerConsumerTest[]{
        new ProducerConsumerTest(1, 1, bufferSupplier.get()),
        new ProducerConsumerTest(10, 10, bufferSupplier.get()),
        new ProducerConsumerTest(10, 2, bufferSupplier.get()),
        new ProducerConsumerTest(2, 10, bufferSupplier.get()),
    };
    System.out.format("Starting test for: %s\n", testLabel);
    for (ProducerConsumerTest test : tests) test.run();
    System.out.format("Ending test for: %s\n", testLabel);
  }
}
