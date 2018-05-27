package uk.dangrew.nuts.graphics.tutorial.architecture;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FriendlyMessageGenerator {

   private static final Random SINGLETON_RANDOM = new Random();
   private static final Map< Integer, String > confirmationMessages = Collections.unmodifiableMap( 
            Stream.of( 
                     new SimpleEntry<>( 0, "Ok, got it!" ),
                     new SimpleEntry<>( 1, "Heard Chef!" ),
                     new SimpleEntry<>( 2, "Understood" ),
                     new SimpleEntry<>( 3, "No problemo" ),
                     new SimpleEntry<>( 4, "Sure thing" ),
                     new SimpleEntry<>( 5, "Aye Captain" ),
                     new SimpleEntry<>( 6, "Making sense" ),
                     new SimpleEntry<>( 7, "I'm with you" )
            ).collect( Collectors.toMap( SimpleEntry::getKey, SimpleEntry::getValue ) ) );
   
   private final Random random;
   
   public FriendlyMessageGenerator() {
      this( SINGLETON_RANDOM );
   }//End Constructor
   
   FriendlyMessageGenerator( Random random ) {
      this.random = random;
   }//End Constructor
   
   public String friendlyConfirmation(){
      int next = random.nextInt( confirmationMessages.size() );
      return confirmationMessages.get( next );
   }//End Method
   
   String friendlyConfirmationAnswer( int i ) {
      return confirmationMessages.get( i );
   }//End Method
   
   int friendlyConfirmationAnswerCount() {
      return confirmationMessages.size();
   }//End Method
   
}//End Class
