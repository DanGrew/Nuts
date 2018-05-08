/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph.custom;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.datetime.DateTimeFormats;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveComboLocalDateProperty;
import uk.dangrew.kode.javafx.custom.ResponsiveDoubleAsTextProperty;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.graphics.graph.custom.GraphController;
import uk.dangrew.nuts.progress.weight.SystemDateRange;

public class GraphSettings extends GridPane {
   
   static final LocalDate DEFAULT_DATE_LOWER_BOUND = LocalDate.now().minusDays( 15 );
   static final LocalDate DEFAULT_DATE_UPPER_BOUND = LocalDate.now().plusDays( 15 );
   static final double DEFAULT_LOWER_BOUND = 0;
   static final double DEFAULT_UPPER_BOUND = 100;
   
   private final DateTimeFormats formats;
   private final JavaFxStyle styling;
   
   public GraphSettings( GraphController controller ) {
      this.formats = new DateTimeFormats();
      this.styling = new JavaFxStyle();
      
      styling.configureConstraintsForEvenColumns( this, 1 );
      styling.configureConstraintsForEvenRows( this, 1 );
      SystemDateRange dataRange = new SystemDateRange();
      
      getChildren().add( new PropertiesPane( "Graph Range", 
          new PropertyRowBuilder()
             .withLabelName( "Minimum Date" )
             .withBinding( new ResponsiveComboLocalDateProperty(
                         dataRange.get(),
                         DEFAULT_DATE_LOWER_BOUND,
                         ( s, o, n ) -> controller.setDateLowerBound( formats.toDayBeginningEpochSeconds( n ) )
             ) ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Date" )
               .withBinding( new ResponsiveComboLocalDateProperty(
                        dataRange.get(),
                        DEFAULT_DATE_UPPER_BOUND,
                        ( s, o, n ) -> controller.setDateUpperBound( formats.toDayBeginningEpochSeconds( n ) )
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Minimum Value" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_LOWER_BOUND, 
                        ( o, n ) -> controller.setRecordingLowerBound( n ), 
                        true 
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Value" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_UPPER_BOUND, 
                        ( o, n ) -> controller.setRecordingUpperBound( n ), 
                        true 
               ) )
      ) );
      
      controller.setDateLowerBound( formats.toDayBeginningEpochSeconds( DEFAULT_DATE_LOWER_BOUND ) );
      controller.setDateUpperBound( LocalDateTime.of( DEFAULT_DATE_UPPER_BOUND, LocalTime.MIN ).toEpochSecond( ZoneOffset.UTC ) );
      controller.setRecordingLowerBound( DEFAULT_LOWER_BOUND );
      controller.setRecordingUpperBound( DEFAULT_UPPER_BOUND );
   }//End Constructor

}//End Class
