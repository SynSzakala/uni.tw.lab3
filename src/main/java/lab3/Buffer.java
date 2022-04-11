package lab3;

public interface Buffer {
  void put(int value) throws InterruptedException;

  int get() throws InterruptedException;
}
