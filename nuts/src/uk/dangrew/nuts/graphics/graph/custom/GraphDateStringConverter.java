package uk.dangrew.nuts.graphics.graph.custom;

import javafx.util.StringConverter;
import uk.dangrew.kode.datetime.DateTimeFormats;

public class GraphDateStringConverter extends StringConverter< Number > {

   private final DateTimeFormats formats;
   
   public GraphDateStringConverter() {
      this.formats = new DateTimeFormats();
   }//End Constructor
   
   @Override public String toString( Number object ) {
      return formats.toTimestampString( object );
   }//End Method

   @Override public Number fromString( String string ) {
      return formats.parseTimestampToEpochSeconds( string );
   }//End Method

}//End Class
