package uk.dangrew.nuts.graphics.graph.custom;

import javafx.util.StringConverter;
import uk.dangrew.kode.datetime.TimestampFormat;

public class GraphDateStringConverter extends StringConverter< Number > {

   private final TimestampFormat format;
   
   public GraphDateStringConverter() {
      this.format = new TimestampFormat();
   }//End Constructor
   
   @Override public String toString( Number object ) {
      return format.toTimestampString( object );
   }//End Method

   @Override public Number fromString( String string ) {
      return format.parseTimestampToEpochSeconds( string );
   }//End Method

}//End Class
