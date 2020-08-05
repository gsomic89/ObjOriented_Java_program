package shop.main;

import shop.ui.ControlState;
import shop.ui.UI;
import shop.ui.UIMenu;
import shop.ui.UIMenuAction;
//import shop.ui.UIMenuBuilder;
import shop.ui.UIObjectBuilder;
import shop.ui.UIError;
import shop.ui.UIForm;
import shop.ui.UIFormTest;
//import shop.ui.UIFormBuilder;
import shop.data.Data;
import shop.data.Inventory;
import shop.data.Video;
import shop.data.Record;
import shop.command.Command;
import java.util.Iterator;
import java.util.Comparator;

class Control {
//converted to enums; have states to defined in one file
  private static final int NUMSTATES = 10;
  private UIMenu[] _menus;
  private ControlState _state;

  private UIForm _getVideoForm;
  private UIFormTest _numberTest;
  private UIFormTest _stringTest;
    
  private Inventory _inventory;
  private UI _ui;
  
  Control(Inventory inventory, UI ui) {
    _inventory = inventory;
    _ui = ui;

//enums
    _menus = new UIMenu[NUMSTATES];
    _state = ControlState.START;
    addSTART(ControlState.START);
    addEXIT(ControlState.EXIT);
    
    UIFormTest yearTest = new UIFormTest() {
        public boolean run(String input) {
          try {
            int i = Integer.parseInt(input);
            return i > 1800 && i < 2025;
          } catch (NumberFormatException e) {
            return false;
          }
        }
      };
    _numberTest = new UIFormTest() {
        public boolean run(String input) {
          try {
            Integer.parseInt(input);
            return true;
          } catch (NumberFormatException e) {
            return false;
          }
        }
      };
    _stringTest = new UIFormTest() {
        public boolean run(String input) {
          return ! "".equals(input.trim());
        }
      };

    UIObjectBuilder f = new UIObjectBuilder();
    f.add("Title", _stringTest);
    f.add("Year", yearTest);
    f.add("Director", _stringTest);
    _getVideoForm = f.toUIForm("Enter Video");
  }
  
  void run() {
    try {
      while (_state != ControlState.EXITED) {
        _ui.processMenu(_menus[_state.ordinal()]);
      }
    } catch (UIError e) {
      _ui.displayError("UI closed");
    }
  }
  
  private void addSTART(ControlState stateNum) {
	  UIObjectBuilder m = new UIObjectBuilder();
    
    m.add("Default",
      new UIMenuAction() {
        public void run() {
          _ui.displayError("doh!");
        }
      });
    m.add("Add/Remove copies of a video",
      new UIMenuAction() {
        public void run() {
          String[] result1 = _ui.processForm(_getVideoForm);
		if(result1 == null) {
				_ui.displayMessage("Add/Remove cancelled");
		} else {
			Video v = Data.newVideo(result1[0], Integer.parseInt(result1[1]), result1[2]);

			UIObjectBuilder f = new UIObjectBuilder();
			f.add("Number of copies to add/remove", _numberTest);
			String[] result2 = _ui.processForm(f.toUIForm(""));
                                             
			Command c = Data.newAddCmd(_inventory, v, Integer.parseInt(result2[0]));
			if (! c.run()) {
				_ui.displayError("Command failed");
			}
          	}
        }
      });
	m.add("Check in a video",
			() -> {
				String[] result1 = _ui.processForm(_getVideoForm);
				if(result1 == null) {
					_ui.displayMessage("Check in a video cancelled");
				} else {
					Video v = Data.newVideo(result1[0], Integer.parseInt(result1[1]), result1[2]);

					Command c = Data.newInCmd(_inventory, v);
					if (!c.run()) {
						_ui.displayError("Command failed");
					}
				}
      });
	m.add("Check out a video",
			() -> {
				String[] result1 = _ui.processForm(_getVideoForm);
				if(result1 == null) {
					_ui.displayMessage("Check out a video cancelled");
				} else { 
					Video v = Data.newVideo(result1[0], Integer.parseInt(result1[1]), result1[2]);

					Command c = Data.newOutCmd(_inventory,v);
					if (!c.run()) {
						_ui.displayError("Command failed");
					}
					}
      });
    m.add("Print the inventory",
      new UIMenuAction() {
        public void run() {
          _ui.displayMessage(_inventory.toString());
        }
      });
    m.add("Clear the inventory",
      new UIMenuAction() {
        public void run() {
          if (!Data.newClearCmd(_inventory).run()) {
            _ui.displayError("Command failed");
          }
        }
      });
    m.add("Undo",
      new UIMenuAction() {
        public void run() {
          if (!Data.newUndoCmd(_inventory).run()) {
            _ui.displayError("Command failed");
          }
        }
      });
    m.add("Redo",
      new UIMenuAction() {
        public void run() {
          if (!Data.newRedoCmd(_inventory).run()) {
            _ui.displayError("Command failed");
          }
        }
      });
	m.add("Print top ten all time rentals in order",
			() -> {
				Iterator<Record> it = _inventory.iterator((r1, r2) -> r2.numRentals() - r1.numRentals());
				int count = 10;
				StringBuilder s = new StringBuilder();
				while (it.hasNext() && count > 0) {
					Record r = it.next();
					s.append(r.toString());
					s.append("\n");
					count--;
				}
				_ui.displayMessage(s.toString());
			});
          
    m.add("Exit",
      new UIMenuAction() {
        public void run() {
          _state = ControlState.EXIT;
        }
      });
    
    m.add("Initialize with bogus contents",
      new UIMenuAction() {
        public void run() {
          Data.newAddCmd(_inventory, Data.newVideo("apple", 1989, "m"), 1).run();
          Data.newAddCmd(_inventory, Data.newVideo("bob", 1990, "m"), 2).run();
          Data.newAddCmd(_inventory, Data.newVideo("carrot", 1991, "m"), 3).run();
          Data.newAddCmd(_inventory, Data.newVideo("dawn", 1992, "m"), 4).run();
          Data.newAddCmd(_inventory, Data.newVideo("emily", 1993, "m"), 5).run();
          Data.newAddCmd(_inventory, Data.newVideo("fan", 1994, "m"), 6).run();
          Data.newAddCmd(_inventory, Data.newVideo("garden", 1995, "m"), 7).run();
          Data.newAddCmd(_inventory, Data.newVideo("house", 1996, "m"), 8).run();
          Data.newAddCmd(_inventory, Data.newVideo("inside", 1997, "m"), 9).run();
          Data.newAddCmd(_inventory, Data.newVideo("jk", 1998, "m"), 10).run();
          Data.newAddCmd(_inventory, Data.newVideo("king", 1999, "m"), 11).run();
          Data.newAddCmd(_inventory, Data.newVideo("lion", 2000, "m"), 12).run();
          Data.newAddCmd(_inventory, Data.newVideo("man", 2001, "m"), 13).run();
          Data.newAddCmd(_inventory, Data.newVideo("nancy", 2002, "m"), 14).run();
          Data.newAddCmd(_inventory, Data.newVideo("orange", 2003, "m"), 15).run();
          Data.newAddCmd(_inventory, Data.newVideo("purple", 2004, "m"), 16).run();
          Data.newAddCmd(_inventory, Data.newVideo("quiet", 2005, "m"), 17).run();
          Data.newAddCmd(_inventory, Data.newVideo("rest", 2006, "m"), 18).run();
          Data.newAddCmd(_inventory, Data.newVideo("sleep", 2007, "m"), 19).run();
          Data.newAddCmd(_inventory, Data.newVideo("time", 2008, "m"), 20).run();
          Data.newAddCmd(_inventory, Data.newVideo("under", 2009, "m"), 21).run();
          Data.newAddCmd(_inventory, Data.newVideo("venom", 2010, "m"), 22).run();
          Data.newAddCmd(_inventory, Data.newVideo("will", 2011, "m"), 23).run();
          Data.newAddCmd(_inventory, Data.newVideo("xanax", 2012, "m"), 24).run();
          Data.newAddCmd(_inventory, Data.newVideo("yahoo", 2013, "m"), 25).run();
          Data.newAddCmd(_inventory, Data.newVideo("zoo", 2014, "m"), 26).run();
        }
      });
    
    _menus[stateNum.ordinal()] = m.toUIMenu("Bob's Video");
  }
  private void addEXIT(ControlState stateNum) {
	  UIObjectBuilder m = new UIObjectBuilder();
    
    m.add("Default", new UIMenuAction() { public void run() {} });
    m.add("Yes",
      new UIMenuAction() {
        public void run() {
          _state = ControlState.EXITED;
        }
      });
    m.add("No",
      new UIMenuAction() {
        public void run() {
          _state = ControlState.START;
        }
      });
    
    _menus[stateNum.ordinal()] = m.toUIMenu("Are you sure you want to exit?");
  }
}
