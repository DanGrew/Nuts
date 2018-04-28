package uk.dangrew.nuts.progress.custom;

import java.util.function.BiConsumer;

public class ProgressChangedListener< TypeT > {

   private BiConsumer< TypeT, Double > whenAdded;
   private BiConsumer< TypeT, Double > whenUpdated;
   private BiConsumer< TypeT, Double > whenRemoved;
   
   public void progressAdded( TypeT timestamp, Double value ){
      if ( whenAdded != null ) {
         whenAdded.accept( timestamp, value );
      }
   }//End Method
   
   public void progressRemoved( TypeT timestamp, Double value ){
      if ( whenRemoved != null ) {
         whenRemoved.accept( timestamp, value );
      }
   }//End Method
   
   public void progressUpdated( TypeT timestamp, Double value ){
      if ( whenUpdated != null ) {
         whenUpdated.accept( timestamp, value );
      }
   }//End Method
   
   public void whenProgressAdded( BiConsumer< TypeT, Double > consumer ) {
      this.whenAdded = consumer;
   }//End Method
   
   public void whenProgressRemoved( BiConsumer< TypeT, Double > consumer ) {
      this.whenRemoved = consumer;
   }//End Method

   public void whenProgressUpdated( BiConsumer< TypeT, Double > consumer ) {
      this.whenUpdated = consumer;
   }//End Method
   
}//End Interface

