package lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BufferLock implements Buffer {
  private Integer data;
  private Lock lock = new ReentrantLock();
  private Condition isEmptyCondition = lock.newCondition();
  private Condition isFullCondition = lock.newCondition();

  @Override public void put(int value) throws InterruptedException {
    lock.lock();
    try {
      while (data != null) {
        isEmptyCondition.await();
      }
      data = value;
      isFullCondition.signalAll();
    } finally {
      lock.unlock();
    }
  }

  @Override public int get() throws InterruptedException {
    lock.lock();
    try {
      while (data == null) {
        isFullCondition.await();
      }
      int value = data;
      data = null;
      isEmptyCondition.signalAll();
      return value;
    } finally {
      lock.unlock();
    }
  }
}
