package shop.ui;


//replaced pair w/ PromptableAction in own class
public class PromptableAction {
	String prompt;
	RunnableAction action;
	
	public PromptableAction(String _prompt, UIFormTest _action) {
		prompt = _prompt;
		action = new RunnableAction(_action);
	}
	
	public PromptableAction(String _prompt, UIMenuAction _action) {
		prompt = _prompt;
		action = new RunnableAction(_action);
	}

}
