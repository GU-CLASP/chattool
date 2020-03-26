package diet.server.ConversationController.WYSIWYGTube;

import diet.message.MessageWYSIWYGDocumentSyncFromClientInsert;
import diet.server.ConversationController.Dyadic_WYSIWYGInterface_Manipulation;
import diet.server.ConversationController.WYSIWYGTube.Content.TubeFakeInsertedText;
import diet.server.ConversationController.ui.CustomDialog;
import diet.server.Participant;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
 

public class TubeIn_InsertingFakeText_Balloon_Why extends Thread implements WYSIWYGTube{
    TubeOut to;
    Hashtable<Participant, Vector> htEachParticipantsText = new Hashtable<>();

    public String[] targets = new String[] { "doctor", "pilot", "woman", "child", "mozart", "mother", "researcher", "scientist", "teacher", "prodigy", "Tom", "Susie", "Nick", "Heather" };
    public String[] queries = new String[] { " why? ", " what do you mean? " };
   
    public int interventionMinPreInterventionGap = 100;
    public int interventionMaxPreInterventionGap = 300;
    public int interventionMinIntraWordGap = 20;
    public int interventionMaxIntraWordGap = 200;
    public int interventionMinInterWordGap = 50;
    public int interventionMaxInterWordGap = 400;
    public int postInterventionMaxDelayBetweenCharactersWhenFlushingBuffer = 70;
    public int postInterventionMinDelayBetweenCharactersWhenFlushingBuffer = 50;

    public Participant mostRecentSender = null;
    public long timeOfMostRecentTurnChange = (new Date()).getTime();
    long timeOfLastTrigger = (new Date()).getTime();
    long minGapBtwnInter = 1000L;
    long minGapSinceTurnStart = 2000L;
   
    Dyadic_WYSIWYGInterface_Manipulation dw;
 
    Random r;
    
	 
    public void doSetup() {
	this.interventionMinPreInterventionGap = CustomDialog.getInteger("Fake turn:\nWhat is minumum response time?\n(Milliseconds after detecting target)", this.interventionMinPreInterventionGap);
        this.interventionMaxPreInterventionGap = CustomDialog.getInteger("Fake turn:\nWhat is maximum response time?\n(Milliseconds after detecting target)", this.interventionMaxPreInterventionGap);
     
        this.interventionMinIntraWordGap = CustomDialog.getInteger("Fake turn:\nWhat is minumum gap (millisecs) between characters? (Within words)", this.interventionMinIntraWordGap);
	this.interventionMaxIntraWordGap = CustomDialog.getInteger("Fake turn:\nWhat is maximum gap (millisecs) between characters? (Within words)", this.interventionMaxIntraWordGap);
     
	this.interventionMinInterWordGap = CustomDialog.getInteger("Fake turn:\nWhat is minumum gap (millisecs) between words?", this.interventionMinIntraWordGap);
	this.interventionMaxInterWordGap = CustomDialog.getInteger("Fake turn:\nWhat is maximum gap (millisecs) between words?", this.interventionMaxIntraWordGap);
     
	this.interventionMinInterWordGap = CustomDialog.getInteger("After the intervention there might be text from participants that is buffered\nWhat is minumum gap between characters?", this.interventionMinInterWordGap);
	this.interventionMaxInterWordGap = CustomDialog.getInteger("After the intervention there might be text from participants that is buffered\nWhat is maximum gap between characters?", this.interventionMaxInterWordGap);
     
	this.to.setMaxDelayBetweenCharactersWhenFlushingBufferPostIntervention(this.postInterventionMaxDelayBetweenCharactersWhenFlushingBuffer);
	this.to.setMinDelayBetweenCharactersWhenFlushingBufferPostIntervention(this.postInterventionMinDelayBetweenCharactersWhenFlushingBuffer);
    }
 
    public synchronized void add(Participant sender, MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert) {
	this.to.add(sender, mWYSIWYGInsert);
    
	if (sender != this.mostRecentSender) {
            this.mostRecentSender = sender;
            this.timeOfMostRecentTurnChange = (new Date()).getTime();
	} 
     
	Vector<MessageWYSIWYGDocumentSyncFromClientInsert> participantsText = this.htEachParticipantsText.get(sender);
	if (participantsText == null) {
            participantsText = new Vector();
            this.htEachParticipantsText.put(sender, participantsText);
	} 
	participantsText.add(mWYSIWYGInsert);
	detectTarget(sender);
      	notifyAll();
    }
   
    public long timeSinceLastTurnChange() {
	return (new Date()).getTime() - this.timeOfMostRecentTurnChange;
    }
   
    public TubeIn_InsertingFakeText_Balloon_Why(Dyadic_WYSIWYGInterface_Manipulation dw) { 
        this.r = new Random(); 
        this.dw = dw; 
        this.to = new TubeOut(dw); 
        boolean dosetup = CustomDialog.getBoolean("Do you want to customize the intervention settings?", "Customize", "Use default");
	if(dosetup)this.doSetup();
            this.start(); 
	} 
	
	public void addFakeText(Participant fakeSender, String text, int minPreInterventionGap, int maxPreInterventionGap, int minIntraWordGap, int maxIntraWordGap, int minInterWordGap, int maxInterWordGap) { 
            if (text == null) return;  
            if (text.equalsIgnoreCase("")) return;
            
            long gap = 0L;
            String charc = "" + text.charAt(0);
            gap = minPreInterventionGap + this.r.nextInt(maxPreInterventionGap - minPreInterventionGap);
            TubeFakeInsertedText wfi = new TubeFakeInsertedText(fakeSender, charc);
            wfi.delayBeforeSending = gap;
            this.to.addFakeTurn(wfi);
 
	for (int i = 1; i < text.length(); i++) {
            charc = "" + text.charAt(i);
            gap = 0L;
            try {
		if (!charc.equalsIgnoreCase(" ")) {
                    gap = minIntraWordGap + this.r.nextInt(maxIntraWordGap - minIntraWordGap);
		} else {
                    gap = minInterWordGap + this.r.nextInt(maxInterWordGap - minInterWordGap);
		} 
            } catch (Exception e) {
		gap = 0L;
		e.printStackTrace();
            } 
       
            wfi = new TubeFakeInsertedText(fakeSender, charc);
            wfi.delayBeforeSending = gap;
            this.to.addFakeTurn(wfi);
	}  
    }
 
    public void detectTarget(Participant sender) {
	Vector<MessageWYSIWYGDocumentSyncFromClientInsert> participantsText = this.htEachParticipantsText.get(sender);
	if (participantsText == null)return;  
	String previousText = ""; 
        int i;
	for (i = participantsText.size() - 1; i >= 0 && i >= participantsText.size() - 100; i--) {
            MessageWYSIWYGDocumentSyncFromClientInsert mWYSIWYGInsert = participantsText.elementAt(i);
            if (mWYSIWYGInsert.getTextTyped().equalsIgnoreCase(" ")) break;  
            previousText = mWYSIWYGInsert.getTextTyped() + previousText;
        }			 
     
	for (i = 0; i < this.targets.length; i++) {
            String target = this.targets[i];
            if (previousText.endsWith(target)) {
		String query = generateQuery(target);
		Participant fakesender = this.dw.pp.getRecipients(sender).elementAt(0);
		if ((new Date()).getTime() - this.timeOfLastTrigger > this.minGapBtwnInter && timeSinceLastTurnChange() > this.minGapSinceTurnStart) {
                    addFakeText(fakesender, query, this.interventionMinPreInterventionGap, this.interventionMaxPreInterventionGap, this.interventionMinIntraWordGap, this.interventionMaxIntraWordGap, this.interventionMinInterWordGap, this.interventionMaxInterWordGap);
                    this.timeOfLastTrigger = (new Date()).getTime();
		} 
            System.err.println("PREVIOUS TEXT IS NONNULL:" + previousText);
            } 
	} 
    }
 
    
 
    public String generateQuery(String target) {
	Random r = new Random();
        int rand = r.nextInt(4);
	if (rand < 1){
            return target + "? "; 
        } else if (rand < 2){
            return target + "? why? "; 
        } else if (rand < 3) {
            return "why " + target + "? ";
	}
        return this.queries[r.nextInt(this.queries.length)];
    }
   
    public void addIntervention() {}

}
