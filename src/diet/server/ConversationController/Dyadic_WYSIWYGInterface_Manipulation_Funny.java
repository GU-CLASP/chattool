 package diet.server.ConversationController;
 
 import diet.attribval.AttribVal;
 import diet.message.Message;
 import diet.message.MessageButtonPressFromClient;
 import diet.message.MessageChangeClientInterfaceProperties;
 import diet.message.MessageChatTextFromClient;
 import diet.message.MessageClientInterfaceEvent;
 import diet.message.MessageKeypressed;
 import diet.message.MessageTask;
 import diet.message.MessageWYSIWYGAppendText;
 import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
 import diet.message.MessageWYSIWYGDocumentSyncFromClientRemove;
 import diet.message.MessageWYSIWYGFloorChangeConfirm;
 import diet.message.MessageWYSIWYGFloorRequest;
 import diet.server.Conversation;
 import diet.server.ConversationController.WYSIWYGTube.Content.TubeFakeInsertedText;
 import diet.server.ConversationController.WYSIWYGTube.TubeIn_InsertingFakeText_Balloon_Haha;
 import diet.server.ConversationController.WYSIWYGTube.WYSIWYGTube;
 import diet.server.Participant;
 import java.awt.Dimension;
 import java.util.Vector;
 import javax.swing.text.MutableAttributeSet;
 
 
 
 
 
 
 
 
 
 
 public class Dyadic_WYSIWYGInterface_Manipulation_Funny
   extends Dyadic_WYSIWYGInterface_Manipulation
 {
   WYSIWYGTube wt = (WYSIWYGTube)new TubeIn_InsertingFakeText_Balloon_Haha(this);
 
 
 
 
   
   public static boolean showcCONGUI() {
     return true;
   }
 
 
   
   public Dyadic_WYSIWYGInterface_Manipulation_Funny(Conversation c) {
     super(c, 2, 6000);
   }
 
 
 
   
   public Dyadic_WYSIWYGInterface_Manipulation_Funny(Conversation c, int numberOfTracks, int durationOfTimeout) {
     super(c, 2, 6000);
   }
 
 
 
 
 
   
   public synchronized void processTaskMove(MessageTask mtm, Participant origin) {}
 
 
 
 
   
   public synchronized void processChatText(Participant sender, MessageChatTextFromClient mct) {
     super.processChatText(sender, mct);
   }
 
 
 
 
 
   
   public void processKeyPress(Participant sender, MessageKeypressed mkp) {}
 
 
 
 
   
   public void processWYSIWYGTextInserted(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
     this.wt.add(sender, mWYSIWYGkp);
   }
 
   
   public void processWYSIWYGTextInserted_AfterManipulationByQueue(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGkp) {
     this.saveOutputFromDocInserts_hashtableMOSTRECENTTEXT.putObject(sender, mWYSIWYGkp.getTextTyped());
 
     
     Vector<Participant> recipients = this.pp.getRecipients(sender);
     for (int i = 0; i < recipients.size(); i++) {
       Participant pRecipient = recipients.elementAt(i);
       int windowNumber = this.wysiwygtm.getTrackInWhichSendersTextIsDisplayedOnRecepientsScreen(sender, pRecipient);
       if (windowNumber < 0) windowNumber = 0; 
       MutableAttributeSet mas = this.sm.getStyleForRecipient(sender, pRecipient);
       sendWYSIWYGTextToClient(pRecipient, windowNumber, mWYSIWYGkp.getTextTyped(), mas, sender.getUsername());
     } 
 
     
     String subdialogueID = "UNSET";
     try {
       subdialogueID = this.pp.getSubdialogueID(sender);
     } catch (Exception e) {
       e.printStackTrace();
     } 
     this.wysiwygRTFDI.processAppendedDocumentInsert(sender, mWYSIWYGkp);
   }
 
 
 
 
 
   
   public void processWYSIWYGTextInserted_AfterManipulationByQueue(TubeFakeInsertedText wfi) {
     Vector<Participant> recipients = this.pp.getRecipients(wfi.apparentSender);
     for (int i = 0; i < recipients.size(); i++) {
       Participant pRecipient = recipients.elementAt(i);
       int windowNumber = this.wysiwygtm.getTrackInWhichSendersTextIsDisplayedOnRecepientsScreen(wfi.apparentSender, pRecipient);
       if (windowNumber < 0) windowNumber = 0; 
       MutableAttributeSet mas = this.sm.getStyleForRecipient(wfi.apparentSender, pRecipient);
       sendWYSIWYGTextToClient(pRecipient, windowNumber, wfi.text, mas, wfi.apparentSender.getUsername());
     } 
 
     
     String subdialogueID = "UNSET";
     try {
       subdialogueID = this.pp.getSubdialogueID(wfi.apparentSender);
     } catch (Exception e) {
       e.printStackTrace();
     } 
     this.wysiwygRTFDI.processFakeInsertedText(wfi);
   }
 
 
 
 
   
   public void processWYSIWYGTextRemoved(Participant sender, MessageWYSIWYGDocumentSyncFromClientRemove mWYSIWYGkp) {}
 
 
 
 
   
   public void processClientEvent(Participant origin, MessageClientInterfaceEvent mce) {
     this.wysiwygRTFCI.processClientEvent(origin, mce);
   }
 
 
 
   
   public void processWYSIWYGFloorRequestDEPRECATED(Participant sender, MessageWYSIWYGFloorRequest mwysiwygfr) {}
 
 
 
   
   public void processWYSIWYGFloorChangeConfirmDEPRECATED(Participant sender, MessageWYSIWYGFloorChangeConfirm mwysiwygfcc) {}
 
 
 
   
   public Vector<AttribVal> getAdditionalInformationForParticipant(Participant p) {
     return new Vector<>();
   }
 
 
 
   
   public void processButtonPress(Participant sender, MessageButtonPressFromClient mbfc) {
     super.processButtonPress(sender, mbfc);
   }
 
 
   
   public void sendWYSIWYGTextToClient(Participant recipient, int wysiwygWindownumber, String textToBeAppended, MutableAttributeSet mas, String originUsername) {
     MessageWYSIWYGAppendText mwysiwygat = new MessageWYSIWYGAppendText(wysiwygWindownumber, textToBeAppended, mas, originUsername);
     this.c.getParticipants().sendMessageToParticipant(recipient, (Message)mwysiwygat);
   }
   
   public void changeClientInterfaceToRightJustified(Participant recipient, int width, int height, long duration, int state, int numberOfWindows) {
     Dimension d = new Dimension(width, height);
     MessageChangeClientInterfaceProperties mccip = new MessageChangeClientInterfaceProperties(this.c.generateNextIDForClientDisplayConfirm(), 23, d, Long.valueOf(duration), Integer.valueOf(state), Integer.valueOf(numberOfWindows));
     this.c.getParticipants().sendMessageToParticipant(recipient, (Message)mccip);
   }
 
   
   public void changeClientInterfaceCharacterWhitelist(Participant recipient, String whitelist) {
     MessageChangeClientInterfaceProperties mccip = new MessageChangeClientInterfaceProperties(this.c.generateNextIDForClientDisplayConfirm(), 30, whitelist);
     this.c.getParticipants().sendMessageToParticipant(recipient, (Message)mccip);
   }
 }

