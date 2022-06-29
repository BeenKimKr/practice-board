package kb.practiceboard.handler;

public class AlreadyExistEmailException extends RuntimeException {
  private static final String MESSAGE = "이미 가입된 이메일입니다.";

  public AlreadyExistEmailException() {
    super(MESSAGE);
  }
}
