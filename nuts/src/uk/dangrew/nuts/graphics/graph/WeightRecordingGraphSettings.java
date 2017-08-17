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
   static final double DEFAULT_FAT_UPPER_BOUND = 20.0;
   static final double DEFAULT_FAT_LOWER_BOUND = 15.0;
   
   /**
    * Constructs a new {@link WeightRecordingGraphSettings}.
    * @param weightController the {@link WeightRecordingGraphController} for the weight properties.
    * @param fatController the {@link WeightRecordingGraphController} for the body fat properties.
    */
   public WeightRecordingGraphSettings( 
            WeightRecordingGraphController weightController,
            WeightRecordingGraphController fatController
   ) {
      new JavaFxStyle().configureConstraintsForEvenColumns( this, 1 );
      new JavaFxStyle().configureConstraintsForEvenRows( this, 1 );
      WeightProgressDateRange dataRange = new WeightProgressDateRange();
      
      getChildren().add( new PropertiesPane( "Graph Range", 
          new PropertyRowBuilder()
             .withLabelName( "Minimum Date" )
             .withBinding( new ResponsiveComboLocalDateProperty(
                         dataRange.get(),
                         DEFAULT_DATE_LOWER_BOUND,
                         ( s, o, n ) -> {
                            weightController.setDateLowerBound( n.toEpochDay() + 0.0 );
                            fatController.setDateLowerBound( n.toEpochDay() + 0.0 );
                         }
             ) ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Date" )
               .withBinding( new ResponsiveComboLocalDateProperty(
                        dataRange.get(),
                        DEFAULT_DATE_UPPER_BOUND,
                        ( s, o, n ) -> {
                           weightController.setDateUpperBound( n.toEpochDay() + 0.0 );
                           fatController.setDateUpperBound( n.toEpochDay() + 0.0 );
                        }
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Minimum Weight" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_WEIGHT_LOWER_BOUND, 
                        ( o, n ) -> weightController.setRecordingLowerBound( n ), 
                        true 
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Weight" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_WEIGHT_UPPER_BOUND, 
                        ( o, n ) -> weightController.setRecordingUpperBound( n ), 
                        true 
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Minimum Percentage" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_FAT_LOWER_BOUND, 
                        ( o, n ) -> fatController.setRecordingLowerBound( n ), 
                        true 
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Maximum Percentage" )
               .withBinding( new ResponsiveDoubleAsTextProperty( 
                        DEFAULT_FAT_UPPER_BOUND, 
                        ( o, n ) -> fatController.setRecordingUpperBound( n ), 
                        true 
               ) )
      ) );
      
      weightController.setDateLowerBound( DEFAULT_DATE_LOWER_BOUND.toEpochDay() );
      weightController.setDateUpperBound( DEFAULT_DATE_UPPER_BOUND.toEpochDay() );
      weightController.setRecordingLowerBound( DEFAULT_WEIGHT_LOWER_BOUND );
      weightController.setRecordingUpperBound( DEFAULT_WEIGHT_UPPER_BOUND );
      
      fatController.setDateLowerBound( DEFAULT_DATE_LOWER_BOUND.toEpochDay() );
      fatController.setDateUpperBound( DEFAULT_DATE_UPPER_BOUND.toEpochDay() );
      fatController.setRecordingLowerBound( DEFAULT_FAT_LOWER_BOUND );
      fatController.setRecordingUpperBound( DEFAULT_FAT_UPPER_BOUND );
   }//End Constructor

}//End Class
