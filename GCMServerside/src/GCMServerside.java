import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GCMServerside {
	public void sendMessage() throws IOException {
		Sender sender = new Sender("AIzaSyCL9nmfHoSeR9MebvwG5cFoq7WZ5o1mFko");
		
		String regId = "APA91bGG1qG0iZsNq7EhzCLd86blUcMxmaAs0OJQ0JrIzjr7K9quqXcCluo1clG71VUsmhlttTGbi-EkWAVAAsmAnhBnCuaTCfd65kGHsWb6J8U19cTbC4cKioiUdNbeDNTaGwnWZUAR";
		
		Message message = new Message.Builder().addData("msg", "push notify").build();
	
		List<String> list = new ArrayList<String>();
		list.add(regId);
		
		MulticastResult multiResult = sender.send(message, list, 5);
	
		if(multiResult != null) {
			List<Result> resultList = multiResult.getResults();
			
			for(Result result : resultList) {
				System.out.println(result.getMessageId());
			}
		}
	}
	
	public static void main (String[] args) throws Exception {
		GCMServerside s = new GCMServerside();
		s.sendMessage();
	}

}
