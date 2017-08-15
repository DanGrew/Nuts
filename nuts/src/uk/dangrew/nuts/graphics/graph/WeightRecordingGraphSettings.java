/*
 * ----------------------------------------
 *      Nutrient Usage Tracking System
 * ----------------------------------------
 *          Produced by Dan Grew
 *                 2017
 * ----------------------------------------
 */
package uk.dangrew.nuts.graphics.graph;

import java.time.LocalDate;

import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveComboLocalDateProperty;
import uk.dangrew.kode.javafx.custom.ResponsiveDoubleAsTextProperty;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.progress.WeightProgressDateRange;

/**
 * {@link WeightRecordingGraphSettings} is responsible for providing settings and configuration
 * for the {@link WeightRecordingGraph}.
 */
public class WeightRecordingGraphSettings extends GridPane {
   
   static final LocalDate DEFAULT_DATE_LOWER_BOUND = LocalDate.of( 2017, 7, 25 );
   static final LocalDate DEFAULT_DATE_UPPER_BOUND = LocalDate.now().plusDays( 3 );
   static final double DEFAULT_WEIGHT_LOWER_BOUND = 160;
   static final double DEFAULT_WEIGHT_UPPER_BOUND = 200;
   
   /**
    * Constructs a new {@link WeightRecordingGraphSettings}.
    * @param controller the {@link WeightRecordingGraphController}.
    */
   public WeightRecordingGraphSettings( WeightRecordingGraphController controller ) {
      new JavaFxStyle().configureConstraintsForEvenColumns( this, 1 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );
      WeightProgressDateRange dataRange = new WeightProgressDateRange();
      
      getChildren().add( new PropertiesPane( "Graph Range", 
          new PropertyRowBuilder()
             .withLabelName( "Minimum Date" )
             .withBinding( 
                      new ResponsiveComboLocalDateProperty(
                               dataRange.get(),
                               DEFAULT_DATE_LOWER_BOUND,
                               ( s, o, n ) -> controller.setDateLowerBound( n.toEpochDay() + 0.0 ) 
                      ) 
             ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Date" )
               .withBinding( 
                        new ResponsiveComboLocalDateProperty(
                                 dataRange.get(),
                                 DEFAULT_DATE_UPPER_BOUND,
                                 ( s, o, n ) -> controller.setDateUpperBound( n.toEpochDay() + 0.0 ) 
                        ) 
               ),
         new PropertyRowBuilder()
               .withLabelName( "Minimum Weight" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_WEIGHT_LOWER_BOUND, ( o, n ) -> controller.setWeightLowerBound( n ), true 
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Weight" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_WEIGHT_UPPER_BOUND, ( o, n ) -> controller.setWeightUpperBound( n ), true 
               ) )
      ) );
      
      controller.setDateLowerBound( DEFAULT_DATE_LOWER_BOUND.toEpochDay() );
      controller.setDateUpperBound( DEFAULT_DATE_UPPER_BOUND.toEpochDay() );
      controller.setWeightLowerBound( DEFAULT_WEIGHT_LOWER_BOUND );
      controller.setWeightUpperBound( DEFAULT_WEIGHT_UPPER_BOUND );
   }//End Constructor

}//End Class
