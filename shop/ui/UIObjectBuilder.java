package shop.ui;

import java.util.ArrayList;
import java.util.List;


public class UIObjectBuilder {
  private final List _menu;
  public UIObjectBuilder() {
    _menu = new ArrayList();
  }
  //placed in single file
  // UIMenuBuilder
  public UIMenu toUIMenu(String heading) {
    validateBuilder(heading);

    PromptableAction[] array = new PromptableAction[_menu.size()];
    for (int i = 0; i < _menu.size(); i++)
      array[i] = (PromptableAction) (_menu.get(i));

    return new UIMenu(heading, array);
  }
  
  //placed in single file
  // UIFormBuilder
  public UIForm toUIForm(String heading) {
    validateBuilder(heading);

    PromptableAction[] array = new PromptableAction[_menu.size()];
    for (int i = 0; i < _menu.size(); i++)
      array[i] = (PromptableAction) (_menu.get(i));
    return new UIForm(heading, array);
  }

  // UIFormBuilder
  public void add(String prompt, UIFormTest test) {
    _menu.add(new PromptableAction(prompt, test));
  }
  
  // UIMenuBuilder
  public void add(String prompt, UIMenuAction action) {
    if (null == action)
      throw new IllegalArgumentException();
    _menu.add(new PromptableAction(prompt, action));
  }

  //central location where validation is happening
  private boolean validateBuilder(String heading)
  {
    if (null == heading)
      throw new IllegalArgumentException();
    if (_menu.size() < 1)
      throw new IllegalStateException();
    return true;
  }
}
