package uk.dangrew.nuts.progress.custom;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class ProgressChangedListener< DateT, ValueT > {

   private final Set< BiConsumer< DateT, ValueT > > whenAdded;
   private final Set< BiConsumer< DateT, ValueT > > whenUpdated;
   private final Set< BiConsumer< DateT, ValueT > > whenRemoved;
   
   public ProgressChangedListener() {
      this.whenAdded = new LinkedHashSet<>();
      this.whenUpdated = new LinkedHashSet<>();
      this.whenRemoved = new LinkedHashSet<>();
   }//End Constructor
   
   public void progressAdded( DateT timestamp, ValueT value ){
      whenAdded.forEach( c -> c.accept( timestamp, value ) );
   }//End Method
   
   public void progressRemoved( DateT timestamp, ValueT value ){
      whenRemoved.forEach( c -> c.accept( timestamp, value ) );
   }//End Method
   
   public void progressUpdated( DateT timestamp, ValueT value ){
      whenUpdated.forEach( c -> c.accept( timestamp, value ) );
   }//End Method
   
   public void whenProgressAdded( BiConsumer< DateT, ValueT > consumer ) {
      this.whenAdded.add( consumer );
   }//End Method
   
   public void whenProgressRemoved( BiConsumer< DateT, ValueT > consumer ) {
      this.whenRemoved.add( consumer );
   }//End Method

   public void whenProgressUpdated( BiConsumer< DateT, ValueT > consumer ) {
      this.whenUpdated.add( consumer );
   }//End Method

   public void removeWhenProgressAdded( BiConsumer< DateT, ValueT > consumer ) {
      this.whenAdded.remove( consumer );
   }//End Method
   
   public void removeWhenProgressUpdated( BiConsumer< DateT, ValueT > consumer ) {
      this.whenUpdated.remove( consumer );
   }//End Method
   
   public void removeWhenProgressRemoved( BiConsumer< DateT, ValueT > consumer ) {
      this.whenRemoved.remove( consumer );
   }//End Method
   
}//End Interface

