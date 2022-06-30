package kb.practiceboard.handler;

public class CannotFindEmailException extends RuntimeException {
  private static final String MESSAGE = "해당 이메일을 찾을 수 없습니다.";

  public CannotFindEmailException() {
    super(MESSAGE);
  }
}
