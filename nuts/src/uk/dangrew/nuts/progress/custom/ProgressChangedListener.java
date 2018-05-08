package uk.dangrew.nuts.progress.custom;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class ProgressChangedListener< TypeT > {

   private final Set< BiConsumer< TypeT, Double > > whenAdded;
   private final Set< BiConsumer< TypeT, Double > > whenUpdated;
   private final Set< BiConsumer< TypeT, Double > > whenRemoved;
   
   public ProgressChangedListener() {
      this.whenAdded = new LinkedHashSet<>();
      this.whenUpdated = new LinkedHashSet<>();
      this.whenRemoved = new LinkedHashSet<>();
   }//End Constructor
   
   public void progressAdded( TypeT timestamp, Double value ){
      whenAdded.forEach( c -> c.accept( timestamp, value ) );
   }//End Method
   
   public void progressRemoved( TypeT timestamp, Double value ){
      whenRemoved.forEach( c -> c.accept( timestamp, value ) );
   }//End Method
   
   public void progressUpdated( TypeT timestamp, Double value ){
      whenUpdated.forEach( c -> c.accept( timestamp, value ) );
   }//End Method
   
   public void whenProgressAdded( BiConsumer< TypeT, Double > consumer ) {
      this.whenAdded.add( consumer );
   }//End Method
   
   public void whenProgressRemoved( BiConsumer< TypeT, Double > consumer ) {
      this.whenRemoved.add( consumer );
   }//End Method

   public void whenProgressUpdated( BiConsumer< TypeT, Double > consumer ) {
      this.whenUpdated.add( consumer );
   }//End Method

   public void removeWhenProgressAdded( BiConsumer< TypeT, Double > consumer ) {
      this.whenAdded.remove( consumer );
   }//End Method
   
   public void removeWhenProgressUpdated( BiConsumer< TypeT, Double > consumer ) {
      this.whenUpdated.remove( consumer );
   }//End Method
   
   public void removeWhenProgressRemoved( BiConsumer< TypeT, Double > consumer ) {
      this.whenRemoved.remove( consumer );
   }//End Method
   
}//End Interface

