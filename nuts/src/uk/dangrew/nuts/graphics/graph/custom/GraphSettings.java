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

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import uk.dangrew.kode.javafx.custom.PropertiesPane;
import uk.dangrew.kode.javafx.custom.ResponsiveButtonRegion;
import uk.dangrew.kode.javafx.custom.ResponsiveComboProperty;
import uk.dangrew.kode.javafx.custom.ResponsiveDoubleAsTextProperty;
import uk.dangrew.kode.javafx.custom.ResponsiveTextRegion;
import uk.dangrew.kode.javafx.style.JavaFxStyle;
import uk.dangrew.kode.javafx.style.PropertyRowBuilder;
import uk.dangrew.nuts.graphics.common.DateTimeTextBox;

public class GraphSettings extends GridPane {
   
   static final TimestampPeriod DEFAULT_OUTLOOK = TimestampPeriod.TenDays;
   static final double DEFAULT_LOWER_BOUND = 0;
   static final double DEFAULT_UPPER_BOUND = 100;
   
   private final JavaFxStyle styling;
   
   private final GraphController controller;
   private final DateTimeTextBox horizontalAxisFocusField;
   private final ComboBox< TimestampPeriod > timestampPeriodBox;
   
   public GraphSettings( GraphController controller ) {
      this.controller = controller;
      this.styling = new JavaFxStyle();
      this.styling.configureConstraintsForEvenColumns( this, 1 );
      this.styling.configureConstraintsForEvenRows( this, 1 );
      
      this.horizontalAxisFocusField = new DateTimeTextBox();
      this.timestampPeriodBox = new ComboBox<>();
      this.timestampPeriodBox.getItems().addAll( TimestampPeriod.values() );
      
      getChildren().add( new PropertiesPane( "Graph Range", 
          new PropertyRowBuilder()
             .withLabelName( "Timestamp Mid Point" )
             .withBinding( new ResponsiveTextRegion(
                         horizontalAxisFocusField,
                         ( s, o, n ) -> updateHorizontalRange()
             ) ),
         new PropertyRowBuilder()
               .withLabelName( "Period Outlook" )
               .withBinding( new ResponsiveComboProperty<>(
                        timestampPeriodBox,
                        DEFAULT_OUTLOOK,
                        ( s, o, n ) -> updateHorizontalRange()
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
               ) ),
         new PropertyRowBuilder()
               .withLabelName( "Auto Scale Horizontal" )
               .withBinding( new ResponsiveButtonRegion( new Button( "Apply" ), e -> controller.autoScaleHorizontal() 
         ) ),
         new PropertyRowBuilder()
               .withLabelName( "Auto Scale Vertical" )
               .withBinding( new ResponsiveButtonRegion( new Button( "Apply" ), e -> controller.autoScaleVertical() 
         ) )
      ) );
      
      controller.setRecordingLowerBound( DEFAULT_LOWER_BOUND );
      controller.setRecordingUpperBound( DEFAULT_UPPER_BOUND );
      horizontalAxisFocusField.resetInputToNow();
      updateHorizontalRange();
   }//End Constructor
   
   private void updateHorizontalRange(){
      controller.focusHorizontalAxisOn( 
               horizontalAxisFocusField.getTextValue(), 
               timestampPeriodBox.getSelectionModel().getSelectedItem() 
      );
   }//End Method

}//End Class
