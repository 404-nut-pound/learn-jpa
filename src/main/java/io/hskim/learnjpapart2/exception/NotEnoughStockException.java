package io.hskim.learnjpapart2.exception;

public class NotEnoughStockException extends RuntimeException {

  public NotEnoughStockException() {
  }

  public NotEnoughStockException(String arg0) {
    super(arg0);
  }

  public NotEnoughStockException(Throwable arg0) {
    super(arg0);
  }

  public NotEnoughStockException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public NotEnoughStockException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
    super(arg0, arg1, arg2, arg3);
  }

}
