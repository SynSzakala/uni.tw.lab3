package lab3;

import java.util.concurrent.Semaphore;

class BufferSemaphore implements Buffer {
  final private Semaphore emptySemaphore = new Semaphore(1);
  final private Semaphore fullSemaphore = new Semaphore(0);
  private Integer data;

  @Override public void put(int value) throws InterruptedException {
    emptySemaphore.acquire();
    data = value;
    fullSemaphore.release();
  }

  @Override public int get() throws InterruptedException {
    fullSemaphore.acquire();
    int value = data;
    data = null;
    emptySemaphore.release();
    return value;
  }
}
