import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
//import org.eclipse.wb.swt.SWTResourceManager;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/******************************************************************************************************************************
 	V0.2

 	TABLE OF CONTENTS:
 	1	DATA FIELD
 	2	UI FUNCTIONS
 		2.1 VerifyListeners	
 		2.2	Functions to update ArrayList contents
 			2.2.1 Update Funding (UI)
 			2.2.2 Update Expenditure (UI)
 			2.2.3 Update Program Flow (UI)
 			2.2.4 Update Program Flow Description (Cache)
 			2.2.5 Add a Row (to key in manually) (UI)
 			2.2.6 Update Participant List (UI)
 			2.2.7 Update Participant List (Cache)
 			2.2.8 Update Event List (Cache)
 			2.2.9 Update BookedUI (UI)
 		2.3 startUp()

 	------start of background constructor

 	3	GENERAL COMPONENT OF BACKGROUND
 	4	EVENT MANAGEMENT
 		4.1 Save
 		4.2 Create Event
 		4.3 Switch Event
 		4.4 Delete Event
 	5	BUDGET TAB
 		5.1 All composites and basic labels
 		5.2 Funding UI
 			5.2.1 Add Funding
 			5.2.2 Delete Funding
 		5.3 Expenditure UI
 			5.3.1 Add Item
 			5.3.2 Update Price and Quantity (of all items)
 			5.3.3 Delete Item
 	6	VENUE TAB
 			6.1 All composites and basic labels
 			6.2 Hardcode for a list of venues
 			6.3 Search for Venue UI
 			6.4 Venue Booking UI (coming soon...)
 				5.4.1 Book Venue
 				5.4.2 Delete Booked Venue
 	7	PROGRAM FLOW TAB
 		7.1 All composites and basic labels
 		7.2 Activity UI
 			7.2.1 Add Activity
 			7.2.2 Update Description (of all activities)
 			7.2.3 Delete Activity
 	8	PARTICIPANTS TAB
 		8.1 Add Rows
 		8.2 Update Participants
 		8.3 Delete Participants

 	------end of background constructor

 	9	MAIN FUCTION


 	MAIN ARCHITECTURE:
 	Every button will update the database and then update the UI after changes are made on the database.
 	Hence, buttons are the "controllers" in our MVC architecture.
 	Classes behind UI is structured in a multilayer architecture.

	BUGS:
	-	Cannot delete the text files of event after we click the switch button for that particular event.
		E.g. let's say we switch to event A. When we press delete event A, APF.txt will still be in the database
		However, if we switch to event A, close the application, start application again, then delete event A (without switching to it)
		then it will work.
		I think this has got to do with readFile. It creates new File objects that points to the same object as in deleteEvent.
		Hence even if we delete the files using deleteEvent, it will still be there. Maybe they create duplicates??
		How to solve this problem?

 	TO DO:
 	-	Before switching events, prompt user to save first
 	-	And make similar buttons for funding, expenditure, venue, and participants; a button that saves only that section to the database
 	-	REFACTORING

 ***********************************************************************************************************************************/


public class background extends Composite implements VerifyListener{

	//	1 DATA FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text inActivity;
	private Text inItem;
	private Composite composite_1;
	private Text inAmount;
	private Text inSponsor;
	private Button btnAddBudget;
	private ScrolledComposite scrolledComposite;
	private Composite composite_4;
	private Button btnDelFunding;
	private ScrolledComposite scrolledComposite_1;
	private Composite composite_5;
	private Button btnAddActivity;
	private Combo comboItem;
	private ScrolledComposite scrolledComposite_2;
	private Label outTotalFunding;
	private Composite composite_6;
	private Label outTotExpenditure;
	private DateTime dateSearch;
	private Combo comboTimeStart;
	private Combo comboTimeEnd;
	//private Label lblNewLabel_2;
	private Composite compoAvailable;
	private DateTime timeStartActivity;
	private DateTime timeEndActivity;
	private Label inAmountErrLabel;
	private Label priceErrLabel;
	private Text textNewEvent;
	private Combo comboEventList;
	private Label lblCurrentEvent;

	final EventList el = new EventList();

	final FundingList fl = new FundingList();
	final ArrayList<Label> lblFunding = new ArrayList<Label>();
	final ArrayList<Button> checkFunding = new ArrayList<Button>();

	final ItemList il = new ItemList();
	final ArrayList<Label> lblActivityExpenditure = new ArrayList<Label>();
	final ArrayList<Label> lblItemBudget = new ArrayList<Label>();
	final ArrayList<Text> textPrice = new ArrayList<Text>();
	final ArrayList<Text> textQty = new ArrayList<Text>();
	final ArrayList<Label> lblTotalPrice = new ArrayList<Label>();
	final ArrayList<Button> checkItem = new ArrayList<Button>();

	final ProgramFlow pf = new ProgramFlow();
	final ArrayList<Label> lblDateOut = new ArrayList<Label>();
	final ArrayList<Label> lblTimeOut = new ArrayList<Label>();
	final ArrayList<Label> lblActivityOut = new ArrayList<Label>();
	final ArrayList<Text> textDesc = new ArrayList<Text>();
	final ArrayList<Button> checkActivity = new ArrayList<Button>();

	final VenueList vl = new VenueList();
	final BookingList bookvl = new BookingList();
	final ArrayList<Label> lblAvailableVenue = new ArrayList<Label>();
	final ArrayList<Label> lblVenuePrice = new ArrayList<Label>();
	final ArrayList<Button> checkAvailableVenue = new ArrayList<Button>();
	final ArrayList<Label> lblBookedVenue = new ArrayList<Label>();
	final ArrayList<Label> lblBookedDate = new ArrayList<Label>();
	final ArrayList<Label> lblBookedTimeStart = new ArrayList<Label>();
	final ArrayList<Label> lblTo = new ArrayList<Label>();
	final ArrayList<Label> lblBookedTimeEnd = new ArrayList<Label>();
	final ArrayList<Label> lblBookedPrice = new ArrayList<Label>();
	final ArrayList<Button> checkBookedVenue = new ArrayList<Button>();
	int timestart, timeend, date;

	ParticipantList pl = new ParticipantList();
	final ArrayList<Text> textName = new ArrayList<Text>();
	final ArrayList<Text> textID = new ArrayList<Text>();
	final ArrayList<Text> textContact = new ArrayList<Text>();
	final ArrayList<Text> textEmail = new ArrayList<Text>();
	final ArrayList<Button> checkPaid = new ArrayList<Button>();
	final ArrayList<Button> checkParticipant = new ArrayList<Button>();
	private Composite composite_9;
	private DateTime dateTime;
	private Label lblTimeError;
	private Label lblClash;
	private Label lblEventWarning;
	private Button btnSave;
	private Text inParticipantPrice;
	private Label priceError;
	private Label outRevenue;
	private Label outParticipantPrice;
	private Label labeltry;
	private Label lblVenueTimeError;
	private Composite compoBooked;
	private ScrolledComposite scrolledCompoBooked;
	private ScrolledComposite scrolledCompAvailable;
	private Label outFromVenue;
	private Label outNetBudget;



	//	2 UI FUNCTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//	2.1 Verify Listeners*******************************************************************************************************

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	@Override
	public void verifyText(VerifyEvent arg0) {
		char[] chars = arg0.text.toCharArray();
		for(int i=0;i<chars.length;i++) {
			if (!(('0' <= chars[i] && chars[i] <= '9') || chars[i]=='.')) {
				arg0.doit = false;
				return;
			}
		}	
	}

	public void showNumberFormatFail(Label lbl, String labelName){
		lbl.setText("Error : " + labelName + " field needs to have two decimal places");
	}

	//	2.2 Functions to update display of ArrayList content **********************************************************************
	//	2.2.1 Update Funding -----------------------------------------------------------------------------------------------------
	public void updateFundingUI(){
		int i;

		//if funding list > controls, make new controls
		for (i = lblFunding.size(); i < fl.size(); i++){
			//new label to display Funding
			lblFunding.add(new Label(composite_4, SWT.NONE));
			lblFunding.get(lblFunding.size()-1).setBounds(10, 15*(lblFunding.size()-1) + 10, 300, 15);
			toolkit.adapt(lblFunding.get(lblFunding.size()-1), true, true);

			//new check button
			checkFunding.add(new Button(composite_4, SWT.CHECK));
			checkFunding.get(checkFunding.size()-1).setBounds(500,15*(checkFunding.size()-1) + 10, 16, 15);
			toolkit.adapt(checkFunding.get(checkFunding.size()-1), true, true);
		}

		//update content of control
		for (i = 0 ; i < fl.size() ; i++) {
			lblFunding.get(i).setText(fl.printFunding(i));
			checkFunding.get(i).setVisible(true);
		}

		//make the unused controls invisible
		for (i = fl.size(); i < lblFunding.size(); i++) 
		{
			lblFunding.get(i).setText("");
			checkFunding.get(i).setVisible(false);
		}

		//update total funding output
		outTotalFunding.setText(fl.printAmountTotal());

		//update net budget output

	}


	//	2.2.2 Update Expenditure ----------------------------------------------------------------------------------------------------------
	public void updateExpenditureUI(){
		int i;

		//if item list > controls, make new controls
		for (i = lblItemBudget.size() ; i < il.size() ; i++){

			//make new label for Activity
			lblActivityExpenditure.add(new Label(composite_6, SWT.NONE));
			lblActivityExpenditure.get(lblActivityExpenditure.size()-1).setBounds(10, 23*(lblActivityExpenditure.size()-1) + 10, 140, 15);
			toolkit.adapt(lblActivityExpenditure.get(lblActivityExpenditure.size()-1), true, true);

			//make new label for Item
			lblItemBudget.add(new Label(composite_6, SWT.NONE));
			lblItemBudget.get(lblItemBudget.size()-1).setBounds(166, 23*(lblItemBudget.size()-1) + 10, 140, 15);
			toolkit.adapt(lblItemBudget.get(lblItemBudget.size()-1), true, true);

			//make new text boxes for prices
			textPrice.add(new Text(composite_6, SWT.BORDER));
			textPrice.get(textPrice.size()-1).setBounds(319, 23*(textPrice.size()-1) + 10, 50, 20);
			toolkit.adapt(textPrice.get(textPrice.size()-1), true, true);
			(textPrice.get(textPrice.size()-1)).addVerifyListener(new VerifyListener() {

				public void verifyText(VerifyEvent arg0) {
					char[] chars = arg0.text.toCharArray();
					for(int i=0;i<chars.length;i++) {
						if (!(('0' <= chars[i] && chars[i] <= '9') || chars[i]=='.')) {
							arg0.doit = false;
							return;
						}
					}
				}});

			//make new text boxes for quantity
			textQty.add(new Text(composite_6, SWT.BORDER));
			textQty.get(textQty.size()-1).setBounds(373, 23*(textQty.size()-1) + 10, 50, 20);
			toolkit.adapt(textQty.get(textQty.size()-1), true, true);
			(textQty.get(textQty.size()-1)).addVerifyListener(new VerifyListener() {

				public void verifyText(VerifyEvent arg0) {
					char[] chars = arg0.text.toCharArray();
					for(int i=0;i<chars.length;i++) {
						if (!(('0' <= chars[i] && chars[i] <= '9'))) {
							arg0.doit = false;
							return;
						}
					}
				}});

			//make new label for total price
			lblTotalPrice.add(new Label(composite_6, SWT.NONE));
			lblTotalPrice.get(lblTotalPrice.size()-1).setBounds(450, 23*(lblTotalPrice.size()-1) + 10, 50, 15);
			toolkit.adapt(lblTotalPrice.get(lblTotalPrice.size()-1), true, true);

			//make new check boxes to delete item
			checkItem.add(new Button(composite_6, SWT.CHECK));
			checkItem.get(checkItem.size()-1).setBounds(520, 23*(checkItem.size()-1) + 10, 16, 16);
			toolkit.adapt(checkItem.get(checkItem.size()-1), true, true);

		}

		//update content of controls
		for (i = 0; i < il.size(); i++){
			lblActivityExpenditure.get(i).setText(il.getItemActivity(i));
			lblItemBudget.get(i).setText(il.getItemName(i));
			textPrice.get(i).setText(il.getItemPrice(i));
			textPrice.get(i).setVisible(true);
			textQty.get(i).setText(il.getItemQty(i));
			textQty.get(i).setVisible(true);
			lblTotalPrice.get(i).setText(il.getItemTotalPrice(i));
			checkItem.get(i).setVisible(true);
		}

		//make unused controls invisible
		for(i = il.size() ; i < lblItemBudget.size(); i++) {
			lblActivityExpenditure.get(i).setText("");
			lblItemBudget.get(i).setText("");
			textPrice.get(i).setVisible(false);
			textQty.get(i).setVisible(false);
			lblTotalPrice.get(i).setText("");
			checkItem.get(i).setVisible(false);
		}

		//update total expenditure
		outTotExpenditure.setText(il.printAmountTotal());

		//update total venue
		double result = 0;
		for (int j = 0; j<bookvl.size(); j++) {
			result += bookvl.get(j).getPrice();
		}
		outFromVenue.setText("$" + Double.toString(result));

		//update net budget
		double result2 = (fl.getAmountTotal() - il.getAmountTotal() - result) ;
		outNetBudget.setText("$" + Double.toString(result2));
	}

	//	2.2.3 Update Program Flow -----------------------------------------------------------------------------------------------
	public void updateProgramFlowUI(){
		int i;

		//if activities > controls, make new controls
		for (i = lblActivityOut.size() ; i < pf.size() ; i++){

			//new label to display date and time
			lblDateOut.add(new Label(composite_5, SWT.WRAP));
			toolkit.adapt(lblDateOut.get(lblDateOut.size()-1), true, true);
			lblDateOut.get(lblDateOut.size()-1).setBounds(10, 75*(lblDateOut.size()-1) + 10, 70, 30);


			lblTimeOut.add(new Label(composite_5, SWT.WRAP));
			toolkit.adapt(lblTimeOut.get(lblTimeOut.size()-1), true, true);
			lblTimeOut.get(lblTimeOut.size()-1).setBounds(81, 75*(lblTimeOut.size()-1) + 10, 70, 30);

			//new label to display activity name
			lblActivityOut.add(new Label(composite_5, SWT.WRAP));
			toolkit.adapt(lblActivityOut.get(lblActivityOut.size()-1), true, true);
			lblActivityOut.get(lblActivityOut.size()-1).setBounds(150, 75*(lblActivityOut.size()-1) + 10, 130, 70);

			//new text for users to fill in description
			textDesc.add(new Text(composite_5, SWT.BORDER));
			textDesc.get(textDesc.size()-1).setBounds(290, 75*(textDesc.size()-1) + 10, 200, 70);
			toolkit.adapt(textDesc.get(textDesc.size()-1), true, true);

			//new check button
			checkActivity.add(new Button(composite_5, SWT.CHECK));
			checkActivity.get(checkActivity.size()-1).setBounds(500, 75*(checkActivity.size()-1) + 10, 16, 16);
			toolkit.adapt(checkActivity.get(checkActivity.size()-1), true, true);
		}


		//update all labels
		for (i = 0 ; i < pf.size() ; i++){
			String day = Integer.toString(pf.get(i).getDate()/1000000);
			String month = Integer.toString((pf.get(i).getDate()/10000%100) + 1);
			String year = Integer.toString(pf.get(i).getDate()%10000);
			lblDateOut.get(i).setText(day+"-"+month+"-"+year);
			lblTimeOut.get(i).setText(pf.printTime(i));
			lblActivityOut.get(i).setText(pf.printActivity(i));
			textDesc.get(i).setVisible(true);
			textDesc.get(i).setText(pf.printDesc(i));
			checkActivity.get(i).setVisible(true);
		}

		for(i = pf.size() ; i < lblActivityOut.size(); i++) {
			lblDateOut.get(i).setText("");
			lblTimeOut.get(i).setText("");
			lblActivityOut.get(i).setText("");
			textDesc.get(i).setText("");
			textDesc.get(i).setVisible(false);
			checkActivity.get(i).setVisible(false);
		}

		comboItem.removeAll();
		comboItem.add("Miscellaneous");
		for(i = 0 ; i < pf.size() ; i++){
			comboItem.add(pf.get(i).getName());
		}

		//clash checks
		if (pf.hasClash()) lblClash.setText("Some of your activities clashed!");
		else lblClash.setText("");
	}

	//	2.2.4 Update Program Flow Description ---------------------------------------------------------------------------------------
	public void updateDescription() {
		int i;
		for (i = 0 ; i < pf.size() ; i++){
			if (textDesc.get(i).getText().equals("")) pf.setDesc(i, "-");
			else pf.setDesc(i, textDesc.get(i).getText());
		}

		btnSave.setEnabled(true);

	}
	// 	2.2.5 Add Rows --------------------------------------------------------------------------------------------------------------
	public void addRow(){
		//if participant >= controls, make one new controls
		if (textName.size() <= pl.size()){
			textName.add(new Text(composite_9, SWT.BORDER));
			textName.get(textName.size()-1).setBounds(10, 23*(textName.size()-1) + 30, 170, 20);
			toolkit.adapt(textName.get(textName.size()-1), true, true);

			textID.add(new Text(composite_9, SWT.BORDER));
			textID.get(textID.size()-1).setBounds(190, 23*(textID.size()-1) + 30, 90, 20);
			toolkit.adapt(textID.get(textID.size()-1), true, true);

			textContact.add(new Text(composite_9, SWT.BORDER));
			textContact.get(textContact.size()-1).setBounds(290, 23*(textContact.size()-1) + 30, 90, 20);
			toolkit.adapt(textContact.get(textContact.size()-1), true, true);

			textEmail.add(new Text(composite_9, SWT.BORDER));
			textEmail.get(textEmail.size()-1).setBounds(390, 23*(textEmail.size()-1) + 30, 180, 20);
			toolkit.adapt(textEmail.get(textEmail.size()-1), true, true);

			checkPaid.add(new Button(composite_9, SWT.CHECK));
			checkPaid.get(checkPaid.size()-1).setBounds(588,23*(checkPaid.size()-1) + 30, 16, 15);
			toolkit.adapt(checkPaid.get(checkPaid.size()-1), true, true);

			checkParticipant.add(new Button(composite_9, SWT.CHECK));
			checkParticipant.get(checkParticipant.size()-1).setBounds(633,23*(checkParticipant.size()-1) + 30, 16, 15);
			toolkit.adapt(checkParticipant.get(checkParticipant.size()-1), true, true);
		}
		//else make the next text boxes reappear
		else{
			textName.get(pl.size()).setVisible(true);
			textID.get(pl.size()).setVisible(true);
			textContact.get(pl.size()).setVisible(true);
			textEmail.get(pl.size()).setVisible(true);
			checkPaid.get(pl.size()).setVisible(true);
			checkPaid.get(pl.size()).setSelection(false);
			checkParticipant.get(pl.size()).setVisible(true);
		}
	}

	//	2.2.6 Update Participant List ------------------------------------------------------------------------------------------------
	public void updateParticipantListUI(){
		int i;

		//if participant > controls, make new controls
		for (i = textName.size() ; i < pl.size() ; i++){
			textName.add(new Text(composite_9, SWT.BORDER));
			textName.get(textName.size()-1).setBounds(10, 23*(textName.size()-1) + 30, 170, 20);
			toolkit.adapt(textName.get(textName.size()-1), true, true);

			textID.add(new Text(composite_9, SWT.BORDER));
			textID.get(textID.size()-1).setBounds(190, 23*(textID.size()-1) + 30, 90, 20);
			toolkit.adapt(textID.get(textID.size()-1), true, true);

			textContact.add(new Text(composite_9, SWT.BORDER));
			textContact.get(textContact.size()-1).setBounds(290, 23*(textContact.size()-1) + 30, 90, 20);
			toolkit.adapt(textContact.get(textContact.size()-1), true, true);

			textEmail.add(new Text(composite_9, SWT.BORDER));
			textEmail.get(textEmail.size()-1).setBounds(390, 23*(textEmail.size()-1) + 30, 180, 20);
			toolkit.adapt(textEmail.get(textEmail.size()-1), true, true);

			checkPaid.add(new Button(composite_9, SWT.CHECK));
			checkPaid.get(checkPaid.size()-1).setBounds(588,23*(checkPaid.size()-1) + 30, 16, 15);
			toolkit.adapt(checkPaid.get(checkPaid.size()-1), true, true);

			checkParticipant.add(new Button(composite_9, SWT.CHECK));
			checkParticipant.get(checkParticipant.size()-1).setBounds(633,23*(checkParticipant.size()-1) + 30, 16, 15);
			toolkit.adapt(checkParticipant.get(checkParticipant.size()-1), true, true);
		}

		//update content of control
		for (i = 0 ; i < pl.size() ; i++){
			textName.get(i).setText(pl.getName(i));
			textName.get(i).setVisible(true);
			textID.get(i).setText(pl.getID(i));
			textID.get(i).setVisible(true);
			textContact.get(i).setText(pl.getContact(i));
			textContact.get(i).setVisible(true);
			textEmail.get(i).setText(pl.getEmail(i));
			textEmail.get(i).setVisible(true);
			checkPaid.get(i).setVisible(true);
			checkPaid.get(i).setSelection(pl.getPaid(i));
			checkParticipant.get(i).setVisible(true);
		}
		//make the remaining controls invisible
		for(i = pl.size() ; i < textName.size(); i++) {
			textName.get(i).setText("");
			textName.get(i).setVisible(false);
			textID.get(i).setVisible(false);
			textID.get(i).setText("");
			textContact.get(i).setVisible(false);
			textContact.get(i).setText("");
			textEmail.get(i).setVisible(false);
			textEmail.get(i).setText("");
			checkPaid.get(i).setVisible(false);
			checkParticipant.get(i).setVisible(false);
		}

		pl.calculateIncome();
		outParticipantPrice.setText(pl.printPrice());
		outRevenue.setText(pl.printTotalPrice());
	}

	//	2.2.7 Update Participant List (Cache) --------------------------------------------------------------------------------------------------
	public void updatePL(){
		int i;
		//erase content of pl
		pl.clear();
		//copy contents of textboxes into pl
		for (i = 0 ; i < textName.size() ; i++){
			if (textName.get(i).getVisible())
				pl.add(new Participant(textName.get(i).getText(), textID.get(i).getText(), textContact.get(i).getText(), textEmail.get(i).getText(), checkPaid.get(i).getSelection()));
		}

		pl.calculateIncome();
		outParticipantPrice.setText(pl.printPrice());
		outRevenue.setText(pl.printTotalPrice());
		btnSave.setEnabled(true);
	}
	//	2.2.8 Update Event List ----------------------------------------------------------------------------------------------------------------
	public void updateEventList(){
		comboEventList.removeAll();
		int i;
		for (i = 0 ; i < el.size(); i++)
			comboEventList.add(el.get(i).getName());
	}


	// 2.2.9 Update Booked UI-------------------------------------------------------------------------------------------------------------------
	public void updateBookedUI(){
		int i;

		//if participant > controls, make new controls
		for (i = lblBookedVenue.size() ; i < bookvl.size() ; i++){
			lblBookedVenue.add(new Label(compoBooked, SWT.NONE));
			lblBookedVenue.get(lblBookedVenue.size()-1).setBounds(10, 21*(lblBookedVenue.size()-1) + 10, 55, 15);
			toolkit.adapt(lblBookedVenue.get(lblBookedVenue.size()-1), true, true);

			lblBookedDate.add(new Label(compoBooked, SWT.NONE));
			lblBookedDate.get(lblBookedDate.size()-1).setBounds(88, 21*(lblBookedDate.size()-1) + 10, 55, 15);
			toolkit.adapt(lblBookedDate.get(lblBookedDate.size()-1), true, true);

			lblBookedTimeStart.add(new Label(compoBooked, SWT.NONE));
			lblBookedTimeStart.get(lblBookedTimeStart.size()-1).setBounds(158, 21*(lblBookedTimeStart.size()-1) + 10, 30, 15);
			toolkit.adapt(lblBookedTimeStart.get(lblBookedTimeStart.size()-1), true, true);

			lblTo.add(new Label(compoBooked, SWT.NONE));
			lblTo.get(lblTo.size()-1).setBounds(190, 21*(lblTo.size()-1) + 10, 15, 15);
			toolkit.adapt(lblTo.get(lblTo.size()-1), true, true);
			lblTo.get(lblTo.size()-1).setText("to");

			lblBookedTimeEnd.add(new Label(compoBooked, SWT.NONE));
			lblBookedTimeEnd.get(lblBookedTimeEnd.size()-1).setBounds(206, 21*(lblBookedTimeEnd.size()-1) + 10, 30, 15);
			toolkit.adapt(lblBookedTimeEnd.get(lblBookedTimeEnd.size()-1), true, true);

			lblBookedPrice.add(new Label(compoBooked, SWT.NONE));
			lblBookedPrice.get(lblBookedPrice.size()-1).setBounds(245, 21*(lblBookedPrice.size()-1) + 10, 55, 15);
			toolkit.adapt(lblBookedPrice.get(lblBookedPrice.size()-1), true, true);

			checkBookedVenue.add(new Button(compoBooked, SWT.CHECK));
			checkBookedVenue.get(checkBookedVenue.size()-1).setBounds(301, 21*(checkBookedVenue.size()-1) + 10, 55, 15);
			toolkit.adapt(checkBookedVenue.get(checkBookedVenue.size()-1), true, true);


		}

		//update content of control
		for (i = 0 ; i < bookvl.size() ; i++){
			lblBookedVenue.get(i).setText(bookvl.get(i).getName());
			lblBookedVenue.get(i).setVisible(true);
			String day = Integer.toString(bookvl.get(i).getDate()/1000000);
			String month = Integer.toString((bookvl.get(i).getDate()/10000%100));
			String year = Integer.toString(bookvl.get(i).getDate()%10000);
			lblBookedDate.get(i).setText("" + day + "-" + month + "-" + year);
			lblBookedDate.get(i).setVisible(true);
			lblBookedTimeStart.get(i).setText(Integer.toString(bookvl.get(i).getTimeStart())+":00");
			lblBookedTimeStart.get(i).setVisible(true);
			lblTo.get(i).setVisible(true);
			lblBookedTimeEnd.get(i).setText(Integer.toString(bookvl.get(i).getTimeEnd())+":00");
			lblBookedTimeEnd.get(i).setVisible(true);
			lblBookedPrice.get(i).setText("$" + Double.toString(bookvl.get(i).getPrice()));
			lblBookedPrice.get(i).setVisible(true);
			checkBookedVenue.get(i).setSelection(false);
			checkBookedVenue.get(i).setVisible(true);

		}
		//make the remaining controls invisible
		for(i = bookvl.size() ; i < lblBookedVenue.size(); i++) {
			lblBookedVenue.get(i).setText("");
			lblBookedVenue.get(i).setVisible(false);
			lblBookedDate.get(i).setText("");
			lblBookedDate.get(i).setVisible(false);
			lblBookedTimeStart.get(i).setText("");
			lblBookedTimeStart.get(i).setVisible(false);
			lblTo.get(i).setVisible(false);
			lblBookedTimeEnd.get(i).setText("");
			lblBookedTimeEnd.get(i).setVisible(false);
			lblBookedPrice.get(i).setText("");
			lblBookedPrice.get(i).setVisible(false);
			checkBookedVenue.get(i).setSelection(false);
			checkBookedVenue.get(i).setVisible(false);
		}

	}

	// 2.2.10 Clear Venue UI-----------------------------------------------------------------------------------------------------------------------

	public void clearVenueUI() {
		for(int j=0; j<lblAvailableVenue.size() ;j++){
			lblAvailableVenue.get(j).setVisible(false);
			lblAvailableVenue.get(j).setText("");
			lblVenuePrice.get(j).setVisible(false);
			lblVenuePrice.get(j).setText("");
			checkAvailableVenue.get(j).setSelection(false);
			checkAvailableVenue.get(j).setVisible(false);
		}
	}
	//	2.3 startUp() *****************************************************************************************************************
	public void startup(){
		//Read file Event List
		ReadFile.readEventList(el);
		updateEventList();

		//Read file Venue List
		ReadFile.readVenueList(vl);

		//Read the timeslots
		for(int i = 0 ; i < vl.size() ; i++)
			ReadFile.readVenueTimeSlot(vl.get(i));

		//Update VenueList UI array lists
		for (int i = 0 ; i < vl.size(); i++){
			lblAvailableVenue.add(new Label(compoAvailable, SWT.NONE));
			lblAvailableVenue.get(lblAvailableVenue.size()-1).setBounds(10, 10 + 20*(lblAvailableVenue.size()-1), 60, 15);
			toolkit.adapt(lblAvailableVenue.get(lblAvailableVenue.size()-1), true, true);

			lblVenuePrice.add(new Label(compoAvailable, SWT.NONE));
			lblVenuePrice.get(lblVenuePrice.size()-1).setBounds(100, 10 + 20*(lblVenuePrice.size()-1), 50, 15);
			toolkit.adapt(lblVenuePrice.get(lblVenuePrice.size()-1), true, true);

			checkAvailableVenue.add(new Button(compoAvailable, SWT.CHECK));
			checkAvailableVenue.get(checkAvailableVenue.size()-1).setBounds(160, 10 + 20*(checkAvailableVenue.size()-1), 16, 15);
			toolkit.adapt(checkAvailableVenue.get(checkAvailableVenue.size()-1), true, true);
			checkAvailableVenue.get(checkAvailableVenue.size()-1).setVisible(false);
		}
	}



	//	3 GENERAL COMPONENT OF BACKGROUND ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public background(Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(171, 0, 619, 510);
		toolkit.adapt(tabFolder);
		toolkit.paintBordersFor(tabFolder);

		//	4 EVENT MANAGEMENT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		Composite composite = new Composite(this, SWT.NONE);
		//		composite.setForeground(SWTResourceManager.getColor(0, 0, 0));
		//		composite.setBackground(SWTResourceManager.getColor(255, 255, 255));
		composite.setBounds(10, 10, 155, 500);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);

		Label lblYouAreCurrently = new Label(composite, SWT.WRAP);
		lblYouAreCurrently.setBounds(10, 10, 135, 30);
		toolkit.adapt(lblYouAreCurrently, true, true);
		lblYouAreCurrently.setText("You are currently managing:");

		lblCurrentEvent = new Label(composite, SWT.NONE);
		lblCurrentEvent.setBounds(10, 46, 135, 15);
		toolkit.adapt(lblCurrentEvent, true, true);
		lblCurrentEvent.setText("None");

		comboEventList = new Combo(composite, SWT.NONE);
		comboEventList.setBounds(10, 240, 135, 23);
		toolkit.adapt(comboEventList);
		toolkit.paintBordersFor(comboEventList);

		//	4.1 Save ***********************************************************************************************************************
		btnSave = new Button(composite, SWT.NONE);
		btnSave.setEnabled(false);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//update cache for participants' list and program flow description
				updatePL();
				updateDescription();

				//write to files
				EventManager.eventWrite(lblCurrentEvent.getText(), fl, il, pf, pl, bookvl, vl);
				/*	WriteFile.writeFile(fl, lblCurrentEvent.getText() + "FL.txt");
				WriteFile.writeFile(il, lblCurrentEvent.getText() + "IL.txt");
				WriteFile.writeFile(pf, lblCurrentEvent.getText() + "PF.txt");
				WriteFile.writeFile(pl, lblCurrentEvent.getText() + "PL.txt"); */
				//WriteFile.writeVenueList(vl);

				btnSave.setEnabled(false);
			}
		});
		btnSave.setBounds(70, 85, 75, 25);
		toolkit.adapt(btnSave, true, true);
		btnSave.setText("SAVE");

		textNewEvent = new Text(composite, SWT.BORDER);
		textNewEvent.setBounds(10, 417, 135, 21);
		toolkit.adapt(textNewEvent, true, true);

		Button btnNewEvent = new Button(composite, SWT.NONE);
		btnNewEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//make event only if user writes event name in text box
				if(!textNewEvent.getText().equals("")){

					//check if an event with the same name already existed
					boolean exist = false;
					for (int i = 0 ; i < el.size() ; i++) {
						if (el.get(i).getName().equals(textNewEvent.getText())) {	
							lblEventWarning.setText("Event already existed.");
							exist = true;
						}
					}

					if (!exist){
						//create the event
						EventManager.eventCreator(textNewEvent.getText(), el);

						//update UI
						comboEventList.add(textNewEvent.getText());
						comboEventList.setText(textNewEvent.getText());
						textNewEvent.setText("");
						lblEventWarning.setText("");
					}
				}

			}
		});
		btnNewEvent.setBounds(10, 444, 75, 25);
		toolkit.adapt(btnNewEvent, true, true);
		btnNewEvent.setText("Create Event");


		//	4.3 Switch Event *****************************************************************************************************************	

		Button btnSwitchEvent = new Button(composite, SWT.NONE);
		btnSwitchEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//switch event only if that event exists
				boolean exist = false;
				for (int i = 0 ; i < el.size() ; i++) {
					if (comboEventList.getText().equals(el.get(i).getName()))
						exist = true;
				}

				if(exist){
					//pop up dialog open
					if (btnSave.isEnabled()){
						final Shell dialogShell = new Shell(getDisplay());
						dialogShell.setText("Save unsaved updates?");
						dialogShell.setSize(400, 150);

						dialogShell.open();

						final Button buttonOK = new Button(dialogShell, SWT.PUSH);
						buttonOK.setText("Save");
						buttonOK.setBounds(70, 55, 80, 25);

						Button buttonCancel = new Button(dialogShell, SWT.PUSH);
						buttonCancel.setText("No");
						buttonCancel.setBounds(220, 55, 80, 25);

						final Label dlabel = new Label(dialogShell, SWT.NONE);
						dlabel.setText("Save unsaved updates for " + lblCurrentEvent.getText() + " ?");
						dlabel.setBounds(20, 15, 300, 20);

						Listener listener = new Listener() {
							public void handleEvent(Event event) {
								if (event.widget == buttonOK) {
									//update cache for participants' list and program flow description
									updatePL();
									updateDescription();

									//write to files
									EventManager.eventWrite(lblCurrentEvent.getText(), fl, il, pf, pl, bookvl, vl);
									//WriteFile.writeVenueList(vl);


									btnSave.setEnabled(false);
								} else {
								}
								dialogShell.close();
							}
						};

						buttonOK.addListener(SWT.Selection, listener);
						buttonCancel.addListener(SWT.Selection, listener);

						while (!dialogShell.isDisposed()) {
							if (!getDisplay().readAndDispatch())
								getDisplay().sleep();
						}
					}				

					//load data base to cache
					EventManager.eventSwitch(comboEventList.getText(), fl, il, pf, pl, bookvl);

					//update UI
					lblCurrentEvent.setText(comboEventList.getText());
					updateFundingUI();
					updateExpenditureUI();
					updateProgramFlowUI();
					updateParticipantListUI();
					updateBookedUI();

					clearVenueUI();

					btnSave.setEnabled(false);
				}
			}

		});
		btnSwitchEvent.setBounds(10, 300, 75, 25);
		toolkit.adapt(btnSwitchEvent, true, true);
		btnSwitchEvent.setText("Switch Event");



		//	4.4 Delete Event *******************************************************************************************************************
		Button btnDeleteEvent = new Button(composite, SWT.NONE);
		btnDeleteEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//delete event only if that event is in event list
				boolean exist = false;
				for (int i = 0 ; i < el.size() ; i++) {
					if (comboEventList.getText().equals(el.get(i).getName()))
						exist = true;
				}

				if (exist) {

					final Shell dialogShell = new Shell(getDisplay());
					dialogShell.setText("Delete?");
					dialogShell.setSize(400, 150);

					dialogShell.open();

					final Button buttonOK = new Button(dialogShell, SWT.PUSH);
					buttonOK.setText("Delete");
					buttonOK.setBounds(70, 55, 80, 25);

					Button buttonCancel = new Button(dialogShell, SWT.PUSH);
					buttonCancel.setText("No");
					buttonCancel.setBounds(220, 55, 80, 25);

					final Label dlabel = new Label(dialogShell, SWT.NONE);
					dlabel.setText("Are you sure you want to delete " + comboEventList.getText()  +"?");
					dlabel.setBounds(20, 15, 300, 20);

					Listener listener = new Listener() {
						public void handleEvent(Event event) {
							if (event.widget == buttonOK) {

								//delete event in database
								EventManager.eventDelete(comboEventList.getText(), el, vl);

								//if event deleted is the one the user is currently managing
								if (lblCurrentEvent.getText().equals(comboEventList.getText())){
									//empty all lists
									fl.clear();
									il.clear();
									pf.clear();
									pl.clear();
									bookvl.clear();

									//update all labels
									updateFundingUI();
									updateExpenditureUI();
									updateProgramFlowUI();
									updateParticipantListUI();
									updateBookedUI();
									clearVenueUI();
									lblCurrentEvent.setText("None");

									btnSave.setEnabled(false);
								}

								//delete event in event list cache and UI
								updateEventList(); 
							}
							dialogShell.close();
						}
					};

					buttonOK.addListener(SWT.Selection, listener);
					buttonCancel.addListener(SWT.Selection, listener);

					while (!dialogShell.isDisposed()) {
						if (!getDisplay().readAndDispatch())
							getDisplay().sleep();
					}

				}

			}
		});
		btnDeleteEvent.setBounds(10, 269, 75, 25);
		toolkit.adapt(btnDeleteEvent, true, true);
		btnDeleteEvent.setText("Delete Event");

		Label lblNoteSwitchingEvent = new Label(composite, SWT.WRAP);
		lblNoteSwitchingEvent.setBounds(10, 331, 135, 54);
		toolkit.adapt(lblNoteSwitchingEvent, true, true);
		lblNoteSwitchingEvent.setText("When switching event, current progress is not automatically saved");

		//	4.2	Create Event ***************************************************************************************************************	

		lblEventWarning = new Label(composite, SWT.NONE);
		lblEventWarning.setBounds(10, 475, 135, 15);
		toolkit.adapt(lblEventWarning, true, true);





		//	5 BUDGET TAB ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//	5.1 All composites and basic labels ***************************************************************************

		TabItem tbtmBudget = new TabItem(tabFolder, SWT.NONE);
		tbtmBudget.setText("Budget");

		composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmBudget.setControl(composite_1);
		toolkit.paintBordersFor(composite_1);

		Label lblBudget = new Label(composite_1, SWT.NONE);
		lblBudget.setBounds(10, 10, 55, 15);
		toolkit.adapt(lblBudget, true, true);
		lblBudget.setText("Budget");

		Label lblExpenditure = new Label(composite_1, SWT.NONE);
		lblExpenditure.setBounds(10, 193, 62, 15);
		toolkit.adapt(lblExpenditure, true, true);
		lblExpenditure.setText("Expenditure");

		scrolledComposite = new ScrolledComposite(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setMinWidth(400);
		scrolledComposite.setMinHeight(400);
		scrolledComposite.setBounds(10, 31, 591, 85);
		toolkit.adapt(scrolledComposite);
		toolkit.paintBordersFor(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		composite_4 = new Composite(scrolledComposite, SWT.NONE);
		toolkit.adapt(composite_4);
		toolkit.paintBordersFor(composite_4);
		scrolledComposite.setContent(composite_4);
		//scrolledComposite.setMinSize(composite_4.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label lblFrom = new Label(composite_1, SWT.NONE);
		lblFrom.setBounds(92, 125, 26, 15);
		toolkit.adapt(lblFrom, true, true);
		lblFrom.setText("from");

		Label lblTotalBudget = new Label(composite_1, SWT.NONE);
		lblTotalBudget.setAlignment(SWT.RIGHT);
		lblTotalBudget.setBounds(413, 153, 99, 15);
		toolkit.adapt(lblTotalBudget, true, true);
		lblTotalBudget.setText("Total Budget:");

		scrolledComposite_2 = new ScrolledComposite(composite_1, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_2.setMinHeight(2000);
		scrolledComposite_2.setMinWidth(400);
		scrolledComposite_2.setBounds(10, 246, 591, 143);
		toolkit.adapt(scrolledComposite_2);
		toolkit.paintBordersFor(scrolledComposite_2);
		scrolledComposite_2.setExpandHorizontal(true);
		scrolledComposite_2.setExpandVertical(true);

		composite_6 = new Composite(scrolledComposite_2, SWT.NONE);
		toolkit.adapt(composite_6);
		toolkit.paintBordersFor(composite_6);
		scrolledComposite_2.setContent(composite_6);
		//scrolledComposite_2.setMinSize(composite_6.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label lblItem = new Label(composite_1, SWT.NONE);
		lblItem.setBounds(180, 225, 55, 15);
		toolkit.adapt(lblItem, true, true);
		lblItem.setText("Item");

		Label lblPrice = new Label(composite_1, SWT.NONE);
		lblPrice.setBounds(331, 225, 39, 15);
		toolkit.adapt(lblPrice, true, true);
		lblPrice.setText("Price");

		Label lblQty = new Label(composite_1, SWT.NONE);
		lblQty.setBounds(389, 225, 62, 15);
		toolkit.adapt(lblQty, true, true);
		lblQty.setText("Qty");

		Label lblTotal = new Label(composite_1, SWT.NONE);
		lblTotal.setBounds(457, 225, 62, 15);
		toolkit.adapt(lblTotal, true, true);
		lblTotal.setText("Total Price");

		Label lblTotalExpenditure = new Label(composite_1, SWT.NONE);
		lblTotalExpenditure.setAlignment(SWT.RIGHT);
		lblTotalExpenditure.setBounds(403, 395, 109, 15);
		toolkit.adapt(lblTotalExpenditure, true, true);
		lblTotalExpenditure.setText("Total Expenditure:");

		Label lblNet = new Label(composite_1, SWT.NONE);
		lblNet.setAlignment(SWT.RIGHT);
		lblNet.setBounds(413, 428, 99, 15);
		toolkit.adapt(lblNet, true, true);
		lblNet.setText("Budget Left:");

		Label lblUnder = new Label(composite_1, SWT.NONE);
		lblUnder.setBounds(151, 454, 39, 15);
		toolkit.adapt(lblUnder, true, true);
		lblUnder.setText("under");

		Label lblActivityBudget = new Label(composite_1, SWT.NONE);
		lblActivityBudget.setBounds(22, 225, 55, 15);
		toolkit.adapt(lblActivityBudget, true, true);
		lblActivityBudget.setText("Activity");


		//	5.2 Funding UI *************************************************************************************************

		inAmount = new Text(composite_1, SWT.BORDER);
		inAmount.setBounds(10, 122, 76, 21);
		toolkit.adapt(inAmount, true, true);

		inSponsor = new Text(composite_1, SWT.BORDER);
		inSponsor.setText("sponsor");
		inSponsor.setBounds(125, 122, 272, 21);
		toolkit.adapt(inSponsor, true, true);

		outTotalFunding = new Label(composite_1, SWT.NONE);
		outTotalFunding.setBounds(518, 153, 55, 15);
		toolkit.adapt(outTotalFunding, true, true);
		outTotalFunding.setText("$0.00");



		//	5.2.1 Add Funding ------------------------------------------------------------------------------------------

		inAmount.addVerifyListener(this);
		btnAddBudget = new Button(composite_1, SWT.NONE);
		btnAddBudget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(!inAmount.getText().equals("") || !inSponsor.getText().equals(""))
					{
						//UPDATE FUNDING LIST
						fl.add(new Funding(inAmount.getText(), inSponsor.getText()));
						fl.recalcAmounts();

						//UPDATE UI
						updateFundingUI();
						updateExpenditureUI();

						btnSave.setEnabled(true);
					}

					//clear error message
					inAmountErrLabel.setText("");
				}
				catch (Exception x){
					//print error message
					showNumberFormatFail(inAmountErrLabel, "Amount");
				}
			}
		});
		btnAddBudget.setBounds(403, 120, 89, 25);
		toolkit.adapt(btnAddBudget, true, true);
		btnAddBudget.setText("Add to Budget");


		//	5.2.2 Delete Funding --------------------------------------------------------------------------------

		btnDelFunding = new Button(composite_1, SWT.NONE);
		btnDelFunding.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				int i;
				int temp = fl.size();

				//UPDATE FUNDING LIST
				//delete all the funding that are checked
				for (i = temp-1 ; i >= 0 ; i--)
					if (checkFunding.get(i).getSelection())
					{
						fl.remove(i);
						checkFunding.get(i).setSelection(false);
					}
				//update total funding
				fl.recalcAmounts();

				//UPDATE UI
				updateFundingUI();

				btnSave.setEnabled(true);

			}
		});
		btnDelFunding.setBounds(498, 120, 75, 25);
		toolkit.adapt(btnDelFunding, true, true);
		btnDelFunding.setText("Delete");


		//	5.3 Expenditure UI ******************************************************************************************************

		inItem = new Text(composite_1, SWT.BORDER);
		inItem.setText("item");
		inItem.setBounds(10, 451, 135, 21);
		toolkit.adapt(inItem, true, true);

		comboItem = new Combo(composite_1, SWT.NONE);
		comboItem.setBounds(196, 451, 201, 23);
		toolkit.adapt(comboItem);
		toolkit.paintBordersFor(comboItem);
		comboItem.add("Miscellaneous");

		outTotExpenditure = new Label(composite_1, SWT.NONE);
		outTotExpenditure.setText("$0.00");
		outTotExpenditure.setBounds(518, 395, 55, 15);
		toolkit.adapt(outTotExpenditure, true, true);

		outNetBudget = new Label(composite_1, SWT.NONE);
		outNetBudget.setText("$0.00");
		outNetBudget.setBounds(518, 428, 55, 15);
		toolkit.adapt(outNetBudget, true, true);



		//	5.3.1 Add Item ------------------------------------------------------------------------------------------------------

		Button btnAddItem = new Button(composite_1, SWT.NONE);
		btnAddItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//UPDATE ITEM LIST
				il.add(new Item(inItem.getText(), comboItem.getText()));
				//recalculate total Expenditure
				il.recalcAmounts();

				//UPDATE UI
				updateExpenditureUI();

				btnSave.setEnabled(true);
			}
		});
		btnAddItem.setBounds(417, 449, 75, 25);
		toolkit.adapt(btnAddItem, true, true);
		btnAddItem.setText("Add Item");


		//	5.3.2 Delete Item --------------------------------------------------------------------------------------------

		Button btnDelItem = new Button(composite_1, SWT.NONE);
		btnDelItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i;
				int temp = il.size();

				//UPDATE ITEM LIST
				//delete all items checked
				for (i = temp-1 ; i >= 0 ; i--)
					if (checkItem.get(i).getSelection())
					{
						il.remove(i);
						checkItem.get(i).setSelection(false);
					}
				//update total expenditure
				il.recalcAmounts();

				//UPDATE UI
				updateExpenditureUI();

				btnSave.setEnabled(true);
			}
		});
		btnDelItem.setBounds(498, 449, 75, 25);
		toolkit.adapt(btnDelItem, true, true);
		btnDelItem.setText("Delete");

		//update item list
		//update ui (display, expenditure, net budget)


		// 	5.3.3 Update Price and Quantity ------------------------------------------------------------------------------

		Button btnUpdateItem = new Button(composite_1, SWT.NONE);
		btnUpdateItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i;

				//UPDATE ITEM LIST
				//input price and quantity into the respective items and update total price
				try {
					for (i = 0 ; i < il.size() ; i++){
						il.setPrice(i, textPrice.get(i).getText());
						il.setQty(i, Integer.parseInt(textQty.get(i).getText()));
					}

					btnSave.setEnabled(true);
				}

				catch (Exception e1){
					//print error message
					showNumberFormatFail(priceErrLabel, "Price");
					return;
				}
				//update total Expenditure
				il.recalcAmounts();

				//UPDATE UI
				updateExpenditureUI();

				//clear error message
				priceErrLabel.setText("");


			}
		});
		btnUpdateItem.setBounds(322, 395, 75, 25);
		toolkit.adapt(btnUpdateItem, true, true);
		btnUpdateItem.setText("Update");

		inAmountErrLabel = new Label(composite_1, SWT.NONE);
		inAmountErrLabel.setBounds(10, 153, 397, 15);
		toolkit.adapt(inAmountErrLabel, true, true);

		priceErrLabel = new Label(composite_1, SWT.NONE);
		priceErrLabel.setBounds(10, 395, 297, 15);
		toolkit.adapt(priceErrLabel, true, true);

		Label lblNewLabel_2 = new Label(composite_1, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.RIGHT);
		lblNewLabel_2.setBounds(413, 412, 99, 15);
		toolkit.adapt(lblNewLabel_2, true, true);
		lblNewLabel_2.setText("From Venue:");

		outFromVenue = new Label(composite_1, SWT.NONE);
		outFromVenue.setBounds(518, 412, 55, 15);
		toolkit.adapt(outFromVenue, true, true);
		outFromVenue.setText("$0.00");


		//	6 VENUE TAB~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//	6.1 All composites and basic labels ***************************************************************************************

		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("Venue");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(composite_2);
		toolkit.paintBordersFor(composite_2);

		Label lblDateVenue = new Label(composite_2, SWT.NONE);
		lblDateVenue.setBounds(10, 14, 32, 15);
		toolkit.adapt(lblDateVenue, true, true);
		lblDateVenue.setText("Date");

		Label label = new Label(composite_2, SWT.NONE);
		label.setText("to");
		label.setBounds(142, 45, 21, 15);
		toolkit.adapt(label, true, true);

		Label lblTime_1 = new Label(composite_2, SWT.NONE);
		lblTime_1.setText("Time");
		lblTime_1.setBounds(10, 45, 32, 15);
		toolkit.adapt(lblTime_1, true, true);

		Label lblNewLabel = new Label(composite_2, SWT.NONE);
		lblNewLabel.setBounds(10, 90, 120, 15);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("Available Venues");

		Label lblVenue1 = new Label(composite_2, SWT.NONE);
		lblVenue1.setBounds(10, 111, 55, 15);
		toolkit.adapt(lblVenue1, true, true);
		lblVenue1.setText("Venue");

		Label lblPricePHr1 = new Label(composite_2, SWT.NONE);
		lblPricePHr1.setBounds(108, 111, 47, 15);
		toolkit.adapt(lblPricePHr1, true, true);
		lblPricePHr1.setText("Price/hr");


		scrolledCompAvailable = new ScrolledComposite(composite_2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompAvailable.setMinHeight(1000);
		scrolledCompAvailable.setBounds(10, 132, 221, 300);
		toolkit.adapt(scrolledCompAvailable);
		toolkit.paintBordersFor(scrolledCompAvailable);
		scrolledCompAvailable.setExpandHorizontal(true);
		scrolledCompAvailable.setExpandVertical(true);


		compoAvailable = new Composite(scrolledCompAvailable, SWT.NONE);
		toolkit.adapt(compoAvailable);
		toolkit.paintBordersFor(compoAvailable);
		scrolledCompAvailable.setContent(compoAvailable);
		//scrolledComposite_3.setMinSize(composite_7.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label lblBookedVenues = new Label(composite_2, SWT.NONE);
		lblBookedVenues.setBounds(253, 90, 86, 15);
		toolkit.adapt(lblBookedVenues, true, true);
		lblBookedVenues.setText("Booked Venues");

		Label labelVenue2 = new Label(composite_2, SWT.NONE);
		labelVenue2.setText("Venue");
		labelVenue2.setBounds(253, 111, 55, 15);
		toolkit.adapt(labelVenue2, true, true);

		Label lblTimeslot = new Label(composite_2, SWT.NONE);
		lblTimeslot.setText("Timeslot");
		lblTimeslot.setBounds(411, 111, 55, 15);
		toolkit.adapt(lblTimeslot, true, true);

		scrolledCompoBooked = new ScrolledComposite(composite_2, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledCompoBooked.setMinHeight(1000);
		scrolledCompoBooked.setBounds(253, 132, 348, 300);
		toolkit.adapt(scrolledCompoBooked);
		toolkit.paintBordersFor(scrolledCompoBooked);
		scrolledCompoBooked.setExpandHorizontal(true);
		scrolledCompoBooked.setExpandVertical(true);

		compoBooked = new Composite(scrolledCompoBooked, SWT.NONE);
		toolkit.adapt(compoBooked);
		scrolledCompoBooked.setContent(compoBooked);
		//		toolkit.paintBordersFor(compoBooked);


		Label lblDateBooked = new Label(composite_2, SWT.NONE);
		lblDateBooked.setText("Date");
		lblDateBooked.setBounds(357, 111, 32, 15);
		toolkit.adapt(lblDateBooked, true, true);



		//	6.3 Search Venue UI ********************************************************************************************************

		comboTimeStart = new Combo(composite_2, SWT.NONE);
		comboTimeStart.setBounds(50, 40, 86, 23);
		toolkit.adapt(comboTimeStart);
		toolkit.paintBordersFor(comboTimeStart);
		comboTimeStart.add("00:00"); comboTimeStart.add("01:00"); comboTimeStart.add("02:00"); comboTimeStart.add("03:00"); comboTimeStart.add("04:00");
		comboTimeStart.add("05:00"); comboTimeStart.add("06:00"); comboTimeStart.add("07:00"); comboTimeStart.add("08:00"); comboTimeStart.add("09:00");
		comboTimeStart.add("10:00"); comboTimeStart.add("11:00"); comboTimeStart.add("12:00"); comboTimeStart.add("13:00"); comboTimeStart.add("14:00");
		comboTimeStart.add("15:00"); comboTimeStart.add("16:00"); comboTimeStart.add("17:00"); comboTimeStart.add("18:00"); comboTimeStart.add("19:00");
		comboTimeStart.add("20:00"); comboTimeStart.add("21:00"); comboTimeStart.add("22:00"); comboTimeStart.add("23:00");

		comboTimeEnd = new Combo(composite_2, SWT.NONE);
		comboTimeEnd.setBounds(168, 40, 86, 23);
		toolkit.adapt(comboTimeEnd);
		toolkit.paintBordersFor(comboTimeEnd);
		comboTimeEnd.add("00:00"); comboTimeEnd.add("01:00"); comboTimeEnd.add("02:00"); comboTimeEnd.add("03:00"); comboTimeEnd.add("04:00");
		comboTimeEnd.add("05:00"); comboTimeEnd.add("06:00"); comboTimeEnd.add("07:00"); comboTimeEnd.add("08:00"); comboTimeEnd.add("09:00");
		comboTimeEnd.add("10:00"); comboTimeEnd.add("11:00"); comboTimeEnd.add("12:00"); comboTimeEnd.add("13:00"); comboTimeEnd.add("14:00");
		comboTimeEnd.add("15:00"); comboTimeEnd.add("16:00"); comboTimeEnd.add("17:00"); comboTimeEnd.add("18:00"); comboTimeEnd.add("19:00");
		comboTimeEnd.add("20:00"); comboTimeEnd.add("21:00"); comboTimeEnd.add("22:00"); comboTimeEnd.add("23:00");

		dateSearch = new DateTime(composite_2, SWT.DATE);
		dateSearch.setBounds(50, 10, 86, 24);
		toolkit.adapt(dateSearch);
		toolkit.paintBordersFor(dateSearch);

		Button btnSearchVenue = new Button(composite_2, SWT.NONE);
		/*	btnSearchVenue.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){

				for(int j=0; j<vl.size() ;j++){
					lblAvailableVenue.get(j).setVisible(false);
					lblAvailableVenue.get(j).setText("");
					lblVenuePrice.get(j).setVisible(false);
					lblVenuePrice.get(j).setText("");
					checkAvailableVenue.get(j).setSelection(false);
					checkAvailableVenue.get(j).setVisible(false);
				}

				int day = dateSearch.getDay();
				int month = dateSearch.getMonth() + 1;
				int year = dateSearch.getYear();
				date = day * 1000000 + month * 10000 + year;
				timestart = Integer.parseInt(comboTimeStart.getText().substring(0, 2));
				timeend = Integer.parseInt(comboTimeEnd.getText().substring(0, 2));

				for (int i=0;i<vl.size();i++){
					for (int j=0;j<vl.get(i).getTS().size();j++){
						if (vl.get(i).getTS().get(j).getDate() == date && vl.get(i).getTS().get(j).getHour() == timestart && !vl.get(i).getTS().get(j).isBooked()) {
							for (int k = timestart; k< timeend; k++){
								if(vl.get(i).getTS().get(j).getHour()==k && 
							}
						}
					}
				}

			}
		}); */


		btnSearchVenue.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int day = dateSearch.getDay();
					int month = dateSearch.getMonth() + 1;
					int year = dateSearch.getYear();
					date = day * 1000000 + month * 10000 + year;

					timestart = Integer.parseInt(comboTimeStart.getText().substring(0, 2));
					timeend = Integer.parseInt(comboTimeEnd.getText().substring(0, 2));
					if(timestart >= timeend)
						lblVenueTimeError.setText("Time start must be before time end");
					else{

						clearVenueUI();

						int index, indexTime;
						boolean isAvailable;

						int UIindex = 0;
						int i;

						for(i = 0 ; i < vl.size() ; i++){
							index = -1;
							for (int j = 0 ; j < vl.get(i).getTS().size() ; j++){
								if (vl.get(i).getTS().get(j).getDate() == date && vl.get(i).getTS().get(j).getHour() == timestart)
								{
									index = j;
									break;
								}
							}

							//labeltry.setText(Integer.toString(date));

							if (index != -1)
							{
								isAvailable = true;
								for (indexTime = timestart ; indexTime < timeend ; indexTime++){
									if (vl.get(i).getTS().get(index).isBooked())
									{
										isAvailable = false;
										break;
									}
									else
										index++;
								}

								if(isAvailable)
								{
									lblAvailableVenue.get(UIindex).setVisible(true);
									lblAvailableVenue.get(UIindex).setText(vl.get(i).getName());
									lblVenuePrice.get(UIindex).setVisible(true);
									lblVenuePrice.get(UIindex).setText(Double.toString(vl.get(i).getPrice()));
									checkAvailableVenue.get(UIindex).setSelection(false);
									checkAvailableVenue.get(UIindex).setVisible(true);
									UIindex++;
								}

							}

							lblVenueTimeError.setText("");
						}
					}
				}
				catch (Exception x){
					lblVenueTimeError.setText("Please specify both fields");
				}


			}
		});

		btnSearchVenue.setBounds(293, 40, 75, 25);
		toolkit.adapt(btnSearchVenue, true, true);
		btnSearchVenue.setText("Search");

		//	6.4 Venue Booking UI ***************************************************************************************************
		//	6.4.1 Book Venue ---------------------------------------------------------------------------------------------------------

		Button btnAdd = new Button(composite_2, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i;
				for(i = 0 ; i < checkAvailableVenue.size() ; i++){
					if ((checkAvailableVenue.get(i).getSelection())){

						//add new booking to bookinglist

						Booking toBeAdded = new Booking(lblAvailableVenue.get(i).getText(), (timeend-timestart)*Double.parseDouble(lblVenuePrice.get(i).getText()), date, timestart, timeend);

						if(bookvl.has(toBeAdded)==-1) {

							bookvl.add(toBeAdded);

							//set the venue timeslot as booked i.e.true
							for(int j = 0; j< vl.size(); j++)
							{
								for (int k=0; k < vl.get(i).getTS().size(); k++ )
								{
									if ((vl.get(j).getName() == lblAvailableVenue.get(i).getText()) && (vl.get(j).getTS().get(k).getDate() == date) && (vl.get(j).getTS().get(k).getHour() >= timestart && vl.get(j).getTS().get(k).getHour() < timeend))
									{
										vl.get(j).getTS().get(k).setBooked(true);
										System.out.println("DATAS SUPPOSEDLY CHANGE " + vl.get(j).getName() + " " + vl.get(j).getTS().get(k).getHour());
										System.out.println(vl.get(j).getTS().get(k).isBooked());
									}
								}
							}


						}
						//update bookedUI
						updateBookedUI();
						btnSave.setEnabled(true);
						updateExpenditureUI();

					}
				}

			}
		});
		btnAdd.setBounds(156, 438, 75, 25);
		toolkit.adapt(btnAdd, true, true);
		btnAdd.setText("Book");

		//	6.4.2 Delete Booked Venue ------------------------------------------------------------------------------------------------


		Label lblBookingWillAutomatically = new Label(composite_2, SWT.NONE);
		lblBookingWillAutomatically.setBounds(10, 465, 250, 15);
		toolkit.adapt(lblBookingWillAutomatically, true, true);
		lblBookingWillAutomatically.setText("Booking will automatically update net budget");

		labeltry = new Label(composite_2, SWT.NONE);
		labeltry.setBounds(411, 14, 55, 15);
		toolkit.adapt(labeltry, true, true);
		labeltry.setText("New Label");

		lblVenueTimeError = new Label(composite_2, SWT.NONE);
		lblVenueTimeError.setBounds(49, 69, 205, 15);
		toolkit.adapt(lblVenueTimeError, true, true);

		Button btnDeleteBooked = new Button(composite_2, SWT.NONE);
		btnDeleteBooked.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i;
				int temp = checkBookedVenue.size();
				for(i = temp-1 ; i>=0 ;i--){
					if ((checkBookedVenue.get(i).getSelection())){

						//create Booking to be deleted
						String name = lblBookedVenue.get(i).getText();
						String pr = lblBookedPrice.get(i).getText();
						double price = Double.parseDouble(pr.substring(1));
						String da = lblBookedDate.get(i).getText();						
						da=da.replace('-', ' ');
						Scanner sc = new Scanner(da);
						int day = sc.nextInt();
						int month = sc.nextInt();
						int year = sc.nextInt();
						int date = year + month*10000 + day*1000000;
						sc.close();
						int timeStart = Integer.parseInt((lblBookedTimeStart.get(i).getText()).substring(0,2));
						int timeEnd = Integer.parseInt((lblBookedTimeEnd.get(i).getText()).substring(0, 2));

						//						System.out.println("" + name + " " + price + " " + date + " " + timeStart + " " + timeEnd);

						Booking toBeDeleted = new Booking(name, price, date, timeStart, timeEnd);

						//						System.out.println("" + bookvl.has(toBeDeleted));



						if (bookvl.has(toBeDeleted)!=-1) {
							bookvl.remove(bookvl.has(toBeDeleted));

							for(int j=0; j<vl.size(); j++)
							{
								if(vl.get(j).getName().equals(name))
								{
									for(int k=0; k<vl.get(j).getTS().size(); k++)
									{
										if(vl.get(j).getTS().get(k).getDate() == date && vl.get(j).getTS().get(k).getHour() >= timeStart && vl.get(j).getTS().get(k).getHour() < timeEnd )
											vl.get(j).getTS().get(k).setBooked(false);					
									}
								}
							}							
						}

						btnSave.setEnabled(true);					
					}
				}
				updateBookedUI();
				updateExpenditureUI();
			}
		});
		btnDeleteBooked.setBounds(526, 438, 75, 25);
		toolkit.adapt(btnDeleteBooked, true, true);
		btnDeleteBooked.setText("Delete");

		Label lblNewLabel_3 = new Label(composite_2, SWT.NONE);
		lblNewLabel_3.setBounds(498, 111, 55, 15);
		toolkit.adapt(lblNewLabel_3, true, true);
		lblNewLabel_3.setText("Total Price");


		//	7 PROGRAM FLOW TAB~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//	7.1 All composites and basic labels **********************************************************************************************

		TabItem tbtmProgram = new TabItem(tabFolder, SWT.NONE);
		tbtmProgram.setText("Program");

		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmProgram.setControl(composite_3);
		toolkit.paintBordersFor(composite_3);

		Label lblTime = new Label(composite_3, SWT.NONE);
		lblTime.setBounds(91, 10, 27, 15);
		toolkit.adapt(lblTime, true, true);
		lblTime.setText("Time");

		Label lblActivity = new Label(composite_3, SWT.NONE);
		lblActivity.setBounds(156, 10, 55, 15);
		toolkit.adapt(lblActivity, true, true);
		lblActivity.setText("Activity");

		Label lblDescription = new Label(composite_3, SWT.NONE);
		lblDescription.setBounds(300, 10, 76, 15);
		toolkit.adapt(lblDescription, true, true);
		lblDescription.setText("Description");

		scrolledComposite_1 = new ScrolledComposite(composite_3, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_1.setMinWidth(400);
		scrolledComposite_1.setMinHeight(5000);
		scrolledComposite_1.setBounds(10, 31, 591, 270);
		toolkit.adapt(scrolledComposite_1);
		toolkit.paintBordersFor(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);

		composite_5 = new Composite(scrolledComposite_1, SWT.NONE);
		toolkit.adapt(composite_5);
		toolkit.paintBordersFor(composite_5);
		scrolledComposite_1.setContent(composite_5);
		//scrolledComposite_1.setMinSize(composite_5.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label lblTo = new Label(composite_3, SWT.NONE);
		lblTo.setBounds(101, 383, 17, 15);
		toolkit.adapt(lblTo, true, true);
		lblTo.setText("to");

		inActivity = new Text(composite_3, SWT.BORDER);
		inActivity.setText("activity");
		inActivity.setBounds(210, 380, 210, 21);
		toolkit.adapt(inActivity, true, true);

		timeStartActivity = new DateTime(composite_3, SWT.TIME);
		timeStartActivity.setBounds(10, 380, 85, 24);
		toolkit.adapt(timeStartActivity);
		toolkit.paintBordersFor(timeStartActivity);

		timeEndActivity = new DateTime(composite_3, SWT.TIME);
		timeEndActivity.setBounds(119, 380, 85, 24);
		toolkit.adapt(timeEndActivity);
		toolkit.paintBordersFor(timeEndActivity);



		//	7.2.1 Add Activity -----------------------------------------------------------------------------------------

		btnAddActivity = new Button(composite_3, SWT.NONE);
		btnAddActivity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!inActivity.getText().equals(""))
				{
					//update description of all activities first
					updateDescription();

					//UPDATE PROGRAM FLOW
					int timeStart, timeEnd, day, month, year;
					timeStart = timeStartActivity.getHours()*100 + timeStartActivity.getMinutes();
					timeEnd = timeEndActivity.getHours()*100 + timeEndActivity.getMinutes();
					if (timeEnd < timeStart) {
						lblTimeError.setText("End time cannot be earlier than start time!");
						return;
					}
					day = dateTime.getDay();
					month = dateTime.getMonth();
					year = dateTime.getYear();
					int date = day * 1000000 + month * 10000 + year;day = dateTime.getDay();

					pf.add(new Activity(inActivity.getText(), timeStart, timeEnd, date));
					//rearrange activities
					pf.arrangeByTime();

					//UPDATE UI
					updateProgramFlowUI();
					lblTimeError.setText("");

					btnSave.setEnabled(true);

				}
			}
		});
		btnAddActivity.setBounds(460, 380, 75, 25);
		toolkit.adapt(btnAddActivity, true, true);
		btnAddActivity.setText("Add Activity");


		//	7.2.2 Update Description ------------------------------------------------------------------------------------------

		Button btnUpdateDesc = new Button(composite_3, SWT.NONE);
		btnUpdateDesc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateDescription();
			}
		});
		btnUpdateDesc.setBounds(379, 314, 85, 25);
		toolkit.adapt(btnUpdateDesc, true, true);
		btnUpdateDesc.setText("Update Desc");


		//	7.2.3 Delete Activities --------------------------------------------------------------------------------------------

		Button btnDelActivity = new Button(composite_3, SWT.NONE);
		btnDelActivity.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//update description of all activities first
				updateDescription();

				int i, j;
				int temp = pf.size();
				int temp2 = il.size();

				//UPDATE PROGRAM FLOW AND ITEM LIST
				//delete all activities checked
				//delete items associated with that activity
				//delete the activity in comboItem budget tab
				for (i = temp-1 ; i >= 0 ; i--)
				{
					if (checkActivity.get(i).getSelection())
					{
						//delete items that has the activity to be deleted
						for (j = temp2-1 ; j >=0 ; j--)
						{
							if(j < il.size()) //this is to make sure that j does not go beyond size of item list
								if(il.getItemActivity(j).equals(pf.printActivity(i)))
									il.remove(j);
						}

						//delete the activity in comboItem in budget tab
						comboItem.remove(pf.printActivity(i));
						//ATTENTIONNNNNNNNNNNNNN
						//THERE ARE 2 WAYS OF REMOVING, ONE IS BY STRING, THE OTHER BY INDEX
						//IF THERE ARE 2 ACTIVITIES WITH THE SAME NAME, BUT DIFFERENT TIMING, THIS MAY CAUSE PROBLEM??

						//delete the activity
						pf.remove(i);
						checkActivity.get(i).setSelection(false);
					}
				}

				//UPDATE ACTIVITIES UI
				updateProgramFlowUI();

				//UPDATE EXPENDITURE UI
				updateExpenditureUI();

				btnSave.setEnabled(true);
			}
		});
		btnDelActivity.setBounds(470, 314, 96, 25);
		toolkit.adapt(btnDelActivity, true, true);
		btnDelActivity.setText("Delete Activities");

		Label lblDate = new Label(composite_3, SWT.NONE);
		lblDate.setBounds(24, 10, 55, 15);
		toolkit.adapt(lblDate, true, true);
		lblDate.setText("Date");

		dateTime = new DateTime(composite_3, SWT.NONE);
		dateTime.setBounds(119, 341, 86, 24);
		toolkit.adapt(dateTime);
		toolkit.paintBordersFor(dateTime);

		Label label_3 = new Label(composite_3, SWT.NONE);
		label_3.setText("Date");
		label_3.setBounds(63, 344, 32, 15);
		toolkit.adapt(label_3, true, true);

		lblTimeError = new Label(composite_3, SWT.NONE);
		lblTimeError.setBounds(10, 410, 410, 15);
		toolkit.adapt(lblTimeError, true, true);

		lblClash = new Label(composite_3, SWT.NONE);
		lblClash.setBounds(10, 431, 410, 15);
		toolkit.adapt(lblClash, true, true);


		//	8 PARTICIPANTS TAB ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		TabItem tbtmParticipants = new TabItem(tabFolder, SWT.NONE);
		tbtmParticipants.setText("Participants");

		Composite composite_8 = new Composite(tabFolder, SWT.NONE);
		tbtmParticipants.setControl(composite_8);
		toolkit.paintBordersFor(composite_8);

		ScrolledComposite scrolledComposite_5 = new ScrolledComposite(composite_8, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite_5.setMinHeight(4000);
		scrolledComposite_5.setMinWidth(750);
		scrolledComposite_5.setBounds(10, 100, 591, 285);
		toolkit.adapt(scrolledComposite_5);
		toolkit.paintBordersFor(scrolledComposite_5);
		scrolledComposite_5.setExpandHorizontal(true);
		scrolledComposite_5.setExpandVertical(true);

		composite_9 = new Composite(scrolledComposite_5, SWT.NONE);
		toolkit.adapt(composite_9);
		toolkit.paintBordersFor(composite_9);

		Label lblName = new Label(composite_9, SWT.NONE);
		lblName.setBounds(10, 10, 55, 15);
		toolkit.adapt(lblName, true, true);
		lblName.setText("Name");

		Label lblID = new Label(composite_9, SWT.NONE);
		lblID.setBounds(190, 10, 55, 15);
		toolkit.adapt(lblID, true, true);
		lblID.setText("ID");

		Label lblContact = new Label(composite_9, SWT.NONE);
		lblContact.setBounds(290, 10, 55, 15);
		toolkit.adapt(lblContact, true, true);
		lblContact.setText("Contact");

		Label lblEmail = new Label(composite_9, SWT.NONE);
		lblEmail.setBounds(390, 10, 55, 15);
		toolkit.adapt(lblEmail, true, true);
		lblEmail.setText("Email");
		scrolledComposite_5.setContent(composite_9);

		Label lblPaid = new Label(composite_9, SWT.NONE);
		lblPaid.setBounds(580, 10, 30, 15);
		toolkit.adapt(lblPaid, true, true);
		lblPaid.setText("Paid?");

		Label lblDeleteParticipant = new Label(composite_9, SWT.NONE);
		lblDeleteParticipant.setBounds(620, 10, 60, 15);
		toolkit.adapt(lblDeleteParticipant, true, true);
		lblDeleteParticipant.setText("Delete");

		Button btnAddRow = new Button(composite_8, SWT.NONE);
		btnAddRow.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updatePL();
				//add new textboxes
				addRow();
			}
		});
		btnAddRow.setBounds(526, 391, 75, 25);
		toolkit.adapt(btnAddRow, true, true);
		btnAddRow.setText("Add Row");

		Button btnDelParticipant = new Button(composite_8, SWT.NONE);
		btnDelParticipant.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updatePL();
				int i;
				int temp = pl.size();

				//UPDATE PARTICIPANT LIST
				//delete all the participants that are checked
				for (i = temp-1 ; i >= 0 ; i--)
					if (checkParticipant.get(i).getSelection())
					{
						pl.remove(i);
						checkParticipant.get(i).setSelection(false);
						checkPaid.get(i).setSelection(false);
					}

				//UPDATE UI
				updateParticipantListUI();

				btnSave.setEnabled(true);
			}
		});
		btnDelParticipant.setBounds(526, 422, 75, 25);
		toolkit.adapt(btnDelParticipant, true, true);
		btnDelParticipant.setText("Delete");

		Button btnUpdateParticipant = new Button(composite_8, SWT.NONE);
		btnUpdateParticipant.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updatePL();
				btnSave.setEnabled(true);
			}
		});
		btnUpdateParticipant.setBounds(445, 391, 75, 25);
		toolkit.adapt(btnUpdateParticipant, true, true);
		btnUpdateParticipant.setText("Update");

		inParticipantPrice = new Text(composite_8, SWT.BORDER);
		inParticipantPrice.setBounds(10, 73, 76, 21);
		toolkit.adapt(inParticipantPrice, true, true);
		inParticipantPrice.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent arg0) {
				char[] chars = arg0.text.toCharArray();
				for(int i=0;i<chars.length;i++) {
					if (!(('0' <= chars[i] && chars[i] <= '9') || chars[i]=='.')) {
						arg0.doit = false;
						return;
					}
				}
			}});

		Button btnSetParticipantPrice = new Button(composite_8, SWT.NONE);
		btnSetParticipantPrice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(!inParticipantPrice.equals(""))
					{
						//set price per person
						pl.setPrice(inParticipantPrice.getText());

						//update UI
						outParticipantPrice.setText(pl.printPrice());
						outRevenue.setText(pl.printTotalPrice());
					}

					//clear error message
					priceError.setText("");
				}
				catch (Exception x){
					//print error message
					showNumberFormatFail(priceError, "price per person");
				}
			}
		});
		btnSetParticipantPrice.setBounds(99, 69, 75, 25);
		toolkit.adapt(btnSetParticipantPrice, true, true);
		btnSetParticipantPrice.setText("Set price/pr");

		priceError = new Label(composite_8, SWT.NONE);
		priceError.setBounds(180, 73, 421, 15);
		toolkit.adapt(priceError, true, true);

		Label lblRevenue = new Label(composite_8, SWT.NONE);
		lblRevenue.setBounds(10, 10, 48, 15);
		toolkit.adapt(lblRevenue, true, true);
		lblRevenue.setText("Revenue:");

		outRevenue = new Label(composite_8, SWT.NONE);
		outRevenue.setBounds(64, 10, 55, 15);
		toolkit.adapt(outRevenue, true, true);

		Label lblNewLabel_1 = new Label(composite_8, SWT.NONE);
		lblNewLabel_1.setBounds(10, 31, 55, 15);
		toolkit.adapt(lblNewLabel_1, true, true);
		lblNewLabel_1.setText("Price/pr:");

		outParticipantPrice = new Label(composite_8, SWT.NONE);
		outParticipantPrice.setBounds(64, 31, 55, 15);
		toolkit.adapt(outParticipantPrice, true, true);


	}

	//	9 MAIN FUNCTION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static void main(String[]args){
		Display display = new Display();
		Shell shell = new Shell(display);
		background bgd = new background(shell, SWT.NONE);
		bgd.pack();
		shell.pack();
		shell.open();
		bgd.startup();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}
	}
}
