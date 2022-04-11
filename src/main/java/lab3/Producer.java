package lab3;

class Producer extends Thread {
  final private Buffer buffer;

  public Producer(Buffer buffer) {
    this.buffer = buffer;
  }

  public void run() {
    try {
      for (int i = 0; i < 100; ++i) {
        sleep(10);
        buffer.put(i);
      }
    } catch (InterruptedException unused) {
      // ignore
    }
  }
}
