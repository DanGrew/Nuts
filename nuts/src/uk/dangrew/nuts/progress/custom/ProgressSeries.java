package uk.dangrew.nuts.progress.custom;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.dangrew.kode.concept.Concept;
import uk.dangrew.kode.concept.Properties;

public class ProgressSeries implements Concept {

   private final Properties properties;
   private final SeriesValues values;
   private final SeriesText headers;
   private final SeriesText notes;
   
   public ProgressSeries( String name ) {
      this( new Properties( name ) );
   }//End Constructor
   
   public ProgressSeries( String id, String name ) {
      this( new Properties( id, name ) );
   }//End Constructor
   
   ProgressSeries( Properties properties ) {
      this.properties = properties;
      this.values = new SeriesValues();
      this.headers = new SeriesText();
      this.notes = new SeriesText();
   }//End Constructor
   
   @Override public Properties properties(){
      return properties;
   }//End Method
   
   @Override public Concept duplicate() {
      return this;
   }//End Method
   
   public Set< LocalDateTime > entries() {
      return Stream.of( values.entries(), headers.entries(), notes.entries() )
               .flatMap( Collection::stream )
               .collect( Collectors.toCollection( TreeSet::new ) );
   }//End Method
   
   public SeriesValues values(){
      return values;
   }//End Method
   
   public SeriesText headers(){
      return headers;
   }//End Method
   
   public SeriesText notes(){
      return notes;
   }//End Method
   
   public void clear(){
      values().clear();
      headers().clear();
      notes().clear();
   }//End Method
   
}//End Class
