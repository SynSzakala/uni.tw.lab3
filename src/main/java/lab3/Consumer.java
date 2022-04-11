package lab3;

class Consumer extends Thread {
  final private Buffer buffer;

  public Consumer(Buffer buffer) {
    this.buffer = buffer;
  }

  public void run() {
    try {
      while (true) {
        sleep(20);
        buffer.get();
      }
    } catch (InterruptedException unused) {
      // ignore
    }
  }
}
