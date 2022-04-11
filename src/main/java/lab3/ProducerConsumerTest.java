package lab3;

import java.util.ArrayList;

class ProducerConsumerTest implements Runnable {
  private int n1, n2;
  private Buffer buffer;

  public ProducerConsumerTest(int n1, int n2, Buffer buffer) {
    this.n1 = n1;
    this.n2 = n2;
    this.buffer = buffer;
  }

  @Override
  public void run() {
    var startTime = System.nanoTime();
    var consumers = new ArrayList<Consumer>();
    var producers = new ArrayList<Producer>();
    for (int i = 0; i < n1; ++i) {
      var producer = new Producer(buffer);
      producers.add(producer);
      producer.start();
    }

    for (int i = 0; i < n2; ++i) {
      var consumer = new Consumer(buffer);
      consumers.add(consumer);
      consumer.start();
    }

    for (Producer producer : producers) {
      try {
        producer.join();
      } catch (InterruptedException e) {
        // ignore
      }
    }

    var stopTime = System.nanoTime();
    var duration = stopTime - startTime;
    System.out.format("Producers: %d | Consumers: %d | Duration %dms\n", n1, n2, duration / 1000 / 1000);

    try {
      // make sure that all consumers are done
      Thread.sleep(100);
    } catch (InterruptedException e) {
      // ignore
    }

    for (Consumer consumer : consumers) {
      consumer.interrupt();
    }
  }
}
