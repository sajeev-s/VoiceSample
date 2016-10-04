import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;


public class LiveSpeech2 {

	
	public static void main(final String[] args) throws IOException {
		 final Configuration configuration = new Configuration();

	        configuration
	                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
	        configuration
	                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
	        configuration
	                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		
		final LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
		// Start recognition process pruning previously cached data.
		recognizer.startRecognition(true);
		
		while (true) {
			final SpeechResult result = recognizer.getResult();
			if(result!=null) {
				System.out.println(result.getHypothesis());
			}
		}
		// Pause recognition process. It can be resumed then with startRecognition(false).
//		recognizer.stopRecognition();
	}
}
