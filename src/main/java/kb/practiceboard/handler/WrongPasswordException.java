package kb.practiceboard.handler;

public class WrongPasswordException extends RuntimeException {
  private static final String MESSAGE = "비밀번호가 틀렸습니다.";

  public WrongPasswordException() {
    super(MESSAGE);
  }
}
