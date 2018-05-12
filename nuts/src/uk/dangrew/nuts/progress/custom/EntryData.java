package uk.dangrew.nuts.progress.custom;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class EntryData< DateT, ValueT > {

   private final TreeMap< DateT, ValueT > entries;
   private final ProgressChangedListener< DateT, ValueT > changeListener;
   
   public EntryData() {
      this( new ProgressChangedListener<>() );
   }//End Constructor
   
   public EntryData( ProgressChangedListener< DateT, ValueT > changeListener ) {
      this.entries = new TreeMap<>();
      this.changeListener = changeListener;
   }//End Constructor
   
   public Set< DateT > entries() {
      return entries.keySet();
   }//End Method
   
   public DateT first(){
      return entries.firstKey();
   }//End Method
   
   public DateT last(){
      return entries.lastKey();
   }//End Method
   
   void clear(){
      while ( !entries.isEmpty() ) {
         remove( entries.firstKey() );
      }
   }//End Method

   public ValueT entryFor( DateT timestamp ){
      return entries.get( timestamp );
   }//End Method
   
   public void record( DateT timestamp, ValueT value ) {
      if ( value == null ) {
         remove( timestamp );
      } else {
         update( timestamp, value );
      }
   }//End Method
   
   private void update( DateT timestamp, ValueT value ){
      ValueT existingValue = entryFor( timestamp );
      if ( existingValue == null ) {
         internal_put( timestamp, value );
         progressChangedListener().progressAdded( timestamp, value );
      } else if ( existingValue.equals( value ) ){
         //do nothing
      } else {
         internal_put( timestamp, value );
         progressChangedListener().progressUpdated( timestamp, value );
      }
   }//End Method
   
   private void remove( DateT timestamp ){
      ValueT existingValue = entryFor( timestamp );
      if ( existingValue != null ) {
         internal_remove( timestamp );
         progressChangedListener().progressRemoved( timestamp, existingValue );
      }
   }//End Method
   
   private void internal_put( DateT timestamp, ValueT value ) {
      entries.put( timestamp, value );
   }//End Method
   
   private void internal_remove( DateT timestamp ) {
      entries.remove( timestamp );
   }//End Method

   public NavigableMap< DateT, ValueT > range( DateT t1, DateT t2 ) {
      return entries.subMap( t1, true, t2, true );
   }//End Method
   
   public ProgressChangedListener< DateT, ValueT > progressChangedListener(){
      return changeListener;
   }//End Method
   
}//End Class
