package uk.dangrew.nuts.progress.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import uk.dangrew.nuts.system.Concept;
import uk.dangrew.nuts.system.Properties;

public class ProgressSeries implements Concept {

   private final Properties properties;
   private final TreeMap< LocalDateTime, Double > entries;
   private final ProgressChangedListener< LocalDateTime > changeListener;
   
   public ProgressSeries( String name ) {
      this( new Properties( name ), new ProgressChangedListener<>() );
   }//End Constructor
   
   public ProgressSeries( String id, String name ) {
      this( new Properties( id, name ), new ProgressChangedListener<>() );
   }//End Constructor
   
   ProgressSeries( Properties properties, ProgressChangedListener< LocalDateTime > changeListener ) {
      this.properties = properties;
      this.entries = new TreeMap<>();
      this.changeListener = changeListener;
   }//End Constructor
   
   @Override public Properties properties(){
      return properties;
   }//End Method
   
   @Override public Concept duplicate( String referenceId ) {
      return this;
   }//End Method
   
   public Set< LocalDateTime > entries() {
      return entries.keySet();
   }//End Method
   
   public LocalDateTime first(){
      return entries.firstKey();
   }//End Method
   
   public LocalDateTime last(){
      return entries.lastKey();
   }//End Method
   
   public Double entryFor( LocalDateTime timestamp ){
      return entries.get( timestamp );
   }//End Method
   
   public ProgressChangedListener< LocalDateTime > progressChangedListener(){
      return changeListener;
   }//End Method

   public void record( LocalDateTime timestamp, Double value ) {
      if ( value == null ) {
         remove( timestamp );
      } else {
         update( timestamp, value );
      }
   }//End Method
   
   private void update( LocalDateTime timestamp, Double value ){
      Double existingValue = entryFor( timestamp );
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
   
   private void remove( LocalDateTime timestamp ){
      Double existingValue = entryFor( timestamp );
      if ( existingValue != null ) {
         internal_remove( timestamp );
         progressChangedListener().progressRemoved( timestamp, existingValue );
      }
   }//End Method
   
   private void internal_put( LocalDateTime timestamp, Double value ) {
      entries.put( timestamp, value );
   }//End Method
   
   private void internal_remove( LocalDateTime timestamp ) {
      entries.remove( timestamp );
   }//End Method

   public NavigableMap< LocalDateTime, Double > range( LocalDateTime t1, LocalDateTime t2 ) {
      return entries.subMap( t1, true, t2, true );
   }//End Method
   
   public NavigableMap< LocalDateTime, Double > range( LocalDate t1 ) {
      return range( LocalDateTime.of( t1, LocalTime.MIN ), LocalDateTime.of( t1, LocalTime.MAX ) );
   }//End Method
   
}//End Class
