package shop.ui;

/**
 * @see UIFormBuilder
 */
public final class UIForm {
  private final String _heading;
  private final PromptableAction[] _form;
  
  UIForm(String heading, PromptableAction[] menu) {
    _heading = heading;
    _form = menu;
  }

  public int size() {
    return _form.length;
  }
  public String getHeading() {
    return _heading;
  }
  public String getPrompt(int i) {
    return _form[i].prompt;
  }
  public boolean checkInput(int i, String input) {
    if (null == _form[i])
      return true;
    return _form[i].action.run(input);
  }
}

