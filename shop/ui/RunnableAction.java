package shop.ui;

public class RunnableAction {
	
	UIFormTest test;
	UIMenuAction action;
	
	public RunnableAction(UIFormTest _test) {
		test = _test;
	}
	public RunnableAction(UIMenuAction _action) {
		action = _action;
	}
	public void run() {
		action.run();
	}
	public boolean run(String text) {
		return test.run(text);
	}
}